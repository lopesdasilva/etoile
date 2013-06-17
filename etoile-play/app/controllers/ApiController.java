package controllers;


import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import models.Comment;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;


import flexjson.JSONSerializer;


import models.Blog;
import models.Student;
import models.User;
import models.manytomany.Usertest;
import models.module.Lesson;
import models.module.Module;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionGroup;


import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sun.misc.BASE64Decoder;


public class ApiController extends Controller {


    public static Result getModules() {

        List<Module> obj = Module.find.all();
        JSONSerializer postDetailsSerializer = new JSONSerializer().include("contents").exclude("*.class");
        System.out.println("Class: ApiController; Method: getModules; Modules size: " + obj.size());
        return ok(postDetailsSerializer.serialize(obj)).as("application/json");


    }

    public static Result getNews() {
        List<Blog> obj = Blog.find.all();
        JSONSerializer postDetailsSerializer = new JSONSerializer().exclude("*.class");
        System.out.println("Class: ApiController; Method: getNews; News size: " + obj.size());
        return ok(postDetailsSerializer.serialize(obj)).as("application/json");
    }

    public static Result getDashboard() throws IOException {

        String auth = request().getHeader(AUTHORIZATION);
        if (auth != null) {

            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];

            if (User.authenticateSHA1(username, password) != null) {

                User user = User.findByEmail(username);
                JSONSerializer postDetailsSerializer = new JSONSerializer().include("modules", "onGoingTests").exclude("password", "*.class");
//			getOngoingTests
                System.out.println("Class: ApiController; Method: getDashboard; User: " + username);
                return ok(postDetailsSerializer.serialize(user)).as("application/json");

            } else {
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                System.out.println("Class: ApiController; Method: getDashboard; Failed wrong user or password.");
                return ok(result).as("application/json");
            }

        }
        System.out.println("Class: ApiController; Method: getDashboard; Request not JSON");
        return badRequest("Expecting Json data.");
    }

    public static Result authenticate() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Class: ApiController; Method: autheticate; Request not JSON");
            return badRequest("Expecting Json data.");
        } else {
            String username = json.findPath("username").getTextValue();
            String password = json.findPath("password").getTextValue();
            if (username == null || password == null) {
                System.out.println("Class: ApiController; Method: autheicate; Password or Username not sent");
                return badRequest("Missing parameteres");
            } else if (User.authenticateSHA1(username, password) != null) {
                ObjectNode result = Json.newObject();
                result.put("status", "success");
                result.put("message", "all good!");
                System.out.println("Class: ApiController; Method: autheticate; User: " + username);
                return ok(result).as("application/json");
            } else {
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                result.put("error", "failed authentication");
                System.out.println("Class: ApiController; Method: autheticate; Wrong user or password");
                return ok(result).as("application/json");
            }

        }


    }

    public static Result getModule(String module_acronym) throws IOException {

        String auth = request().getHeader(AUTHORIZATION);
        if (auth != null) {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            Module module = Module.findByAcronym(module_acronym);
            if (User.authenticateSHA1(username, password) != null && module != null) {


                JSONSerializer postDetailsSerializer = new JSONSerializer().include(
                        "professors",
                        "lessons",
                        "contents",
                        "bibliography",
                        "categories",
                        "language")
                        .exclude("professors.user", "*.class");
                System.out.println("Class: ApiController; Method: getModule; Module: " + module.acronym);
                return ok(postDetailsSerializer.serialize(module)).as("application/json");

            } else {
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                System.out.println("Class: ApiController; Method: getModule; wrong user or password");
                return ok(result).as("application/json");
            }
        }
        System.out.println("Class: ApiController; Method: getModule; Request not JSON");
        return badRequest("Expecting Json data.");
    }

    public static Result getLesson(String module_acronym, String lesson_acronym) throws IOException {
        String auth = request().getHeader(AUTHORIZATION);
        if (auth != null) {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            Module module = Module.findByAcronym(module_acronym);
            if (User.authenticateSHA1(username, password) != null && module != null) {
                Lesson lesson = Lesson.findByAcronym(lesson_acronym);

                System.out.println("tests size: " + lesson.tests.size());
                Usertest aux = null;


                lesson.tests.removeAll(lesson.tests);
                for (Test test : Test.findByLessonId(lesson.id)) {
                    if (test.published) {
                        test.users.removeAll(test.users);
                        if (Usertest.findByUserAndTest(username, test.id) != null) {
                            Usertest utest = Usertest.findByUserAndTest(username, test.id);
                            test.users.add(utest);

                        }
                        lesson.tests.add(test);
                    }


                }


                if (lesson != null && module.lessons.contains(lesson)) {

                    JSONSerializer postDetailsSerializer = new JSONSerializer().include(
                            "tests",
                            "lessoncontents",
                            "lessonalerts",
                            "tests.users")
                            .exclude(
                                    "module",
                                    "tests.users.user",
                                    "tests.markersLimitDate",
                                    "tests.markers_limit_date",
                                    "test.published",
                                    "*.class");
                    System.out.println("Class: ApiController; Method: getLesson; Lesson: " + lesson.acronym);
                    return ok(postDetailsSerializer.serialize(lesson)).as("application/json");
                }

            } else {
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                System.out.println("Class: ApiController; Method: getLesson; wrong user or password");
                return ok(result).as("application/json");
            }
        }
        System.out.println("Class: ApiController; Method: getLesson; Request not JSON");
        return badRequest("Expecting Json data.");
    }

    public static Result getTest(String module_acronym, String lesson_acronym, Long test_id) throws IOException {

        String auth = request().getHeader(AUTHORIZATION);
        if (auth != null) {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            Module module = Module.findByAcronym(module_acronym);
            if (User.authenticateSHA1(username, password) != null && module != null) {
                Lesson lesson = Lesson.findByAcronym(lesson_acronym);
                if (lesson != null && module.lessons.contains(lesson)) {
                    Test test = Test.find.byId(test_id);
                    if (test != null && lesson.tests.contains(test)) {
                        JSONSerializer postDetailsSerializer = new JSONSerializer().include(
                                "test.groups",
                                "test.groups.questions",
                                "test.groups.questions.hypothesislist")
                                .exclude(
                                        "lesson",
                                        "user",
                                        "test.lesson",
                                        "test.groups.questions.lesson",
                                        "test.groups.questions.user",
                                        "test.groups.questions.answerSuggestedByStudent",
                                        "test.groups.questions.isCopy",
                                        "test.groups.questions.hypothesislist.user",
                                        "test.groups.questions.hypothesislist.isCorrect",
                                        "test.groups.questions.hypothesislist.isSaved",
                                        "test.groups.questions.hypothesislist.questionImageURL",
                                        "test.groups.questions.openanswer.isSaved",
                                        "test.groups.questions.openanswer.answerMarker",
                                        "test.groups.questions.openanswer.questionevaluation",
                                        "*.class");
                        Usertest userTest = Usertest.findByUserAndTest(username, test_id);

                        for (QuestionGroup group : userTest.test.groups) {
                            for (Question question : group.questions) {
                                switch (question.typeOfQuestion) {
                                    case 0:
                                        question.openanswer = Answer.findByUserTestAndQuestion(userTest.id, question.id);
                                        break;
                                    case 1:
                                        question.hypothesislist = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                        break;
                                    case 2:
                                        question.hypothesislist = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                        break;
                                }

                            }
                        }

                        if (userTest == null) {
                            //TODO create new userTest
                        }
                        System.out.println("Class: ApiController; Method: getTest; test: " + test.id);
                        return ok(postDetailsSerializer.serialize(userTest)).as("application/json");
                    }
                }
            } else {
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                System.out.println("Class: ApiController; Method: getTest; wrong user or password");
                return ok(result).as("application/json");
            }
        }
        System.out.println("Class: ApiController; Method: getTest; Request not JSON");
        return badRequest("Expecting Json data.");
    }

    public static Result saveQuestion() throws IOException {
        JsonNode json = request().body().asJson();
        String auth = request().getHeader(AUTHORIZATION);
        if (json == null || auth == null) {
            System.out.println("Class: ApiController; Method: saveQuestion; Request not JSON");
            return badRequest("Expecting Json data.");
        } else {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            if (User.authenticateSHA1(username, password) != null) {


                long answer_id = json.findPath("answer_id").asLong();
                String answerText = json.findPath("answer_text").getTextValue();
                Answer answer = Answer.find.byId(answer_id);
                if (answer != null && username.equals(answer.usertest.user.email)) {
                    if (!answer.isSaved) {
                        changeTestProgress((long) answer.usertest.id, username);
                        answer.usertest.refresh();
                        System.out.println("Test Progress: " + answer.usertest.progress);
                    }
                    answer.answer = answerText;
                    answer.isSaved = true;
                    answer.save();

                    System.out.println("Class: ApiController; Method: saveanswer Answer: " + answer_id + " saved text: " + answerText);

                    ObjectNode result = Json.newObject();
                    result.put("status", "success");
                    result.put("message", "saved");
                    result.put("test_progress", "" + answer.usertest.progress);
                    return ok(result).as("application/json");
                } else {
                    ObjectNode result = Json.newObject();
                    result.put("status", "failure");
                    result.put("error", "failed saving answer not owned");
                    System.out.println("Class: ApiController; Method: saveanswer user does not own this answer or answer does not exists");
                    return ok(result).as("application/json");
                }


            } else {
                System.out.println("Class: ApiController; Method: saveQuestion; authentication failed");
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                result.put("error", "failed authentication");
                return ok(result).as("application/json");
            }
        }

    }

    public static void changeTestProgress(Long usertest_id, String user_email) {
        int totalNumQuestions = 0;
        Usertest userTest = Usertest.find.byId(usertest_id);
        Test t = Test.find.byId(userTest.test.id);
        for (QuestionGroup g : t.groups) {
            totalNumQuestions += g.questions.size();
        }

        float progress = userTest.progress + 100 / totalNumQuestions;
        userTest.progress = progress;
        if (userTest.progress == 99) {
            userTest.progress = progress + 1;

        }
        userTest.save();
    }

    public static Result saveMutipleAnswer() throws IOException {
        JsonNode json = request().body().asJson();
        String auth = request().getHeader(AUTHORIZATION);
        if (json == null || auth == null) {
            System.out.println("Class: ApiController; Method: saveMutipleAnswer; Request not JSON");
            return badRequest("Expecting Json data.");
        } else {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            if (User.authenticateSHA1(username, password) != null) {
                JsonNode hyp_list = json.get("hypothesislist");

                Iterator<JsonNode> a = hyp_list.getElements();


                while (a.hasNext()) {

                    JsonNode b = a.next();
                    Long id = b.findPath("id").asLong();
                    boolean selected = b.findPath("selected").asBoolean();

                    Hypothesis h = Hypothesis.find.byId(id);
                    if (h.user.email.equals(username)) {
                        System.out.println("Class: ApiController; Method: saveMutipleAnswer; hyp_id: " + id);
                        h.selected = selected;
                        h.save();
                    } else {

                        ObjectNode result = Json.newObject();
                        result.put("status", "failure");
                        result.put("error", "failed saving answer not owned");
                        System.out.println("Class: ApiController; Method: saveMutipleAnswer user does not own this hyp or hyp does not exists");
                        return ok(result).as("application/json");
                    }
                }
                System.out.println("Class: ApiController; Method: saveMutipleAnswer; saved");
                ObjectNode result = Json.newObject();
                result.put("status", "success");
                result.put("message", "saved");
                return ok(result).as("application/json");


            } else {
                System.out.println("Class: ApiController; Method: saveMutipleAnswer; authentication failed");
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                result.put("error", "failed authentication");
                return ok(result).as("application/json");
            }
        }
    }

    public static Result getListOfURLs(Long question_id) throws IOException {
        String auth = request().getHeader(AUTHORIZATION);
        if (auth != null) {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            Question question = Question.find.byId(question_id);
            if (User.authenticateSHA1(username, password) != null) {
                if (question != null) {
                    System.out.println("question found");
                    JSONSerializer postDetailsSerializer = new JSONSerializer().exclude("*.class", "question", "user", "test", "studentProfile", "added");
                    System.out.println("Class: ApiController; Method: getListOfURLs; Question: " + question.id);
                    return ok(postDetailsSerializer.serialize(question.urls)).as("application/json");


                }
            } else {
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                System.out.println("Class: ApiController; Method: getTest; wrong user or password");
                return ok(result).as("application/json");
            }
        }

        System.out.println("Class: ApiController; Method: getListOfURLs; Request not JSON");
        return badRequest("Expecting Json data.");

    }

    public static Result likeURL(Long url_id) throws IOException {
        String auth = request().getHeader(AUTHORIZATION);
        if (auth == null) {
            System.out.println("Class: ApiController; Method: likeURL; Request not JSON");
            return badRequest("Expecting Json data.");
        } else {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            if (User.authenticateSHA1(username, password) != null) {
                User user = User.findByEmail(username);
                models.test.question.URL url = models.test.question.URL.find.byId(url_id);

                if (url != null) {
                    if (url.voters.contains(user)) {
                        ObjectNode result = Json.newObject();
                        result.put("status", "failure");
                        result.put("message", "vote denied - already voted");
                        result.put("url_likes", url.likes);
                        return ok(result).as("application/json");
                    } else {
                        url.likes = url.likes + 1;
                        url.voters.add(user);
                        url.save();
                        ObjectNode result = Json.newObject();
                        result.put("status", "success");
                        result.put("message", "voted");
                        result.put("url_likes", url.likes);
                        return ok(result).as("application/json");
                    }
                } else {
                    ObjectNode result = Json.newObject();
                    result.put("status", "failure");
                    result.put("message", "invalid url");
                    return ok(result).as("application/json");
                }


            } else {
                System.out.println("Class: ApiController; Method: likeURL; authentication failed");
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                result.put("error", "failed authentication");
                return ok(result).as("application/json");
            }
        }
    }

    public static Result commentNews(Long blog_id) throws IOException {

        JsonNode json = request().body().asJson();
        String auth = request().getHeader(AUTHORIZATION);
        if (json == null || auth == null) {
            System.out.println("Class: ApiController; Method: saveQuestion; Request not JSON");
            return badRequest("Expecting Json data.");
        } else {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            if (User.authenticateSHA1(username, password) != null) {

                Blog blog = Blog.find.byId(blog_id);
                if (blog != null) {
                    String blog_comment = json.findPath("comment").getTextValue();
                    Comment comment = new Comment();
                    comment.blog = blog;
                    comment.text = blog_comment;
                    comment.user = User.findByEmail(username);
                    comment.save();

                    System.out.println("Class: ApiController; Method: commentNews; blog_id:" + blog_id + " Comment added: " + blog_comment + " user:" + username);
                    ObjectNode result = Json.newObject();
                    result.put("status", "success");
                    return ok(result).as("application/json");


                } else {
                    System.out.println("Class: ApiController; Method: commentNews; Blog does not exists");
                    ObjectNode result = Json.newObject();
                    result.put("status", "failure");
                    result.put("error", "Blog does not exists");
                    return ok(result).as("application/json");
                }

            } else {
                System.out.println("Class: ApiController; Method: commentNews; authentication failed");
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                result.put("error", "failed authentication");
                return ok(result).as("application/json");
            }
        }

    }

}
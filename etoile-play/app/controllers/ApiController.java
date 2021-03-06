package controllers;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import models.*;
import models.curriculum.Category;
import models.curriculum.Curriculumlesson;
import models.curriculum.Curriculummodule;
import models.curriculum.Curriculumtopic;
import models.module.Lessoncontent;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;


import flexjson.JSONSerializer;


import models.manytomany.Usertest;
import models.module.Lesson;
import models.module.Module;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionEvaluation;
import models.test.question.QuestionGroup;
import models.test.question.URL;


import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sun.misc.BASE64Decoder;

import javax.persistence.OrderBy;


public class ApiController extends Controller {


    public static Result getModules() {

        List <Module> final_list = new LinkedList<Module>();
        List<Module> obj = Module.find.all();
        JSONSerializer postDetailsSerializer = new JSONSerializer().include("contents").exclude("*.class");

        for(Module m: obj){
          if(m.published){
            final_list.add(m);
          }
        }

        System.out.println("Class: ApiController; Method: getModules; Modules size: " + final_list.size());
        return ok(postDetailsSerializer.serialize(final_list)).as("application/json");

    }

    public static Result getNews() {

        List<Blog> obj = Blog.find.orderBy().desc("date").findList();



        JSONSerializer postDetailsSerializer = new JSONSerializer().exclude("*.class");
        System.out.println("Class: ApiController; Method: getNews; News size: " + obj.size());
        return ok(postDetailsSerializer.serialize(obj)).as("application/json");
    }

    public static Result getNewsComments(Long blog_id) {
        Blog blog = Blog.find.byId(blog_id);
        JSONSerializer postDetailsSerializer = new JSONSerializer().exclude("blog").exclude("*.class");
        System.out.println("Class: ApiController; Method: getNewsComment; Comments Size: " + blog.comments.size());
        return ok(postDetailsSerializer.serialize(blog.comments)).as("application/json");
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


                        if (userTest == null) {
                            if (test.suggestquestionrequired) {
                                ObjectNode result = Json.newObject();
                                result.put("status", "failure");
                                result.put("error", "Test requires suggestion");
                                System.out.println("Class: ApiController; Method: getTest;test requires suggestion");
                                return ok(result).as("application/json");
                            } else {
                                User user = User.findByEmail(username);
                                //signup in test
                                Usertest user_test = new Usertest();
                                user_test.user = user;
                                user_test.test = test;
                                user_test.expired = false;
                                user_test.inmodule = false;
                                user_test.submitted = false;
                                user_test.save();
                                user.tests.add(user_test);
                                test.users.add(user_test);
                                user.save();
                                test.save();
                                user_test.save();

                                for (QuestionGroup group : test.groups) {
                                    for (Question question : group.questions) {
                                        if (question.subtopic != null) {
                                            SubtopicReputation subtopicreputation = SubtopicReputation.findByUserAndTopic(user.email, question.subtopic.id);
                                            if (subtopicreputation == null) {
                                                SubtopicReputation subtopicreputationuser = new SubtopicReputation();
                                                subtopicreputationuser.subtopic = question.subtopic;
                                                subtopicreputationuser.user = user;
                                                subtopicreputationuser.reputationAsAMarker = new Long(0);
                                                subtopicreputationuser.reputationAsAStudent = new Long(0);
                                                subtopicreputationuser.save();
                                            }
                                        }
                                    }

                                }

                                Usertest usertest = Usertest.findByUserAndTest(user.email, test_id);
                                usertest.inmodule = true;
                                usertest.save();

                                userTest = Usertest.findByUserAndTest(username, test_id);

                                if (userTest.answers.isEmpty()) {
                                    System.out.println("There is no answers creating.");
                                    for (QuestionGroup g : test.groups) {
                                        for (Question q : g.questions) {
                                            if (q.typeOfQuestion == 0) {
                                                Answer empty_answer = new Answer();
                                                empty_answer.answer = "No answer.";
                                                empty_answer.openQuestion = q;
                                                empty_answer.test = test;
                                                empty_answer.usertest = usertest;
                                                empty_answer.group = g;
                                                empty_answer.openQuestion = q;
                                                empty_answer.save();
                                                test.answers.add(empty_answer);
                                                test.save();
                                            }
                                        }
                                    }
                                }

                                for (QuestionGroup group : userTest.test.groups) {
                                    for (Question question : group.questions) {
                                        switch (question.typeOfQuestion) {
                                            case 0:
                                                question.openanswer = Answer.findByUserTestAndQuestion(userTest.id, question.id);
                                                break;
                                            case 1:
                                                question.hypothesislist = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                List<Hypothesis> hypothesis_aux = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                if (hypothesis_aux.size() < 1) {
                                                    for (Hypothesis h : Hypothesis.findByQuestion(question.id)) {
                                                        Hypothesis new_h = new Hypothesis();
                                                        new_h.number = h.number;
                                                        new_h.isSaved = false;
                                                        new_h.selected = false;
                                                        new_h.question = h.question;
                                                        new_h.text = h.text;
                                                        new_h.user = user;
                                                        new_h.isCorrect = h.isCorrect;
                                                        new_h.save();
                                                    }
                                                    hypothesis_aux = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                }
                                                question.hypothesislist = hypothesis_aux;
                                                break;
                                            case 2:
                                                question.hypothesislist = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                List<Hypothesis> hypothesis_aux2 = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                if (hypothesis_aux2.size() < 1) {
                                                    for (Hypothesis h : Hypothesis.findByQuestion(question.id)) {
                                                        Hypothesis new_h = new Hypothesis();
                                                        new_h.number = h.number;
                                                        new_h.question = h.question;
                                                        new_h.text = h.text;
                                                        new_h.user = user;
                                                        new_h.isCorrect = h.isCorrect;
                                                        new_h.save();
                                                    }
                                                    hypothesis_aux2 = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                }
                                                question.hypothesislist = hypothesis_aux2;
                                                break;
                                        }

                                    }
                                }


                                System.out.println("Class: ApiController; Method: getTest; test: " + test.id);
                                return ok(postDetailsSerializer.serialize(userTest)).as("application/json");
                            }
                        } else {

                            if (userTest.answers.isEmpty()) {
                                System.out.println("There is no answers creating.");
                                for (QuestionGroup g : test.groups) {
                                    for (Question q : g.questions) {
                                        if (q.typeOfQuestion == 0) {
                                            Answer empty_answer = new Answer();
                                            empty_answer.answer = "No answer.";
                                            empty_answer.openQuestion = q;
                                            empty_answer.test = test;
                                            empty_answer.usertest = userTest;
                                            empty_answer.group = g;
                                            empty_answer.openQuestion = q;
                                            empty_answer.save();
                                            test.answers.add(empty_answer);
                                            test.save();
                                        }
                                    }
                                }

                                User user = User.findByEmail(username);
                                for (QuestionGroup group : userTest.test.groups) {
                                    for (Question question : group.questions) {
                                        switch (question.typeOfQuestion) {
                                            case 0:
                                                question.openanswer = Answer.findByUserTestAndQuestion(userTest.id, question.id);
                                                break;
                                            case 1:
                                                question.hypothesislist = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                List<Hypothesis> hypothesis_aux = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                if (hypothesis_aux.size() < 1) {
                                                    for (Hypothesis h : Hypothesis.findByQuestion(question.id)) {
                                                        Hypothesis new_h = new Hypothesis();
                                                        new_h.number = h.number;
                                                        new_h.question = h.question;
                                                        new_h.text = h.text;
                                                        new_h.user = user;
                                                        new_h.isCorrect = h.isCorrect;
                                                        new_h.save();
                                                    }
                                                    hypothesis_aux = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                }
                                                question.hypothesislist = hypothesis_aux;
                                                break;
                                            case 2:
                                                question.hypothesislist = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                List<Hypothesis> hypothesis_aux2 = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                if (hypothesis_aux2.size() < 1) {
                                                    for (Hypothesis h : Hypothesis.findByQuestion(question.id)) {
                                                        Hypothesis new_h = new Hypothesis();
                                                        new_h.number = h.number;
                                                        new_h.question = h.question;
                                                        new_h.text = h.text;
                                                        new_h.user = user;
                                                        new_h.isCorrect = h.isCorrect;
                                                        new_h.save();
                                                    }
                                                    hypothesis_aux2 = Hypothesis.findByUserEmailAndQuestion(username, question.id);
                                                }
                                                question.hypothesislist = hypothesis_aux2;
                                                break;
                                        }

                                    }
                                }


                            }


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
                            System.out.println("Class: ApiController; Method: getTest; test: " + test.id);
                            return ok(postDetailsSerializer.serialize(userTest)).as("application/json");
                        }
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

    public static Result register() throws IOException {
        System.out.println("register");
        JsonNode json = request().body().asJson();
        System.out.println(json.asText());
        if (json == null) {
            System.out.println("Class: ApiController; Method: saveQuestion; Request not JSON");
            return badRequest("Expecting Json data.");
        } else {

            String username = json.findPath("username").getTextValue();
            String password = json.findPath("password").getTextValue();
            String email = json.findPath("email").getTextValue();
            String name = json.findPath("name").getTextValue();
            boolean male = json.findPath("male").asBoolean();
            String university = json.findPath("university").getTextValue();
            String scientific_domain = json.findPath("scientific_domain").getTextValue();
            String country = json.findPath("country").getTextValue();

            if (User.findByEmail(email) == null) {
                Student student = new Student();
                student.male = male;
                student.acronym = username;
                student.firstname = name;
                student.imageURL = "";
                student.contact = email;
                student.save();

                User user = new User();
                user.username = username;
                user.password = password;
                user.email = email;
                user.name = name;
                user.country = country;
                user.olduser = true;
                user.studentProfile = student;
                user.studentProfile.acronym = username;
                user.olduser = true;
                user.save();

                student.user = user;
                student.save();

                ObjectNode result = Json.newObject();
                result.put("status", "success");
                result.put("message", "created");
                return ok(result).as("application/json");
            } else {
                ObjectNode result = Json.newObject();
                result.put("status", "failed");
                result.put("message", "email already used");
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


    public static Result saveMutipleAnswer(Long usertest_id) throws IOException {
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

                    List<Hypothesis> hypothesis_list = Hypothesis.findByUserEmailAndQuestion(username, h.question.id);
                    if (hypothesis_list.size() > 0) {
                        if (!hypothesis_list.get(0).isSaved) {
                            changeTestProgress(usertest_id, username);
                        }
                        for (Hypothesis h_aux : hypothesis_list) {
                            h_aux.isSaved = true;
                            h_aux.save();
                        }
                    }


                }
                System.out.println("Class: ApiController; Method: saveMutipleAnswer; saved");
                ObjectNode result = Json.newObject();
                result.put("status", "success");
                result.put("message", "saved");
                result.put("test_progress", "" + Usertest.find.byId(usertest_id).progress);
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

        System.out.println("Class: ApiController; Method: getListOfTopURLs; Request not JSON");
        return badRequest("Expecting Json data.");

    }

    public static Result getListOfTopURLs(Long question_id) throws IOException {
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
                    System.out.println("Class: ApiController; Method: getListOfTopURLs; Question: " + question.id);
                    return ok(postDetailsSerializer.serialize(question.getTopUrls(question_id))).as("application/json");


                }
            } else {
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                System.out.println("Class: ApiController; Method: getListOfTopURLs; wrong user or password");
                return ok(result).as("application/json");
            }
        }

        System.out.println("Class: ApiController; Method: getListOfURLs; Request not JSON");
        return badRequest("Expecting Json data.");

    }

    public static Result addURL(Long question_id) throws IOException {

        JsonNode json = request().body().asJson();
        String auth = request().getHeader(AUTHORIZATION);
        if (json == null || auth == null) {
            System.out.println("Class: ApiController; Method: addURL; Request not JSON");
            return badRequest("Expecting Json data.");
        } else {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            if (User.authenticateSHA1(username, password) != null) {

                Question question = Question.find.byId(question_id);
                if (question != null) {
                    String address = json.findPath("address").getTextValue();
                    String description = json.findPath("description").getTextValue();
                    String title = json.findPath("title").getTextValue();
                    String image_url = json.findPath("image_url").getTextValue();
                    System.out.println("title:" + title);

                    User user = User.find.byId(username);


                    URL url = new URL();
                    url.description = description;
                    url.name = title;
                    url.imageURL = image_url;
                    url.adress = address;
                    url.question = question;
                    url.user = user;
                    url.likes = 0;
                    url.added = new DateTime();
                    url.save();


                    System.out.println("URL: " + url.adress);
                    System.out.println("URL: " + url.description);
                    System.out.println("URL: " + url.name);
                    System.out.println("URL: " + url.imageURL);

                    System.out.println("Class: ApiController; Method: addURL; question_id:" + question_id + " url_added: " + address + " user:" + username);
                    ObjectNode result = Json.newObject();
                    result.put("status", "success");
                    return ok(result).as("application/json");


                } else {
                    System.out.println("Class: ApiController; Method: addURL; Question does not exists");
                    ObjectNode result = Json.newObject();
                    result.put("status", "failure");
                    result.put("error", "Question does not exists");
                    return ok(result).as("application/json");
                }

            } else {
                System.out.println("Class: ApiController; Method: addURL; authentication failed");
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                result.put("error", "failed authentication");
                return ok(result).as("application/json");
            }
        }

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


    public static Result signinTest(Long test_id) throws IOException {
        JsonNode json = request().body().asJson();
        String auth = request().getHeader(AUTHORIZATION);
        if (json == null || auth == null) {
            System.out.println("Class: ApiController; Method: signinTest; Request not JSON");
            return badRequest("Expecting Json data; Auth failed");
        } else {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            if (User.authenticateSHA1(username, password) != null) {
                Test test = Test.find.byId(test_id);
                User user = User.findByEmail(username);
                if (test != null && user.modules.contains(test.lesson.module)) {
                    String suggestedQuestion = json.findPath("suggested_question").getTextValue();
                    String suggestedAnswer = json.findPath("suggested_answer").getTextValue();


                    //signup in test
                    Usertest user_test = new Usertest();
                    user_test.user = user;
                    user_test.test = test;
                    user_test.expired = false;
                    user_test.inmodule = false;
                    user_test.submitted = false;
                    user_test.save();
                    user.tests.add(user_test);
                    test.users.add(user_test);
                    user.save();
                    test.save();
                    user_test.save();

                    for (QuestionGroup group : test.groups) {
                        for (Question question : group.questions) {
                            if (question.subtopic != null) {
                                SubtopicReputation subtopicreputation = SubtopicReputation.findByUserAndTopic(user.email, question.subtopic.id);
                                if (subtopicreputation == null) {
                                    SubtopicReputation subtopicreputationuser = new SubtopicReputation();
                                    subtopicreputationuser.subtopic = question.subtopic;
                                    subtopicreputationuser.user = user;
                                    subtopicreputationuser.reputationAsAMarker = new Long(0);
                                    subtopicreputationuser.reputationAsAStudent = new Long(0);
                                    subtopicreputationuser.save();
                                }
                            }
                        }

                    }

                    Usertest usertest = Usertest.findByUserAndTest(user.email, test_id);
                    usertest.inmodule = true;
                    usertest.save();

                    Question new_question = new Question();
                    new_question.question = suggestedQuestion;
                    new_question.answerSuggestedByStudent = suggestedAnswer;
                    new_question.lesson = test.lesson;
                    new_question.user = user;
                    new_question.save();
                    test.lesson.save();


                    return getTest(test.lesson.module.acronym, test.lesson.acronym, test_id);


                } else {
                    System.out.println("Class: ApiController; Method: signinTest; authentication failed");
                    ObjectNode result = Json.newObject();
                    result.put("status", "failure");
                    result.put("error", "test not found");
                    return ok(result).as("application/json");
                }


            } else {
                System.out.println("Class: ApiController; Method: signinTest; authentication failed");
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                result.put("error", "failed authentication");
                return ok(result).as("application/json");
            }
        }

    }

    public static Result submitTest(Long usertest_id) throws IOException {

        String auth = request().getHeader(AUTHORIZATION);
        if (auth == null) {
            System.out.println("Class: ApiController; Method: submitTest; Authentication failed");
            return badRequest("Authentication failed");
        } else {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            if (User.authenticateSHA1(username, password) != null) {


                Usertest usertest = Usertest.find.byId(usertest_id);

                if (usertest.submitted) {
                    System.out.println("Class: ApiController; Method: submitTest");
                    ObjectNode result = Json.newObject();
                    result.put("status", "failure");
                    result.put("error", "test already submitted");
                    return ok(result).as("application/json");
                }
                usertest.user.refresh();
                System.out.println(usertest.user.email);
                System.out.println(username);
                if (usertest.user.email.equals(username)) {
                    Test test = usertest.test;
                    //CORRIGIR TESTE
                    int reputation = 0;
                    for (QuestionGroup g : test.groups) {
                        for (Question q : g.questions) {
                            boolean answered = false;
                            boolean bool = true;
                            List<Hypothesis> hypothesis_aux = Hypothesis.findByUserEmailAndQuestion(username, q.id);
                            for (Hypothesis h : hypothesis_aux) {
                                if (h.selected) {
                                    answered = true;
                                }
                            }
                            if (answered) {

                                if (q.typeOfQuestion == 1) {
                                    List<Hypothesis> hypothesis = Hypothesis.findByUserEmailAndQuestion(username, q.id);
                                    for (Hypothesis h : hypothesis) {
                                        if ((h.isCorrect && !h.selected) || (!h.isCorrect && h.selected) && bool) {
                                            bool = false;
                                        }
                                    }

                                    if (bool) {
                                        reputation = reputation + q.weight;
                                    } else {
                                        reputation = reputation - q.weightToLose;
                                    }
                                } else if (q.typeOfQuestion == 2) {
                                    List<Hypothesis> hypothesis = Hypothesis.findByUserEmailAndQuestion(username, q.id);
                                    if (hypothesis.size() != 0) {
                                        for (Hypothesis h : hypothesis) {
                                            if ((h.isCorrect && !h.selected) || (!h.isCorrect && h.selected)) {
                                                bool = false;
                                            }
                                        }
                                    } else {
                                        bool = false;
                                    }
                                    if (bool) {
                                        reputation = reputation + q.weight;
                                    } else {
                                        reputation = reputation - q.weightToLose;
                                    }


                                }

                                usertest.reputationAsAstudent = reputation;
                                usertest.inmodule = false;
                                usertest.save();
                            }
                            if (q.typeOfQuestion == 1 || q.typeOfQuestion == 2) {
                                QuestionEvaluation qe;
                                if (QuestionEvaluation.findByUserAndQuestion(usertest.id, q.id) == null) {
                                    qe = new QuestionEvaluation();
                                } else {
                                    qe = QuestionEvaluation.findByUserAndQuestion(usertest.id, q.id);
                                }
                                if (bool && answered) {
                                    qe.isCorrect = true;
                                    qe.score = q.weight;
                                } else if (!bool && answered) {
                                    qe.score = -q.weightToLose;
                                } else {
                                    qe.score = 0;
                                }

                                qe.usertest = usertest;
                                qe.question = q;
                                qe.save();
                            }

                        }

                    }


                    usertest.reviewd = false;
                    usertest.reputationAsAstudent = reputation;
                    usertest.inmodule = false;
                    usertest.submitted = true;
                    usertest.save();

                    ObjectNode result = Json.newObject();
                    result.put("status", "success");
                    result.put("message", "test submited");
                    return ok(result).as("application/json");


                } else {
                    System.out.println("Class: ApiController; Method: submitTest; wrong usertest");
                    return badRequest("Expecting Json data.");
                }


            } else {
                System.out.println("Class: ApiController; Method: submitTest; authentication failed");
                ObjectNode result = Json.newObject();
                result.put("status", "failure");
                result.put("error", "failed authentication");
                return ok(result).as("application/json");
            }
        }
    }


    public static Result getModuleForum(String module_acronym) {
        Module module = Module.findByAcronym(module_acronym);
        JSONSerializer postDetailsSerializer = new JSONSerializer().exclude("*.class").exclude("forum").include("replies").exclude("replies.user.ongoingTests").exclude("starter").exclude("replies.user.password");
        System.out.println("Class: ApiController; Method: getForum; Topics size: " + module.forum.topics.size());
        return ok(postDetailsSerializer.serialize(module.forum.topics)).as("application/json");
    }

    public static Result getCurriculumDendrogram() {
        List<Category> categories = Category.find.all();

        ObjectNode result = Json.newObject();
        result.put("name", "Curriculum");
        ArrayNode arrayNode = result.putArray("children");

        for (Category c : categories) {
            ObjectNode row = Json.newObject();
            row.put("name", c.name);
            arrayNode.add(row);

            ArrayNode arrayNode_CurriculumModules = row.putArray("children");
            for (Curriculummodule cm : c.curriculummodules) {
                ObjectNode row_cm = Json.newObject();
                row_cm.put("name", cm.name);
                arrayNode_CurriculumModules.add(row_cm);

                ArrayNode arrayNode_CurriculumLessons = row_cm.putArray("children");
                for (Curriculumlesson cl : cm.curriculumlessons) {
                    ObjectNode row_cl = Json.newObject();
                    row_cl.put("name", cl.name);
                    arrayNode_CurriculumLessons.add(row_cl);

                    ArrayNode arrayNode_CurriculumTopics = row_cl.putArray("children");
                    for (Curriculumtopic ct : cl.curriculumtopics) {
                        ObjectNode row_ct = Json.newObject();
                        row_ct.put("name", ct.text);
                        arrayNode_CurriculumTopics.add(row_ct);
                    }
                }
            }
        }


        return ok(result).as("application/json");
    }

    public static Result getModuleDendrogram(String module_acronym) {
        System.out.println("getModuleDendrogram: " + module_acronym);
        Module module = Module.findByAcronym(module_acronym);

        System.out.println("Module: " + module.name);
        ObjectNode result = Json.newObject();
        result.put("name", module.name);
        ArrayNode arrayNode = result.putArray("children");

        for (Lesson lesson : module.lessons) {
            ObjectNode row = Json.newObject();
            row.put("name", lesson.name);
            arrayNode.add(row);

            ArrayNode arrayNode_lessonResources = row.putArray("children");
            for (Lessoncontent lc : lesson.lessoncontents) {
                ObjectNode row_lc = Json.newObject();
                row_lc.put("name", lc.name);
                arrayNode_lessonResources.add(row_lc);
            }
        }

        return ok(result).as("application/json");

    }

    public static Result voteCurriculumResource() throws IOException {

        //TODO: Check if user can vote and check if resource exists and actually vote, after ruben make is stuff



        JsonNode json = request().body().asJson();
        String auth = request().getHeader(AUTHORIZATION);

        if (json == null || auth == null) {
            System.out.println("Class: ApiController; Method: signinTest; Request not JSON");
            return badRequest("Expecting Json data; Auth failed");
        } else {
            auth = auth.substring(6);
            byte[] decodeAuth = new BASE64Decoder().decodeBuffer(auth);
            String[] credString = new String(decodeAuth, "UTF-8").split(":");

            String username = credString[0];
            String password = credString[1];
            if (User.authenticateSHA1(username, password) != null) {
                User user = User.findByEmail(username);

               Long id=json.findPath("id").asLong();


                 Curriculumtopic resource=Curriculumtopic.find.byId(id);
                ObjectNode result = Json.newObject();

                //TODO: Make a actual if clause
                if (resource!=null && !resource.voters.contains(user)) {
                    //VOTING
                    resource.likes+=1;
                    resource.voters.add(user);
                    resource.save();

                    resource.user.commitmentReputation++;
                    resource.user.save();

                    result.put("status", "success");
                    result.put("message", "voted");
                    result.put("resource_likes", resource.likes);
                    return ok(result).as("application/json");
                } else {

                    if (resource==null) {
                        result.put("message", "Resource does not exist");
                    }
                    else if (resource.voters.contains(user)) {
                        result.put("message", "You already voted on this resource");
                        result.put("resource_likes", resource.likes);
                    }
                    result.put("status", "failed");

                    return ok(result).as("application/json");
                }

            }

        }
        System.out.println("Class: ApiController; Method: voteCurriculumResource; authentication failed");
        ObjectNode result = Json.newObject();
        result.put("status", "failure");
        result.put("error", "failed authentication");
        return ok(result).as("application/json");
    }

}
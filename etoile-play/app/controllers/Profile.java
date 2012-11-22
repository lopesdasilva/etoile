package controllers;


import java.util.LinkedList;
import java.util.List;

import controllers.Application.Login;

import models.Blog;
import models.curriculum.Category;
import models.Comment;


import models.User;
import models.course.Course;
import models.course.Lesson;
import models.course.University;
import models.manytomany.UserTest;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.OneChoiceAnswer;
import models.test.OneChoiceQuestion;
import models.test.OpenQuestion;
import models.test.Test;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.collection.mutable.ArrayLike;
import views.html.*;

/**
 * Manage Profile related operations.
 */
@Security.Authenticated(Secured.class)
public class Profile extends Controller {
	
	
	/**
     * Describes the comment form.
     */
	public static class Comment{
		
		public String comment;
	}
	
public static class QuestionAnswer{
		
		public String qanswer;
	}

public static class OneChoiceQuestionAnswer{
	
	public Long ocqanswer;
}

public static class MultipleChoiceQuestionAnswer{
	
	public List<Long> mcqanswers;
}

public static class OpenQuestionSuggestion{
	
	public String openquestionsuggestion;
	
}
	
	
	/**
     * Display the dashboard.
     */
    public static Result index() {
    	List<Blog> blogs = Blog.getAllBlogs();
    	User user=User.find.byId(request().username());
    	List<Category> categories = Category.getAllCategories();
    	
    	
    	//This is to load Universities(weird)
    	for (Course c:user.courses){
    		System.out.println(c.university.name);	
    	}
    	 
        return ok(home.render(user,blogs,categories) );
    }
    
    
    public static Result course(String course_acronym){
    	Course course = Course.findByAcronym(course_acronym);
    	User user=User.find.byId(session("email"));
    	List<Category> categories = Category.getAllCategories();
    	
    	
    	if(!user.courses.contains(course))
    	return ok(views.html.secured.courseGeneral.render(user,categories,course));
    	else
    	//Has this course
    	return ok(views.html.secured.course.render(user,categories,course));

    	
    }
    public static Result signupcourse(String course_acronym){
    	Course course = Course.findByAcronym(course_acronym);
    	User user=User.find.byId(session("email"));
    	List<Category> categories = Category.getAllCategories();
    	
    	//Trying to add the course to user
    	
    	if(!user.courses.contains(course)){
    	user.courses.add(course);
    	user.save();
    	course.save();	
    	
    	for(Lesson lesson: course.lessons){
    		for(Test test: lesson.tests){
    			UserTest usertest = UserTest.findByUserAndTest(user.email, test.id);
    	    	if(usertest==null){
        		UserTest user_test = new UserTest();
        		user_test.user = user;
        		user_test.test = test;
        		user_test.expired = false;
        		user_test.incourse = false;
        		user_test.submited = false;
        		user_test.save();
        		user.tests.add(user_test);
        		test.users.add(user_test);
        		user.save();
        		test.save();
        		user_test.save();
        	}
    		}
    	}
    	}

    	//Has this course
    	return ok(views.html.secured.course.render(user,categories,course));

    	
    }
    
    public static Result addquestion(Long test_id, String lesson_acronym, String course_acronym){
    	User user = User.find.byId(request().username());
    	//Test test = models.test.Test.find.byId(test_id);
    	UserTest usertest = UserTest.findByUserAndTest(user.email, test_id);
    	usertest.incourse = true;
    	usertest.save();

    	
    	List<Category> categories = Category.getAllCategories();
    	Course course=Course.findByAcronym(course_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	

    	
    	Form<Profile.OpenQuestionSuggestion> form = form(Profile.OpenQuestionSuggestion.class).bindFromRequest();

    	System.out.println("MODULESIZE: "+lesson.questions.size());
    	OpenQuestion new_question = new OpenQuestion();
    	new_question.question = form.get().openquestionsuggestion;
    	new_question.lesson = lesson;
    	new_question.user = user;
    	new_question.questionImageURL = "http://2.bp.blogspot.com/_n9nhDiNysbI/TTgaGiOpZGI/AAAAAAAAANo/eWv-c-7041I/s1600/ponto-interrogacao-21.jpg";
    	new_question.save();
    	lesson.save();
    	System.out.println("MODULESIZE2: "+lesson.questions.size());

    	
    	return ok(views.html.secured.lesson.render(user,categories,lesson,course,form(OpenQuestionSuggestion.class)));
    }
    
    public static Result test(Long test_id, String lesson_acronym, String course_acronym){
    	List<Category> categories = Category.getAllCategories();
    	Test test = models.test.Test.find.byId(test_id);
    	User user = User.find.byId(request().username());
    	List<Answer> answers = Answer.findByUserEmailAndTestId(user.email, test_id);
    	List<OneChoiceAnswer> onechoiceanswers = OneChoiceAnswer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;
    	

    	
    	test_aux.answers = answers;
    	test_aux.onechoiceanswers = onechoiceanswers;
    	if(test_aux.answers.isEmpty()){
    		for(OpenQuestion openquestion: test_aux.openquestions){
    			Answer emptyAnswer = new Answer();
    			emptyAnswer.answer = "";
    			emptyAnswer.openQuestion = openquestion;
    			emptyAnswer.test=test;
    			emptyAnswer.user = user;
    			emptyAnswer.save();
    			test.answers.add(emptyAnswer);
    			test.save();
    		}
    	}
    	
    	if(test_aux.onechoiceanswers.isEmpty()){
    		System.out.println("Entrei");
    		for(OneChoiceQuestion onechoicequestion: test_aux.onechoicequestions){
    			OneChoiceAnswer emptyAnswer = new OneChoiceAnswer();
    			emptyAnswer.oneChoiceQuestion = onechoicequestion;
    			long  a = 1;
    			emptyAnswer.hypothesis = Hypothesis.find.byId(a);
    			emptyAnswer.test=test;
    			emptyAnswer.user = user;
    			emptyAnswer.save();
    			test.onechoiceanswers.add(emptyAnswer);
    			test.save();
    		}
    	}
    	test_aux=test;
    	System.out.println("SIZE"+test_aux.onechoiceanswers.size());
    	
    	Course course = Course.findByAcronym(course_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	
		return ok(views.html.secured.test.render(user,course,lesson,categories,test_aux,form(QuestionAnswer.class),form(OneChoiceQuestionAnswer.class)));
    	
    }
    
    public static Result lesson(String lesson_acronym, String course_acronym){
    	
    	List<Category> categories = Category.getAllCategories();
    	Course course=Course.findByAcronym(course_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	User user=User.find.byId(request().username());
    	


    	return ok(views.html.secured.lesson.render(user,categories,lesson,course,form(OpenQuestionSuggestion.class)));

    	
    }
    
    public static Result courses() {
		List<Course> allCourses = Course.getAllCourses();
		List<Category> categories = Category.getAllCategories();
		
		//check this line
		User user=User.find.byId(session("email"));
		return ok(views.html.secured.courses.render(user,allCourses,categories));
	}
    
    public static Result contact() {
		List<Category> categories = Category.getAllCategories();
		
		//check this line
		User user=User.find.byId(session("email"));
		return ok(views.html.secured.contact.render(user,categories));
	}
    
    public static Result about() {
		List<Category> categories = Category.getAllCategories();
		
		//check this line
		User user=User.find.byId(session("email"));
		return ok(views.html.secured.about.render(user,categories));
	}
    
	public static Result blog(Long blog){
		List<Category> categories = Category.getAllCategories();
		
		//check this line
		User user=User.find.byId(session("email"));
			return ok(views.html.secured.blog.render(user,Blog.find.byId(blog),categories,form(Comment.class)));
		}
	
	public static Result postcomment(Long blog){
		 Form<Profile.Comment> form = form(Profile.Comment.class).bindFromRequest();
		 List<Category> categories = Category.getAllCategories();
		 User user=User.find.byId(session("email"));
		 	
		//New Comment
		models.Comment c=new models.Comment();
		c.text=form.get().comment;
		c.blog=Blog.find.byId(blog);
		c.user=user;
		c.save();

		return ok(views.html.secured.blog.render(user,Blog.find.byId(blog),categories,form(Comment.class)));
	}
	
	public static Result postquestionanswer(String course_acronym, String lesson_acronym, Long test_id, Long question_id){
		Form<Profile.QuestionAnswer> form = form(Profile.QuestionAnswer.class).bindFromRequest();
		System.out.println("Answer: " + form.get().qanswer + "Question: " +question_id);
		
		OpenQuestion question = OpenQuestion.find.byId(question_id);
		Test test = Test.find.byId(test_id);
		
		Answer userAnswer = Answer.findByUserAndQuestion(question_id, request().username());
		if(userAnswer==null){
		
		Answer answer = new Answer();
		answer.answer = form.get().qanswer;
		answer.openQuestion = question;
		answer.test = test;
		answer.user = User.find.byId(request().username());
		answer.save();

		
		}else{
			userAnswer.answer = form.get().qanswer;
			userAnswer.save();
		}
		
		List<Category> categories = Category.getAllCategories();
    	User user = User.find.byId(request().username());
    	List<OneChoiceAnswer> onechoiceanswers = OneChoiceAnswer.findByUserEmailAndTestId(user.email, test_id);
    	List<Answer> answers = Answer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;
    	test_aux.onechoiceanswers = onechoiceanswers;
    	test_aux.answers = answers;
    	
    	Course course = Course.findByAcronym(course_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	
    	
		return ok(views.html.secured.test.render(user,course,lesson,categories,test_aux,form(QuestionAnswer.class), form(OneChoiceQuestionAnswer.class)));
		

	}
	
	public static Result postonechoicequestionanswer(String course_acronym, String lesson_acronym, Long test_id, Long question_id){
		Form<Profile.OneChoiceQuestionAnswer> form = form(Profile.OneChoiceQuestionAnswer.class).bindFromRequest();
		System.out.println("One Choice: " + form.get().ocqanswer);
		
		OneChoiceQuestion question = OneChoiceQuestion.find.byId(question_id);
		Test test = Test.find.byId(test_id);
		
		OneChoiceAnswer userAnswer = OneChoiceAnswer.findByUserAndQuestion(question_id, request().username());
		
		if(userAnswer == null){
			userAnswer = new OneChoiceAnswer();
			userAnswer.oneChoiceQuestion = question;
			userAnswer.test = test;
			userAnswer.user = User.find.byId(request().username());
			userAnswer.save();
		}
		User user = User.find.byId(request().username());
		List<Answer> openanswers = Answer.findByUserEmailAndTestId(user.email, test_id);
		
		Hypothesis answer = Hypothesis.find.byId(form.get().ocqanswer);
		
		userAnswer.hypothesis = answer;
		userAnswer.save();
		
		List<Category> categories = Category.getAllCategories();
    	List<OneChoiceAnswer> onechoiceanswers = OneChoiceAnswer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;
    	test_aux.answers = openanswers;
    	test_aux.onechoiceanswers = onechoiceanswers;
    	
    	Course course = Course.findByAcronym(course_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	
    	
		return ok(views.html.secured.test.render(user,course,lesson,categories,test_aux,form(QuestionAnswer.class), form(OneChoiceQuestionAnswer.class)));
	}
	
	public static Result postmultiplechoicequestionanswer(String course_acronym, String module_acronym, Long test_id, Long question_id){
		System.out.println("Save - MultipleChoiceQuestion");
		Form<Profile.MultipleChoiceQuestionAnswer> form = form(Profile.MultipleChoiceQuestionAnswer.class).bindFromRequest();
		
		return null;
	}
    
    
    
// -- Queries
    
    public static Model.Finder<Long,Profile> find = new Model.Finder(Long.class, Profile.class);
    
    /**
     * Check if a user is a member of this project
     */
    public static boolean isMember(Long project, String user) {
        return find.where()
            .eq("members.email", user)
            .eq("id", project)
            .findRowCount() > 0;
    } 
}

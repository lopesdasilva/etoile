package controllers;


import java.util.LinkedList;
import java.util.List;

import controllers.Application.Login;

import models.Blog;
import models.curriculum.Category;
import models.Comment;


import models.User;
import models.manytomany.UserTest;
import models.module.Module;
import models.module.Lesson;
import models.module.University;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
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
	
	public int[] mcqanswers = new int[4];
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
    	for (Module c:user.modules){
    		System.out.println(c.university.name);	
    	}
    	 
        return ok(home.render(user,blogs,categories) );
    }
    
    
    public static Result module(String module_acronym){
    	Module module = Module.findByAcronym(module_acronym);
    	User user=User.find.byId(session("email"));
    	List<Category> categories = Category.getAllCategories();
    	
    	
    	if(!user.modules.contains(module))
    	return ok(views.html.secured.moduleGeneral.render(user,categories,module));
    	else
    	//Has this module
    	return ok(views.html.secured.module.render(user,categories,module));

    	
    }
    public static Result signupmodule(String module_acronym){
    	Module module = Module.findByAcronym(module_acronym);
    	User user=User.find.byId(session("email"));
    	List<Category> categories = Category.getAllCategories();
    	
    	//Trying to add the module to user
    	
    	if(!user.modules.contains(module)){
    	user.modules.add(module);
    	user.save();
    	module.save();	
    	
    	for(Lesson lesson: module.lessons){
    		for(Test test: lesson.tests){
    			UserTest usertest = UserTest.findByUserAndTest(user.email, test.id);
    	    	if(usertest==null){
        		UserTest user_test = new UserTest();
        		user_test.user = user;
        		user_test.test = test;
        		user_test.expired = false;
        		user_test.inmodule = false;
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

    	//Has this module
    	return ok(views.html.secured.module.render(user,categories,module));

    	
    }
    
    public static Result addquestion(Long test_id, String lesson_acronym, String module_acronym){
    	User user = User.find.byId(request().username());
    	//Test test = models.test.Test.find.byId(test_id);
    	UserTest usertest = UserTest.findByUserAndTest(user.email, test_id);
    	usertest.inmodule = true;
    	usertest.save();

    	
    	List<Category> categories = Category.getAllCategories();
    	Module module=Module.findByAcronym(module_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	

    	
    	Form<Profile.OpenQuestionSuggestion> form = form(Profile.OpenQuestionSuggestion.class).bindFromRequest();

    	System.out.println("MODULESIZE: "+lesson.questions.size());
    	Question new_question = new Question();
    	new_question.question = form.get().openquestionsuggestion;
    	new_question.lesson = lesson;
    	new_question.user = user;
    	new_question.imageURL = "http://2.bp.blogspot.com/_n9nhDiNysbI/TTgaGiOpZGI/AAAAAAAAANo/eWv-c-7041I/s1600/ponto-interrogacao-21.jpg";
    	new_question.save();
    	lesson.save();
    	System.out.println("MODULESIZE2: "+lesson.questions.size());

    	
    	return ok(views.html.secured.lesson.render(user,categories,lesson,module,form(OpenQuestionSuggestion.class)));
    }
    
    public static Result test(Long test_id, String lesson_acronym, String module_acronym){
    	List<Category> categories = Category.getAllCategories();
    	Test test = models.test.Test.find.byId(test_id);
    	User user = User.find.byId(request().username());
    	
    	Test test_aux = test;
    	
    	Module module = Module.findByAcronym(module_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	
    	for(Question que : test_aux.questions){
    		System.out.println(que.typeOfQuestion);
    	}
    	
		return ok(views.html.secured.test.render(user,module,lesson,categories,test_aux,form(QuestionAnswer.class),form(OneChoiceQuestionAnswer.class)));
    	
    }
    
    public static Result lesson(String lesson_acronym, String module_acronym){
    	
    	List<Category> categories = Category.getAllCategories();
    	Module module=Module.findByAcronym(module_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	User user=User.find.byId(request().username());
    	


    	return ok(views.html.secured.lesson.render(user,categories,lesson,module,form(OpenQuestionSuggestion.class)));

    	
    }
    
    public static Result modules() {
		List<Module> allModules = Module.getAllModules();
		List<Category> categories = Category.getAllCategories();
		
		//check this line
		User user=User.find.byId(session("email"));
		return ok(views.html.secured.modules.render(user,allModules,categories));
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
	
	public static Result postquestionanswer(String module_acronym, String lesson_acronym, Long test_id, Long question_id){
		Form<Profile.QuestionAnswer> form = form(Profile.QuestionAnswer.class).bindFromRequest();
		System.out.println("Answer: " + form.get().qanswer + "Question: " +question_id);
		
		Question question = Question.find.byId(question_id);
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
    	List<Answer> answers = Answer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;

    	
    	Module module = Module.findByAcronym(module_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	
    	
		return ok(views.html.secured.test.render(user,module,lesson,categories,test_aux,form(QuestionAnswer.class), form(OneChoiceQuestionAnswer.class)));
		

	}
	
	public static Result postonechoicequestionanswer(String module_acronym, String lesson_acronym, Long test_id, Long question_id){
		Form<Profile.OneChoiceQuestionAnswer> form = form(Profile.OneChoiceQuestionAnswer.class).bindFromRequest();
		System.out.println("One Choice: " + form.get().ocqanswer);
		
		
		Test test = Test.find.byId(test_id);
		
		
		
		
		User user = User.find.byId(request().username());
		List<Answer> openanswers = Answer.findByUserEmailAndTestId(user.email, test_id);
		
		Hypothesis answer = Hypothesis.find.byId(form.get().ocqanswer);
		
		
		List<Category> categories = Category.getAllCategories();
    	
    	Test test_aux = test;
    	test_aux.answers = openanswers;
    	
    	Module module = Module.findByAcronym(module_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	
    	
		return ok(views.html.secured.test.render(user,module,lesson,categories,test_aux,form(QuestionAnswer.class), form(OneChoiceQuestionAnswer.class)));
	}
	
	public static Result postmultiplechoicequestionanswer(String module_acronym, String lesson_acronym, Long test_id, Long question_id){
		Form<Profile.MultipleChoiceQuestionAnswer> form = form(Profile.MultipleChoiceQuestionAnswer.class).bindFromRequest();
		
		
		Test test = Test.find.byId(test_id);
		
//		
//		if(userMultipleChoiceAnswer.hypothesislist.size()!=0){
//		for(MultipleChoiceHypothesis hyp: userMultipleChoiceAnswer.hypothesislist){
//			userMultipleChoiceAnswer.hypothesislist.remove(hyp);
//			hyp.save();
//			userMultipleChoiceAnswer.save();
//		}
//		}
		
		
		
		
		
		
		
		
		User user = User.find.byId(request().username());
		List<Answer> openanswers = Answer.findByUserEmailAndTestId(user.email, test_id);
		
		List<Category> categories = Category.getAllCategories();
    	Test test_aux = test;
    	test_aux.answers = openanswers;

    	
    	Module module = Module.findByAcronym(module_acronym);
    	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
    	
    	
		return ok(views.html.secured.test.render(user,module,lesson,categories,test_aux,form(QuestionAnswer.class), form(OneChoiceQuestionAnswer.class)));
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

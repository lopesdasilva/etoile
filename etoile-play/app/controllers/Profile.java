package controllers;


import java.util.LinkedList;
import java.util.List;

import controllers.Application.Login;

import models.Blog;
import models.curriculum.Category;
import models.Comment;


import models.User;
import models.course.Course;
import models.course.Module;
import models.course.University;
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
    

    
    public static Result test(Long test_id, String module_acronym, String course_acronym){
    	List<Category> categories = Category.getAllCategories();
    	Test test = models.test.Test.find.byId(test_id);
    	User user = User.find.byId(request().username());
    	List<Answer> answers = Answer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;
    	test_aux.answers = answers;
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
    	test_aux=test;
    	
    	Course course = Course.findByAcronym(course_acronym);
    	Module module = Module.findByAcronym(module_acronym);
    	
		return ok(views.html.secured.test.render(user,course,module,categories,test_aux,form(QuestionAnswer.class),form(OneChoiceQuestionAnswer.class)));
    	
    }
    
    public static Result module(String module_acronym, String course_acronym){
    	
    	List<Category> categories = Category.getAllCategories();
    	Course course=Course.findByAcronym(course_acronym);
    	Module module = Module.findByAcronym(module_acronym);
    	User user=User.find.byId(request().username());

    	return ok(views.html.secured.module.render(user,categories,module,course));

    	
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
	
	public static Result postquestionanswer(String course_acronym, String module_acronym, Long test_id, Long question_id){
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
    	List<Answer> answers = Answer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;
    	test_aux.answers = answers;
    	
    	Course course = Course.findByAcronym(course_acronym);
    	Module module = Module.findByAcronym(module_acronym);
    	
    	
		return ok(views.html.secured.test.render(user,course,module,categories,test_aux,form(QuestionAnswer.class), form(OneChoiceQuestionAnswer.class)));
		

	}
	
	public static Result postonechoicequestionanswer(String course_acronym, String module_acronym, Long test_id, Long question_id){
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
		
		
		Hypothesis answer = Hypothesis.find.byId(form.get().ocqanswer);
		
		userAnswer.hypothesis = answer;
		userAnswer.save();
		
		List<Category> categories = Category.getAllCategories();
    	User user = User.find.byId(request().username());
    	List<Answer> answers = Answer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;
    	test_aux.answers = answers;
    	
    	Course course = Course.findByAcronym(course_acronym);
    	Module module = Module.findByAcronym(module_acronym);
    	
    	
		return ok(views.html.secured.test.render(user,course,module,categories,test_aux,form(QuestionAnswer.class), form(OneChoiceQuestionAnswer.class)));
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

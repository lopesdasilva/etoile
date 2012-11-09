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
import models.test.Answer;
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
	
	
	/**
     * Display the dashboard.
     */
    public static Result index() {
    	List<Blog> blogs = Blog.getAllBlogs();
    	User user=User.find.byId(request().username());
    	List<Category> categories = Category.getAllCategories();
        return ok(home.render(user,blogs,categories) );
    }
    
    
    public static Result course(Long course_id){
    	Course course = Course.find.byId(course_id);
    	User user=User.find.byId(session("email"));
    	List<Category> categories = Category.getAllCategories();
    	
    	
    	if(!user.courses.contains(course))
    	return ok(views.html.secured.courseGeneral.render(user,categories,course));
    	else
    	//Has this course
    	return ok(views.html.secured.course.render(user,categories,course));

    	
    }
    

    
    public static Result test(Long test_id, Long module_id){
    	List<Category> categories = Category.getAllCategories();
    	Test test = models.test.Test.find.byId(test_id);
    	User user = User.find.byId(request().username());
    	List<Answer> answers = Answer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;
    	test_aux.answers = answers;
    	
		return ok(views.html.secured.test.render(user,categories,test_aux,form(QuestionAnswer.class)));
    	
    }
    
    public static Result module(Long module_id, Long course_id){
    	
    	List<Category> categories = Category.getAllCategories();
    	Module module = Module.find.byId(module_id);
    	User user=User.find.byId(request().username());

    	return ok(views.html.secured.module.render(user,categories,module));

    	
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
	
	public static Result postquestionanswer(Long module_id, Long test_id, Long question_id){
		Form<Profile.QuestionAnswer> form = form(Profile.QuestionAnswer.class).bindFromRequest();
		System.out.println("Answer: " + form.get().qanswer + "Question: " +question_id);
		
		OpenQuestion question = OpenQuestion.find.byId(question_id);
		Answer answer = new Answer();
		answer.answer = form.get().qanswer;
		answer.save();
		question.answers.add(answer);
		question.save();

		List<Category> categories = Category.getAllCategories();
    	Test test = models.test.Test.find.byId(test_id);
    	User user = User.find.byId(request().username());
    	List<Answer> answers = Answer.findByUserEmailAndTestId(user.email, test_id);
    	Test test_aux = test;
    	test_aux.answers = answers;
		
		return ok(views.html.secured.test.render(user,categories,test_aux,form(QuestionAnswer.class)));

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

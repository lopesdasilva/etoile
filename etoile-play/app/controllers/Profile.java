package controllers;


import java.util.List;

import controllers.Application.Login;

import models.Blog;
import models.Category;
import models.Comment;


import models.User;
import models.course.Course;
import models.course.Module;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
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
    	System.out.println("COURSE ID: "+course_id);
    	
    	Course course = Course.find.byId(course_id);
    	User user=User.find.byId(request().username());
    	List<Category> categories = Category.getAllCategories();
    	
    	
    	System.out.println();
    	if(!user.courses.contains(course))
    	return ok(views.html.secured.courseGeneral.render(user,categories,course));
    	else
    	//Has this course
    	return ok(views.html.secured.course.render(user,categories,course));

    	
    }
    

    
    public static Result test(Long test_id, Long module_id){
    	System.out.println("TEST ID: " + test_id);
    	List<Category> categories = Category.getAllCategories();
    	models.test.Test test = models.test.Test.find.byId(test_id);
    	User user = User.find.byId(request().username());
		return ok(views.html.secured.test.render(user,categories,test));
    	
    }
    
    public static Result module(Long module_id, Long course_id){
    	System.out.println("MODULE ID: "+module_id);
    	
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
		 	
		 //DEBUG
		System.out.println("DEBUG: User:"+user.email+" is adding a new comment to blog id: "+blog);
		System.out.println("DEBUG: Comment text: "+form.get().comment);
		
		//New Comment
		models.Comment c=new models.Comment();
		c.text=form.get().comment;
		c.blog=Blog.find.byId(blog);
		c.user=user;
		c.save();

		return ok(views.html.secured.blog.render(user,Blog.find.byId(blog),categories,form(Comment.class)));
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

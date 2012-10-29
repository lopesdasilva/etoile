package controllers;

import java.util.List;

import controllers.Profile.Comment;

import models.*;
import models.course.Course;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
	
	final static Form<User> userForm = form(User.class);
	
	// -- Authentication
    
	 public static class Login {
	        
	        public String email;
	        public String password;
	        
	        public String validate() {
	            if(User.authenticate(email, password) == null) {
	                return "Invalid user or password";
	            }
	            return null;
	        }
	        
	    }

	public static Result index() {
		List<Blog> blogs = Blog.getAllBlogs();
		List<Category> categories = Category.getAllCategories();
		return ok(index.render(blogs,categories));
	}
	
	public static Result postcomment(){
		 Form<Comment> form = form(Comment.class).bindFromRequest();
		 List<Category> categories = Category.getAllCategories();
		System.out.println("Post Comment");
	
		
//		User user=User.find.byId(session("email"));
		return ok(views.html.statics.about.render(categories));
	}
	
	
	
	public static Result about() {
		if(session("email")!=null){
			return Profile.about();
		}
		return ok(views.html.statics.about.render(
				Category.getAllCategories()
				));
	}
	
	public static Result contact() {
		if(session("email")!=null){
			return Profile.contact();
		}
		return ok(views.html.statics.contact.render(
				Category.getAllCategories()
				));
	}


	//CHECK THIS METHOD
	public static Result courses(){
		
		if(session("email")!=null){
			return Profile.courses();
		}
		return ok(courses.render(Course.getAllCourses(),Category.getAllCategories()));
	}
	
	public static Result blog(Long blog){
		List<Category> categories = Category.getAllCategories();
		if(session("email")!=null){
			return Profile.blog(blog);
		}
		return ok(views.html.blog.blog.render(Blog.find.byId(blog),categories));
	}

	
	/**
     * Login page.
     */
    public static Result login() {
        return ok(login.render(form(Login.class)));
    }
    
    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
    	
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                routes.Profile.index()
            );
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.index()
        );
    }
	

}
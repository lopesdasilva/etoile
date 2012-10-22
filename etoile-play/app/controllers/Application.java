package controllers;

import java.util.List;

import models.Blog;
import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
	
	final static Form<User> userForm = form(User.class);

	public static Result index() {
		List<Blog> blog = Blog.getAllBlogs();
		return ok(index.render(blog, userForm));
	}
	
	public static Result submit() {
		Form<User> filledform = userForm.bindFromRequest();
		List<Blog> blog = Blog.getAllBlogs();
		User created = filledform.get();
		return ok(submit.render(blog, created));
	}
	

}
package controllers;


import java.util.List;

import models.Blog;
import models.curriculum.Category;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.secured.*;

/**
 * Manage Profile related operations.
 */
@Security.Authenticated(SecuredProfessor.class)
public class ProfessorController extends Controller {


	/**
	 * Display the dashboard.
	 */
	
	public static Result index() {
		
		
		if(SecuredProfessor.isProfessor(session("email"))){
		List<Blog> blogs = Blog.getAllBlogs();
		User user = User.find.byId(session("email"));
		List<Category> categories = Category.getAllCategories();

		return ok(views.html.professor.homeprofessor.render(user, blogs, categories));
		}
		return Profile.index(); 
	}
	

}

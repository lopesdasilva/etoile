package controllers;


import java.util.List;

import models.Blog;
import models.curriculum.Category;
import models.module.Module;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.secured.*;

/**
 * Manage StudentController related operations.
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
	
		user.professorProfile.refresh();
		
		return ok(views.html.professor.homeprofessor.render(user, blogs, categories));
		}
		return StudentController.index(); 
	}

	public static Result module(String module_acronym) {
			
			Module module = Module.findByAcronym(module_acronym);
			User user = User.find.byId(session("email"));
			user.professorProfile.refresh();
			List<Category> categories = Category.getAllCategories();
			
			if(SecuredProfessor.isOwner(user,module)){
				return ok(views.html.professor.moduleGeneralEdit.render(user, categories,
						module));
			}
			return ok(views.html.professor.moduleGeneral.render(user, categories,
					module));
		
	}

	
	
}

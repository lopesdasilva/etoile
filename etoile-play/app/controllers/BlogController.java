package controllers;

import java.util.List;

import models.Blog;
import models.User;
import models.continent.Continent;
import models.curriculum.Category;

import java.util.Date;
import java.util.LinkedList;
import controllers.Application.Login;




import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import controllers.secured.*;



public class BlogController extends Controller {

	
	/**
	 * Describes the comment form.
	 */
	public static class Comment_Form {

		public String comment;
	}

	
	public static Result blog(Long blog_id) {
		
		Blog blog =Blog.find.byId(blog_id);
		if(blog==null){
			return redirect(routes.Application.news());
		}
		List<Category> categories = Category.getAllCategories();
		if(session("email")!=null){

			User user = User.find.byId(session("email"));
			if (SecuredProfessor.isProfessor(session("email"))){
				user.professorProfile.refresh();
				return ok(views.html.professor.blog.render(user, blog, Form.form(Comment_Form.class)));
			}
			user.studentProfile.refresh();

			return ok(views.html.secured.blog.render(user, blog,categories, Form.form(Comment_Form.class)));


		}
		return ok(views.html.blog.blog.render(blog,categories,Continent.getAllContinents()));
		
	}

	@Security.Authenticated(Secured.class)
	public static Result postcomment(Long blog_id) {
		
		Blog blog =Blog.find.byId(blog_id);
		if(blog==null){
			return redirect(routes.Application.news());
		}
		
		Form<Comment_Form> form = Form.form(Comment_Form.class)
				.bindFromRequest();
		List<Category> categories = Category.getAllCategories();
		User user = User.find.byId(session("email"));

		// New Comment
		models.Comment c = new models.Comment();
		c.text = form.get().comment;
		c.blog = blog;
		c.user = user;
		c.date = new Date();
		c.save();
		return redirect(routes.BlogController.blog(blog_id));
	}
}

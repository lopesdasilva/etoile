package controllers;

import java.util.List;

import models.Blog;
import models.User;
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

	
	public static Result blog(Long blog) {
		
		List<Category> categories = Category.getAllCategories();
		if(session("email")!=null){
			User user = User.find.byId(session("email"));
			
			return ok(views.html.secured.blog.render(user, Blog.find.byId(blog),
					categories, form(Comment_Form.class)));
		}
		return ok(views.html.blog.blog.render(Blog.find.byId(blog),categories));
		
	}

	@Security.Authenticated(Secured.class)
	public static Result postcomment(Long blog) {
		Form<Comment_Form> form = form(Comment_Form.class)
				.bindFromRequest();
		List<Category> categories = Category.getAllCategories();
		User user = User.find.byId(session("email"));

		// New Comment
		models.Comment c = new models.Comment();
		c.text = form.get().comment;
		c.blog = Blog.find.byId(blog);
		c.user = user;
		c.date = new Date();
		c.save();
		System.out.println("Comentario: "+form.get().comment);
		return redirect(routes.BlogController.blog(blog));
	}
}

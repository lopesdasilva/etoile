package controllers;

import java.util.List;

import controllers.Profile.Comment;

import models.*;
import models.course.Course;
import models.curriculum.Category;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Administration extends Controller {
	
public static class Blog_Aux{
		
		public String title;
		public String text;
		public String articleImageURL;
	}
	
    public static Result index() {
    	List<User> users = User.findAll();
    	List<Course> courses = Course.getAllCourses();
    	List<Blog> blogs = Blog.getAllBlogs();
    	 
    	return ok(views.html.administration.admin.render(users,courses,blogs));
    }
    
    public static Result removeuser(String email){
    	
    	
    	
    	System.out.println("user to remove: "+email);
    	User user=User.findByEmail(email);
    	 user.delete();
    	
    	 List<User> users = User.findAll();
    	List<Course> courses = Course.getAllCourses();
    	List<Blog> blogs = Blog.getAllBlogs();
    	 
    	return ok(views.html.administration.admin.render(users,courses,blogs));
    }
    
    
    public static Result removeblog(Long blog_id){
		List<Category> categories = Category.getAllCategories();
		System.out.println("Blog to remove (id): "+blog_id); 	
    	Blog blog =Blog.find.byId(blog_id);
    	blog.delete();
		List<User> users = User.findAll();
    	List<Course> courses = Course.getAllCourses();
    	List<Blog> blogs = Blog.getAllBlogs();
    	 
    	return ok(views.html.administration.admin.render(users,courses,blogs));
	}
    
    public static Result createblog(){
    	
    	 Form<Blog_Aux> form = form(Blog_Aux.class).bindFromRequest();
    	return ok(views.html.administration.createblog.render(form));
    }

    public static Result addblog(){
    	System.out.println("ADDING BLOG ENTRY");
    	 Form<Blog_Aux> form = form(Blog_Aux.class).bindFromRequest(); 	 
    	 System.out.println("Title:"+form.get().title);
    	 System.out.println("ImageURL:"+form.get().articleImageURL);
    	 System.out.println("Text:"+form.get().text);
    	 
    	 Blog blog= new Blog();
    	 blog.alternateText=form.get().text.substring(0,255);
    	 blog.alternateHeader=form.get().title.substring(0,50);
    	 blog.header=form.get().title;
    	 blog.text=form.get().text;
    	 blog.articleImageURL=form.get().articleImageURL;
    	 blog.save();
    	 
    	 
    	List<User> users = User.findAll();
    	List<Course> courses = Course.getAllCourses();
    	List<Blog> blogs = Blog.getAllBlogs();
    	flash("success", "New Article created!");
    	return ok(views.html.administration.admin.render(users,courses,blogs));
    }
    
    

}

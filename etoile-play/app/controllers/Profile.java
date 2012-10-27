package controllers;


import java.util.List;

import models.Blog;
import models.Category;
import models.Course;
import models.Module;
import models.User;
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

    	return ok(views.html.secured.course.render(user,course));

    	
    }
    
    public static Result module(Long module_id, Long course_id){
    	System.out.println("MODULE ID: "+module_id);
    	
    	Module module = Module.find.byId(module_id);
    	User user=User.find.byId(request().username());

    	return ok(views.html.secured.module.render(user,module));

    	
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

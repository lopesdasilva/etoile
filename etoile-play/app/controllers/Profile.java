package controllers;


import java.util.List;

import models.Blog;
import models.Course;
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
        return ok(home.render(user,blogs) );
    }
    
    public static Result course(Long course_id){
    	System.out.println("COURSE ID: "+course_id);
    	
    	
    	Course course = Course.find.byId(course_id);
    	User user=User.find.byId(request().username());
<<<<<<< HEAD
    	return ok(views.html.statics.blank.render());
=======
    	return ok(views.html.secured.course.render(user,course));
>>>>>>> Modules Update
    	
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

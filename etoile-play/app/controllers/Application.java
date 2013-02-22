package controllers;

import java.util.Collections;
import java.util.List;

import controllers.secured.Secured;
import controllers.secured.SecuredProfessor;


import models.*;
import models.continent.Continent;
import models.curriculum.Category;
import models.module.Lesson;
import models.module.Module;
import play.data.Form;
import play.mvc.*;


import views.html.*;

public class Application extends Controller {
	
	
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
		
		if(session("email")!=null){
			return StudentController.index();
		}
		List<Blog> blogs = Blog.getAllBlogs();
		List<Category> categories = Category.getAllCategories();
		List <Continent> continents = Continent.getAllContinents();
		List<Module> modules= Module.getAllModules();
		if (modules.size()>3){
		Collections.shuffle(modules);
		modules=modules.subList(0, 3);
		}
		if(blogs.size()>3)
		blogs=blogs.subList(0, 3);
		return ok(index.render(blogs,categories,continents,modules));
	}
	

	
	public static Result curriculum() {
		
		
		return ok(views.html.statics.curriculum.render(
				Category.getAllCategories()
				));
	}
	
public static Result professorprofile(String professor_acronym) {
	
		Professor professor = Professor.findByAcronym(professor_acronym);
	
		
		return ok(views.html.statics.professorprofile.render(professor));
	}


	public static Result digitalcampus() {
		if(session("email")!=null){
			return StudentController.digitalcampus();
		}
		return ok(views.html.statics.digitalcampus.render(
				Category.getAllCategories(),Continent.getAllContinents()
				));
	}
	public static Result news() {
		
		if(session("email")!=null){
			return StudentController.news();
		}

		return ok(views.html.blog.blogs.render(
				Blog.getAllBlogs(),Category.getAllCategories(),Continent.getAllContinents()
				));
	}
	
	public static Result categoryModules(Long category_id){
		Category category = Category.find.byId(category_id);
		List<Category> categories = Category.getAllCategories();
		return ok(categorymodules.render(category,categories));
	}
	

	 public static Result continent(String continent_acronym){
	    	Continent continent = Continent.findByAcronym(continent_acronym);
	    
	    	
	    	List<Category> categories = Category.getAllCategories();
	    	List <Continent> continents = Continent.getAllContinents();
	    	
	    	if(session("email")!=null){
				return StudentController.continent(categories,continents,continent,continent.universities);
			}
	    	
	    	return ok(views.html.statics.continent.render(categories,continents,continent,continent.universities));
	    }
	
	
	 public static Result about() {
		if(session("email")!=null){
			return StudentController.about();
		}
		
		
		return ok(views.html.statics.about.render(
				Category.getAllCategories(),Continent.getAllContinents()
				));
	}
	
	public static Result contact() {
		if(session("email")!=null){
			return StudentController.contact();
		}
		return ok(views.html.statics.contact.render(
				Category.getAllCategories(),Continent.getAllContinents()
				));
	}


	public static Result module(String module_acronym){
		
		Module module = Module.findByAcronym(module_acronym);
		module.language.refresh();
		List<Category> categories = Category.getAllCategories();
		module.language.refresh();
		
		if(session("email")!=null){
			if(Secured.isStudent(session("email"))){
			return StudentController.module(module_acronym);
			}
			if (SecuredProfessor.isProfessor(session("email"))){
				
				//TODO Subsituir por modules
				return ProfessorController.module(module_acronym);
			}
		}
		return ok(views.html.statics.module.render(categories,Continent.getAllContinents(),module));
	}
	
	public static Result lesson(String module_acronym, String lesson_acronym){
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		List<Category> categories = Category.getAllCategories();
		
		if(session("email")!=null){
			if(Secured.isStudent(session("email"))){
				return StudentController.module(module_acronym);
				}
			if (SecuredProfessor.isProfessor(session("email"))){
				return ProfessorController.lesson(lesson_acronym, module_acronym);
			}
		}
	
		return ok(views.html.statics.module.render(categories,Continent.getAllContinents(),module));
	}




	//CHECK THIS METHOD
	public static Result modules(){
		
		if(session("email")!=null){
			if(Secured.isStudent(session("email"))){
				return StudentController.modules();
			}
			if (SecuredProfessor.isProfessor(session("email"))){

				//TODO Subsituir por modules
				return ProfessorController.index();
			}
		}
		return ok(modules.render(Module.getAllModules(),Category.getAllCategories(),Continent.getAllContinents()));
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
            
            switch (User.find.byId(session("email")).account_type){
            case 0:
            	return redirect(routes.StudentController.index());
            case 1:
            	return redirect(routes.ProfessorController.index());
            }
        }
		return null;
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

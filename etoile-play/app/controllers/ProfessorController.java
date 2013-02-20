package controllers;


import java.util.List;

import models.Blog;
import models.Professor;
import models.continent.Continent;
import models.curriculum.Category;
import models.module.Lesson;
import models.module.Module;
import models.test.question.Question;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.StudentTestController.OpenQuestionSuggestion;
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
			String[] profs_emails = Professor.getAllEmails();
			System.out.println("numero de profs "+profs_emails.length);
			System.out.println("email 0"+profs_emails[0]);
			if(SecuredProfessor.isOwner(user,module)){
				return ok(views.html.professor.moduleGeneralEdit.render(user, categories,
						module,Professor.getAllEmails()));
			}
			return ok(views.html.professor.moduleGeneral.render(user, categories,
					module));
		
	}

	public static Result lesson(String lesson_acronym, String module_acronym) {
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		user.professorProfile.refresh();
		List<Category> categories = Category.getAllCategories();
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		
		for(Question q: lesson.questions){
			if(q.user!=null){
				
				q.user.refresh();
			}
		}
		
		return ok(views.html.professor.lesson.render(user, categories, lesson,
				module, form(ProfessorLessonController.NewAlert_Form.class)));
	}

	public static Result myprofile() {
		if(session("email")!=null && SecuredProfessor.isProfessor(session("email"))) {
			User user = User.find.byId(session("email"));
			return ok(views.html.professor.professorprofileEdit.render(user.professorProfile));		
			}
	
		return Application.index();
	}

	public static Result digitalcampus() {
		User user = User.find.byId(session("email"));
		user.professorProfile.refresh();
		return ok(views.html.professor.digitalcampus.render(user));
		
	}

	public static Result news() {
		
		User user = User.find.byId(session("email"));
		user.professorProfile.refresh();
		return ok(views.html.professor.blogs.render(user,Blog.getAllBlogs()));
	}

	public static Result about() {
		User user = User.find.byId(session("email"));
		user.professorProfile.refresh();
		return ok(views.html.professor.about.render(user));
	}

	public static Result contact() {
		User user = User.find.byId(session("email"));
		user.professorProfile.refresh();
		return ok(views.html.professor.contact.render(user));
	}
	
	
}

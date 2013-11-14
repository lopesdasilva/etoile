package controllers;


import java.util.List;

import models.Blog;
import models.Professor;
import models.SubtopicReputation;
import models.continent.Continent;
import models.curriculum.Category;
import models.module.Lesson;
import models.module.Module;
import models.test.question.Question;
import models.curriculum.Curriculumtopic;

import models.User;
import play.data.Form;
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
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(user.email)){
			System.out.println("********* start:"+user.email+"***********");
			System.out.println("Controller: ProfessorController.java");
			System.out.println("Method: index");
			System.out.println("User is professor");
		List<Blog> blogs = Blog.find.all();
	
		List<Category> categories = Category.find.all();
	
		user.professorProfile.refresh();
		System.out.println("*********   end:"+user.email+"***********");
		return ok(views.html.professor.homeprofessor.render(user, blogs, categories));
		}
		return StudentController.index(); 
	}

    public static Result manageCurriculum() {
        User user = User.find.byId(session("email"));
        if(session("email")!=null && SecuredProfessor.isProfessor(session("email"))) {
        return ok(views.html.professor.manageCurriculum.render(
                Category.find.all()
        ));
        }

        return Application.index();

    }

	public static Result module(String module_acronym) {

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		

		
			
			User user = User.find.byId(session("email"));
			user.professorProfile.refresh();
			List<Category> categories = Category.find.all();

			System.out.println("********* start:"+user.email+"***********");
			System.out.println("Controller: ProfessorController.java");
			System.out.println("Method: module");
			System.out.println("User is professor");
			
			if(SecuredProfessor.isOwner(user,module)){
				
				System.out.println("User is owner of module: "+module.id);
				System.out.println("*********   end:"+user.email+"***********");
				
				return ok(views.html.professor.moduleGeneralEdit.render(user, categories,
						module,Professor.getAllEmails()));
			}
			System.out.println("User is not owner of module: "+module.id);
			
			System.out.println("*********   end:"+user.email+"***********");
			return ok(views.html.professor.moduleGeneral.render(user, categories,
					module));
		
	}

	public static Result lesson(String lesson_acronym, String module_acronym) {
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		User user = User.find.byId(session("email"));
		if(session("email")!=null && SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)) {
			
		user.professorProfile.refresh();
		List<Category> categories = Category.find.all();
		
		for(Question q: lesson.questions){
			if(q.user!=null){
				
				q.user.refresh();
			}
		}
		System.out.println("********* start:"+user.email+"***********");
		System.out.println("Controller: ProfessorController.java");
		System.out.println("Method: lesson");
		System.out.println("User is professor");
		System.out.println("User is owner of module: "+module.id);
		System.out.println("*********   end:"+user.email+"***********");
		
		return ok(views.html.professor.lesson.render(user, categories, lesson,
				module, Form.form(ProfessorLessonController.NewAlert_Form.class)));
		}
		return redirect(routes.Application.module(module_acronym));
	}

	public static Result myprofile() {
		if(session("email")!=null && SecuredProfessor.isProfessor(session("email"))) {
			User user = User.find.byId(session("email"));
			return ok(views.html.professor.professorprofileEdit.render(user.professorProfile));		
			}else if(session("email") != null && Secured.isStudent(session("email"))){
			User user = User.find.byId(session("email"));
			user.studentProfile.refresh();
			if(user.studentProfile.university!=null){
			user.studentProfile.university.refresh();
			}
			for(SubtopicReputation sr :user.subtopicreputation){
				sr.subtopic.refresh();
			}
			
//			System.out.println("********* start:"+user.email+"***********");
//			System.out.println("Controller: ProfessorController.java");
//			System.out.println("Method: myprofile");
//			System.out.println("User is professor");
//			System.out.println("Profile refreshed");
//			System.out.println("*********   end:"+user.email+"***********");
			
			
			return ok(views.html.secured.studentprofileEdit.render(user));	
			}
	
		return Application.index();
	}

    public static Result deleteExternalResource(long resource_id){
       User user = User.find.byId(session("email"));
        if(session("email")!=null && SecuredProfessor.isProfessor(session("email"))) {

            Curriculumtopic resource = Curriculumtopic.find.byId(resource_id);

            resource.user.commitmentReputation--;
            resource.user.save();

            resource.delete();

            return ok(views.html.professor.manageCurriculum.render(
                    Category.find.all()
            ));

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
		return ok(views.html.professor.blogs.render(user,Blog.find.all()));
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

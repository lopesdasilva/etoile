package controllers;

import controllers.routes;

import models.User;
import models.curriculum.Category;
import models.curriculum.Curriculumtopic;
import models.curriculum.Curriculumlesson;
import models.module.Bibliography;
import models.module.Lesson;
import models.module.Lessonalert;
import models.module.Lessoncontent;
import models.module.Module;
import models.test.question.Question;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.register;
import controllers.ProfessorModuleController.BibliographyItem_Form;
import controllers.StudentTestController.OpenQuestionSuggestion;
import controllers.secured.*;
import controllers.extra.SendMail;
import java.util.List;


/**
 * Manage StudentController related operations.
 */
@Security.Authenticated(SecuredProfessor.class)
public class ProfessorLessonController extends Controller {
	
	
	public static class NewAlert_Form {
		
		public String name;
		
		public String text;
		
		public String imageURL;
		
		public String validate() {
			if(name.length()<6){
				return "A lesson name must be at least 6 characters";
            	}
            return null;
        }
		
	}
	
	public static class NewContent_Form {
		
		public String name;
		
		public String text;
		
		public String imageURL;
		
		public String url;

        public long resource_challenge;
		
	}
	
	public static class LessonDescription_Form {
		
		public String name;
		
		public String acronym;
		
		public String shortdescription;
		
		public String description;
		
		public String imageURL;
		
		public String videoURL;
		
	}
	

	
	public static Result addlessonalert(String module_acronym, String lesson_acronym) {

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		User user = User.find.byId(session("email"));
		List<Category> categories = Category.find.all();
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<NewAlert_Form> form = Form.form(NewAlert_Form.class).bindFromRequest();
			if(form.hasErrors()) {
	            return badRequest(views.html.professor.lesson.render(user, categories, lesson,
	    				module,form));
			}else{
			Lessonalert lessonalert = new Lessonalert();
			lessonalert.name = form.get().name;
			lessonalert.text = form.get().text;
			if(form.get().imageURL.equals("")){
				lessonalert.imageURL = "http://placehold.it/75x75";
			}
			else{
				lessonalert.imageURL = form.get().imageURL;
			}
			
			lessonalert.lesson = lesson;
			lessonalert.save();
			
			System.out.println("******* start:"+user.email+"*********");
	        System.out.println("Controller: ProfessorLessonEdit.java");
	        System.out.println("Method: addlessonalert");
	        System.out.println("Lesson Alert added");
	        System.out.println("*******   end:"+user.email+"*********");

			//List<User> users_list = module.users;
		//	for(User u : users_list) {
		//		SendMail.sendMail(u.email, "[Etoile] News in module "+module_acronym, "blablablablablabl some New from the professor "+user.email); 
		//	}
			}
		
		}
		
		
		return redirect(routes.Application.lesson(module_acronym,lesson_acronym));
	}
	
	public static Result addlessoncontent(String module_acronym, String lesson_acronym){

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		Form<NewContent_Form> form = Form.form(NewContent_Form.class).bindFromRequest();
	if(!(form.get().name==null || form.get().name.equals("") || form.get().name.length()<6)){
		
		
		User user = User.find.byId(session("email"));
		
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
            String url=form.get().url;
            if (!url.startsWith("https://") && !url.startsWith("http://")) {
                url = "http://" + url;
            }

            try{
			Lessoncontent content = new Lessoncontent();
			content.name = form.get().name;
			content.text = form.get().text;
			if(form.get().imageURL.equals("")){
				content.lessonContentImageURL = "http://us.123rf.com/400wm/400/400/zybr/zybr1204/zybr120400005/13195450-vector-illustration-pile-of-books-isolated-on-white.jpg";
			}else{
				content.lessonContentImageURL = form.get().imageURL;
			}
			content.url = url;
			content.lesson = lesson;
			content.save();
            }catch(Exception e){
                flash("failed", "Resource not added.");
            }




            try{
            models.curriculum.Curriculumlesson challenge = Curriculumlesson.find.byId(form.get().resource_challenge);
            Curriculumtopic externalResource = new Curriculumtopic();
            externalResource.text = form.get().name;
            externalResource.keyword = url;
            externalResource.curriculumlesson = challenge;
            externalResource.save();

            challenge.curriculumtopics.add(externalResource);
            challenge.save();
            flash("success", "Resource added.");
            }catch(Exception e){
                flash("warning", "Resource added. Resource not added as a Curriculum resource because it already exists.");
            }



			System.out.println("******* start:"+user.email+"*********");
	        System.out.println("Controller: ProfessorLessonEdit.java");
	        System.out.println("Method: addlessonacontent");
	        System.out.println("Lesson Content added");
	        System.out.println("*******   end:"+user.email+"*********");
	        
		}
		return redirect(routes.Application.lesson(module_acronym,lesson_acronym));
	}
		flash("error", "Error in the form.");
		return redirect(routes.Application.lesson(module_acronym,lesson_acronym));
	}
	
	public static Result editlesson(String module_acronym, String lesson_acronym){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		User user = User.find.byId(session("email"));
		Form<LessonDescription_Form> form = Form.form(LessonDescription_Form.class).bindFromRequest();
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			lesson.acronym = form.get().acronym;
			lesson.description = form.get().description;
			lesson.imageURL = form.get().imageURL;
			lesson.name = form.get().name;
			lesson.shortDescription = form.get().shortdescription;
			lesson.save();
			
			System.out.println("******* start:"+user.email+"*********");
	        System.out.println("Controller: ProfessorLessonEdit.java");
	        System.out.println("Method: edit lesson");
	        System.out.println("Lesson Edited");
	        System.out.println("*******   end:"+user.email+"*********");
	        
		}
		
		
		return redirect(routes.Application.lesson(module_acronym,lesson.acronym));
	}
	
	public static Result deletequestion(String module_acronym, String lesson_acronym,Long question_id){
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		

		User user = User.find.byId(session("email"));
			if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
				Question question= Question.find.byId(question_id);
				if(question==null){
					return redirect(routes.Application.lesson(module_acronym,lesson_acronym));
				}
				question.delete();
				question.save();
				
				System.out.println("******* start:"+user.email+"*********");
		        System.out.println("Controller: ProfessorLessonEdit.java");
		        System.out.println("Method: deletequestion");
		        System.out.println("Lesson Question deleted");
		        System.out.println("*******   end:"+user.email+"*********");
		        
			}
		
		
		return redirect(routes.Application.lesson(module_acronym,lesson_acronym));
	}
	
}

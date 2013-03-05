package controllers;


import models.User;

import models.module.Bibliography;
import models.module.Content;
import models.module.Lesson;
import models.module.Module;

import controllers.routes;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.secured.*;

import controllers.extra.SendMail;
import java.util.List;



@Security.Authenticated(SecuredProfessor.class)
public class ProfessorModuleController extends Controller {

	
	/**
	 * Describes the comment form.
	 */
	public static class Comment_Form {

		public String comment;
	}
	
	public static class BibliographyItem_Form {
		
		public String title;
		
		public String description;
		
		public String imageURL;
		
		public String url;
		
	}
	
	public static class LessonItem_Form {
		
		public String name;
		
		public String description;
		
		public String shortDescription;
		
		public String acronym;
		
		public String imageURL;
		
	}
	
	public static class ModuleDescription_Form{
		public String description;
		
		public String imageURL;
		
		public String videoURL;
	}
	
	
	public static class ModuleTitle_Form{
		public String title;
		
		public String acronym;
	}
	
	public static class Content_Form{
		public String title;
		
		public String description;
	}

	
	public static Result editmoduletitle(String module_acronym){
		
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<ModuleTitle_Form> form = Form.form(ModuleTitle_Form.class).bindFromRequest();
			module.name=form.get().title;
			module.acronym=form.get().acronym;
			module.save();
		}
		
		
		return redirect(routes.Application.module(module.acronym));
	}

	public static Result editmoduledescription(String module_acronym){

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<ModuleDescription_Form> form = Form.form(ModuleDescription_Form.class).bindFromRequest();
			module.description=form.get().description;
			module.imageURL=form.get().imageURL;
			module.videoURL=form.get().videoURL;
			module.save();
		}


		return redirect(routes.Application.module(module_acronym));
	}

	
	public static Result addbibliographyitem(String module_acronym){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Bibliography bibliographyItem= new Bibliography();
			Form<BibliographyItem_Form> form = Form.form(BibliographyItem_Form.class).bindFromRequest();
			bibliographyItem.title=form.get().title;
			bibliographyItem.description=form.get().description;
			bibliographyItem.imageURL=form.get().imageURL;
			bibliographyItem.link=form.get().url;
			bibliographyItem.module=module;
			bibliographyItem.save();
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result addlesson(String module_acronym) throws java.sql.SQLException{
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		
		User user = User.find.byId(session("email"));
		
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<LessonItem_Form> form = Form.form(LessonItem_Form.class).bindFromRequest();
			
			Lesson lesson = new Lesson();
			lesson.acronym = form.get().acronym;
			lesson.name = form.get().name;
			lesson.description = form.get().description;
			lesson.shortDescription = form.get().description;
			lesson.imageURL = form.get().imageURL;
			lesson.number = module.lessons.size() + 1;
			lesson.module = module;
			lesson.save();

			List<User> users_list = module.users;
			for(User u : users_list) {
				SendMail.sendMail(u.email, "[Etoile] New lesson in "+module_acronym+" module !", "blablablablablabl some new lesson called "+form.get().name); 
			}
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result editbibliographyitem(String module_acronym,Long bibliography_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Bibliography bibliographyItem= Bibliography.find.byId(bibliography_id);
		if (bibliography_id==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		User user = User.find.byId(session("email"));

		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			Form<BibliographyItem_Form> form = Form.form(BibliographyItem_Form.class).bindFromRequest();
			bibliographyItem.title=form.get().title;
			bibliographyItem.description=form.get().description;
			bibliographyItem.imageURL=form.get().imageURL;
			bibliographyItem.link=form.get().url;
			bibliographyItem.save();
			
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	
	public static Result deletebibliographyitem(String module_acronym,Long bibliography_id) {
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Bibliography bibliographyItem= Bibliography.find.byId(bibliography_id);
		if (bibliography_id==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		User user = User.find.byId(session("email"));
	
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			bibliographyItem.delete();
		
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	
	public static Result addmoduledcontent(String module_acronym){

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Content_Form> form = Form.form(Content_Form.class).bindFromRequest();
			Content content= new Content();
			content.title=form.get().title;
			content.text=form.get().description;
			content.module=module;
			content.save();
		}


		return redirect(routes.Application.module(module.acronym));
	}
	
	
	
	public static Result editmoduledcontent(String module_acronym, Long content_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Content content= Content.find.byId(content_id);
		if (content==null){
			return redirect(routes.Application.module(module_acronym));
		}

		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Content_Form> form = Form.form(Content_Form.class).bindFromRequest();
			
			content.title=form.get().title;
			content.text=form.get().description;
			content.save();
		}


		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result deletemodulecontent(String module_acronym, Long content_id){

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Content content= Content.find.byId(content_id);
		if (content==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){

			content.delete();
		}


		return redirect(routes.Application.module(module.acronym));
	}
	

	
}

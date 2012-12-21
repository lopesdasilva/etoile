package controllers;


import models.User;

import models.module.Bibliography;
import models.module.Content;
import models.module.Module;

import controllers.routes;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.secured.*;


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
		
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<ModuleTitle_Form> form = form(ModuleTitle_Form.class).bindFromRequest();
			module.name=form.get().title;
			module.acronym=form.get().acronym;
			module.save();
		}
		
		
		return redirect(routes.Application.module(module.acronym));
	}

	public static Result editmoduledescription(String module_acronym){

		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<ModuleDescription_Form> form = form(ModuleDescription_Form.class).bindFromRequest();
			System.out.println(form.get().description);
			module.description=form.get().description;
			module.imageURL=form.get().imageURL;
			module.videoURL=form.get().videoURL;
			module.save();
		}


		return redirect(routes.Application.module(module_acronym));
	}

	
	public static Result addbibliographyitem(String module_acronym){
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Bibliography bibliographyItem= new Bibliography();
			Form<BibliographyItem_Form> form = form(BibliographyItem_Form.class).bindFromRequest();
			bibliographyItem.title=form.get().title;
			bibliographyItem.description=form.get().description;
			bibliographyItem.imageURL=form.get().imageURL;
			bibliographyItem.link=form.get().url;
			bibliographyItem.module=module;
			bibliographyItem.save();
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result editbibliographyitem(String module_acronym,Long bibliography_id){
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));

		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Bibliography bibliographyItem= Bibliography.find.byId(bibliography_id);
			Form<BibliographyItem_Form> form = form(BibliographyItem_Form.class).bindFromRequest();
			bibliographyItem.title=form.get().title;
			bibliographyItem.description=form.get().description;
			bibliographyItem.imageURL=form.get().imageURL;
			bibliographyItem.link=form.get().url;
			bibliographyItem.save();
			
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	
	public static Result deletebibliographyitem(String module_acronym,Long bibliography_item) {
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
	
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		Bibliography bibliography = Bibliography.find.byId(bibliography_item);
		bibliography.delete();
		
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result editmoduledcontent(String module_acronym, Long content_id){

		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Content_Form> form = form(Content_Form.class).bindFromRequest();
			Content content= Content.find.byId(content_id);
			content.title=form.get().title;
			content.text=form.get().description;
			content.save();
		}


		return redirect(routes.Application.module(module.acronym));
	}
	
}

package controllers;


import models.User;

import models.module.Bibliography;
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
	
	
	
	public static Result addbibliographyitem(String module_acronym){
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		
		
		System.out.println("a adicionar");
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
		
		System.out.println("Deleting module");
	
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		Bibliography bibliography = Bibliography.find.byId(bibliography_item);
		bibliography.delete();
		
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
}

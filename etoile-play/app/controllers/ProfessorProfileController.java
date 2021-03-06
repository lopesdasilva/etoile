package controllers;


import models.ProfessorContent;
import models.User;
import controllers.secured.SecuredProfessor;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


@Security.Authenticated(SecuredProfessor.class)
public class ProfessorProfileController  extends Controller {

	public static class Content_Form{
		public String imageURL;

		public String description;
	}

	public static class Adress_Form{
		public String address;
	}
	
	public static class Profile_Form{
		public String firstname;
		public String lastname;
		public String degree;
		public String imageURL;
		public String shortdescription;
	}


	public static Result editprofile(){
		if(SecuredProfessor.isProfessor(session("email"))){
			Form<Profile_Form> form = Form.form(Profile_Form.class).bindFromRequest();
			User user = User.find.byId(session("email"));
			user.professorProfile.firstname=form.get().firstname;
			user.professorProfile.lastname=form.get().lastname;
			user.professorProfile.degree=form.get().degree;
			user.professorProfile.imageURL=form.get().imageURL;
			user.professorProfile.shortdescription=form.get().shortdescription;
			user.professorProfile.save();		
			
			System.out.println("******* start:"+user.email+"*********");
	        System.out.println("Controller: ProfessorProfileController.java");
	        System.out.println("Method: editprofile");
	        System.out.println("Profile edited");
	        System.out.println("*******   end:"+user.email+"*********");
	        
		}
		return redirect(routes.ProfessorController.myprofile());
	}

	public static Result addprofilecontent(){
		if(SecuredProfessor.isProfessor(session("email"))){
			User user = User.find.byId(session("email"));

			Form<Content_Form> form = Form.form(Content_Form.class).bindFromRequest();
			ProfessorContent professorContent= new ProfessorContent();
			professorContent.description=form.get().description;
			professorContent.imageURL=form.get().imageURL;
			professorContent.professor=user.professorProfile;
			professorContent.save();
			
			System.out.println("******* start:"+user.email+"*********");
	        System.out.println("Controller: ProfessorProfileController.java");
	        System.out.println("Method: addprofilecontent");
	        System.out.println("Profile content added");
	        System.out.println("*******   end:"+user.email+"*********");

			return redirect(routes.ProfessorController.myprofile());
		}
		return redirect(routes.ProfessorController.myprofile());
	}

	public static Result deleteprofilecontent(Long professorcontent_id){
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email"))){
			ProfessorContent professorContent = ProfessorContent.find.byId(professorcontent_id);
			if(professorContent!=null){
			professorContent.delete();
			
			System.out.println("******* start:"+user.email+"*********");
	        System.out.println("Controller: ProfessorProfileController.java");
	        System.out.println("Method: deleteprofilecontent");
	        System.out.println("Profile content deleted");
	        System.out.println("*******   end:"+user.email+"*********");
	        
			}
		}

		return redirect(routes.ProfessorController.myprofile());
	}

	public static Result editprofilecontent(Long professorcontent_id){
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email"))){
			Form<Content_Form> form = Form.form(Content_Form.class).bindFromRequest();
			ProfessorContent professorContent = ProfessorContent.find.byId(professorcontent_id);
			professorContent.imageURL=form.get().imageURL;
			professorContent.description=form.get().description;
			professorContent.save();
			
			System.out.println("******* start:"+user.email+"*********");
	        System.out.println("Controller: ProfessorProfileController.java");
	        System.out.println("Method: editprofilecontent");
	        System.out.println("Profile content edited");
	        System.out.println("*******   end:"+user.email+"*********");
	        
		}

		return redirect(routes.ProfessorController.myprofile());
	}
	
	public static Result editprofilecontact(){
		if(SecuredProfessor.isProfessor(session("email"))){
			User user = User.find.byId(session("email"));
			Form<Adress_Form> form = Form.form(Adress_Form.class).bindFromRequest();

			user.professorProfile.contact=form.get().address;
			user.professorProfile.save();
			
			System.out.println("******* start:"+user.email+"*********");
	        System.out.println("Controller: ProfessorProfileController.java");
	        System.out.println("Method: editprofilecontact");
	        System.out.println("Profile contact edited");
	        System.out.println("*******   end:"+user.email+"*********");
	        
		}
		return redirect(routes.ProfessorController.myprofile());
	}
}

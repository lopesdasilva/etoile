package controllers;

import controllers.Application.Login;
import models.Student;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.register;
import controllers.extra.sha1;
import controllers.extra.SendMail;

public class Register extends Controller {

	public static class NewUser {

		public String inputUsername;
		public String inputEmail;
		public String inputPassword;
		public String inputRepeatPassword;
		public String inputCountry;
		public String inputName;

		public String firstName;
		public String lastname;
		
		public String validate() {
			if(inputPassword.length()<6)
				return "A password must be at least 6 characters";
			if(!inputPassword.equals(inputRepeatPassword))
				return "Passwords missmatch";
			for (User user : User.findAll()){
        		if(user.email.equals(inputEmail))
        			return "Email already exists";
        		else if(user.username.equals(inputUsername))
        			return "User already exists";
            	}
            return null;
        }


	}
	public static Result register2(){
		 return ok(register.render(form(Register.NewUser.class)));
	}
	public static Result register(){
		 Form<NewUser> form = form(NewUser.class).bindFromRequest();

		 
		 if(form.hasErrors()) {
	            return badRequest(register.render(form));
	        } else {
	        	Student student = new Student();
	        	student.save();
	        	
	        	User user = new User();
	        	user.name=form.get().inputName;
	        	user.username=form.get().inputUsername;
	        	user.password= sha1.parseSHA1Password( form.get().inputPassword);
	        	user.email=form.get().inputEmail;
	        	user.country=form.get().inputCountry;
	        	user.account_type = 0;
	        	user.studentProfile = student;
			SendMail.sendMail(form.get().inputEmail, "Welcome to Etoile "+form.get().inputUsername+"!", "Thank you for join us, help us to do a better community :)");    
	        	user.save();  
	        	
	        	
	        	
	       	 System.out.println("DEBUG**************START**************");
			 System.out.println("New user registration");
			 System.out.println("User: "+form.get().inputUsername);
			 System.out.println("Email: "+form.get().inputEmail);
			 System.out.println("DEBUG**************END**************");
	        	 flash("success", "User created!");
	        	System.out.println("Registering");
	        }
				
		 			return ok(login.render(form(Login.class)));
	}
}

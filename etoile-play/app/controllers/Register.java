package controllers;

import java.util.Date;
import java.util.Random;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import controllers.Application.Login;
import models.Student;
import models.User;
import models.module.University;
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
		
		public String gender;
		
		public String scientific_area;
		public String university;
		
		public String recaptcha_challenge_field;
		public String recaptcha_response_field;
		
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
	
	public static class register_Form {
        
        public String email;
        
	}
	
	public static Result resetpassword(){
		 Form<register_Form> form = Form.form(register_Form.class).bindFromRequest();
		 String email = form.get().email;
		 User user = User.findByEmail(email);
		 if(user!=null){
			 

			 String new_password = getRandomPassword(6);
			 user.password = sha1.parseSHA1Password(new_password);
			 System.out.println("NEW PASSWORD: "+new_password);
             SendMail.sendMail(user.email, "Your password has been changed.", "Hello "+user.studentProfile.firstname+", \nYour password has been changed to: " + new_password + "\nThe Étoile team.");
			 user.save();
				System.out.println("********* start:"+user.email+"***********");
				System.out.println("Controller: Register.java");
				System.out.println("Method: resetpassword");
				System.out.println("User has a new password");
				System.out.println("*********   end:"+user.email+"***********"); 
		 }
		 
		 
		 return ok(login.render(Form.form(Login.class)));
	}
	
	private static final String charset = "0123456789abcdefghijklmnopqrstuvwxyz";
	public static String getRandomPassword(int length) {
	    Random rand = new Random(System.currentTimeMillis());
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	        int pos = rand.nextInt(charset.length());
	        sb.append(charset.charAt(pos));
	    }
	    return sb.toString();
	}
	
	
	public static Result register2(){
		 return ok(register.render(Form.form(Register.NewUser.class)));
	}
	public static Result register(){
		 Form<NewUser> form = Form.form(NewUser.class).bindFromRequest();

		 
		 if(form.hasErrors()) {
	            return badRequest(register.render(form));
	        }if(! validateCaptcha(form.get().recaptcha_challenge_field,form.get().recaptcha_response_field)){
	       
	        	 flash("captcha", "The captcha is wrong. Try again, please.");
	        	 return ok(register.render(Form.form(Register.NewUser.class)));
	        }
	        	else {
	        
	        	Student student = new Student();
	        	student.save();
	        	
	        	User user = new User();
	        	user.name=form.get().inputName;
	        	user.username=form.get().inputUsername;
	        	user.password= sha1.parseSHA1Password( form.get().inputPassword);
	        	user.email=form.get().inputEmail.toLowerCase();
	        	user.country=form.get().inputCountry;
	        	user.account_type = 0;
	        	user.studentProfile = student;
	        	user.studentProfile.acronym=form.get().inputUsername;
	        	user.olduser = true;
            user.createDate=new Date();
	        	if(form.get().gender.equals("male")){
	        		user.studentProfile.male = true;
	        	}else{
	        		user.studentProfile.male = false;
	        	}
	        	user.studentProfile.imageURL = "http://forum.must.ac.ug/sites/default/files/default-avatar.png";
	        	user.save();
	        	
	        	University university = University.findByName(form.get().university);
	        	
	        	student.firstname = user.name;
	        	student.contact = user.email;
	        	student.user=user;
	        	student.scientific_area = form.get().scientific_area;
	        	student.university = university;
	        	student.save();

                SendMail.sendMail(user.email, "Welcome to Étoile Platform", "Welcome to Étoile "+user.studentProfile.firstname+", \nThank you for joining the Étoile Platform. Help us to do a better community! \nThe Étoile team.");




            System.out.println("********************************");
				System.out.println("Controller: Register.java");
				System.out.println("Method: register");
				 System.out.println("User: "+form.get().inputUsername);
				 System.out.println("Email: "+form.get().inputEmail.toLowerCase());
				 System.out.println("CAPTCHA: "+validateCaptcha(form.get().recaptcha_challenge_field,form.get().recaptcha_response_field));
				System.out.println("********************************");
	        	 flash("success", "User created!");
	        	System.out.println("Registering");
	        }
				
		 			return ok(login.render(Form.form(Login.class)));
	}
	public static boolean validateCaptcha(String recaptcha_challenge_field, String recaptcha_response_field){
		
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6LcJht0SAAAAAAQ26lclTYNg3yyTtoRRfKTnebjN");
		String remoteAddr = request().remoteAddress();

		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, recaptcha_challenge_field, recaptcha_response_field);
		 return reCaptchaResponse.isValid();
	}
	
}

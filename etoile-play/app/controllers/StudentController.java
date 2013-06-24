package controllers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import controllers.Application.Login;
import controllers.BlogController.Comment_Form;
import controllers.ProfessorProfileController.Profile_Form;
import controllers.StudentTestController.OpenQuestionSuggestion;

import models.Blog;
import models.Modulescore;
import models.SubtopicReputation;
import models.continent.Continent;
import models.curriculum.Category;
import models.Comment;

import models.User;
import models.manytomany.Usertest;
import models.module.Module;
import models.module.Lesson;
import models.module.University;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionGroup;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.collection.mutable.ArrayLike;
import views.html.*;
import controllers.extra.SendMail;
import controllers.extra.sha1;
import controllers.secured.*;

/**
 * Manage StudentController related operations.
 */
@Security.Authenticated(Secured.class)
public class StudentController extends Controller {

	private final static boolean DEBUG=true;

	/**
	 * Display the homescreen.
	 */

	public static class Profile_Form{
		public String firstname;
		public String lastname;
		public String contact;
		public String imageURL;
		public String webpage;
		public String shortdescription;
		public String address;

		public String scientific_area;
		public String degree; 
		public String description;
		public String university;
	}

	public static class NewPassword_Form{
		public String inputPassword;
	}

	public static class Privacy_Form{
		public int privacy;
	}



	public static Result index() {
		User user = User.find.byId(session("email"));


		if(Secured.isStudent(session("email"))){
			List<Blog> blogs = Blog.find.orderBy("date desc").findList();

			List<Category> categories = Category.find.all();

			user.studentProfile.refresh();
			if(blogs.size()>3)
				blogs=blogs.subList(0, 3);
			
			
			System.out.println("Class: StudentController; Method: index; user: "+user.email+" browser: "+request().getHeader("user-agent"));
			return ok(home.render(user, blogs, categories));
		}
		if (SecuredProfessor.isProfessor(session("email"))){

			return ProfessorController.index();
		}
		return redirect(routes.Application.index());
	}

	public static Result editprofile(){
		if(Secured.isStudent(session("email"))){
			Form<Profile_Form> form = Form.form(Profile_Form.class).bindFromRequest();
			User user = User.find.byId(session("email"));
			user.studentProfile.webpage = form.get().webpage;
			user.studentProfile.firstname=form.get().firstname;
			user.studentProfile.lastname=form.get().lastname;
			user.studentProfile.contact=form.get().contact;
			user.studentProfile.imageURL=form.get().imageURL;
			user.studentProfile.shortdescription=form.get().shortdescription;
			user.studentProfile.address = form.get().address;
			user.studentProfile.save();	
			System.out.println("Class: StudentController; Method: editprofile; Profiled saved; user: "+user.email+" browser: "+request().getHeader("user-agent"));
		}
		return redirect(routes.ProfessorController.myprofile());
	}

	public static Result settings(){
		if(Secured.isStudent(session("email"))){
			User user = User.find.byId(session("email"));
			user.studentProfile.refresh();
			
			System.out.println("Class: StudentController; Method: settings; user: "+user.email+" browser: "+request().getHeader("user-agent"));
			return ok(views.html.secured.studentSettings.render(user));
		}
		return null;
	}

	public static Result editprofileabout(){
		if(Secured.isStudent(session("email"))){
			Form<Profile_Form> form = Form.form(Profile_Form.class).bindFromRequest();
			User user = User.find.byId(session("email"));
			user.studentProfile.scientific_area = form.get().scientific_area;
			user.studentProfile.degree = form.get().degree;
			
			University university = University.findByName(form.get().university);
			if(university != null){
				user.studentProfile.university = university;
			}
			user.studentProfile.description = form.get().description;
			user.studentProfile.save();
			user.save();
			System.out.println("Class: StudentController; Method: editprofileabout; Profile about saved ;user: "+user.email+" browser: "+request().getHeader("user-agent"));
		}
		return redirect(routes.ProfessorController.myprofile());
	}

	public static Result changepassword(){
		if(Secured.isStudent(session("email"))){
			User user = User.find.byId(session("email"));
			Form<NewPassword_Form> form = Form.form(NewPassword_Form.class).bindFromRequest();
			user.password = sha1.parseSHA1Password(form.get().inputPassword);
//			SendMail.sendMail(user.email, "Your password has been changed, "+user.username+".", "Your new password is: " + form.get().inputPassword);
			user.save();
			System.out.println("Class: StudentController; Method: changepassword; Password changed; user: "+user.email+" browser: "+request().getHeader("user-agent"));
		}
		return redirect(routes.StudentController.settings());
	}

	public static Result changeprivacy(){
		if(Secured.isStudent(session("email"))){

			User user = User.find.byId(session("email"));
			Form<Privacy_Form> form = Form.form(Privacy_Form.class).bindFromRequest();

			if(form.get().privacy==1){

			user.studentProfile.privateProfile = true;
			user.studentProfile.save();
			}else{

			user.studentProfile.privateProfile = false;
			user.studentProfile.save();
			}
			System.out.println("Class: StudentController; Method: changeprivacy; privacy saved;user: "+user.email+" browser: "+request().getHeader("user-agent"));
		}
		return redirect(routes.StudentController.settings());
	}

	public static Result module(String module_acronym) {

			Module module = Module.findByAcronym(module_acronym);
			if (module==null){
				System.out.println("The module does not exist.");
				return redirect(routes.Application.modules());
			}


			User user = User.find.byId(session("email"));
			List<Category> categories = Category.find.all();
			module.language.refresh();


			if (!user.modules.contains(module)){
				System.out.println("Class: StudentController; Method: module: "+module_acronym+" user does not contain this module redirecting to signup module page ;user: "+user.email+" browser: "+request().getHeader("user-agent"));

				return ok(views.html.secured.moduleGeneral.render(user, categories,
						module));
			}
			else{
				System.out.println("Class: StudentController; Method: module: "+module_acronym+" redirecting to module page ;user: "+user.email+" browser: "+request().getHeader("user-agent"));
				return ok(views.html.secured.module
						.render(user, categories, module));
			}
	}

	public static Result signupmodule(String module_acronym) {
		Module module = Module.findByAcronym(module_acronym);
		if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}


		User user = User.find.byId(session("email"));
		List<Category> categories = Category.find.all();



		// Trying to add the module to user

		if (Secured.isStudent(user.email) && !user.modules.contains(module)) {
			


			user.modules.add(module);
			user.olduser=true;
			user.save();
			module.save();
			

			Modulescore modulescore = new Modulescore();
			modulescore.module = module;
			modulescore.user = user;
			modulescore.score = 0;
			modulescore.save();
			System.out.println("Class: StudentController; Method: signupmodule: "+module_acronym+" signup complete: "+user.email+" browser: "+request().getHeader("user-agent"));

		}


		// Has this module
		return redirect(routes.Application.module(module_acronym));

	}

	public static Result question(int question_number, Long test_id,String lesson_acronym,String module_acronym){

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}

		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}

		if(Secured.isStudent(session("email"))){
		Test test = models.test.Test.find.byId(test_id);
		User user = User.find.byId(request().username());

		Usertest usertest = Usertest.findByUserAndTest(user.email,
				test.id);
	
		List<Answer> test_answers = Answer.findByUserTestAndTestId(usertest.id,
				test_id);
		if (test_answers.isEmpty()) {
			for(QuestionGroup g: test.groups){
			for (Question q : g.questions) {
				if (q.typeOfQuestion == 0) {
					Answer empty_answer = new Answer();
					empty_answer.answer = "No answer.";
					empty_answer.openQuestion = q;
					empty_answer.test = test;
					empty_answer.usertest.user = user;
					empty_answer.group = g;
					empty_answer.save();
					test.answers.add(empty_answer);
					test.save();
				}
			}
			}
		}


		if (question_number<=test.groups.size() && question_number>0){

		QuestionGroup group = test.groups.get(question_number-1);

		QuestionGroup group_aux = new QuestionGroup();
		group_aux=new QuestionGroup();
		group_aux.id = group.id;
		group_aux.imageURL = group.imageURL;
		group_aux.number = group.number;
		group_aux.question = group.question;
		group_aux.test = group.test;
		group_aux.videoURL = group.videoURL;

		System.out.println("Group_aux created");

		for(Question q: group.questions){
			Question q_aux = new Question();
			q_aux.id = q.id;
			q_aux.imageURL = q.imageURL;
			q_aux.lesson = q.lesson;
			q_aux.number = q.number;
			q_aux.question = q.question;
			q_aux.typeOfQuestion = q.typeOfQuestion;
			q_aux.user = q.user;
			q_aux.videoURL = q.videoURL;
			q_aux.urls=q.urls;


			group_aux.questions.add(q_aux);
			if(q.typeOfQuestion==2 || q.typeOfQuestion == 1){

			List<Hypothesis> hypothesis_aux=Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
			if (hypothesis_aux.size()<1){
				for (Hypothesis h: Hypothesis.findByQuestion(q.id)){
					Hypothesis new_h=new Hypothesis();
					new_h.number=h.number;
					new_h.question=h.question;
					new_h.text=h.text;
					new_h.user=user;
					new_h.save();
				}
				hypothesis_aux=Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
			}

			q_aux.hypothesislist=hypothesis_aux;
			q=q_aux;
			}
			else{
				q_aux.openanswer=Answer.findByUserTestAndQuestion( usertest.id,q.id);
				q=q_aux;
			}



			}

		
		System.out.println("Class: StudentController; Method: question: Test :"+test_id+" OK lesson: "+lesson.acronym+" module: "+module_acronym+"; user: "+user.email+" browser: "+request().getHeader("user-agent"));
		return ok(views.html.secured.question.question.render(user,module,lesson,test,group_aux,usertest));
		}
		else
			return ok(views.html.statics.error.render());
		}
		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.index();
		}
		return redirect(routes.Application.index());

	}

	public static Result lesson(String lesson_acronym, String module_acronym) {
		if(Secured.isStudent(session("email"))){
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		User user = User.find.byId(request().username());
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null || !module.users.contains(user)){
			return redirect(routes.Application.module(module_acronym));
		}




		List<Category> categories = Category.find.all();


		module.language.refresh();

		System.out.println("Class: StudentController; Method: lesson; lesson: "+lesson.acronym+" module: "+module_acronym+"; user: "+user.email+" browser: "+request().getHeader("user-agent"));

		return ok(views.html.secured.lesson.render(user, categories, lesson,
				module));
		}
		if (SecuredProfessor.isProfessor(session("email"))){
			//Subsituir por module
			return ProfessorController.lesson(lesson_acronym,module_acronym);
		}
		return redirect(routes.Application.index());
	}

	public static Result modules() {
		List<Module> allModules = Module.find.all();
		List<Category> categories = Category.find.all();

        List<Module> modulesToPresent = new LinkedList<Module>();


        for(Module m: allModules){
            if(m.published){
                  modulesToPresent.add(m);
            }
        }
		// check this line
		User user = User.find.byId(session("email"));
		System.out.println("Class: StudentController; Method: modules; modules size: "+modulesToPresent.size()+"; user: "+user.email+" browser: "+request().getHeader("user-agent"));
		return ok(views.html.secured.modules.render(user, modulesToPresent,
				categories));
	}

	public static Result contact() {
		List<Category> categories = Category.find.all();

		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.contact();
		}


		// check this line
		User user = User.find.byId(session("email"));
		user.studentProfile.refresh();
		return ok(views.html.secured.contact.render(user, categories));
	}

	public static Result about() {

		List<Category> categories = Category.find.all();

		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.about();
		}

		User user = User.find.byId(session("email"));
		user.studentProfile.refresh();
		return ok(views.html.secured.about.render(user, categories));
	}

	public static Result news() {

		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.news();
		}
		User user = User.find.byId(session("email"));
		user.studentProfile.refresh();
		System.out.println("Class: StudentController; Method: news; news size: "+Blog.find.all().size()+"; user: "+user.email+" browser: "+request().getHeader("user-agent"));

		return ok(views.html.secured.blogs.render(user,
				Blog.getAllBlogs(),Category.find.all(),Continent.getAllContinents()
				));
	}

	public static Result digitalcampus() {

		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.digitalcampus();
		}

		return ok(views.html.secured.digitalcampus.render(User.find.byId(session("email")),
                Category.find.all(),Continent.getAllContinents()
				));


	}

	public static Result continent(List<Category> categories,
			List<Continent> continents, Continent continent,
			List<University> universities) {

		User user=User.find.byId(session("email"));
		System.out.println("Class: StudentController; Method: continent; user: "+user.email+" browser: "+request().getHeader("user-agent"));

		return ok(views.html.secured.continent.render(user,
				categories,continents,continent,continent.universities));

	}
	public static Result olduser(){
		User user = User.find.byId(session("email"));
		user.olduser=!user.olduser;
		user.save();
		System.out.println("Class: StudentController; Method: oluser; user: "+user.email+" is now olduser browser: "+request().getHeader("user-agent"));

		return ok("done");
	}

}
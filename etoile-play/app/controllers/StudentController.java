package controllers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import controllers.Application.Login;
import controllers.BlogController.Comment_Form;
import controllers.StudentTestController.OpenQuestionSuggestion;

import models.Blog;
import models.continent.Continent;
import models.curriculum.Category;
import models.Comment;

import models.User;
import models.manytomany.UserTest;
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
	public static Result index() {
		if(Secured.isStudent(session("email"))){
			List<Blog> blogs = Blog.getAllBlogs();
			User user = User.find.byId(session("email"));
			List<Category> categories = Category.getAllCategories();

			
			if(blogs.size()>3)
				blogs=blogs.subList(0, 3);
			return ok(home.render(user, blogs, categories));
		}
		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.index();
		}
		return redirect(routes.Application.index());
	}

	public static Result module(String module_acronym) {
		
			Module module = Module.findByAcronym(module_acronym);
			User user = User.find.byId(session("email"));
			List<Category> categories = Category.getAllCategories();

			if(DEBUG){
				System.out.println("---USER: "+user+" is Student");
			}
			
			if (!user.modules.contains(module))
				return ok(views.html.secured.moduleGeneral.render(user, categories,
						module));
			else
				// Has this module
				return ok(views.html.secured.module
						.render(user, categories, module));
		
	}

	public static Result signupmodule(String module_acronym) {
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		List<Category> categories = Category.getAllCategories();

		// Trying to add the module to user

		if (!user.modules.contains(module)) {
			user.modules.add(module);
			user.save();
			module.save();

			for (Lesson lesson : module.lessons) {
				for (Test test : lesson.tests) {
					UserTest usertest = UserTest.findByUserAndTest(user.email,
							test.id);
					if (usertest == null) {
						UserTest user_test = new UserTest();
						user_test.user = user;
						user_test.test = test;
						user_test.expired = false;
						user_test.inmodule = false;
						user_test.submitted = false;
						user_test.save();
						user.tests.add(user_test);
						test.users.add(user_test);
						user.save();
						test.save();
						user_test.save();
					}
				}
			}
		}

		// Has this module
		return ok(views.html.secured.module.render(user, categories, module));

	}

	public static Result question(int question_number, Long test_id,String lesson_acronym,String module_acronym){
		if(Secured.isStudent(session("email"))){
		Test test = models.test.Test.find.byId(test_id);
		User user = User.find.byId(request().username());
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		UserTest usertest = UserTest.findByUserAndTest(user.email,
				test.id);
		
		List<Answer> test_answers = Answer.findByUserEmailAndTestId(user.email,
				test_id);
		if (test_answers.isEmpty()) {
			for(QuestionGroup g: test.groups){
			for (Question q : g.questions) {
				if (q.typeOfQuestion == 0) {
					Answer empty_answer = new Answer();
					empty_answer.answer = "No answer.";
					empty_answer.openQuestion = q;
					empty_answer.test = test;
					empty_answer.user = user;
					empty_answer.group = g;
					empty_answer.save();
					test.answers.add(empty_answer);
					test.save();
				}
			}
			}
		}
		
		
		if (question_number<=test.groups.size() && question_number>0){
		System.out.println("A imprimir as questoes");
		
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
			
			
			//q_aux.hypothesislist?????
			group_aux.questions.add(q_aux);
			
			System.out.println("QUESTION: "+q.id);
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
				q_aux.openanswer=Answer.findByUserAndQuestion( user.email,q.id);
				q=q_aux;
			}
			
			
			
			}
		
		
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
		List<Category> categories = Category.getAllCategories();
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		User user = User.find.byId(request().username());
		
		
		return ok(views.html.secured.lesson.render(user, categories, lesson,
				module, form(OpenQuestionSuggestion.class)));
		}
		if (SecuredProfessor.isProfessor(session("email"))){
			//Subsituir por module
			return ProfessorController.lesson(lesson_acronym,module_acronym);
		}
		return redirect(routes.Application.index());
	}
	
	public static Result modules() {
		List<Module> allModules = Module.getAllModules();
		List<Category> categories = Category.getAllCategories();

		// check this line
		User user = User.find.byId(session("email"));
		return ok(views.html.secured.modules.render(user, allModules,
				categories));
	}

	public static Result contact() {
		List<Category> categories = Category.getAllCategories();

		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.contact();
		}
		
		
		// check this line
		User user = User.find.byId(session("email"));
		return ok(views.html.secured.contact.render(user, categories));
	}

	public static Result about() {
	
		List<Category> categories = Category.getAllCategories();
		
		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.about();
		}
		
		User user = User.find.byId(session("email"));
		return ok(views.html.secured.about.render(user, categories));
	}
	
	public static Result news() {
		
		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.news();
		}

		return ok(views.html.secured.blogs.render(User.find.byId(session("email")),
				Blog.getAllBlogs(),Category.getAllCategories(),Continent.getAllContinents()
				));
	}
	
	public static Result digitalcampus() {

		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.digitalcampus();
		}
		
		return ok(views.html.secured.digitalcampus.render(User.find.byId(session("email")),
				Category.getAllCategories(),Continent.getAllContinents()
				));
		
		
	}

	public static Result continent(List<Category> categories,
			List<Continent> continents, Continent continent,
			List<University> universities) {

		return ok(views.html.secured.continent.render(User.find.byId(session("email")),
				categories,continents,continent,continent.universities));
		   
	}

}

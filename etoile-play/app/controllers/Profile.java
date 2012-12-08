package controllers;

import java.util.LinkedList;
import java.util.List;

import controllers.Application.Login;

import models.Blog;
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

/**
 * Manage Profile related operations.
 */
@Security.Authenticated(Secured.class)
public class Profile extends Controller {

	/**
	 * Describes the comment form.
	 */
	public static class Comment {

		public String comment;
	}

	public static class QuestionAnswer {

		public String qanswer;
	}

	public static class OneChoiceQuestionAnswer {

		public Long ocqanswer;
	}

	public static class MultipleChoiceQuestionAnswer {

		public int[] mcqanswers = new int[4];
	}

	public static class OpenQuestionSuggestion {

		public String openquestionsuggestion;

	}

	/**
	 * Display the dashboard.
	 */
	public static Result index() {
		List<Blog> blogs = Blog.getAllBlogs();
		User user = User.find.byId(request().username());
		List<Category> categories = Category.getAllCategories();

		// This is to load Universities(weird)
		for (Module c : user.modules) {
			System.out.println(c.university.name);
		}

		return ok(home.render(user, blogs, categories));
	}

	public static Result module(String module_acronym) {
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		List<Category> categories = Category.getAllCategories();

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
						user_test.submited = false;
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

	public static Result addquestion(Long test_id, String lesson_acronym,
			String module_acronym) {
		User user = User.find.byId(request().username());
		// Test test = models.test.Test.find.byId(test_id);
		UserTest usertest = UserTest.findByUserAndTest(user.email, test_id);
		usertest.inmodule = true;
		usertest.save();

		List<Category> categories = Category.getAllCategories();
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);

		Form<Profile.OpenQuestionSuggestion> form = form(
				Profile.OpenQuestionSuggestion.class).bindFromRequest();

		System.out.println("MODULESIZE: " + lesson.questions.size());
		Question new_question = new Question();
		new_question.question = form.get().openquestionsuggestion;
		new_question.lesson = lesson;
		new_question.user = user;
		new_question.imageURL = "http://2.bp.blogspot.com/_n9nhDiNysbI/TTgaGiOpZGI/AAAAAAAAANo/eWv-c-7041I/s1600/ponto-interrogacao-21.jpg";
		new_question.save();
		lesson.save();
		System.out.println("MODULESIZE2: " + lesson.questions.size());

		return ok(views.html.secured.lesson.render(user, categories, lesson,
				module, form(OpenQuestionSuggestion.class)));
	}

	public static Result test(Long test_id, String lesson_acronym,String module_acronym) {
		List<Category> categories = Category.getAllCategories();
		Test test = models.test.Test.find.byId(test_id);
		//DEBUG
		for(QuestionGroup g : test.groups){
			System.out.println("GROUP: " + g.question);
		}
		
		User user = User.find.byId(request().username());

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
					empty_answer.save();
					test.answers.add(empty_answer);
					test.save();
				}
			}
			}
		}

		List<Answer> openanswers = Answer.findByUserEmailAndTestId(user.email,
				test_id);

		Test test_aux = test;
		test_aux.answers = openanswers;

		System.out.println(test.answers);

		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);



		return ok(views.html.secured.test.render(user, module, lesson,
				categories, test_aux, form(QuestionAnswer.class),
				form(OneChoiceQuestionAnswer.class)));

	}

	
	public static Result question(int question_number, Long test_id,String lesson_acronym,String module_acronym){
		Test test = models.test.Test.find.byId(test_id);
		User user = User.find.byId(request().username());
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		UserTest usertest = UserTest.findByUserAndTest(user.email,
				test.id);
		
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
				for (Hypothesis h: q.hypothesislist){
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
	
	public static Result lesson(String lesson_acronym, String module_acronym) {

		List<Category> categories = Category.getAllCategories();
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		User user = User.find.byId(request().username());

		return ok(views.html.secured.lesson.render(user, categories, lesson,
				module, form(OpenQuestionSuggestion.class)));

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

		// check this line
		User user = User.find.byId(session("email"));
		return ok(views.html.secured.contact.render(user, categories));
	}

	public static Result about() {
		List<Category> categories = Category.getAllCategories();

		// check this line
		User user = User.find.byId(session("email"));
		return ok(views.html.secured.about.render(user, categories));
	}

	public static Result blog(Long blog) {
		List<Category> categories = Category.getAllCategories();

		// check this line
		User user = User.find.byId(session("email"));
		return ok(views.html.secured.blog.render(user, Blog.find.byId(blog),
				categories, form(Comment.class)));
	}

	public static Result postcomment(Long blog) {
		Form<Profile.Comment> form = form(Profile.Comment.class)
				.bindFromRequest();
		List<Category> categories = Category.getAllCategories();
		User user = User.find.byId(session("email"));

		// New Comment
		models.Comment c = new models.Comment();
		c.text = form.get().comment;
		c.blog = Blog.find.byId(blog);
		c.user = user;
		c.save();

		return ok(views.html.secured.blog.render(user, Blog.find.byId(blog),
				categories, form(Comment.class)));
	}

//	public static Result postquestionanswer(String module_acronym,
//			String lesson_acronym, Long test_id, Long question_id) {
//		Form<Profile.QuestionAnswer> form = form(Profile.QuestionAnswer.class)
//				.bindFromRequest();
//		System.out.println("Answer: " + form.get().qanswer + "Question: "
//				+ question_id);
//		
//		User user = User.findByEmail(request().username());
//
//		Question question = Question.find.byId(question_id);
//		Test test = Test.find.byId(test_id);
//
//		Answer userAnswer = Answer.findByUserAndQuestion(request()
//				.username(),question_id);
//		if (userAnswer == null) {
//
//			Answer answer = new Answer();
//			answer.answer = form.get().qanswer;
//			answer.openQuestion = question;
//			answer.test = test;
//			answer.user = User.find.byId(request().username());
//			answer.save();
//
//		} else {
//			userAnswer.answer = form.get().qanswer;
//			userAnswer.save();
//		}
//		
//		List<Answer> openanswers = Answer.findByUserEmailAndTestId(user.email,
//				test_id);
//		List<ChoiceAnswer> test_choiceanswers = ChoiceAnswer
//				.findByUserEmailAndTestId(user.email, test_id);
//		
//		List<Category> categories = Category.getAllCategories();
//		Test test_aux = test;
//		test_aux.answers = openanswers;
//		test_aux.choiceanswers = test_choiceanswers;
//		Module module = Module.findByAcronym(module_acronym);
//		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
//
//		return ok(views.html.secured.test.render(user, module, lesson,
//				categories, test_aux, form(QuestionAnswer.class),
//				form(OneChoiceQuestionAnswer.class)));
//
//	}

//	public static Result postonechoicequestionanswer(String module_acronym,
//			String lesson_acronym, Long test_id, Long question_id) {
//		
//
//		Test test = Test.find.byId(test_id);
//		User user = User.find.byId(request().username());
//		Question question = Question.find.byId(question_id);
//
//		ChoiceAnswer last_answer = ChoiceAnswer.findByUserAndQuestion(
//				question_id, user.email);
//
//		if (last_answer == null) {
//			last_answer = new ChoiceAnswer();
//			last_answer.question = question;
//			last_answer.test = test;
//			last_answer.user = user;
//			last_answer.save();
//		}
//		last_answer.delete();
//		new_answer.question = question;
//		new_answer.test = test;
//		new_answer.user = user;
//		
//		if(question.typeOfQuestion == 2){
//			Form<Profile.MultipleChoiceQuestionAnswer> form = form(
//					Profile.MultipleChoiceQuestionAnswer.class).bindFromRequest();
//		for (int h : form.get().mcqanswers) {
//			if (h != 0) {
//				new_answer.question = question;
//				new_answer.test = test;
//				new_answer.user = user;
//				System.out.println("MULTIPLECHOICE: " + h);
//				Hypothesis hypothesis_selected = Hypothesis.find.byId((long) h);
//				System.out.println("Encontrei? " + hypothesis_selected.text);
//				new_answer.hypothesislist.add(hypothesis_selected);
//				hypothesis_selected.save();
//
//				new_answer.save();
//				System.out
//						.println("Salvei " + new_answer.hypothesislist.size());
//
//			}
//		}
//		}else if(question.typeOfQuestion==1){
//			Form<Profile.OneChoiceQuestionAnswer> form = form(
//					Profile.OneChoiceQuestionAnswer.class).bindFromRequest();
//			long h = form.get().ocqanswer;
//			System.out.println("ONECHOICE: " + h);
//			Hypothesis hypothesis_selected = Hypothesis.find.byId(h);
//			System.out.println("Encontrei? " + hypothesis_selected.text);
//			new_answer.hypothesislist.add(hypothesis_selected);
//			hypothesis_selected.save();
//
//			new_answer.save();
//			System.out
//					.println("Salvei " + new_answer.hypothesislist.size());
//			
//		}
//
//		List<Category> categories = Category.getAllCategories();
//		List<Answer> openanswers = Answer.findByUserEmailAndTestId(user.email,
//				test_id);
//		
//		Test test_aux = test;
//		test_aux.answers = openanswers;
//
//		Module module = Module.findByAcronym(module_acronym);
//		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
//
//		return ok(views.html.secured.test.render(user, module, lesson,
//				categories, test_aux, form(QuestionAnswer.class),
//				form(OneChoiceQuestionAnswer.class)));
//	}

	public static Result postquestion(
			int question_number, String module_acronym, String lesson_acronym, Long test_id,
			Long question_id) {
		
		User user = User.find.byId(request().username());
		Question question = Question.find.byId(question_id);
		
			if(question.typeOfQuestion == 1){
				Form<Profile.OneChoiceQuestionAnswer> form = form(Profile.OneChoiceQuestionAnswer.class).bindFromRequest();
				List<Hypothesis> last_answers = Hypothesis.findByUserEmailAndQuestion(user.email, question_id); // Respostas Guardadas
				for(Hypothesis h : last_answers){
					h.selected = false;
					h.save();
				}
				
				Hypothesis hypothesis = Hypothesis.find.byId(form.get().ocqanswer);
				hypothesis.selected = true;
				hypothesis.save();
				
			}else if(question.typeOfQuestion == 2) {
			// GUARDAR ESCOLHA MULTIPLA E ONE CHOICE

			Form<Profile.MultipleChoiceQuestionAnswer> form = form(Profile.MultipleChoiceQuestionAnswer.class).bindFromRequest();
			for (int h : form.get().mcqanswers) {
				System.out.println("VALOR: " + h);
			}
			
			List<Hypothesis> last_answers = Hypothesis.findByUserEmailAndQuestion(user.email, question_id); // Respostas Guardadas
			
			for(Hypothesis h : last_answers){
				h.selected = false;
				h.save();
				
			}
			
			for(int h : form.get().mcqanswers){
				if(h!=0){
					for(Hypothesis hypothesis: last_answers){
						if(hypothesis.id == h){
							hypothesis.selected = true;
							hypothesis.save();
						}
					}
				}
			}
		} else {
			//GUARDAR OPEN QUESTION - WORKING
			
			Form<Profile.QuestionAnswer> form = form(Profile.QuestionAnswer.class).bindFromRequest();
			System.out.println("Open Answer: " + form.get().qanswer);
			
			Answer answer = Answer.findByUserAndQuestion(user.email, question_id); // Resposta Guardada
			answer.answer = form.get().qanswer;
			answer.save();
		}
		
		//COPIA DO METODO question() - Temos que arranjar maneira de isto não ficar tudo repetido
		
		Test test = models.test.Test.find.byId(test_id);
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		UserTest usertest = UserTest.findByUserAndTest(user.email,
				test.id);
		
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
				for (Hypothesis h: q.hypothesislist){
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
		
		//FIM COPIA DO METODO question()
	}

	// -- Queries

	public static Model.Finder<Long, Profile> find = new Model.Finder(
			Long.class, Profile.class);

	/**
	 * Check if a user is a member of this project
	 */
	public static boolean isMember(Long project, String user) {
		return find.where().eq("members.email", user).eq("id", project)
				.findRowCount() > 0;
	}
}

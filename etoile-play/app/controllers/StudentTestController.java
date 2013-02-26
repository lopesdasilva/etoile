package controllers;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import models.SubtopicReputation;
import models.User;
import models.curriculum.Category;
import models.manytomany.Usertest;
import models.module.Lesson;
import models.module.Module;
import models.test.Answer;
import models.test.AnswerMarker;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionEvaluation;
import models.test.question.QuestionGroup;
import models.test.question.URL;
import play.api.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.secured.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controllers.extra.SendMail;

/**
 * Manage Test related operations.
 */
@Security.Authenticated(Secured.class)
public class StudentTestController extends Controller {
	
	
	public static class QuestionAnswer {

		public String qanswer;
	}

	public static class OneChoiceQuestionAnswer {

		public Long ocqanswer;
	}

	public static class MultipleChoiceQuestionAnswer {

		public int[] mcqanswers = new int[50];
		
	}

	public static class OpenQuestionSuggestion {

		public String openquestionsuggestion;

	}
	
	public static class OpenAnswerSuggestion {

		public String openanswersuggestion;

	}
	
	public static class URL_form {
		public String name;
		public String url;
		public String descriptionUrl;
		public String image;
	}


	
	public static Result questionanalysis(Long question_number, Long test_id,String lesson_acronym,String module_acronym){
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		User user = User.find.byId(request().username());
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null || !module.users.contains(user)){
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			return redirect(routes.Application.module(module_acronym)+"#tests");
		}
		
		
		
		
			
			
			Usertest usertest = Usertest.findByUserAndTest(user.email,test.id);
			
//			List<Answer> test_answers = Answer.findByUserEmailAndTestId(user.email,
//					test_id);
			
			if (question_number<=test.groups.size() && question_number>0){
				//System.out.println("A imprimir as questoes");
				
				QuestionGroup group = test.groups.get((int) (question_number-1));
				
				QuestionGroup group_aux = new QuestionGroup();
				group_aux=new QuestionGroup();
				group_aux.id = group.id;
				group_aux.imageURL = group.imageURL;
				group_aux.number = group.number;
				group_aux.question = group.question;
				group_aux.test = group.test;
				group_aux.videoURL = group.videoURL;
				
				//System.out.println("Group_aux created");
				
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
					
					//System.out.println("QUESTION: "+q.id);
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
				
				LinkedList<QuestionEvaluation> evaluations = new LinkedList<QuestionEvaluation>();
				for(Question que : group_aux.questions){
					evaluations.add(QuestionEvaluation.findByUserAndQuestion(usertest.id, que.id));
				}
				
				return ok(views.html.secured.question.questionanalysis.render(user,module,lesson,test,group_aux,usertest, evaluations));
				}else{
					return ok(views.html.statics.error.render());
				}
			
				
	}
	
	
	public static Result question(int question_number, Long test_id,String lesson_acronym,String module_acronym){
		
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		User user = User.find.byId(request().username());
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null || !module.users.contains(user)){
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		
	
		Test test_aux= Test.find.byId(test_id);
		

		if(Secured.isStudent(user.email) && user.isUserSignupTest(test) && user.userSuggestedQuestion(test) && test_aux.published && !test_aux.expired ){
		Usertest usertest = Usertest.findByUserAndTest(user.email,
				test.id);
		
		
		
		
		
		List<Answer> test_answers = Answer.findByUserTestAndTestId(usertest.id,
				test_id);
		
		
		
			
		
		if (test_answers.isEmpty()) {
			System.out.println("There is no answers creating.");
			for(QuestionGroup g: test.groups){
			for (Question q : g.questions) {
				if (q.typeOfQuestion == 0) {
					Answer empty_answer = new Answer();
					empty_answer.answer = "No answer.";
					empty_answer.openQuestion = q;
					empty_answer.test = test;
					empty_answer.usertest=usertest;
					empty_answer.group = g;
					empty_answer.openQuestion = q;
					empty_answer.save();
					test.answers.add(empty_answer);
					test.save();
				}
			}
			}
			
		}
		
		
		if (question_number<=test.groups.size() && question_number>0){
		//System.out.println("A imprimir as questoes");
		
		QuestionGroup group = test.groups.get(question_number-1);
		
		QuestionGroup group_aux = new QuestionGroup();
		group_aux=new QuestionGroup();
		group_aux.id = group.id;
		group_aux.imageURL = group.imageURL;
		group_aux.number = group.number;
		group_aux.question = group.question;
		group_aux.test = group.test;
		group_aux.videoURL = group.videoURL;
		
		//System.out.println("Group_aux created");
		
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
			q_aux.keywords=q.keywords;
			
			//q_aux.hypothesislist?????
			group_aux.questions.add(q_aux);
			
			//System.out.println("QUESTION: "+q.id);
			if(q.typeOfQuestion==2 || q.typeOfQuestion == 1){
				
			List<Hypothesis> hypothesis_aux=Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
			if (hypothesis_aux.size()<1){
				for (Hypothesis h: Hypothesis.findByQuestion(q.id)){
					Hypothesis new_h=new Hypothesis();
					new_h.number=h.number;
					new_h.question=h.question;
					new_h.text=h.text;
					new_h.user=user;
					new_h.isCorrect = h.isCorrect;
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
		
		return ok(views.html.secured.question.question.render(user,module,lesson,test,group_aux,usertest));
		}
		else
			return ok(views.html.statics.error.render());
		}
		if (SecuredProfessor.isProfessor(session("email"))){
			return ProfessorController.index();
		}
		return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		
	}
	
	public static Result postquestion(
			int question_number, String module_acronym, String lesson_acronym, Long test_id,Long usertest_id,
			Long question_id) {
		
		

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		User user = User.find.byId(request().username());
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null || !module.users.contains(user)){
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null){
				return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		Usertest usertest=Usertest.find.byId(usertest_id);
		if(usertest==null || !usertest.user.email.equals(user.email)){
			System.out.println(user);
			System.out.println(usertest.user);
			System.out.println("USERTEST!=USER");
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		System.out.println("TIPO DE QUESTAO: "+question.typeOfQuestion);
			if(question.typeOfQuestion == 1){
				Form<OneChoiceQuestionAnswer> form = form(OneChoiceQuestionAnswer.class).bindFromRequest();
				List<Hypothesis> last_answers = Hypothesis.findByUserEmailAndQuestion(user.email, question_id); // Respostas Guardadas
				System.out.println(last_answers.size());
				for(Hypothesis h : last_answers){
					h.selected = false;
					h.save();
				}
				if(form.get().ocqanswer!=null){
				Hypothesis hypothesis = Hypothesis.find.byId(form.get().ocqanswer);
				hypothesis.selected = true;
				hypothesis.save();
				}
				
			}else if(question.typeOfQuestion == 2) {
			// GUARDAR ESCOLHA MULTIPLA E ONE CHOICE

			Form<MultipleChoiceQuestionAnswer> form = form(MultipleChoiceQuestionAnswer.class).bindFromRequest();
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
			
			Form<QuestionAnswer> form = form(QuestionAnswer.class).bindFromRequest();
			System.out.println("Open Answer: " + form.get().qanswer);
			
			Answer answer = Answer.findByUserTestAndQuestion(usertest.id, question_id); // Resposta Guardada
			answer.answer = form.get().qanswer;
			answer.save();
		}
		
			int totalNumQuestions=0;
			Test t= Test.find.byId(test_id);
			for (QuestionGroup g: t.groups){
				totalNumQuestions+=g.questions.size();
			}
			

			
			
			
			Usertest userTest= Usertest.findByUserAndTest(user.email, test_id);
			float progress = userTest.progress+ 100/totalNumQuestions;
			System.out.println("progress: "+progress);
			userTest.progress=progress;
			userTest.save();
			System.out.println("Vou fazer redirect");
			return redirect(routes.StudentTestController.question(question_number,test_id,lesson_acronym,module_acronym));

	}
	
	


	public static Result submitTest(Long test_id,String lesson_acronym, String module_acronym){
		


		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test_aux = models.test.Test.find.byId(test_id);
		if(test_aux==null){
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(request().username());
		if(Secured.isStudent(user.email) && test_aux.published && !test_aux.expired ){
		
		Usertest usertest= Usertest.findByUserAndTest(user.email, test_id);
		usertest.submitted=true;
		usertest.save();
		System.out.println("USERMAIL:" + user.email);
		System.out.println("Vou criar marker list..");
		//Quando aluno submete o teste, é associado à sua lista answersToMark, todas as answers dos outros alunos ao mesmo teste.
		//Para ser aletório temos q arranjar maneira de ser justo e de não haver muitas respostas dadas aos markers e outras ignoradas
		//Isto é só um teste para ter qlq coisa a funcionar
		
		Test test = usertest.test;
//		for (Usertest ut : Usertest.getAllTests()) {
//			if (usertest.id != ut.id) {
//				if (ut.test.id == test.id) {
//					for (Answer a : test.answers) {
//						System.out.println(a.usertest.user.email);
//						System.out.println(user.email);
//						System.out.println(user.email.equals(a.usertest.user.email));
//						if (!user.email.equals(a.usertest.user.email)) {
//							AnswerMarker am = new AnswerMarker();
//							am.answer = a;
//							am.user = user;
//							am.save();
//						}
//					}
//				}
//			}
//		}
		
		
		
		
		//CORRIGIR TESTE
		int reputation = 0;
		for(QuestionGroup g : test.groups){
			for(Question q : g.questions){
				boolean bool = true;
				if(q.typeOfQuestion==1){
					List<Hypothesis> hypothesis = Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
						for(Hypothesis h : hypothesis){
							if((h.isCorrect && !h.selected) || (!h.isCorrect && h.selected) && bool){
								bool = false;
							}
						}
						
						if(bool){
							reputation = reputation + q.weight;
						}else{
							reputation = reputation - q.weightToLose;
						}
						System.out.println("Reputação após OC acertada: " + reputation);
				}else if(q.typeOfQuestion==2){
					System.out.println("CORRIGIR ESCOLHA MULTIPLA.");
					List<Hypothesis> hypothesis = Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
					if(hypothesis.size() != 0){
					for(Hypothesis h : hypothesis){
						System.out.println(h.text);
						System.out.println("SELECTED: "+ h.selected + "isCORRECT?: " + h.isCorrect);
						if((h.isCorrect && !h.selected) || (!h.isCorrect && h.selected) ){
							 bool = false;
						}
					}
					}else{
						bool = false;
					}
					if(bool){
						System.out.println("MC - A somar weight..");
						reputation = reputation + q.weight;
					}else{
						reputation = reputation - q.weightToLose;
					}
					
					System.out.println("Reputação após MC acertada: " + reputation);

				}
				
				usertest.reputationAsAstudent = reputation;
				usertest.inmodule = false;
				usertest.save();
				
				if(q.typeOfQuestion== 1 || q.typeOfQuestion == 2){
					QuestionEvaluation qe;
					if(QuestionEvaluation.findByUserAndQuestion(usertest.id, q.id)==null){
						 qe = new QuestionEvaluation();
					}else{
						qe = QuestionEvaluation.findByUserAndQuestion(usertest.id, q.id);
					}
				if(bool){
				qe.isCorrect=true;
				qe.score = q.weight;
				}else{
					qe.score = -q.weightToLose;
				}
				
				qe.usertest = usertest;
				qe.question = q;
				qe.save();
				}
			}
		}
		
		usertest.reviewd = false;
		usertest.reputationAsAstudent = reputation;
		usertest.inmodule = false;
		usertest.save();
		

		SendMail.sendMail(user.email, "Congrats "+user.username+"!", "You've answer something"); 
		System.out.println("Reputação no Teste: " + usertest.reputationAsAstudent);
		
		return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym));
		}
		return redirect(routes.Application.index());
	}

	/**
	 * Adiciona uma sugestão de questão por parte do aluno
	 *
	 * @param  test_id  int id do teste
	 * @param  lesson_acronym String acronimo da lição
	 * @return      a mesma pagina
	 */
	public static Result addquestion(Long test_id, String lesson_acronym,
			String module_acronym) {
		
		

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(request().username());
		
		if(!user.isUserSignupTest(test)){
			//signup in test
			Usertest user_test = new Usertest();
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
			
			for(QuestionGroup group: test.groups){
				for(Question question: group.questions){
					System.out.println("QUESTION: " + question.id);
					if(question.subtopic!=null){
					SubtopicReputation subtopicreputation = SubtopicReputation.findByUserAndTopic(user.email, question.subtopic.id);
					System.out.println("SUBTOPIC_REPUTATIOn: " + subtopicreputation!=null);
					if(subtopicreputation==null){
						SubtopicReputation subtopicreputationuser = new SubtopicReputation();
						subtopicreputationuser.subtopic = question.subtopic;
						subtopicreputationuser.user = user;
						subtopicreputationuser.reputationAsAMarker = new Long(0);
						subtopicreputationuser.reputationAsAStudent = new Long(0);
						subtopicreputationuser.save();
					}
				}
				}
					
			}
			
		}
		
		Usertest usertest = Usertest.findByUserAndTest(user.email, test_id);
		usertest.inmodule = true;
		usertest.save();

		List<Category> categories = Category.getAllCategories();

		Form<OpenQuestionSuggestion> form_question = form(
				OpenQuestionSuggestion.class).bindFromRequest();
		
		Form<OpenAnswerSuggestion> form_answer = form(
				OpenAnswerSuggestion.class).bindFromRequest();
		
		

		System.out.println("MODULESIZE: " + lesson.questions.size());
		Question new_question = new Question();
		new_question.question = form_question.get().openquestionsuggestion;
		new_question.answerSuggestedByStudent = form_answer.get().openanswersuggestion;
		new_question.lesson = lesson;
		new_question.user = user;
		new_question.save();
		lesson.save();

		
		return redirect(routes.StudentController.lesson(lesson.acronym, module.acronym)+"#tests");
	}
	
	public static Result voteurl(Long url_id, int question_number, Long test_id,String lesson_acronym,String module_acronym){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		URL url = URL.find.byId(url_id);
		if(url==null){
			return redirect(routes.StudentTestController.question(question_number, test_id, lesson_acronym, module_acronym));
		}
		
		
		
		url.likes ++ ;

		url.save();
		
		return redirect(routes.StudentTestController.question(question_number, test_id, lesson_acronym, module_acronym));
		
	}

	
	public static Result addurl(int question_number, Long test_id,String lesson_acronym,String module_acronym,Long question_id ) {
		
		
		

		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null){
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		Form<URL_form> form = form(
				URL_form.class).bindFromRequest();
		
		User user = User.find.byId(request().username());
		
		System.out.println();
		System.out.println("Url: "+form.get().url);
		System.out.println("Title: "+form.get().name);
		System.out.println("Descr: "+form.get().descriptionUrl);
		System.out.println("ImageUrl: "+form.get().image);
		System.out.println();

		URL url = new URL();
		url.added = new DateTime();
		if(form.get().descriptionUrl.length()>255) url.description = form.get().descriptionUrl.substring(0, 255);
		else url.description = form.get().descriptionUrl;
		url.adress= form.get().url;
		url.imageURL=form.get().image;
		url.name=form.get().name;
		url.likes=0;
		url.question=question;
		url.user=user;
		
		url.save();

		return redirect(routes.StudentTestController.question(question_number, test_id, lesson_acronym, module_acronym));
		
	}
	
	
	
}

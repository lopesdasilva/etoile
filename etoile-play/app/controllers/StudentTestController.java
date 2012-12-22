package controllers;


import java.util.List;

import models.User;
import models.curriculum.Category;
import models.manytomany.UserTest;
import models.module.Lesson;
import models.module.Module;
import models.test.Answer;
import models.test.Evaluation;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionEvaluation;
import models.test.question.QuestionGroup;
import play.api.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.secured.*;

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

		public int[] mcqanswers = new int[4];
	}

	public static class OpenQuestionSuggestion {

		public String openquestionsuggestion;

	}

	
	public static Result questionanalysis(Long question_number, Long test_id,String lesson_acronym,String module_acronym){
			Test test = models.test.Test.find.byId(test_id);
			User user = User.find.byId(request().username());
			Module module = Module.findByAcronym(module_acronym);
			Lesson lesson = Lesson.findByAcronym(lesson_acronym);
			UserTest usertest = UserTest.findByUserAndTest(user.email,test.id);
			
			List<Answer> test_answers = Answer.findByUserEmailAndTestId(user.email,
					test_id);
			
			if (question_number<=test.groups.size() && question_number>0){
				System.out.println("A imprimir as questoes");
				
				QuestionGroup group = test.groups.get((int) (question_number-1));
				
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
				
				
				return ok(views.html.secured.question.questionanalysis.render(user,module,lesson,test,group_aux,usertest));
				}else{
					return ok(views.html.statics.error.render());
				}
			
				
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
					new_h.isCorrect = h.isCorrect;
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
	
	public static Result postquestion(
			int question_number, String module_acronym, String lesson_acronym, Long test_id,
			Long question_id) {
		
		User user = User.find.byId(request().username());
		Question question = Question.find.byId(question_id);
		
			if(question.typeOfQuestion == 1){
				Form<OneChoiceQuestionAnswer> form = form(OneChoiceQuestionAnswer.class).bindFromRequest();
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
			
			Answer answer = Answer.findByUserAndQuestion(user.email, question_id); // Resposta Guardada
			answer.answer = form.get().qanswer;
			answer.save();
		}
		
			int totalNumQuestions=0;
			Test t= Test.find.byId(test_id);
			for (QuestionGroup g: t.groups){
				totalNumQuestions+=g.questions.size();
			}
			System.out.println(totalNumQuestions);
			
			
			
			UserTest userTest= UserTest.findByUserAndTest(user.email, test_id);
			float progress = userTest.progress+ 100/totalNumQuestions;
			System.out.println("progress"+progress);
			userTest.progress=progress;
			userTest.save();
			
			return question(question_number,test_id,lesson_acronym,module_acronym);

	}

	public static Result submitTest(Long test_id,String lesson_acronym, String module_acronym){
		User user = User.find.byId(request().username());
		UserTest userTest= UserTest.findByUserAndTest(user.email, test_id);
		userTest.submitted=true;
		userTest.save();
		System.out.println("USERMAIL:" + user.email);
		
		System.out.println("Vou criar marker list..");
		//Quando aluno submete o teste, é associado à sua lista answersToMark, todas as answers dos outros alunos ao mesmo teste.
		//Para ser aletório temos q arranjar maneira de ser justo e de não haver muitas respostas dadas aos markers e outras ignoradas
		//Isto é só um teste para ter qlq coisa a funcionar
		
		Test test = userTest.test;
		for (UserTest ut : UserTest.getAllTests()) {
			if (userTest.id != ut.id) {
				if (ut.test.id == test.id) {
					for (Answer a : test.answers) {
						System.out.println(a.user.email);
						System.out.println(user.email);
						System.out.println(user.email.equals(a.user.email));
						if (!user.email.equals(a.user.email)) {
							if (!a.markers.contains(user) && a.markers.size() < 3 && user.answersToMark.size() < 3) {
								a.markers.add(user);
								a.save();
							}
						}
					}
				}
			}
		}
		
		
		//CORRIGIR TESTE
		int reputation = 0;
		for(QuestionGroup g : test.groups){
			for(Question q : g.questions){
				boolean bool = true;
				if(q.typeOfQuestion==1){
					List<Hypothesis> hypothesis = Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
						for(Hypothesis h : hypothesis){
							if(h.isCorrect && h.selected && bool){
								reputation = reputation + q.weight;
								System.out.println("OC - A somar weight..");
								bool = true;
							}else if((h.isCorrect && !h.selected) || (!h.isCorrect && h.selected) && bool){
								reputation = reputation - q.weightToLose;
								bool = false;
							}
						}
						System.out.println("Reputação após OC acertada: " + reputation);
				}else if(q.typeOfQuestion==2){
					List<Hypothesis> hypothesis = Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
					for(Hypothesis h : hypothesis){
						System.out.println(h.text);
						if(h.isCorrect && h.selected && bool){
							 bool = true;
						}else if((h.isCorrect && !h.selected) && bool){
							System.out.println("entrei");
							 bool = false;
							 reputation = reputation - q.weightToLose;
						}else if( (!h.isCorrect && h.selected) && bool){
							System.out.println("entrei2");
							 bool = false;
							 reputation = reputation - q.weightToLose;
						}
					}
					if(bool){
						System.out.println("MC - A somar weight..");
						reputation = reputation + q.weight;
					}
					System.out.println("Reputação após MC acertada: " + reputation);

				}
				
				userTest.reviewd = true;
				userTest.reputationAsAstudent = reputation;
				userTest.inmodule = false;
				userTest.save();
				
				QuestionEvaluation qe = new QuestionEvaluation();
				if(bool){
				qe.isCorrect=true;
				}
				qe.score = reputation;
				qe.userTest = userTest;
				qe.question = q;
				qe.save();
			}
		}
		
		userTest.reviewd = true;
		userTest.reputationAsAstudent = reputation;
		userTest.inmodule = false;
		userTest.save();
		
		System.out.println("Reputação no Teste: " + userTest.reputationAsAstudent);
		
		return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym));

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
		User user = User.find.byId(request().username());
		// Test test = models.test.Test.find.byId(test_id);
		UserTest usertest = UserTest.findByUserAndTest(user.email, test_id);
		usertest.inmodule = true;
		usertest.save();

		List<Category> categories = Category.getAllCategories();
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);

		Form<OpenQuestionSuggestion> form = form(
				OpenQuestionSuggestion.class).bindFromRequest();

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
}

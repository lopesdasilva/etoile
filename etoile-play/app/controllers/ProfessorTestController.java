package controllers;


import java.util.List;

import models.User;
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
import java.util.LinkedList;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.ProfessorLessonController.LessonDescription_Form;
import controllers.secured.SecuredProfessor;



@Security.Authenticated(SecuredProfessor.class)
public class ProfessorTestController extends Controller {
	
	
	public static class evaluation_Form {
		
		public int evaluation;
		
		
	}
	
	public static class NewTest_Form {
		
		public String name;
		
		public String text;
		
		
	}
	
	public static class NewQuestion_Form {
		
		public String question;
		
		public String suggestedanswer;
		
		public String keywords;
		
		public String image;
		
		public String video;
		
		public int weight;
		
		public int weighttolose;
		
		
	}
	
	
	public static Result addopenquestionform(String module_acronym, String lesson_acronym, Long test_id, Long group_id){
		System.out.println("ADD OPEN QUESTION FORM");
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		Test test = Test.find.byId(test_id);
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			for(Question q: lesson.questions){
				if(q.user!= null){
					q.user.refresh();
				}
			}
			return ok(views.html.professor.openquestionAdd.render(module,lesson,test, group));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result addopenquestion(String module_acronym, String lesson_acronym, Long test_id, Long group_id){
		System.out.println("ADD OPEN QUESTION");
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		Test test = Test.find.byId(test_id);
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<NewQuestion_Form> form = form(NewQuestion_Form.class).bindFromRequest();
			Question question = new Question();
//			q.group.add(group);
			question.lesson = lesson;
			question.question = form.get().question;
			question.answerSuggestedByStudent = form.get().suggestedanswer;
			question.keywords = form.get().keywords;
			question.imageURL = form.get().image;
			question.videoURL = form.get().video;
			question.weight = form.get().weight;
			question.weightToLose = form.get().weighttolose;
			question.user = user;
			question.number = group.questions.size()+1;
			question.typeOfQuestion = 0;
			
			question.save();
			
			group.questions.add(question);
			group.save();
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test.id));
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static class NewGroup_Form{
		public String question;
		
	}
	
	public static Result publish(String module_acronym, String lesson_acronym, Long test_id){
		Module module = Module.findByAcronym(module_acronym);

		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Test test=Test.find.byId(test_id);
			test.published=true;
			test.save();
			
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}

		return redirect(routes.Application.module(module.acronym));
	}

	public static Result unpublish(String module_acronym, String lesson_acronym, Long test_id){		
		Module module = Module.findByAcronym(module_acronym);

		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Test test=Test.find.byId(test_id);
			test.published=false;
			test.save();
			
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}

		return redirect(routes.Application.module(module.acronym));
	}
	
	
	
	public static Result addtest(String module_acronym, String lesson_acronym){
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		Module module = Module.findByAcronym(module_acronym);
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		Form<NewTest_Form> form = form(NewTest_Form.class).bindFromRequest();
		models.test.Test test = new models.test.Test();
		test.name = form.get().name;
		test.text = form.get().text;
		test.lesson = lesson;
		test.save();
		
		return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test.id));
	}
	
	return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result edittest(String module_acronym, String lesson_acronym, Long test_id){
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		Module module = Module.findByAcronym(module_acronym);
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Test test=Test.find.byId(test_id);
			
			return ok(views.html.professor.testEdit.render(module,lesson,test));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
	
public static Result test(String module_acronym, String lesson_acronym, Long test_id){
		
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Test test=Test.find.byId(test_id);
			for (UserTest usertest: test.users)
			usertest.user.refresh();
			return ok(views.html.professor.testGeneral.render(user,
					module,lesson,test));
		}
		
		
		return redirect(routes.Application.module(module.acronym));
	}


public static Result gradetest(String module_acronym, String lesson_acronym,Long usertest_id, Long question_number){
	
	Module module = Module.findByAcronym(module_acronym);
	Lesson lesson = Lesson.findByAcronym(lesson_acronym);
	User user = User.find.byId(session("email"));
	if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		
		UserTest usertest=UserTest.find.byId(usertest_id);
		QuestionGroup questionGroup=QuestionGroup.find.byId(question_number);
		QuestionGroup group = usertest.test.groups.get((int) (question_number-1));
	
		
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
			q_aux.user = usertest.user;
			q_aux.videoURL = q.videoURL;
			q_aux.urls=q.urls;
			
			
			//q_aux.hypothesislist?????
			group_aux.questions.add(q_aux);
			
			System.out.println("QUESTION: "+q.id);
			if(q.typeOfQuestion==2 || q.typeOfQuestion == 1){
				
			List<Hypothesis> hypothesis_aux=Hypothesis.findByUserEmailAndQuestion(usertest.user.email, q.id);
			if (hypothesis_aux.size()<1){
				for (Hypothesis h: Hypothesis.findByQuestion(q.id)){
					Hypothesis new_h=new Hypothesis();
					new_h.number=h.number;
					new_h.question=h.question;
					new_h.text=h.text;
					new_h.user=usertest.user;
					new_h.isCorrect = h.isCorrect;
					new_h.save();
				}
				hypothesis_aux=Hypothesis.findByUserEmailAndQuestion(usertest.user.email, q.id);
			}
			
			q_aux.hypothesislist=hypothesis_aux;
			q=q_aux;
			}
			else{
				q_aux.openanswer=Answer.findByUserAndQuestion( usertest.user.email,q.id);
				if(q_aux.openanswer.questionevaluation != null){
				q_aux.openanswer.questionevaluation.refresh();
				}
				q=q_aux;
				System.out.println("q answer: "+q.openanswer.answer);
			}
			
			
			
			}
		
		
//		for (Question question: group.questions){
//			System.out.println("user: "+question.user);
//			question.refresh();
//			if(question.typeOfQuestion==0)
//				question.openanswer.refresh();
//			
//		}
		//REFRESH
		usertest.user.refresh();
		
		return ok(views.html.professor.questionanalysis.render(module,lesson,usertest,group_aux));
	}
	
	
	return redirect(routes.Application.module(module.acronym));
}


	public static Result markanswer(String module_acronym, String lesson_acronym,Long usertest_id, Long question_number, Long question_id){
		Form<evaluation_Form> form = form(evaluation_Form.class).bindFromRequest();
		User user = User.find.byId(session("email"));
		
		UserTest usertest = UserTest.find.byId(usertest_id);
		Question question = Question.find.byId(question_id);
		Answer answer = Answer.findByUserAndQuestion(usertest.user.email, question_id);
		QuestionEvaluation evaluation = QuestionEvaluation.findByUserAndQuestion(usertest_id, question_id);
		if(evaluation==null){
			
			double percentagem = 100.0;
			percentagem = (form.get().evaluation/percentagem);
			double peso = question.weight;
			double cotacao = peso*percentagem;
			
			usertest.reputationAsAstudent = usertest.reputationAsAstudent + cotacao;
			usertest.save();
			
			evaluation = new QuestionEvaluation();
			evaluation.question = question;
			evaluation.score = cotacao;
			evaluation.userTest = usertest;
			evaluation.percent = form.get().evaluation;
			evaluation.answer = answer;
			evaluation.save();
			
			answer.questionevaluation = evaluation;
			answer.save();
		}else{
			System.out.println(usertest.reputationAsAstudent +"-"+ evaluation.score + "+" + "(" +form.get().evaluation+ "/100)*" + question.weight + "=" );
			
			double percentagem = 100.0;
			percentagem = (form.get().evaluation/percentagem);
			double peso = question.weight;
			double cotacao = peso*percentagem;
			System.out.println("COTACAO: " + cotacao);

			usertest.reputationAsAstudent = usertest.reputationAsAstudent - evaluation.score + cotacao;
			evaluation.percent = form.get().evaluation;
			usertest.save();
			evaluation.score = cotacao ;
			evaluation.save();
		}
		
		return gradetest(module_acronym, lesson_acronym, usertest_id, question_number);
//		return null;
	}
	
	public static Result submitreviewedtest(String module_acronym, String lesson_acronym, Long test_id, Long usertest_id){
		System.out.println(test_id);
		UserTest usertest = UserTest.find.byId(usertest_id);
		usertest.reviewd = true;
		usertest.save();
		System.out.println("Review Test submitted with: " + usertest.reputationAsAstudent);
		return test(module_acronym, lesson_acronym, test_id);
	}

	public static Result deletetest(Long test_id,String module_acronym, String lesson_acronym){
		
		Module module = Module.findByAcronym(module_acronym);
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Test test= Test.find.byId(test_id);
			test.delete();
		}
		//TODO: Criar ligacao pelo controlador do professor
		return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym));
	}

	public static Result addgroup(String module_acronym, String lesson_acronym, Long test_id){
		User user = User.find.byId(session("email"));
		Module module = Module.findByAcronym(module_acronym);
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Test test=Test.find.byId(test_id);
			Form<NewGroup_Form> form = form(NewGroup_Form.class).bindFromRequest();

			QuestionGroup questiongroup=new QuestionGroup();
			questiongroup.question=form.get().question;
			questiongroup.test=test;
			questiongroup.number=test.groups.size()+1;
			questiongroup.save();

		return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
	}
		
		return redirect(routes.Application.module(module.acronym));
	}
		
		public static Result deletegroup(String module_acronym, String lesson_acronym, Long test_id,Long group_id){
			System.out.println("Estou aqui");
			User user = User.find.byId(session("email"));
			Module module = Module.findByAcronym(module_acronym);
			if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
				Test test=Test.find.byId(test_id);
				

				QuestionGroup questiongroup=QuestionGroup.find.byId(group_id);
				questiongroup.delete();

			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
		
}

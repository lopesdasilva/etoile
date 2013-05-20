package controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
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
import play.api.libs.json.Json;
import play.api.libs.json.Reads;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import util.pdf.PDF;
import views.html.document;
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

	 public static Result document(Long test_id) {

		 User user = User.find.byId(request().username());
		 Test test = models.test.Test.find.byId(test_id);
		 if(test==null){
			 return redirect(routes.Application.index());
			}
		 Usertest usertest = Usertest.findByUserAndTest(user.email,test.id);
		 if(usertest==null || usertest.inmodule || !usertest.submitted){
			 return redirect(routes.Application.index());
		 }
		 
		 System.out.println("******* start:"+user.email+"*********");
         System.out.println("Controller: StudentTestController.java");
         System.out.println("Method: document");
         System.out.println("PDF will be created");
         System.out.println("*******   end:"+user.email+"*********");
         
		 usertest.user.refresh();
		 usertest.test.refresh();
	        return PDF.ok(document.render(usertest));
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
				
				 System.out.println("******* start:"+user.email+"*********");
		         System.out.println("Controller: StudentTestController.java");
		         System.out.println("Method: questionanalysis");
		         System.out.println("Question analysis page will be rendered.");
		         System.out.println("*******   end:"+user.email+"*********");
				
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
			System.out.println("The lesson does not exist.");
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			System.out.println("The test does not exist.");
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		
	
		Test test_aux= Test.find.byId(test_id);
		
		
//		System.out.println("usertest: " + Usertest.findByUserAndTest(user.email, test_id).id);
//		System.out.println("inModule: "+Usertest.findByUserAndTest(user.email, test_id).inmodule);
//		System.out.println("published: "+test_aux.published);
//		System.out.println("expired: "+test_aux.expired);
		
		//if(Secured.isStudent(user.email) && user.isUserSignupTest(test) && user.userSuggestedQuestion(test) && test_aux.published && !test_aux.expired ){
		if(Secured.isStudent(user.email) && Usertest.findByUserAndTest(user.email, test_id)!=null && Usertest.findByUserAndTest(user.email, test_id).inmodule && test_aux.published && !test_aux.expired ){
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
		
		 System.out.println("******* start:"+user.email+"*********");
         System.out.println("Controller: StudentTestController.java");
         System.out.println("Method: question");
         System.out.println("Question will be rendered");
         System.out.println("*******   end:"+user.email+"*********");
		
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
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		
		if(Secured.isStudent(user.email) && test.published && !test.expired && !usertest.submitted ){
			if(question.typeOfQuestion == 1){
				Form<OneChoiceQuestionAnswer> form = Form.form(OneChoiceQuestionAnswer.class).bindFromRequest();
				List<Hypothesis> last_answers = Hypothesis.findByUserEmailAndQuestion(user.email, question_id); // Respostas Guardadas
				if(!last_answers.get(0).isSaved){
					changeTestProgress(test_id, usertest_id, user.email);
				}
				for(Hypothesis h : last_answers){
					h.selected = false;
					h.isSaved = true;
					h.save();
				}
				if(form.get().ocqanswer!=null){
				Hypothesis hypothesis = Hypothesis.find.byId(form.get().ocqanswer);
				hypothesis.selected = true;
				hypothesis.save();
				}
				
				 System.out.println("******* start:"+user.email+"*********");
		         System.out.println("Controller: StudentTestController.java");
		         System.out.println("Method: postquestion");
		         System.out.println("OneChoiceQuestion - answer saved.");
		         System.out.println("*******   end:"+user.email+"*********");
				
			}else if(question.typeOfQuestion == 2) {
			// GUARDAR ESCOLHA MULTIPLA E ONE CHOICE

			Form<MultipleChoiceQuestionAnswer> form = Form.form(MultipleChoiceQuestionAnswer.class).bindFromRequest();
			
			List<Hypothesis> last_answers = Hypothesis.findByUserEmailAndQuestion(user.email, question_id); // Respostas Guardadas
			if(!last_answers.get(0).isSaved){
				changeTestProgress(test_id, usertest_id, user.email);
			}
			for(Hypothesis h : last_answers){
				h.selected = false;
				h.isSaved = true;
				h.save();
				
			}
			
			for(int h : form.get().mcqanswers){
				if(h!=0){
					for(Hypothesis hypothesis: last_answers){
						hypothesis.isSaved = true;
						if(hypothesis.id == h){
							hypothesis.selected = true;
							hypothesis.save();
						}
					}
				}
			}
			 System.out.println("******* start:"+user.email+"*********");
	         System.out.println("Controller: StudentTestController.java");
	         System.out.println("Method: postquestion");
	         System.out.println("MultipleChoiceQuestion - answer saved.");
	         System.out.println("*******   end:"+user.email+"*********");
		} else {
			//GUARDAR OPEN QUESTION - WORKING
			
			Form<QuestionAnswer> form = Form.form(QuestionAnswer.class).bindFromRequest();			
			Answer answer = Answer.findByUserTestAndQuestion(usertest.id, question_id); // Resposta Guardada
			if(!answer.isSaved){
				changeTestProgress(test_id, usertest_id, user.email);
			}
			answer.answer = form.get().qanswer;
			answer.isSaved = true;
			answer.save();
			
			 System.out.println("******* start:"+user.email+"*********");
	         System.out.println("Controller: StudentTestController.java");
	         System.out.println("Method: postquestion");
	         System.out.println("OpenQuestion - answer saved.");
	         System.out.println("*******   end:"+user.email+"*********");
		}
		
			
			return redirect(routes.StudentTestController.question(question_number,test_id,lesson_acronym,module_acronym));
		}
		return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
	}
	

	
	public static void changeTestProgress(Long test_id, Long usertest_id, String user_email){
		int totalNumQuestions=0;
		Test t= Test.find.byId(test_id);
		for (QuestionGroup g: t.groups){
			totalNumQuestions+=g.questions.size();
		}
		Usertest userTest= Usertest.findByUserAndTest(user_email, test_id);
		float progress = userTest.progress+ 100/totalNumQuestions;
		userTest.progress=progress;
		if(userTest.progress == 99){
			userTest.progress = progress+1;
			
		}
		userTest.save();
	}
	
	public static Result saveopenanswer(){
		System.out.println("SaveQuestion!!");
		
		System.out.println(request().uri());
		System.out.println(request().body().asJson());
		
		JsonNode json =request().body().asJson();
		if(json==null){
			System.out.println("JSON NULL");
			return badRequest("Expecting Json data.");
		}else{
			
			
			
			System.out.println(json.findPath("usertest").getTextValue());
			System.out.println(json.findPath("question").getTextValue());
			System.out.println(json.findPath("answer").getTextValue());
			
			Integer question_id = Integer.parseInt( json.findPath("question").getTextValue() );
			User user = User.find.byId(request().username());
			Integer usertest_id = Integer.parseInt( json.findPath("usertest").getTextValue() );
			
			Answer answer = Answer.findByUserTestAndQuestion((long)usertest_id, (long)question_id);
			
			if(!answer.isSaved){
				changeTestProgress((long)usertest_id, user.email);
				
			}
			
			answer.usertest.refresh();
			System.out.println(answer.usertest.user.email + "-" + user.email);
			
			if(answer!= null && answer.usertest.user.email.equals(user.email)){
			answer.answer = json.findPath("answer").getTextValue();
			answer.isSaved = true;
			answer.save();
			System.out.println("saved");
			return ok("success");
			}
			return ok("error");
			
		}
		
		/*System.out.println("UserTest: " + usertest_id);
		System.out.println("Question: " + question_id);
		System.out.println("New_answer: " + new_answer);
		
		User user = User.find.byId(request().username());
		
		Answer answer = Answer.findByUserTestAndQuestion(usertest_id, question_id);
		answer.usertest.refresh();
		System.out.println(answer.usertest.user.email + "-" + user.email);
		
		if(answer!= null && answer.usertest.user.email.equals(user.email)){
		answer.answer = new_answer;
		answer.save();
		return ok("saved");
		}*/
		
		
	}
	
	public static void changeTestProgress(Long usertest_id, String user_email){
		int totalNumQuestions=0;
		Usertest userTest= Usertest.find.byId(usertest_id);
		Test t= Test.find.byId(userTest.test.id);
		for (QuestionGroup g: t.groups){
			totalNumQuestions+=g.questions.size();
		}
		
		float progress = userTest.progress+ 100/totalNumQuestions;
		userTest.progress=progress;
		if(userTest.progress == 99){
			userTest.progress = progress+1;
			
		}
		userTest.save();
	}
	
	public static Result savemultiplechoiceanswer(){
		System.out.println("savemultiplechoiceanswer method");
		System.out.println(request().uri());
		System.out.println(request().body().asJson());
		
		
		JsonNode json =request().body().asJson();
		if(json==null){
			System.out.println("JSON NULL");
			return badRequest("Expecting Json data.");
		}else{
			
			System.out.println(json.findPath("message"));
			System.out.println(json.findPath("message").getTextValue());
			System.out.println(json.findPath("question").getTextValue());
			Integer question_id = Integer.parseInt( json.findPath("question").getTextValue() );
			User user = User.find.byId(request().username());
			
			List<Hypothesis> hypothesis_list = Hypothesis.findByUserEmailAndQuestion(user.email, (long)question_id);
			
			if(hypothesis_list.size() > 0){
				String result= json.findPath("message").getTextValue().replace("[", "").replace("]", "");
				
				if(!hypothesis_list.get(0).isSaved){
					changeTestProgress((long)Integer.parseInt(json.findPath("usertest").getTextValue()), user.email);
				}
				for(Hypothesis h: hypothesis_list){
					h.selected = false;
					h.isSaved = true;
					h.save();
				}
				
				if(result.length()!=0){
				
				String[] array=result.split(",");

			
			for(int i = 0 ; i != array.length; i++){
				int hypothesis_id = Integer.parseInt(array[i]);
				Hypothesis hypothesis = Hypothesis.find.byId((long)hypothesis_id);
				hypothesis.selected = true;
				hypothesis.save();
			}
		
			return ok("saved");
			
			}
			}
		}
//		
//		//int[] intArray = gson.fromJson(null, int[].class);
//		
		return ok("error");
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
		Usertest usertest= Usertest.findByUserAndTest(user.email, test_id);
		
		if(Secured.isStudent(user.email) && test_aux.published && !test_aux.expired && !usertest.submitted ){
		
		
		usertest.submitted=true;
		usertest.save();

			
		System.out.println("******* start:"+user.email+"*********");
        System.out.println("Controller: StudentTestController.java");
        System.out.println("Method: submitTest");
        System.out.println("Test submitted");
        System.out.println("usertest: " + usertest.id);
        System.out.println("*******   end:"+user.email+"*********");
        
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
				boolean answered = false;
				boolean bool = true;
				List<Hypothesis> hypothesis_aux = Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
				for(Hypothesis h : hypothesis_aux){
					if(h.selected){
						answered = true;
					}
				}
				if(answered){
				
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
				}else if(q.typeOfQuestion==2){
					List<Hypothesis> hypothesis = Hypothesis.findByUserEmailAndQuestion(user.email, q.id);
					if(hypothesis.size() != 0){
					for(Hypothesis h : hypothesis){
						if((h.isCorrect && !h.selected) || (!h.isCorrect && h.selected) ){
							 bool = false;
						}
					}
					}else{
						bool = false;
					}
					if(bool){
						reputation = reputation + q.weight;
					}else{
						reputation = reputation - q.weightToLose;
					}
					

				}
				
				usertest.reputationAsAstudent = reputation;
				usertest.inmodule = false;
				usertest.save();
				}
				if(q.typeOfQuestion== 1 || q.typeOfQuestion == 2){
					QuestionEvaluation qe;
					if(QuestionEvaluation.findByUserAndQuestion(usertest.id, q.id)==null){
						 qe = new QuestionEvaluation();
					}else{
						qe = QuestionEvaluation.findByUserAndQuestion(usertest.id, q.id);
					}
				if(bool && answered){
				qe.isCorrect=true;
				qe.score = q.weight;
				}else if(!bool && answered){
					qe.score = -q.weightToLose;
				}else{
					qe.score = 0;
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
		
		System.out.println("******* start:"+user.email+"*********");
        System.out.println("Controller: StudentTestController.java");
        System.out.println("Method: submitTest");
        System.out.println("Test Marked");
        System.out.println("usertest: " + usertest.id);
        System.out.println("test reputationAsAStudent: " + reputation);
        System.out.println("*******   end:"+user.email+"*********");

//		SendMail.sendMail(user.email, "Congrats "+user.username+"!", "You've answer something"); 

		
		return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym));
		}
		return redirect(routes.Application.index());
	}
	
	public static Result signuptest(Long test_id, String lesson_acronym,String module_acronym) {
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			System.out.println("The lesson does not exist.");
			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			System.out.println("The test does not exist.");
			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(request().username());
		System.out.println("Method: signuptest: ");
		System.out.println("Checking if usertest exists");
		if(Usertest.findByUserAndTest(user.email, test_id)==null){
			System.out.println("Not exists: OK ");
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
					if(question.subtopic!=null){
					SubtopicReputation subtopicreputation = SubtopicReputation.findByUserAndTopic(user.email, question.subtopic.id);
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
			

			Usertest usertest = Usertest.findByUserAndTest(user.email, test_id);
			usertest.inmodule = true;
			usertest.save();

			
		}
		System.out.println("Already exists: NOT OK ");

		
		
		return redirect(routes.StudentController.lesson(lesson.acronym, module.acronym)+"#tests");
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
			System.out.println("The lesson does not exist.");

			return redirect(routes.Application.module(module_acronym));
		}	
		
		Test test = models.test.Test.find.byId(test_id);
		if(test==null){
			System.out.println("The test does not exist.");

			return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(request().username());
		System.out.println("Method: addquestion: adding suggestion");
		System.out.println("Checking if usertest exists");
		if(Usertest.findByUserAndTest(user.email, test_id)==null){
			System.out.println("Not exists: OK");

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
					if(question.subtopic!=null){
					SubtopicReputation subtopicreputation = SubtopicReputation.findByUserAndTopic(user.email, question.subtopic.id);
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

		Form<OpenQuestionSuggestion> form_question = Form.form(
				OpenQuestionSuggestion.class).bindFromRequest();
		
		Form<OpenAnswerSuggestion> form_answer = Form.form(
				OpenAnswerSuggestion.class).bindFromRequest();
		
		


		Question new_question = new Question();
		new_question.question = form_question.get().openquestionsuggestion;
		new_question.answerSuggestedByStudent = form_answer.get().openanswersuggestion;
		new_question.lesson = lesson;
		new_question.user = user;
		new_question.save();
		lesson.save();
		
		System.out.println("******* start:"+user.email+"*********");
        System.out.println("Controller: StudentTestController.java");
        System.out.println("Method: addquestion");
        System.out.println("new SuggestedQuestion added");
        System.out.println("usertest: " + usertest.id + " - inmodule is true!");
        System.out.println("*******   end:"+user.email+"*********");
		
		return redirect(routes.StudentController.lesson(lesson.acronym, module.acronym)+"#tests");
	}
	
	public static Result voteurl(Long url_id, int question_number, Long test_id,String lesson_acronym,String module_acronym){
		User user = User.find.byId(session("email"));
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
		url.voters.add(user);
		
		url.save();
		
		System.out.println("******* start:"+user.email+"*********");
        System.out.println("Controller: StudentTestController.java");
        System.out.println("Method: voteurl");
        System.out.println("URL voted.");
        System.out.println("url: " + url.id);
        System.out.println("*******   end:"+user.email+"*********");
		
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
		
		Form<URL_form> form = Form.form(
				URL_form.class).bindFromRequest();
		
		User user = User.find.byId(request().username());



		URL url = new URL();
		url.added = new DateTime();
		if(form.get().descriptionUrl.length()>255) url.description = form.get().descriptionUrl.substring(0, 255);
		else url.description = form.get().descriptionUrl;
		url.adress= form.get().url;
		url.imageURL=form.get().image;
		url.name=form.get().name;
		url.likes=0;
		System.out.println("question: " + question.question);
		url.question=question;
		url.user=user;
		
		url.save();
		
		System.out.println("******* start:"+user.email+"*********");
        System.out.println("Controller: StudentTestController.java");
        System.out.println("Method: addurl");
        System.out.println("Url added");
        System.out.println("url: " + url.id);
        System.out.println("*******   end:"+user.email+"*********");

		return redirect(routes.StudentTestController.question(question_number, test_id, lesson_acronym, module_acronym));
		
	}
	
	
	
}

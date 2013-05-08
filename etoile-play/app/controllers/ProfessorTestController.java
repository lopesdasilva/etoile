package controllers;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Modulescore;
import models.User;
import models.curriculum.Category;
import models.curriculum.Curriculummodule;
import models.manytomany.Usertest;
import models.module.Content;
import models.module.Lesson;
import models.module.Module;
import models.test.Answer;
import models.test.AnswerMarker;
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
import views.html.secured.question.question;
import controllers.ProfessorLessonController.LessonDescription_Form;
import controllers.secured.SecuredProfessor;



@Security.Authenticated(SecuredProfessor.class)
public class ProfessorTestController extends Controller {
	
	
	
	//FORMS
	public static class evaluation_Form {
		
		public int evaluation;
		
		public String evalutationcomment;
		
		
	}
	
	public static class Date_Form{
		public String date;
	}
	
	public static class NewTest_Form {
		
		public String name;
		
		public String text;
		
		public String expectedDuration;
		
		
	}
	
	public static class Question_Form {
		
		public String question;
		
		public String suggestedanswer;
		
		public String keywords;
		
		public String subtopic;
		
		public String image;
		
		public String video;
		
		public int weight;

		public int weighttolose;
		
	}
	
	public static class newHypothesis_form {
		
		public String hypothesis;
		
	}
	
	public static class newMultipleHypothesis_form {
		
		public String hypothesis;
		
		public int isCorrect;
		
	}
	
	public static class NewGroup_Form{
		public String question;
		
	}

	public static class SuggestedQuestionRequired_Form{
		public boolean required;
		
	}
	
	private static long ONEWEEKINMILLIS=604800000;
	
	//METHODS
	public static Result addopenquestionform(String module_acronym, String lesson_acronym, Long test_id, Long group_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			for(Question q: lesson.questions){
				if(q.user!= null){
					q.user.refresh();
				}
			}
			List<Curriculummodule> subtopics=Curriculummodule.getAllModules(); 
			return ok(views.html.professor.openquestionAdd.render(module,lesson,test, group,subtopics));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result addopenquestion(String module_acronym, String lesson_acronym, Long test_id, Long group_id){
				
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Question_Form> form = Form.form(Question_Form.class).bindFromRequest();
			Question question = new Question();
//			q.group.add(group);
			question.lesson = lesson;
			question.question = form.get().question;
			question.answerSuggestedByStudent = form.get().suggestedanswer;
			question.keywords = form.get().keywords;
			question.subtopic=Curriculummodule.findByName(form.get().subtopic);
			
			if(form.get().image.length() > 0){
				question.imageURL = form.get().image;
			}
			
			if(form.get().video.length() > 0){
				question.videoURL = form.get().video;
			}
			question.weight = form.get().weight;
			question.weightToLose = 0;
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
	
	public static Result editopenquestionform(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=0 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			List<Curriculummodule> subtopics=Curriculummodule.getAllModules(); 
			return ok(views.html.professor.openquestionEdit.render(module,lesson,test, group,question,subtopics));

		}
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result editopenquestion(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=0 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Question_Form> form = Form.form(Question_Form.class).bindFromRequest();
			question.question = form.get().question;
			question.answerSuggestedByStudent = form.get().suggestedanswer;
			question.keywords = form.get().keywords;
			
			question.subtopic=Curriculummodule.findByName(form.get().subtopic);
			if(form.get().image.length() > 0){
				question.imageURL = form.get().image;
			}
			
			if(form.get().video.length() > 0){
				question.videoURL = form.get().video;
			}
			question.weight = form.get().weight;
			question.save();
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test.id));
		}
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result addonechoicequestionform(String module_acronym, String lesson_acronym, Long test_id, Long group_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
				
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			for(Question q: lesson.questions){
				if(q.user!= null){
					q.user.refresh();
				}
			}
			return ok(views.html.professor.onechoicequestionAdd.render(module,lesson,test, group));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result addonechoicequestion(String module_acronym, String lesson_acronym, Long test_id, Long group_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Question_Form> form = Form.form(Question_Form.class).bindFromRequest();
			Question question = new Question();
//			q.group.add(group);
			question.lesson = lesson;
			question.question = form.get().question;
			question.keywords = form.get().keywords;
			if(form.get().image.length() > 0){
				question.imageURL = form.get().image;
			}
			
			if(form.get().video.length() > 0){
				question.videoURL = form.get().video;
			}
			question.weight = form.get().weight;
			question.weightToLose = form.get().weighttolose;
			question.user = user;
			question.number = group.questions.size()+1;
			question.typeOfQuestion = 1;
			
			question.save();
			
			group.questions.add(question);
			group.save();
			//return ok(views.html.professor.hypothesisAdd.render(module,lesson,test, group, question));
			return redirect(routes.ProfessorTestController.addhypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result editonechoicequestionform(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=1 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
			
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			for(Question q: lesson.questions){
				if(q.user!= null){
					q.user.refresh();
				}
			}
			return ok(views.html.professor.onechoicequestionEdit.render(module,lesson,test, group, question));
		}
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result editonechoicequestion(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=1 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Question_Form> form = Form.form(Question_Form.class).bindFromRequest();
//			q.group.add(group);
			question.lesson = lesson;
			question.question = form.get().question;
			question.keywords = form.get().keywords;
			if(form.get().image.length() > 0){
				question.imageURL = form.get().image;
			}
			
			if(form.get().video.length() > 0){
				question.videoURL = form.get().video;
			}
			question.weight = form.get().weight;
			question.weightToLose = form.get().weighttolose;
			question.user = user;
			question.number = group.questions.size()+1;
			question.typeOfQuestion = 1;
			
			question.save();
			
			//return ok(views.html.professor.hypothesisAdd.render(module,lesson,test, group, question));
			return redirect(routes.ProfessorTestController.addhypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result addhypothesisform(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);
		if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=1 || !question.group.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			return ok(views.html.professor.hypothesisAdd.render(module,lesson,test, group, question));
		}
		
		return redirect(routes.Application.module(module_acronym));

	}
	
	public static Result addhypothesis(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		
		if(question==null || question.typeOfQuestion!=1 || !question.group.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			Form<newHypothesis_form> form = Form.form(newHypothesis_form.class).bindFromRequest();
			Hypothesis hypothesis = new Hypothesis();
			if(question.hypothesislist.size()==0){
				hypothesis.isCorrect = true;
			}
			hypothesis.number = question.hypothesislist.size()+1;
			hypothesis.question = question;
			hypothesis.text = form.get().hypothesis;
			hypothesis.save();
			
			question.hypothesislist.add(hypothesis);
			question.save();
			
			return redirect(routes.ProfessorTestController.addhypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result changerightonechoiceanswer(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id, Long hypothesis_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=1 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Hypothesis hypothesis_selected = Hypothesis.find.byId(hypothesis_id);
		if(hypothesis_selected==null){
			redirect(routes.ProfessorTestController.addhypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		
		User user = User.find.byId(session("email"));
		
		
		for(Hypothesis h: question.hypothesislist){
			if(h.id != hypothesis_selected.id && h.user == null){
				h.isCorrect = false;
				h.save();
			}else{
				h.isCorrect = true;
				h.save();
			}
		}
		return redirect(routes.ProfessorTestController.addhypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));

	}
	
	public static Result changerightmultiplechoiceanswer(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id, Long hypothesis_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=2 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Hypothesis hypothesis_selected = Hypothesis.find.byId(hypothesis_id);
		if(hypothesis_selected==null){
			redirect(routes.ProfessorTestController.addhypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		
		
		
		User user = User.find.byId(session("email"));
		
		if(!hypothesis_selected.isCorrect){
		hypothesis_selected.isCorrect=true;
		}else{
		hypothesis_selected.isCorrect=false;	
		}
		hypothesis_selected.save();
		
		return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));

	}
	
	public static Result addmultiplechoicequestionform(String module_acronym, String lesson_acronym, Long test_id, Long group_id){
	
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			for(Question q: lesson.questions){
				if(q.user!= null){
					q.user.refresh();
				}
			}
			return ok(views.html.professor.multiplechoicequestionAdd.render(module,lesson,test, group));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result addmultiplechoicequestion(String module_acronym, String lesson_acronym, Long test_id, Long group_id){
	
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Question_Form> form = Form.form(Question_Form.class).bindFromRequest();
			Question question = new Question();
//			q.group.add(group);
			question.lesson = lesson;
			question.question = form.get().question;
			question.keywords = form.get().keywords;
			if(form.get().image.length() > 0){
				question.imageURL = form.get().image;
			}
			
			if(form.get().video.length() > 0){
				question.videoURL = form.get().video;
			}
			question.weight = form.get().weight;
			question.weightToLose = form.get().weighttolose;
			question.user = user;
			question.number = group.questions.size()+1;
			question.typeOfQuestion = 2;
			
			question.save();
			
			group.questions.add(question);
			group.save();
			//return ok(views.html.professor.hypothesisAdd.render(module,lesson,test, group, question));
			//MUDAR PARA MULTIPLE
			return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		
		return redirect(routes.Application.module(module_acronym));
	}

	public static Result addmultiplehypothesisform(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=2 || !question.group.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			return ok(views.html.professor.multiplehypothesisAdd.render(module,lesson,test, group, question));
		}
		
		return redirect(routes.Application.module(module_acronym));

	}
	
	public static Result addmultiplehypothesis(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=2 || !question.group.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
	
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			Form<newMultipleHypothesis_form> form = Form.form(newMultipleHypothesis_form.class).bindFromRequest();
			System.out.println("CORRECT: " + form.get().isCorrect);

			Hypothesis hypothesis = new Hypothesis();
			hypothesis.number = question.hypothesislist.size()+1;
			hypothesis.question = question;
			hypothesis.text = form.get().hypothesis;
			hypothesis.save();
			
			question.hypothesislist.add(hypothesis);
			question.save();
			
			return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result edithypothesis(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id, Long hypothesis_id){
	
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=1 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		User user = User.find.byId(session("email"));
		Hypothesis hypothesis = Hypothesis.find.byId(hypothesis_id);
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			Form<newMultipleHypothesis_form> form = Form.form(newMultipleHypothesis_form.class).bindFromRequest();
			System.out.println("CORRECT: " + form.get().isCorrect);

			hypothesis.number = question.hypothesislist.size()+1;
			hypothesis.question = question;
			hypothesis.text = form.get().hypothesis;
			hypothesis.save();
			
			return redirect(routes.ProfessorTestController.addhypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result editmultiplechoicehypothesis(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id, Long hypothesis_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Hypothesis hypothesis = Hypothesis.find.byId(hypothesis_id);
		if(question==null || question.typeOfQuestion!=2 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			Form<newMultipleHypothesis_form> form = Form.form(newMultipleHypothesis_form.class).bindFromRequest();

			hypothesis.number = question.hypothesislist.size()+1;
			hypothesis.question = question;
			hypothesis.text = form.get().hypothesis;
			hypothesis.save();
			
			return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result removemultiplechoicehypothesis(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id, Long hypothesis_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=2 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Hypothesis hypothesis = Hypothesis.find.byId(hypothesis_id);
		if(hypothesis==null){
			return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){

			hypothesis.delete();
			
			return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));

		}
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result removeonechoicehypothesis(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id, Long hypothesis_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=1 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Hypothesis hypothesis = Hypothesis.find.byId(hypothesis_id);
		if(hypothesis==null){
			return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){

			hypothesis.delete();
			
			return redirect(routes.ProfessorTestController.addhypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));

		}
		return redirect(routes.Application.module(module_acronym));
	}
	
	public static Result editmultiplechoicequestionform(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			for(Question q: lesson.questions){
				if(q.user!= null){
					q.user.refresh();
				}
			}
			return ok(views.html.professor.multiplechoicequestionEdit.render(module,lesson,test, group, question));
		}
		return redirect(routes.Application.module(module_acronym));
	}
	
	
	
	public static Result editmultiplechoicequestion(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null || question.typeOfQuestion!=2 || !group.questions.contains(question)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		User user = User.find.byId(session("email"));
		
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Question_Form> form = Form.form(Question_Form.class).bindFromRequest();
//			q.group.add(group);
			question.lesson = lesson;
			question.question = form.get().question;
			question.keywords = form.get().keywords;
			if(form.get().image.length() > 0){
				question.imageURL = form.get().image;
			}
			
			if(form.get().video.length() > 0){
				question.videoURL = form.get().video;
			}
			question.weight = form.get().weight;
			question.weightToLose = form.get().weighttolose;
			question.user = user;
			question.number = group.questions.size()+1;
			question.typeOfQuestion = 2;
			
			question.save();
			
			//return ok(views.html.professor.hypothesisAdd.render(module,lesson,test, group, question));
			return redirect(routes.ProfessorTestController.addmultiplehypothesisform(module.acronym, lesson.acronym, test.id, group.id, question.id));
		}
		return redirect(routes.Application.module(module_acronym));
	}

	public static Result reusequestionadd(String module_acronym, String lesson_acronym, Long test_id, Long group_id, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
	
		Question old_question = question;
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Question copy_question = new Question();
			copy_question.question = old_question.question;
			copy_question.lesson = lesson;
			copy_question.answerSuggestedByStudent = old_question.answerSuggestedByStudent;
			copy_question.keywords = old_question.keywords;
			copy_question.imageURL = old_question.imageURL;
			copy_question.videoURL = old_question.videoURL;
			copy_question.weight = old_question.weight;
			copy_question.weightToLose = old_question.weightToLose;
			copy_question.user = old_question.user;
			copy_question.number = group.questions.size()+1;
			copy_question.typeOfQuestion = copy_question.typeOfQuestion;
			copy_question.iscopy = true;
			copy_question.save();
			
			group.questions.add(copy_question);
			group.save();
			
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test.id));
		}
		return redirect(routes.Application.module(module_acronym));
	}
		
	public static Result publish(String module_acronym, String lesson_acronym, Long test_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			if(test.groups.size()!=0){
			test.published=true;
			test.save();
			} else{
				 flash("error", "You can't publish a test without groups and questions.");
			}
			
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}

		return redirect(routes.Application.module(module.acronym));
	}

	public static Result unpublish(String module_acronym, String lesson_acronym, Long test_id){		
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		

		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			test.published=false;
			test.save();
			
		
			for(Usertest usertest :test.users){
				
				for(QuestionGroup group:usertest.test.groups){
				for(Question question:group.questions){
					if(question.typeOfQuestion!=0){
					List<Hypothesis> hypothesis = Hypothesis.findByUserEmailAndQuestion(usertest.user.email,question.id );
					for(Hypothesis hyp: hypothesis){
						hyp.delete();
					}
					
					}
				}
				}
						
				
				if(usertest.questionevaluation!=null){
				for(QuestionEvaluation userevaluation: usertest.questionevaluation){
					userevaluation.answer=null;
					userevaluation.save();
				}
				}
				
				if(usertest.answers!=null){
					for(Answer useranswers: usertest.answers){
						if(useranswers.answerMarker!=null){
							useranswers.answerMarker.answer=null;
							useranswers.answerMarker.save();
						}
					}
					}
				
				usertest.delete();
				
				
			}
				
				
			
			
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}

		return redirect(routes.Application.module(module.acronym));
	}
		
	public static Result addtest(String module_acronym, String lesson_acronym){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		Form<NewTest_Form> form = Form.form(NewTest_Form.class).bindFromRequest();
		models.test.Test test = new models.test.Test();
		test.name = form.get().name;
		test.text = form.get().text;
		test.lesson = lesson;
		test.expectedDuration=form.get().expectedDuration;
		test.suggestquestionrequired = true;
		test.begin_date = new Date();
		test.finish_date = new Date();
		Long markerlimitdate_millis = test.finish_date.getTime() + ONEWEEKINMILLIS;//data de fim do teste + 7 dias
		test.markers_limit_date = new Date(markerlimitdate_millis);
		test.save();
		
		return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test.id));
	}
	
	return redirect(routes.Application.module(module.acronym));
	}
	
	public static Result edittesttitle(String module_acronym, String lesson_acronym, Long test_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<NewTest_Form> form = Form.form(NewTest_Form.class).bindFromRequest();
		
			test.name=form.get().name;
			test.text=form.get().text;
			test.expectedDuration=form.get().expectedDuration;
			test.save();
			
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
	public static Result changesuggestrequiredsettings(String module_acronym, String lesson_acronym, Long test_id){
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<SuggestedQuestionRequired_Form> form = Form.form(SuggestedQuestionRequired_Form.class).bindFromRequest();
			if(form.get().required){
				test.suggestquestionrequired = true;
				test.save();
			}else{
				test.suggestquestionrequired = false;
				test.save();
			}
			
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));

		}
		
		return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));

	}
	public static Result changetestdates(String module_acronym, String lesson_acronym, Long test_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			Form<Date_Form> form = Form.form(Date_Form.class).bindFromRequest();
			String string = form.get().date.replace(" ", "");
			String [] dates = string.split("-");
			String [] begin_date = dates[0].split("/");
			String [] finish_date = dates[1].split("/");
			
			Calendar begin_calendar = Calendar.getInstance();
			begin_calendar.set(Integer.parseInt(begin_date[2]),(Integer.parseInt(begin_date[1]))-1, Integer.parseInt(begin_date[0]));

			Calendar finish_calendar = Calendar.getInstance();
			finish_calendar.set(Integer.parseInt(finish_date[2]),(Integer.parseInt(finish_date[1]))-1, Integer.parseInt(finish_date[0]));
			
			test.begin_date = new Date(begin_calendar.getTimeInMillis());
			test.finish_date = new Date(finish_calendar.getTimeInMillis());
			
			test.markers_limit_date = new Date(finish_calendar.getTimeInMillis()+ONEWEEKINMILLIS);
			test.save();
			
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));

		}
		return redirect(routes.Application.module(module.acronym));

	}
	
	
	public static Result edittest(String module_acronym, String lesson_acronym, Long test_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			return ok(views.html.professor.testEdit.render(module,lesson,test));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
		
	public static Result test(String module_acronym, String lesson_acronym, Long test_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			
			for (Usertest usertest: test.users)
			usertest.user.refresh();
			return ok(views.html.professor.testGeneral.render(user,
					module,lesson,test));
		}
		
		
		return redirect(routes.Application.module(module.acronym));
	}

	public static Result gradetest(String module_acronym, String lesson_acronym,Long usertest_id, Long group_number){
	
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		Usertest usertest=Usertest.find.byId(usertest_id);
		if(usertest==null){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group=usertest.test.groups.get((int) (group_number-1));
		if(group==null || !usertest.test.groups.contains(group)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
	
	User user = User.find.byId(session("email"));
	if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		
		
	
	
		
		QuestionGroup group_aux = new QuestionGroup();
		group_aux=new QuestionGroup();
		group_aux.id = group.id;
		group_aux.imageURL = group.imageURL;
		group_aux.number = group.number;
		group_aux.question = group.question;
		group_aux.test = group.test;
		group_aux.videoURL = group.videoURL;
		
		
		for(Question q: group.questions){
			Question q_aux = new Question();
			q_aux.id = q.id;
			q_aux.imageURL = q.imageURL;
			q_aux.lesson = q.lesson;
			q_aux.number = q.number;
			q_aux.question = q.question;
			q_aux.typeOfQuestion = q.typeOfQuestion;
			q_aux.urls=q.urls;
			
			
			//q_aux.hypothesislist?????
			group_aux.questions.add(q_aux);
			
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
				q_aux.openanswer=Answer.findByUserTestAndQuestion( usertest.id,q.id);
				if(q_aux.openanswer.questionevaluation != null){
				q_aux.openanswer.questionevaluation.refresh();
				}
				q=q_aux;
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

	public static Result markanswer(String module_acronym, String lesson_acronym,Long usertest_id, Long group_number, Long question_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Usertest usertest=Usertest.find.byId(usertest_id);
		if(usertest==null){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group=usertest.test.groups.get((int) (group_number-1));
		if(group==null || !usertest.test.groups.contains(group)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		Question question = Question.find.byId(question_id);
		if(question==null){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		Form<evaluation_Form> form = Form.form(evaluation_Form.class).bindFromRequest();
		User user = User.find.byId(session("email"));
		Answer answer = Answer.findByUserTestAndQuestion(usertest.id, question_id);
		QuestionEvaluation evaluation = QuestionEvaluation.findByUserAndQuestion(usertest_id, question_id);
		if(evaluation==null){
			
			double percentage = 100.0;
			percentage = (form.get().evaluation/percentage);
			double weight = question.weight;
			double mark = weight*percentage;
			
			usertest.reputationAsAstudent = usertest.reputationAsAstudent + mark;
			usertest.save();
			
			evaluation = new QuestionEvaluation();
			evaluation.question = question;
			evaluation.score = mark;
			evaluation.usertest = usertest;
			evaluation.percent = form.get().evaluation;
			evaluation.evalutationcomment=form.get().evalutationcomment;
			evaluation.answer = answer;
			evaluation.professormarker=user.professorProfile;
			evaluation.save();
			
			answer.questionevaluation = evaluation;
			answer.save();
		}else{
			
			double percentage = 100.0;
			percentage = (form.get().evaluation/percentage);
			double weight = question.weight;
			double mark = weight*percentage;

			usertest.reputationAsAstudent = usertest.reputationAsAstudent - evaluation.score + mark;
			evaluation.percent = form.get().evaluation;
			usertest.save();
			evaluation.score = mark ;
			evaluation.save();
		}
		
		return redirect(routes.ProfessorTestController.gradetest(module_acronym, lesson_acronym, usertest_id, group_number));

	}
	
	public static Result submitreviewedtest(String module_acronym, String lesson_acronym, Long test_id, Long usertest_id){
	
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Usertest usertest=Usertest.find.byId(usertest_id);
		if(usertest==null){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		usertest.reviewd = true;
		usertest.save();
		System.out.println("Review Test submitted with: " + usertest.reputationAsAstudent);
		
		//Processamento de reputações...
		//Processamento das reputações dos modulos
		User student = usertest.user;
		
		System.out.println("Module: "+module_acronym+"User: "+student.email);
	Modulescore modulescore = Modulescore.findByUserAndModule(student.email, module_acronym);
	
//			Double new_modulescore =  (modulescore.score*lesson.tests.size() + usertest.reputationAsAstudent)/lesson.tests.size();
//			int new_modulescore_int = new_modulescore.intValue(); 
//			System.out.println("New Student Module Reputation: " + new_modulescore_int);
//			modulescore.score = new_modulescore_int;
//			modulescore.save();

			
			//test
			
			int soma = 0;
			int count = 0;

				for(Lesson l: module.lessons){
					for(Test t: l.tests){
						if(t.published){
						count = count +1;
						Usertest usertest_temp = Usertest.findByUserAndTest(student.email, t.id);
						if(usertest_temp!=null && usertest_temp.reviewd){
						soma = (int) (soma + usertest_temp.reputationAsAstudent);
						}
						}
					}
			}
			
			
			Double average_module = (double) (soma/count);
			int modulescore_new_score = average_module.intValue();
			modulescore.score = modulescore_new_score;
			modulescore.save();
			System.out.println("SOMA= " + soma);
			System.out.println("COUNT= " + count);
			System.out.println("MODULEEEE CALCULOUS: " + modulescore_new_score);
			
		
			
		//Processamento da Reputação geral do aluno
			int modulescores_sum = 0;
			for(Module m: student.modules){
				Modulescore mscore = Modulescore.findByUserAndModule(student.email, m.acronym);
				modulescores_sum = modulescores_sum + mscore.score;
			}
			
			Double new_globalreputation = (double) (modulescores_sum/student.modules.size());
			long new_globalreputation_int = new_globalreputation.longValue();
			System.out.println("New Student Global Reputation: " + new_globalreputation_int);
			
			student.globalReputation = new_globalreputation_int;
			student.save();

			
		//Fim do processamento
		return redirect(routes.ProfessorTestController.test(module_acronym, lesson_acronym, test_id));
		}
		
				return redirect(routes.Application.index());
	}

	public static Result deletetest(Long test_id,String module_acronym, String lesson_acronym){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
			test.delete();
		}
		//TODO: Criar ligacao pelo controlador do professor
		return redirect(routes.StudentController.lesson(lesson_acronym,module_acronym));
	}

	public static Result addgroup(String module_acronym, String lesson_acronym, Long test_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		
		User user = User.find.byId(session("email"));
		
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		
			Form<NewGroup_Form> form = Form.form(NewGroup_Form.class).bindFromRequest();

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
			
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
			User user = User.find.byId(session("email"));
		
			if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
				
				QuestionGroup questiongroup=QuestionGroup.find.byId(group_id);
				
				questiongroup.delete();
				
				int i=1;
				for(QuestionGroup questiongroup_aux: test.groups){
					questiongroup_aux.number=i;
					questiongroup_aux.save();
					i++;
				}

			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}
		
		return redirect(routes.Application.module(module.acronym));
	}
		
	public static Result removequestion(String module_acronym, String lesson_acronym, Long test_id,Long group_id, Long question_id){
			
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group_aux = QuestionGroup.find.byId(group_id);
		if(group_aux==null || !test.groups.contains(group_aux)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		Question question_aux = Question.find.byId(question_id);
		if(question_aux==null){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		User user = User.find.byId(session("email"));
			
			if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
				
				for(QuestionGroup group: test.groups){
					if(group.id==group_id){
						Question question=Question.find.byId(question_id);
						group.questions.remove(question);	
						group.save();
						int i =1;
						for(Question q: group.questions){
							q.number=i;
							q.save();
							i++;
							
						}
						}
					}
				
				
			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}
		
		return redirect(routes.Application.module(module.acronym));
		}
		
	public static Result editgroup(String module_acronym, String lesson_acronym, Long test_id,Long group_id){
		
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null){
			return redirect(routes.Application.module(module_acronym));
		}
		
		
		Test test = Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup group = QuestionGroup.find.byId(group_id);
		if(group==null || !test.groups.contains(group)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test_id));
		}
		
		
		User user = User.find.byId(session("email"));
			
			if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
				
				Form<NewGroup_Form> form = Form.form(NewGroup_Form.class).bindFromRequest();

				QuestionGroup questiongroup=QuestionGroup.find.byId(group_id);
				questiongroup.question=form.get().question;
				questiongroup.save();

			return redirect(routes.ProfessorTestController.edittest(module_acronym,lesson_acronym,test_id));
		}
		
		return redirect(routes.Application.module(module.acronym));

		}
	
	public static Result preview(String module_acronym, String lesson_acronym,Long test_id, Long group_number){
		
		
		
			
		Module module = Module.findByAcronym(module_acronym);if (module==null){
			System.out.println("The module does not exist.");
			return redirect(routes.Application.modules());
		}
		
		Lesson lesson = Lesson.findByAcronym(lesson_acronym);
		if (lesson==null || !module.lessons.contains(lesson)){
			return redirect(routes.Application.module(module_acronym));
		}
		
		Test test=Test.find.byId(test_id);
		if(test==null|| !lesson.tests.contains(test)){
			return redirect(routes.Application.lesson(module_acronym,lesson_acronym)+"#tests");
		}
		
		QuestionGroup questionGroup=QuestionGroup.findByTestAndGroupNumber(test_id,group_number);
		if(questionGroup==null || !test.groups.contains(questionGroup)){
			return redirect(routes.ProfessorTestController.edittest(module_acronym, lesson_acronym, test.id));
		}
		User user = User.find.byId(session("email"));
		if(SecuredProfessor.isProfessor(session("email")) && SecuredProfessor.isOwner(user,module)){
		return ok(views.html.professor.previewtest.render(module,lesson,test,questionGroup));
}
		
		return redirect(routes.Application.module(module.acronym));
	}
}

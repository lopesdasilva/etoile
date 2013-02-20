package controllers;


import java.util.List;

import models.User;
import models.curriculum.Category;
import models.manytomany.Usertest;
import models.module.Lesson;
import models.module.Module;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionGroup;
import play.api.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import models.test.AnswerMarkers;
import controllers.secured.*;

@Security.Authenticated(Secured.class)
public class StudentMarkerController extends Controller {
	
	public static class MarkerEvaluation {

		public Long answerscore;
		
		public String markercomment;
	}

	
	
	public static Result answersToMark(){
		User user = User.find.byId(request().username());
		List<Category> categories = Category.getAllCategories();
		List<Answer> answers = user.answersToMark;
			
		for(Answer a: answers){
			a.group.refresh();
			a.group.test.refresh();
			a.group.test.lesson.refresh();
			a.group.test.lesson.module.refresh();
		}

		return ok(views.html.secured.answerstomark.render(user, categories, answers));
	}
	
	public static Result answerToMark(Long answer_id){
		User user = User.find.byId(request().username());
		List<Category> categories = Category.getAllCategories();
		Answer answer = Answer.find.byId(answer_id);
		answer.group.refresh();
		answer.openQuestion.refresh();
		
		
		return ok(views.html.secured.answertomark.render(user, categories, answer, answer.openQuestion,form(MarkerEvaluation.class)));
		
	}
	
	public static Result markanswer(Long answer_id){
		User user = User.find.byId(request().username());
		Form<MarkerEvaluation> form = form(
				MarkerEvaluation.class).bindFromRequest();
		System.out.println("AnswerMarkers" + form.get().answerscore);
		
		Answer answer = Answer.find.byId(answer_id);
		
		AnswerMarkers answerMarkers = new AnswerMarkers();
		answerMarkers.answer = answer;
		answerMarkers.answerscore = form.get().answerscore;
		answerMarkers.markercomment=form.get().markercomment;
		answerMarkers.marker= user;
		answerMarkers.save();
		
		answer.answerMarkers=answerMarkers;
		answer.save();
		System.out.println("AnswerMarkers criada.");
		
		answer.markers.remove(user);
		answer.save();
		
		System.out.println("Answer removida da lista do marker.");
		
	return answersToMark();
	}

}

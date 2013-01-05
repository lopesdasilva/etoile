package controllers;


import java.util.List;

import models.User;
import models.manytomany.UserTest;
import models.module.Lesson;
import models.module.Module;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionGroup;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.secured.SecuredProfessor;



@Security.Authenticated(SecuredProfessor.class)
public class ProfessorTestController extends Controller {
	
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


}

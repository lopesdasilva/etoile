package controllers;


import models.User;
import models.manytomany.UserTest;
import models.module.Lesson;
import models.module.Module;
import models.test.Test;
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
		
		
		return ok(views.html.professor.questionanalysis.render(module,lesson,usertest,questionGroup));
	}
	
	
	return redirect(routes.Application.module(module.acronym));
}


}

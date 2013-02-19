package models.manytomany;

import java.util.*;

import javax.persistence.*;

import models.User;
import models.module.Module;
import models.module.Lesson;
import models.test.Answer;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionEvaluation;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Usertest extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
	public double reputationAsAstudent;
	
	@Constraints.Required
	public int reputationAsAMarker;
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean inmodule;
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean submitted;
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean expired;
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean reviewd;
	
	//Ligação com User
	@ManyToOne
	public User user;
	
	//Ligação com Test
	@ManyToOne
	public Test test;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Answer> answers;
	
	public float progress=0;
	public String progressString="0%";
	
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<QuestionEvaluation> questionevaluation;
	
	public static Model.Finder<Long, Usertest> find = new Model.Finder<Long, Usertest>(
			Long.class, Usertest.class);

	public static List<Usertest> getAllTests() {
		List<Usertest> tests = new ArrayList<Usertest>();
		tests = Ebean.find(Usertest.class).findList(); 
		return tests; 
	}
	
	public static Usertest findByUserAndTest(String user_email, Long test_id) {
        return find.where().eq("user_email", user_email).eq("test_id", test_id).findUnique();
    }
	
	public static List<Usertest> findByUser(String user_email) {
        return find.where().eq("user_email", user_email).findList();
    }
	
	public boolean allAnswersMarked(Long usertest_id){
		Usertest ut = Usertest.find.byId(usertest_id);
		List<Answer> answers_aux = ut.answers;
		boolean allmarked = true;
		for(Answer ans: answers_aux){
			if(ans.questionevaluation==null){
				return false;
			}
		}
		return true;
	}
	
	public boolean allQuestionsAnswered(Long usertest_id){
		Usertest ut = Usertest.find.byId(usertest_id);
		for(Answer ans: ut.answers){
			if(ans.answer.equals("No answer.") || ans.answer.equals("")){
				return false;
			}
		}
		return true;
		
	}
	
}

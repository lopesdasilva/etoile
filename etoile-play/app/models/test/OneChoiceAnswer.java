package models.test;

import java.util.*;

import javax.persistence.*;

import models.Blog;
import models.User;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class OneChoiceAnswer extends Model{
	@Id
	@GeneratedValue
	public Long id;
	
	@ManyToOne
	public OneChoiceQuestion oneChoiceQuestion;

	@ManyToOne
	public Test test;
	
	@ManyToOne
	public User user;
	
	@ManyToOne
	public Hypothesis hypothesis;
	
	public static Model.Finder<Long, OneChoiceAnswer> find = new Model.Finder<Long, OneChoiceAnswer>(
			Long.class, OneChoiceAnswer.class);

	public static List<OneChoiceAnswer> getAllOpenQuestions() {
		List<OneChoiceAnswer> questions = new ArrayList<OneChoiceAnswer>();
		questions = Ebean.find(OneChoiceAnswer.class).findList(); 
		return questions; 
	}
	
    public static List<OneChoiceAnswer> findByUserEmailAndTestId(String email,Long test_id) {
        return find.where().eq("user_email", email).eq("test_id", test_id).findList();
    }

	public static OneChoiceAnswer findByUserAndQuestion(Long onechoicequestion_id, String user_id) {
		return find.where().eq("one_choice_question_id",onechoicequestion_id).eq("user_email", user_id).findUnique();
	}
	
}

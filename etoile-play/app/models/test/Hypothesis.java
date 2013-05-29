package models.test;

import java.util.*;

import javax.persistence.*;

import models.User;
import models.test.question.Question;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Hypothesis extends Model{


	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;
	
	public int number;

	@Constraints.Required
    @Formats.NonEmpty
	public String text;
	
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean isCorrect;
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean isSaved;
	
	@Constraints.Required
	public String questionImageURL;
	

	@ManyToOne
	public Question question;

	public boolean selected;
	
	@ManyToOne
	public User user;
	
	public static Model.Finder<Long, Hypothesis> find = new Model.Finder<Long, Hypothesis>(
			Long.class, Hypothesis.class);

	public static List<Hypothesis> getAllHypothesis() {
		List<Hypothesis> questions = new ArrayList<Hypothesis>();
		questions = Ebean.find(Hypothesis.class).findList(); 
		return questions; 
	}
	
	public static List<Hypothesis> findByUserEmailAndQuestion(String email,
			Long question_id) {
		return find.where().eq("user_email", email).eq("question_id", question_id)
				.findList();
	}
	
	public static List<Hypothesis> findByQuestion(Long question_id) {
		return find.where().eq("user_email", null).eq("question_id", question_id)
				.findList();
	}
	
}

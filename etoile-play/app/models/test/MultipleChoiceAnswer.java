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
public class MultipleChoiceAnswer extends Model{
	@Id
	@GeneratedValue
	public Long id;
	
	@ManyToOne
	public MultipleChoiceQuestion multipleChoiceQuestion;

	@ManyToOne
	public Test test;
	
	@ManyToOne
	public User user;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<MultipleChoiceHypothesis> hypothesislist;
	
	public static Model.Finder<Long, MultipleChoiceAnswer> find = new Model.Finder<Long, MultipleChoiceAnswer>(
			Long.class, MultipleChoiceAnswer.class);

	public static List<MultipleChoiceAnswer> getAllMultipleChoiceAnswers() {
		List<MultipleChoiceAnswer> questions = new ArrayList<MultipleChoiceAnswer>();
		questions = Ebean.find(MultipleChoiceAnswer.class).findList(); 
		return questions; 
	}
	
    public static List<MultipleChoiceAnswer> findByUserEmailAndTestId(String email,Long test_id) {
        return find.where().eq("user_email", email).eq("test_id", test_id).findList();
    }

	public static MultipleChoiceAnswer findByUserAndQuestion(Long multiplechoicequestion_id, String user_id) {
		return find.where().eq("multiple_choice_question_id",multiplechoicequestion_id).eq("user_email", user_id).findUnique();
	}
	
}

package models.test;

import java.util.*;

import javax.persistence.*;

import models.Blog;
import models.User;
import models.test.question.Question;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class ChoiceAnswer extends Model {
	@Id
	@GeneratedValue
	public Long id;

	@ManyToOne
	public Question question;

	@ManyToOne
	public Test test;

	@ManyToOne
	public User user;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Hypothesis> hypothesislist;

	public static Model.Finder<Long, ChoiceAnswer> find = new Model.Finder<Long, ChoiceAnswer>(
			Long.class, ChoiceAnswer.class);

	public static List<ChoiceAnswer> getAllOpenQuestions() {
		List<ChoiceAnswer> questions = new ArrayList<ChoiceAnswer>();
		questions = Ebean.find(ChoiceAnswer.class).findList();
		return questions;
	}

	public static List<ChoiceAnswer> findByUserEmailAndTestId(String email,
			Long test_id) {
		return find.where().eq("user_email", email).eq("test_id", test_id)
				.findList();
	}

	public static ChoiceAnswer findByUserAndQuestion(Long openquestion_id,
			String user_id) {
		return find.where().eq("question_id", openquestion_id)
				.eq("user_email", user_id).findUnique();
	}

}

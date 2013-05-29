package models.test.question;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;
import javax.validation.Constraint;



import models.Professor;
import models.User;
import models.manytomany.Usertest;
import models.module.Lesson;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.enums.QuestionType;
import models.test.question.enums.QuestionType.Type;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class QuestionEvaluation extends Model {
	
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;
	
	@Constraints.Required
	public double score;
	
	@Constraints.Required
	public int percent;
	
	@Constraints.Required
	public boolean isCorrect;
	
	@ManyToOne
	public Usertest usertest;
	
	@ManyToOne(cascade = {CascadeType.DETACH})
	public Question question;
	
	@OneToOne(cascade = {CascadeType.DETACH})
	public Answer answer;
	
	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String evalutationcomment;
	
	@OneToOne
	public Professor professormarker;
	
	
	public static Model.Finder<Long, QuestionEvaluation> find = new Model.Finder<Long, QuestionEvaluation>(
			Long.class, QuestionEvaluation.class);
	
	public static QuestionEvaluation findByUserAndQuestion(Long user_test_id,
			Long question_id) {
		return find.where().eq("usertest_id", user_test_id).eq("question_id", question_id).findUnique();
	}
	
	public static List<QuestionEvaluation> findByUserAndTest(Long user_test_id,
			Long test_id) {
		return find.where().eq("usertest_id", user_test_id).eq("test_id", test_id).findList();
	}
	
	
}

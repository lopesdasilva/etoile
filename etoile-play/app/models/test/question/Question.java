package models.test.question;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import models.Blog;
import models.User;
import models.module.Lesson;
import models.test.Answer;
import models.test.ChoiceAnswer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.enums.QuestionType;
import models.test.question.enums.QuestionType.Type;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Question extends Model {
	
	//ASSOCIAÕES
	
	@ManyToOne
	public Lesson lesson;
	
	@ManyToOne
	public User user;
	//ARGUMENTOS COMUNS
	@ManyToMany(mappedBy="questions")
	List<QuestionGroup> group;
	@Id
	@GeneratedValue
	public Long id;
	
	@Constraints.Required
    @Formats.NonEmpty
	public int number;
	
	public int typeOfQuestion;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String question;
	
	@Constraints.Required
	public String imageURL;
	
	@Constraints.Required
	public String videoURL;

	
	//ARGUMENTOS OPEN QUESTION
		@OneToMany
		List<Answer> openanswers;
		
	//ARGUMENTOS ONE CHOICE QUESTION
		
	@OneToMany
	List<ChoiceAnswer> choiceanswers;
		
	@OneToMany
	public List<Hypothesis> hypothesislist;
	
	
	public static Model.Finder<Long, Question> find = new Model.Finder<Long, Question>(
			Long.class, Question.class);
}

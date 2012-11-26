package models.test;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class MultipleChoiceQuestion extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String question;
	
//	@OneToMany(cascade = {CascadeType.ALL})
//	public List<Hypothesis> correct_hypothesis;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<MultipleChoiceHypothesis> hypothesislist;
	
	@Constraints.Required
	public String questionImageURL;

	@ManyToOne
	public Test test;
	

	
	public static Model.Finder<Long, MultipleChoiceQuestion> find = new Model.Finder<Long, MultipleChoiceQuestion>(
			Long.class, MultipleChoiceQuestion.class);

	public static List<MultipleChoiceQuestion> getAllOpenQuestions() {
		List<MultipleChoiceQuestion> questions = new ArrayList<MultipleChoiceQuestion>();
		questions = Ebean.find(MultipleChoiceQuestion.class).findList(); 
		return questions; 
	}
	
}

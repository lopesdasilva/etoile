package models.test;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class OneChoiceQuestion extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String question;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String correct_hypothesis;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Hypothesis> hypothesyslist;
	
	@Constraints.Required
	public String imageURL;
	
	@Constraints.Required
	public String videoURL;

	@ManyToMany(mappedBy="tests")
	public Test test;
	

	
	public static Model.Finder<Long, OneChoiceQuestion> find = new Model.Finder<Long, OneChoiceQuestion>(
			Long.class, OneChoiceQuestion.class);

	public static List<OneChoiceQuestion> getAllOpenQuestions() {
		List<OneChoiceQuestion> questions = new ArrayList<OneChoiceQuestion>();
		questions = Ebean.find(OneChoiceQuestion.class).findList(); 
		return questions; 
	}
	
}

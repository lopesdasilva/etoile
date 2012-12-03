package models.test;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class MultipleChoiceHypothesis extends Model{
	@Id
	@GeneratedValue
	public Long id;
	
	public int number;

	@Constraints.Required
    @Formats.NonEmpty
	public String text;
	
	
	@Constraints.Required
	public String questionImageURL;

	
	@ManyToMany(mappedBy="hypothesislist")
	public MultipleChoiceQuestion multiplechoicequestion;
	
	@ManyToMany(mappedBy="hypothesislist")
	public MultipleChoiceAnswer multiplechoiceanswer;
	
	@ManyToMany(mappedBy="hypothesislist")
	public MultipleChoiceQuestion question;
	

	
	public static Model.Finder<Long, MultipleChoiceHypothesis> find = new Model.Finder<Long, MultipleChoiceHypothesis>(
			Long.class, MultipleChoiceHypothesis.class);

	public static List<MultipleChoiceHypothesis> getAllMultipleChoiceHypothesis() {
		List<MultipleChoiceHypothesis> questions = new ArrayList<MultipleChoiceHypothesis>();
		questions = Ebean.find(MultipleChoiceHypothesis.class).findList(); 
		return questions; 
	}
	
}

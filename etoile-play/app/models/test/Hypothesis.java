package models.test;

import java.util.*;

import javax.persistence.*;

import models.test.question.Question;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Hypothesis extends Model{
	@Id
	@GeneratedValue
	public Long id;
	
	public int number;

	@Constraints.Required
    @Formats.NonEmpty
	public String text;
	
	
	@Constraints.Required
	public String questionImageURL;

	@ManyToMany(mappedBy="hypothesyslist")
	public OneChoiceQuestion onechoicequestion;
	
	@ManyToMany(mappedBy="hypothesislist")
	public MultipleChoiceQuestion multiplechoicequestion;
	
	@ManyToOne
	public Question question;

	
	public static Model.Finder<Long, Hypothesis> find = new Model.Finder<Long, Hypothesis>(
			Long.class, Hypothesis.class);

	public static List<Hypothesis> getAllHypothesis() {
		List<Hypothesis> questions = new ArrayList<Hypothesis>();
		questions = Ebean.find(Hypothesis.class).findList(); 
		return questions; 
	}
	
}

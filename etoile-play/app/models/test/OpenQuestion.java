package models.test;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class OpenQuestion extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String question;
	
	
	@Constraints.Required
	public String questionImageURL;

	@ManyToMany(mappedBy="tests")
	public Test test;
	

	
	public static Model.Finder<Long, OpenQuestion> find = new Model.Finder<Long, OpenQuestion>(
			Long.class, OpenQuestion.class);

	public static List<OpenQuestion> getAllOpenQuestions() {
		List<OpenQuestion> questions = new ArrayList<OpenQuestion>();
		questions = Ebean.find(OpenQuestion.class).findList(); 
		return questions; 
	}
	
}

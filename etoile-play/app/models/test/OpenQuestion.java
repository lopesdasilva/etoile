package models.test;

import java.util.*;

import javax.persistence.*;

import models.Comment;
import models.User;

import com.avaje.ebean.Ebean;
import models.course.Lesson;

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
	
	@OneToMany
	public List<Answer> answers;
	
	@ManyToOne
	public Lesson lesson;
	
	@ManyToOne
	public User user;

	
	public static Model.Finder<Long, OpenQuestion> find = new Model.Finder<Long, OpenQuestion>(
			Long.class, OpenQuestion.class);

	public static List<OpenQuestion> getAllOpenQuestions() {
		List<OpenQuestion> questions = new ArrayList<OpenQuestion>();
		questions = Ebean.find(OpenQuestion.class).findList(); 
		return questions; 
	}
	
}

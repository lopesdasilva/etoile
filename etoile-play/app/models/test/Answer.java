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
public class Answer extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String answer;
	
	@ManyToOne
	public OpenQuestion openQuestion;

	@ManyToOne
	public Test test;
	
	@ManyToOne
	public User user;
	
	public static Model.Finder<Long, Answer> find = new Model.Finder<Long, Answer>(
			Long.class, Answer.class);

	public static List<Answer> getAllOpenQuestions() {
		List<Answer> questions = new ArrayList<Answer>();
		questions = Ebean.find(Answer.class).findList(); 
		return questions; 
	}
	
    public static List<Answer> findByUserEmailAndTestId(String email,Long test_id) {
        return find.where().eq("user_email", email).eq("test_id", test_id).findList();
    }

	public static Answer findByUserAndQuestion(Long openquestion_id, String user_id) {
		return find.where().eq("open_question_id",openquestion_id).eq("user_email", user_id).findUnique();
	}
	
}

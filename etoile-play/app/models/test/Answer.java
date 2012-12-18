package models.test;

import java.util.*;

import javax.persistence.*;

import models.Blog;
import models.User;
import models.test.question.Question;
import models.test.question.QuestionGroup;

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
    @Column(columnDefinition="TEXT")
	public String answer;
	
	@ManyToOne
	public Question openQuestion;

	@OneToOne
	public Test test;
	
	@ManyToOne
	public User user;
	
	@ManyToOne
	public QuestionGroup group;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<User> markers;
	
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

	public static Answer findByUserAndQuestion(String user_id,Long openquestion_id) {
		return find.where().eq("open_question_id",openquestion_id).eq("user_email", user_id).findUnique();
	}
	
}

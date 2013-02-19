package models.test;

import java.util.*;

import javax.persistence.*;

import models.Blog;
import models.User;
import models.manytomany.Usertest;
import models.test.question.Question;
import models.test.question.QuestionEvaluation;
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
	public Usertest usertest;
	
	@ManyToOne
	public QuestionGroup group;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<User> markers;
	
	@OneToOne
	public Evaluation evaluation;
	
	@OneToOne(cascade = {CascadeType.ALL})
	public QuestionEvaluation questionevaluation;
	
	public static Model.Finder<Long, Answer> find = new Model.Finder<Long, Answer>(
			Long.class, Answer.class);

	public static List<Answer> getAllOpenQuestions() {
		List<Answer> questions = new ArrayList<Answer>();
		questions = Ebean.find(Answer.class).findList(); 
		return questions; 
	}
	
	
    public static List<Answer> findByUserTestAndTestId(Long usertest_id,Long test_id) {
        return find.where().eq("usertest_id", usertest_id).eq("test_id", test_id).findList();
    }
    
    public static List<Answer> findByUserEmailAndTestIdAndGroupId(String email,Long test_id,Long group_id) {
        return find.where().eq("user_email", email).eq("test_id", test_id).eq("group_id", group_id).findList();
    }

	public static Answer findByUserTestAndQuestion(Long usertest_id,Long openquestion_id) {
		return find.where().eq("open_question_id",openquestion_id).eq("usertest_id", usertest_id).findUnique();
	}
	
}

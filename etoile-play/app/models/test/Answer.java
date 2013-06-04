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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
    @Column(columnDefinition="TEXT")
	public String answer;
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean isSaved;
	
	@ManyToOne
	public Question openQuestion;

	@OneToOne(cascade = {CascadeType.DETACH})
	public Test test;
	
	@ManyToOne(cascade = {CascadeType.DETACH})
	public Usertest usertest;
	
	@ManyToOne(cascade = {CascadeType.DETACH})
	public QuestionGroup group;

	
	@OneToOne(cascade = {CascadeType.ALL})
	public AnswerMarker answerMarker;
	
	@OneToOne(cascade = {CascadeType.ALL})
	public QuestionEvaluation questionevaluation;
	
	public static Model.Finder<Long, Answer> find = new Model.Finder<Long, Answer>(
			Long.class, Answer.class);

    @OrderBy("id")
	public static List<Answer> getAllOpenQuestions() {
		List<Answer> questions = new ArrayList<Answer>();
		questions = Ebean.find(Answer.class).findList(); 
		return questions; 
	}

    @OrderBy("id")
    public static List<Answer> findByUserTestAndTestId(Long usertest_id,Long test_id) {
        return find.where().eq("usertest_id", usertest_id).eq("test_id", test_id).findList();
    }


    @OrderBy("id")
    public static List<Answer> findByUserEmailAndTestIdAndGroupId(String email,Long test_id,Long group_id) {
        return find.where().eq("user_email", email).eq("test_id", test_id).eq("group_id", group_id).findList();
    }

	public static Answer findByUserTestAndQuestion(Long usertest_id,Long openquestion_id) {
		return find.where().eq("open_question_id",openquestion_id).eq("usertest_id", usertest_id).findUnique();
	}
	
}

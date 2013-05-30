package models.test.question;

import java.util.List;

import javax.persistence.*;


import models.Blog;
import models.User;
import models.module.Lesson;
import models.test.Answer;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.enums.QuestionType;
import models.test.question.enums.QuestionType.Type;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class QuestionGroup extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    //ASSOCIAÕES
	
	@ManyToOne
	public Test test;
	
	@ManyToMany(cascade = {CascadeType.ALL})
    @OrderBy("id")
	public List<Question> questions;
	
	@ManyToOne(cascade = {CascadeType.ALL})
    @OrderBy("id")
	public List<Answer> answers;
	

	
	@Constraints.Required
    @Formats.NonEmpty
    @Column(columnDefinition="TEXT")
	public String question;
	
	@Constraints.Required
	public String imageURL;
	
	@Constraints.Required
	public String videoURL;
	
	@Constraints.Required
    @Formats.NonEmpty
	public int number;
	
	
	public static Model.Finder<Long, QuestionGroup> find = new Model.Finder<Long, QuestionGroup>(
			Long.class, QuestionGroup.class);
	
	 public static QuestionGroup findByTestAndGroupNumber(Long test_id,Long group_number) {
	        return find.where().eq("test_id", test_id).eq("number",group_number).findUnique();
	    }
	
}

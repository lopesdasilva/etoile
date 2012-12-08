package models.test.question;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


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
	
	//ASSOCIAÕES
	
	@ManyToOne
	public Test test;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Question> questions;
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Constraints.Required
    @Formats.NonEmpty
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
}

package models.test.question;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;
import javax.validation.Constraint;



import models.User;
import models.curriculum.Curriculummodule;
import models.manytomany.Usertest;
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
public class Question extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

	
	//ASSOCIAÕES
	
	@ManyToOne
	public Lesson lesson;
	
	@ManyToOne
	public User user;
	
	
	//ARGUMENTOS COMUNS
	@ManyToMany(mappedBy="questions")
	public List<QuestionGroup> group;
	

	
	@Constraints.Required
	public int weight;
	
	@Constraints.Required
	public int weightToLose;
	
	@Constraints.Required
    @Formats.NonEmpty
	public int number;
	
	public int typeOfQuestion;
	
	@Constraints.Required
    @Formats.NonEmpty
	@Column(columnDefinition="TEXT")
	public String question;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String answerSuggestedByStudent;
	
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String imageURL;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String videoURL;

	@Constraints.Required
	public String keywords;
	
	@Constraints.Required
	public boolean iscopy;
	
	//ARGUMENTOS OPEN QUESTION
	@OneToOne(cascade = {CascadeType.ALL})
	public Answer openanswer;
		
	//ARGUMENTOS ONE CHOICE QUESTION

	@OneToMany(cascade = {CascadeType.ALL})
    @OrderBy("id")
	public List<Hypothesis> hypothesislist;
	
	@OneToMany(cascade=CascadeType.ALL)
    @OrderBy("id")
	public List<URL> urls;
	
	@OneToMany(cascade = {CascadeType.ALL})
    @OrderBy("id")
	public List<QuestionEvaluation> questionevaluation;
	
	@ManyToOne
	public Curriculummodule subtopic;
	
	
	public static Model.Finder<Long, Question> find = new Model.Finder<Long, Question>(
			Long.class, Question.class);
	

	
	public List<URL> getTopUrls(long question_id) {
		List<URL> aux=	URL.findByQuestion(question_id);
		URL url_aux = null;
		if(aux.size()>0){
			Collections.sort(aux);
			if(aux.size()>5){
				for(URL url: aux){
					if(url.isFeatured(url.id)){
						url_aux= url;
						break;
					}

				}
				aux=aux.subList(0, 4);
				if (url_aux!=null && !aux.contains(url_aux))
					aux.add(url_aux);
			}
		}
		Collections.shuffle(aux);
		return aux;
	}
}

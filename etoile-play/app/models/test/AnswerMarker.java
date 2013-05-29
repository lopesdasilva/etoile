package models.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.User;
import models.continent.Continent;


import com.avaje.ebean.Ebean;


import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class AnswerMarker extends Model {

	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public Long answerscore;
	
	@Constraints.Required
	public Long markerscore;
	
	@ManyToOne(cascade = {CascadeType.DETACH})
	public Answer answer;

	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String markercomment;
	
	@ManyToOne(cascade = {CascadeType.DETACH})
	public User user;
	
	@Constraints.Required
	public boolean isMarked;
	
	
	
	public static Model.Finder<Long, AnswerMarker> find = new Model.Finder<Long, AnswerMarker>(
			Long.class, AnswerMarker.class);

	public static List<AnswerMarker> getAllEvaluations() {
		List<AnswerMarker> answerMarker = new ArrayList<AnswerMarker>();
		answerMarker = Ebean.find(AnswerMarker.class)
				.findList(); 
		return answerMarker; 
	}
	
	public static AnswerMarker getByAnswerAndUser(String user_email, Long answer_id){
		return find.where().eq("user_email", user_email).eq("answer_id", answer_id).findUnique();
	}
	
	public static List<AnswerMarker> getByMarker(String user_email){
		return find.where().eq("user_email", user_email).eq("is_marked", false).findList();
	}
}

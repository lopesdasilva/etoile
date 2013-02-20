package models.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.User;
import models.continent.Continent;


import com.avaje.ebean.Ebean;


import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class AnswerMarkers extends Model {

	
	@Id
	@GeneratedValue
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
	
	
	
	public static Model.Finder<Long, AnswerMarkers> find = new Model.Finder<Long, AnswerMarkers>(
			Long.class, AnswerMarkers.class);

	public static List<AnswerMarkers> getAllEvaluations() {
		List<AnswerMarkers> answerMarkers = new ArrayList<AnswerMarkers>();
		answerMarkers = Ebean.find(AnswerMarkers.class)
				.findList(); 
		return answerMarkers; 
	}
	
	public static AnswerMarkers getByAnswerAndUser(String user_email, Long answer_id){
		return find.where().eq("user_email", user_email).eq("answer_id", answer_id).findUnique();
	}
	
	public static List<AnswerMarkers> getByMarker(String user_email){
		return find.where().eq("user_email", user_email).eq("is_marked", false).findList();
	}
}

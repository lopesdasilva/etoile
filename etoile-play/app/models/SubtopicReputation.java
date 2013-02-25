package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.curriculum.Curriculummodule;
import models.module.Module;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class SubtopicReputation extends Model{

	@Id
	@GeneratedValue
    @Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public Long reputationAsAStudent;
	
	@Constraints.Required
	public Long reputationAsAMarker;
	
	@ManyToOne
	public Curriculummodule subtopic;
	
	@ManyToOne
	public User user;
	
	public static Model.Finder<Long, SubtopicReputation> find = new Model.Finder<Long, SubtopicReputation>(
			Long.class, SubtopicReputation.class);
	
	public static SubtopicReputation findByUserAndTopic(String user_email, Long subtopic_id){
		return find.where().eq("user_email", user_email).eq("subtopic_id", subtopic_id).findUnique();
	}
	
}

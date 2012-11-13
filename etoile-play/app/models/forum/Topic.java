package models.forum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
@Entity
public class Topic extends Model {
	@Id
	public Long id;

	@Constraints.Required
	public String text;

	@ManyToOne
	public Forum forum;
	
	@OneToMany
	public List<Reply> replies;

	//public Date created;

	public static Model.Finder<Long, Topic> find = new Model.Finder<Long, Topic>(
			Long.class, Topic.class);

	public static List<Topic> getAllTopics() {
		List<Topic> topics = new ArrayList<Topic>();
		topics = Ebean.find(Topic.class).findList(); 
		return topics; 
	}

}

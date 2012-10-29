package models.forum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import com.avaje.ebean.Ebean;

import play.data.validation.Constraints;
import play.db.ebean.Model;
@Entity
public class Forum extends Model {
	@Id
	@GeneratedValue
	public Long id;
	
	
	@Constraints.Required
	public String description;
	
	@OneToMany
	public List<Topic> topics;
	
	public static Model.Finder<Long, Forum> find = new Model.Finder<Long, Forum>(
			Long.class, Forum.class);

	public static List<Forum> getAllForums() {
		List<Forum> forums = new ArrayList<Forum>();
		forums = Ebean.find(Forum.class)
				.findList(); 
		return forums; 
	}
		
}

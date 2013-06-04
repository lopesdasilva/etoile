package models.forum;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.module.Module;


import com.avaje.ebean.Ebean;

import play.data.validation.Constraints;
import play.db.ebean.Model;
@Entity
public class Forum extends Model {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;
	
	
	@Constraints.Required
	public String description;
	
	@OneToMany
    @OrderBy("date")
	public List<Topic> topics;
	
	@OneToOne
	public Module module;
	
	public static Model.Finder<Long, Forum> find = new Model.Finder<Long, Forum>(
			Long.class, Forum.class);


    @OrderBy("id")
	public static List<Forum> getAllForums() {
		List<Forum> forums = new ArrayList<Forum>();
		forums = Ebean.find(Forum.class)
				.findList(); 
		return forums; 
	}
		
}

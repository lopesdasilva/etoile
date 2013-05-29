package models;

import java.util.*;

import javax.persistence.*;

import models.module.Module;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class Language extends Model {
	
	@Id
	public Long id;

	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	public String name;

	@OneToMany
	@OrderBy("id")
	public List<Module> modules;
	

	public static Model.Finder<Long, Comment> find = new Model.Finder<Long, Comment>(
			Long.class, Comment.class);

	public static List<Comment> getAllComments() {
		List<Comment> comments = new ArrayList<Comment>();
		comments = Ebean.find(Comment.class).findList(); 
		return comments; 
	}
}
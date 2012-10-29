package models.course;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import com.avaje.ebean.Ebean;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Content extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String title;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;
	
	@ManyToOne
	public Course course;
	
	public static Model.Finder<Long, Content> find = new Model.Finder<Long, Content>(
			Long.class, Content.class);

	public static List<Content> getAllContent() {
		List<Content> contents = new ArrayList<Content>();
		contents = Ebean.find(Content.class).findList(); 
		return contents; 
	}
	
}

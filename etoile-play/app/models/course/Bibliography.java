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
public class Bibliography extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String title;
	
	@Constraints.Required
    @Formats.NonEmpty
    @Column(columnDefinition="TEXT")
	public String description;

	@Constraints.Required
	public String imageURL;
	
	@Constraints.Required
	public String link;
	
	@ManyToOne
	public Course course;
	
	public static Model.Finder<Long, Bibliography> find = new Model.Finder<Long, Bibliography>(
			Long.class, Bibliography.class);

	public static List<Bibliography> getAllContent() {
		List<Bibliography> contents = new ArrayList<Bibliography>();
		contents = Ebean.find(Bibliography.class).findList(); 
		return contents; 
	}
	
}

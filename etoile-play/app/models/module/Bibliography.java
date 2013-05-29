package models.module;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import com.avaje.ebean.Ebean;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Bibliography extends Model{
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String title;
	
	@Constraints.Required
    @Formats.NonEmpty
    @Column(columnDefinition="TEXT")
	public String description;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String imageURL;
	
	@Constraints.Required
	public String link;
	
	@ManyToOne
	public Module module;
	
	public static Model.Finder<Long, Bibliography> find = new Model.Finder<Long, Bibliography>(
			Long.class, Bibliography.class);

	public static List<Bibliography> getAllContent() {
		List<Bibliography> contents = new ArrayList<Bibliography>();
		contents = Ebean.find(Bibliography.class).findList(); 
		return contents; 
	}
	
}

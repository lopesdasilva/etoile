package models.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


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

    @Constraints.Required
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date date= new Date();

	@ManyToOne
	public Module module;
	
	public static Model.Finder<Long, Content> find = new Model.Finder<Long, Content>(
			Long.class, Content.class);


    @OrderBy("id")
	public static List<Content> getAllContent() {
		List<Content> contents = new ArrayList<Content>();
		contents = Ebean.find(Content.class).findList(); 
		return contents; 
	}
	
}

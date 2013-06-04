package models.module;

import java.util.*;

import javax.persistence.*;

import models.module.Lesson;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Lessoncontent extends Model{
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String name;
	
	@Constraints.Required
    @Formats.NonEmpty
    @Column(columnDefinition="TEXT")
	public String text;

    @Constraints.Required
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date date= new Date();

	@ManyToOne
	public Lesson lesson;
	
	@Constraints.Required
    @Formats.NonEmpty
    @Column(columnDefinition="TEXT")
	public String url;
	
	@Constraints.Required
    @Formats.NonEmpty
    @Column(columnDefinition="TEXT")
	public String lessonContentImageURL;
	
	

	
	public static Model.Finder<Long, Lessoncontent> find = new Model.Finder<Long, Lessoncontent>(
			Long.class, Lessoncontent.class);

    @OrderBy("id")
	public static List<Lessoncontent> getAllTests() {
		List<Lessoncontent> tests = new ArrayList<Lessoncontent>();
		tests = Ebean.find(Lessoncontent.class).findList(); 
		return tests; 
	}
	
}

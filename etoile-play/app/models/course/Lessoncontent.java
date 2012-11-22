package models.course;

import java.util.*;

import javax.persistence.*;

import models.course.Lesson;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Lessoncontent extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String name;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String text;

	@ManyToMany(mappedBy="lessonscontents")
	public Lesson lesson;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String url;
	
	@Constraints.Required
    @Formats.NonEmpty
	public String lessonContentImageURL;
	
	

	
	public static Model.Finder<Long, Lessoncontent> find = new Model.Finder<Long, Lessoncontent>(
			Long.class, Lessoncontent.class);

	public static List<Lessoncontent> getAllTests() {
		List<Lessoncontent> tests = new ArrayList<Lessoncontent>();
		tests = Ebean.find(Lessoncontent.class).findList(); 
		return tests; 
	}
	
}

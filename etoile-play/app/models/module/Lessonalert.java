package models.module;

import java.util.*;

import javax.persistence.*;

import models.module.Lesson;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Lessonalert extends Model{
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
	public String imageURL;

	
	public static Model.Finder<Long, Lessonalert> find = new Model.Finder<Long, Lessonalert>(
			Long.class, Lessonalert.class);


    @OrderBy("id")
	public static List<Lessonalert> getAllTests() {
		List<Lessonalert> alerts = new ArrayList<Lessonalert>();
		alerts = Ebean.find(Lessonalert.class).findList(); 
		return alerts; 
	}
	
}

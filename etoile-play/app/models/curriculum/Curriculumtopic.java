package models.curriculum;

import java.util.*;

import javax.persistence.*;

import models.User;
import models.test.Test;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Curriculumtopic extends Model implements Comparable<Curriculumtopic>{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	
	@Constraints.Required
	@Column(unique = true)
	public String keyword;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String text;

    @Constraints.Required
    public int likes=0;

    @ManyToOne
    public User user;

    @ManyToMany (cascade = {CascadeType.ALL})
   // @JoinTable(name="voters_resources")
   // @OrderBy("id")
    public List<User> voters;

	@ManyToOne(cascade = {CascadeType.DETACH})
	public Curriculumlesson curriculumlesson;
	
	
	public static Model.Finder<Long, Curriculumtopic> find = new Model.Finder<Long,Curriculumtopic>(
			Long.class, Curriculumtopic.class);

    @OrderBy("id")
	public static List<Curriculumtopic> getAllLessons() {
		List<Curriculumtopic> lessons = new ArrayList<Curriculumtopic>();
		lessons = Ebean.find(Curriculumtopic.class).findList(); 
		return lessons; 
	}

    @Override
    public int compareTo(Curriculumtopic o) {
        return (o.likes<likes ? -1 : (o.likes==likes ? 0 : 1));
    }
}

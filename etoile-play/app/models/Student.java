package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import models.module.Module;
import models.module.University;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import scala.collection.mutable.ArrayBuilder;

@Entity 
public class Student extends Model {

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	@Formats.NonEmpty
	public Long id;
	
	@Constraints.Required
	public String email;
	
	@Constraints.Required
	public String acronym;

	@Constraints.Required
	public String firstname;

	@Constraints.Required
	public String lastname;

	@Constraints.Required
	public String scientific_area;
	
	@Constraints.Required
	public String degree;
	
	@ManyToOne
	public University university;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String description;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String imageURL;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String webpage;

	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String contact;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String address;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String shortdescription;
	
	@Constraints.Required
	public Date date_of_birth;
	
	@OneToOne
    @OrderBy("id")
	public User user;
	
	@Constraints.Required
	public boolean male;
	
	@Constraints.Required
	public boolean privateProfile;
	
	@Constraints.Required
	public int CSSId;

	public static Model.Finder<Long,Student> find = new Model.Finder(Long.class, Student.class);

    @OrderBy("id")
	public static String[] getAllEmails(){
		
		
		List<Student> professors = new ArrayList<Student>();
		professors = Ebean.find(Student.class).findList();
		String[] profs_emails = new String[professors.size()];
		int i=0;
		for(Student p : professors){
			profs_emails[i]=p.email;
			i++;
		}
		return profs_emails;
	}
	
	public static Student findByAcronym(String acronym) {
		return find.where().eq("acronym", acronym).findUnique();
	}

    public static int getNumberOfStudents() {
        int i = 0;
        for(Student student: Student.find.all()){
            if(!student.user.tester){
                i++;
            }
        }
        return i;
    }

}

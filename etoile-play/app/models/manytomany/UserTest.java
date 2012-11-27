package models.manytomany;

import java.util.*;

import javax.persistence.*;

import models.User;
import models.module.Module;
import models.module.Lesson;
import models.test.Test;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class UserTest extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public boolean inmodule;
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean submited;
	
	@Constraints.Required
    @Formats.NonEmpty
	public boolean expired;
	
	//Ligação com User
	@ManyToOne
	public User user;
	
	//Ligação com Test
	@ManyToOne
	public Test test;
	
	
	public static Model.Finder<Long, UserTest> find = new Model.Finder<Long, UserTest>(
			Long.class, UserTest.class);

	public static List<UserTest> getAllTests() {
		List<UserTest> tests = new ArrayList<UserTest>();
		tests = Ebean.find(UserTest.class).findList(); 
		return tests; 
	}
	
	public static UserTest findByUserAndTest(String user_email, Long test_id) {
        return find.where().eq("user_email", user_email).eq("test_id", test_id).findUnique();
    }
	
	public static List<UserTest> findByUser(String user_email) {
        return find.where().eq("user_email", user_email).findList();
    }
	
}

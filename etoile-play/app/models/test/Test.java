package models.test;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Generated;
import javax.persistence.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import models.Comment;
import models.manytomany.Usertest;
import models.module.Lesson;
import models.test.question.Question;
import models.test.question.QuestionGroup;

import com.avaje.ebean.Ebean;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

@Entity
public class Test extends Model{
	@Id
	@GeneratedValue
	public Long id;

	@Constraints.Required
    @Formats.NonEmpty
	public String name;
	
	@Constraints.Required
	@Constraints.MaxLength(value = 255)
	@Column(columnDefinition="TEXT")
	public String text;
	
	@Constraints.Required
	@Column(columnDefinition="TEXT")
	public String testImageURL;
	
	@Constraints.Required
	public String expectedDuration;
	
	@Constraints.Required
	public boolean published=false;

	@ManyToOne
	public Lesson lesson;
	
	@OneToMany
	public List<QuestionGroup> groups;
	
	@OneToMany
	public List<Answer> answers;

	
	@OneToMany
	public List<Usertest> users;
	
	@Constraints.Required
	public Date begin_date;
	
	@Constraints.Required
	public Date finish_date;
	
	public static Model.Finder<Long, Test> find = new Model.Finder<Long, Test>(
			Long.class, Test.class);

	public static List<Test> getAllTests() {
		List<Test> tests = new ArrayList<Test>();
		tests = Ebean.find(Test.class).findList(); 
		return tests; 
	}
	
	
	public boolean studentsEnrolled(Test t){
		for(Usertest userTest : t.users){
			if(userTest.inmodule)
				return true;
		}
		return false;
	}
	public int numberStudentsEnrolled(Test t){
		int number=0;
		for(Usertest userTest : t.users){
			if(userTest.inmodule)
				number++;
		}
		return number;
	}
	
	public int numberOfQuestions(Test t){
		int number=0;
		for(QuestionGroup group: t.groups){
			number+=group.questions.size();
		}
		
		
		return number;
	}
	
	public String getBeginDate(){
		Long yourmilliseconds = begin_date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.UK);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UK"));
        calendar.setTimeInMillis(yourmilliseconds);
        System.out.println("GregorianCalendar -"+sdf.format(calendar.getTime()));
		return sdf.format(calendar.getTime());
	}

	
	public String getFinishDate(){
		Long yourmilliseconds = finish_date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.UK);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UK"));
        calendar.setTimeInMillis(yourmilliseconds);
        System.out.println("GregorianCalendar -"+sdf.format(calendar.getTime()));
		return sdf.format(calendar.getTime());
	}
}

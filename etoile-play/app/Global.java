import play.*;
import java.math.BigDecimal;
import java.util.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		System.out.println(Ebean.find(Blog.class).findRowCount());
		if (Ebean.find(Blog.class).findRowCount() == 0) {
			Logger.info("Init Data");



			Blog blog = new Blog();
			blog.header = "Test Comment #1";
			blog.text = "This is just a test.";
			blog.save();

			Comment c = new Comment();
			c.text = " Suspendisse malesuada pellentesque sapien et aliquet. Duis varius neque vel enim mattis consectetur. Cras ac nisl urna. Duis ornare, erat nec aliquet dapibus, ligula ante congue dolor, sit amet consectetur metus mi ac metus. ";
			c.blog = blog;
			c.save();

			Comment c1 = new Comment();
			c1.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et justo enim. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
			c1.blog = blog;
			c1.save();
			
			Module module = new Module();
			module.name = "First Module";
			module.save();
			
			Course course = new Course();
			course.name = "First Course";
			course.save();
			
			User user = new User();
			user.email = "rub@rub.pt";
			user.password = "123";
			user.name = "Ruben";
			user.save();			
			

		}
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");

	}
}
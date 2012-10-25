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
			blog.header = "The Complex Systems Digital Campus goes to Latin-America";
			blog.text = "The Complex Systems Digital Campus network is getting stronger. Following contacts with the best universities is Latin-America, the network welcomed new members...";
			blog.save();
			
				blog = new Blog();
				blog.header = "Étoile course on “Emergence, Multi-Agent Simulation, and...”";
				blog.text = "A new Étoile course entitled “Emergence, Multi-Agent Simulation, and Network Theory” will be presented at the Université Paris Dauphine, École doctorale EDDIMO...";
				blog.save();
				
				blog = new Blog();
				blog.header = "Étoile course on “Complex Networks and Graph Theory”";
				blog.text = "Jeff Johnson presented the Complex Networks and Graph Theory component of the 2nd Ph.D. School on ”Mathematical Modeling of Complex Systems”, Italy...";
				blog.save();
			

			Comment c = new Comment();
			c.text = "Suspendisse malesuada pellentesque sapien et aliquet. Duis varius neque vel enim mattis consectetur. Cras ac nisl urna. Duis ornare, erat nec aliquet dapibus, ligula ante congue dolor, sit amet consectetur metus mi ac metus. ";
			c.blog = blog;
			c.save();

			Comment c1 = new Comment();
			c1.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et justo enim. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
			c1.blog = blog;
			c1.save();
			
			Module module = new Module();
			module.name = "Sum Module";
			module.save();
			
			Course course = new Course();
			course.name = "Mathematics 101";
			course.save();
			
			User user = new User();
			user.email = "rub@rub.pt";
			user.password = "123";
			user.name = "Ruben";
			user.save();	
			
			user = new User();
			user.email = "rui@rui.pt";
			user.password = "123";
			user.name = "Rui Lopes da Silva";
			user.save();		
			

		}
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");

	}
}
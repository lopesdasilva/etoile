import play.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		System.out.println(Ebean.find(Blog.class).findRowCount());
		if (Ebean.find(Blog.class).findRowCount() == 0) {
			Logger.info("Init Data");

			Category category = new Category();
			category.name="Biology & Life Sciences";
			category.description="";
			category.save();
			
			category = new Category();
			category.name="Mathematics";
			category.description="";
			category.save();
			
			category = new Category();
			category.name="Computer Science";
			category.description="";
			category.save();
			
			category = new Category();
			category.name="Social Sciences";
			category.description="";
			category.save();
			

			Blog blog = new Blog();
			blog.header = "The Complex Systems Digital Campus goes to Latin-America and includes now 50 universities";
			blog.alternateHeader= "The Complex Systems Digital Campus goes to Latin-America and includes now 50 universities";
			blog.text = "The Complex Systems Digital Campus network is getting stronger. Following contacts with the best universities is Latin-America, the network received new and enthusiastic members, including universities, from Argentina, Brazil, Chile, Colombia, just to name a few countries. The  Complex Systems Digital Campus network includes now 50 founding institutions.";
			blog.alternateText = "The Complex Systems Digital Campus network is getting stronger. Following contacts with the best universities is Latin-America, the network received new and enthusiastic members, including universities, from Argentina, Brazil, Chile, Colombia, just to name a few countries. The  Complex Systems Digital Campus network includes now 50 founding institutions.";
			blog.articleImageURL="http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			
			blog.save();
			
			blog = new Blog();
			blog.header = "Étoile course on “Emergence, Multi-Agent Simulation, and Network Theory”";
			blog.alternateHeader= "Étoile course on “Emergence, Multi-Agent Simulation, and Network Theory”";
			blog.text = "A new Étoile course entitled “Emergence, Multi-Agent Simulation, and Network Theory” will be presented by Jorge Louçã at the Université Paris Dauphine, École doctorale EDDIMO (Décision, Informatique, Mathématiques et Organisation), in January/February 2013.";
			blog.alternateText = "A new Étoile course entitled “Emergence, Multi-Agent Simulation, and Network Theory” will be presented by Jorge Louçã at the Université Paris Dauphine, École doctorale EDDIMO (Décision, Informatique, Mathématiques et Organisation), in January/February 2013.";
			blog.articleImageURL="http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/emergence.tiff";
			
			blog.save();
			
			blog = new Blog();
			blog.header = "Étoile will run on tablet !";
			blog.alternateHeader= "Étoile will run on tablet !";
			blog.text = "The migration of the Étoile platform for tablet has started, both for iPad and Android. Be attentive to the next developments !";
			blog.alternateText = "The migration of the Étoile platform for tablet has started, both for iPad and Android. Be attentive to the next developments !";
			blog.articleImageURL="http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/tab.tiff";
			
			blog.save();

			Comment c = new Comment();
			c.text = "Great! I can't wait to try it!";
			c.blog = blog;
			c.save();

			Comment c1 = new Comment();
			c1.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et justo enim. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
			c1.blog = blog;
			c1.save();
			
			Module module = new Module();
			module.name = "Sum Module";
			module.text = "Summation is the operation of adding a sequence of numbers; the result is their sum or total. If numbers are added sequentially from left to right, any intermediate result is a partial sum, prefix sum, or running total of the summation. The numbers to be summed (called addends, or sometimes summands) may be integers, rational numbers, real numbers, or complex numbers. Besides numbers, other types of values can be added as well: vectors, matrices, polynomials and, in general, elements of any additive group (or even monoid). For finite sequences of such elements, summation always produces a well-defined sum (possibly by virtue of the convention for empty sums).";
			module.alternateText = "Summation is the operation of adding a sequence of numbers";
			module.moduleImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			module.save();
			
			module = new Module();
			module.name = "Division Module";
			module.text = "In the expression a ÷ b = c, a is called the dividend or numerator, b the divisor or denominator and the result c is called the quotient. Conceptually, division describes two distinct but related settings. Partitioning involves taking a set of size a and forming b groups that are equal in size. The size of each group formed, c, is the quotient of a and b. Quotative division involves taking a set of size a and forming groups of size b. The number of groups of this size that can be formed, c, is the quotient of a and b.[1]";
			module.alternateText = "In mathematics, especially in elementary arithmetic, division (÷) is an arithmetic operation";
			module.moduleImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			module.save();
			
			Course course = new Course();
			course.name = "Mathematics 101";
			course.courseImageURL = "http://www.clare.cam.ac.uk/data/uploads/admissions/undergraduate/subjects/Mathematics.jpg";
			course.description = "Mathematics module presented at the Master and Doctoral Programme in Complexity Sciences - Lisbon University Institute and Faculty of Sciences at the University of Lisbon. Professor Diogo Pinheiro";
			course.save();

			course = new Course();
			course.name = "Statistics 101";
			course.courseImageURL = "http://www2.icao.int/en/ism/iStars/PublishingImages/statistics.jpg";
			course.description = "Statistics is the study of the collection, organization, analysis, interpretation, and presentation of data. It deals with all aspects of this, including the planning of ..";
			course.save();
			
			course = new Course();
			course.name = "Social Science";
			course.courseImageURL = "http://www.vintank.com/wp-content/uploads/2012/04/stat.jpg";
			course.description = "Social science refers to the academic disciplines concerned with society and human behavior. Social science is commonly used as an umbrella term to refer to ...";


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
import play.*;

import com.avaje.ebean.*;

import models.*;
import models.course.Content;
import models.course.Course;
import models.course.Module;
import models.course.Modulecontent;
import models.curriculum.Category;
import models.curriculum.Curriculumcourse;
import models.curriculum.Curriculummodule;
import models.curriculum.Curriculumtopic;
import models.forum.Topic;
import models.test.Hypothesis;
import models.test.OneChoiceQuestion;
import models.test.OpenQuestion;
import models.test.Test;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		System.out.println(Ebean.find(Blog.class).findRowCount());
		if (Ebean.find(Blog.class).findRowCount() == 0) {
			Logger.info("Init Data");
			
			createCurriculum();
			
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
			
			Hypothesis hypothesis_one = new Hypothesis();
			hypothesis_one.text = "H1";
			hypothesis_one.save();
			
			Hypothesis hypothesis_two = new Hypothesis();
			hypothesis_two.text = "H2";
			hypothesis_two.save();
			
			Hypothesis hypothesis_three = new Hypothesis();
			hypothesis_three.text = "H3";
			hypothesis_three.save();
			
			OneChoiceQuestion onechoicequestion_one = new OneChoiceQuestion();
			onechoicequestion_one.question = "This is the First OneChoice Question.";
			onechoicequestion_one.correct_hypothesis = hypothesis_one.text;
			onechoicequestion_one.hypothesyslist.add(hypothesis_one);
			onechoicequestion_one.hypothesyslist.add(hypothesis_two);
			onechoicequestion_one.hypothesyslist.add(hypothesis_three);
			onechoicequestion_one.save();
			
			OpenQuestion question_one = new OpenQuestion();
			question_one.question = "This is the First Open Question.";
			question_one.save();
			
			OpenQuestion question_two = new OpenQuestion();
			question_two.question = "This is the Second Open Question.";
			question_two.save();
			
			Test test_one = new Test();
			test_one.name = "First Sum Test";
			test_one.text = "Improve your Sum Skills!";
			test_one.testImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			test_one.openquestions.add(question_one);
			test_one.openquestions.add(question_two);
			test_one.onechoicequestions.add(onechoicequestion_one);
			question_one.save();
			question_two.save();
			test_one.save();
			
			Test test_two = new Test();
			test_two.name="Final Sum Test";
			test_two.text = "Final Evaluation of Sum Module";
			test_two.testImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			test_two.save();
			
			Modulecontent mcontent = new Modulecontent();
			mcontent.name = "Content";
			mcontent.text = "This is a new Content.";
			mcontent.url = "http://www.benkler.org/Benkler_Wealth_Of_Networks.pdf";
			mcontent.moduleContentImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			mcontent.save();
			
			Module module_one = new Module();
			module_one.name = "Multiplication Module";
			module_one.text = "Multiplication (often denoted by the cross symbol ×) is the mathematical operation of scaling one number by another. It is one of the four basic operations in ...";
			module_one.alternateText = "Multiplication (often denoted by the cross symbol ×) is the mathematical operation of scaling one number by another. It is one of the four basic operations in ...";
			module_one.moduleImageURL = "http://imguol.com/2012/07/09/saiba-como-usar-tabela-do-word-para-somar-itens-1341868753923_956x500.jpg";
			module_one.tests.add(test_one);
			module_one.tests.add(test_two);
			module_one.modulecontents.add(mcontent);
			mcontent.save();
			module_one.save();
			
			Module module_two = new Module();
			module_two.name = "Division Module";
			module_two.text = "In the expression a ÷ b = c, a is called the dividend or numerator, b the divisor or denominator and the result c is called the quotient. Conceptually, division describes two distinct but related settings. Partitioning involves taking a set of size a and forming b groups that are equal in size. The size of each group formed, c, is the quotient of a and b. Quotative division involves taking a set of size a and forming groups of size b. The number of groups of this size that can be formed, c, is the quotient of a and b.[1]";
			module_two.alternateText = "In mathematics, especially in elementary arithmetic, division (÷) is an arithmetic operation";
			module_two.moduleImageURL = "http://www.coolmath4kids.com/long-division/images/long-division-30.gif";
			module_two.save();
			
			Course course = new Course();
			course.name = "Mathematics 101";
			course.courseImageURL = "http://www.naomiture.com/wp-content/uploads/2010/12/video-shoplocal-play.jpg";
			course.courseVideoURL = "http://www.youtube.com/v/AyPzM5WK8ys";
			course.description = "Mathematics module presented at the Master and Doctoral Programme in Complexity Sciences - Lisbon University Institute and Faculty of Sciences at the University of Lisbon. Professor Diogo Pinheiro";
			course.modules.add(module_one);
			course.modules.add(module_two);
			course.save();
			


			Course course_two = new Course();
			course_two.name = "Statistics 101";
			course_two.courseImageURL = "http://www.vintank.com/wp-content/uploads/2012/04/stat.jpg";
			course_two.description = "Statistics is the study of the collection, organization, analysis, interpretation, and presentation of data. It deals with all aspects of this, including the planning of ..";
			course_two.save();
			
			Course course_three = new Course();
			course_three.name = "Social Science";
			course_three.courseImageURL = "http://www.vaniercollege.qc.ca/social-science/images/social-science.jpg";
			course_three.description = "Social science refers to the academic disciplines concerned with society and human behavior. Social science is commonly used as an umbrella term to refer to ...";
			course_three.save();
			
			Content content= new Content();
			content.course=course_three;
			content.title="About the Professor";
			content.text="J. Alex Haldermanis an assistant professor of computer science and engineering at the University of Michigan. His research spans computer security and tech-centric public policy, including topics such as software security, data privacy, electronic voting, censorship resistance, and cybercrime, as well as technological aspects of intellectual property law and government regulation. He holds a Ph.D. from Princeton University. A noted expert on electronic voting security, Prof. Halderman helped demonstrate the first voting machine virus, participated in California's top-to-bottom electronic voting review, and exposed election security flaws in India, the world's largest democracy. He recently led a team from the University of Michigan that hacked into Washington D.C.'s Internet voting system. In his spare time, he reprogrammed a touch-screen voting machine to play Pac-Man ";
			content.save();
			
			content= new Content();
			content.course=course_three;
			content.title="About the Professor";
			content.text="J. Alex Haldermanis an assistant professor of computer science and engineering at the University of Michigan. His research spans computer security and tech-centric public policy, including topics such as software security, data privacy, electronic voting, censorship resistance, and cybercrime, as well as technological aspects of intellectual property law and government regulation. He holds a Ph.D. from Princeton University. A noted expert on electronic voting security, Prof. Halderman helped demonstrate the first voting machine virus, participated in California's top-to-bottom electronic voting review, and exposed election security flaws in India, the world's largest democracy. He recently led a team from the University of Michigan that hacked into Washington D.C.'s Internet voting system. In his spare time, he reprogrammed a touch-screen voting machine to play Pac-Man ";
			content.save();
			
			content= new Content();
			content.course=course_three;
			content.title="Course format";
			content.text="The class will consist of lecture videos totaling about 2 hours a week. These will several enrichment and evaluation questions. There will also be optional reading and a final essay.";
			content.save();
			
			content= new Content();
			content.course=course;
			content.title="Recommended Background";
			content.text="Most of this course will be accessible to non-technical students. We will provide optional materials for those with some college-level computer science background.";
			content.save();
			
			
			User user = new User();
			user.email = "rub@rub.pt";
			user.password = "123";
			user.name = "Ruben";
			user.account_type = "student";
			user.courses.add(course);
			user.courses.add(course_two);
			user.courses.add(course_three);
			course.save();
			course_two.save();
			course_three.save();
			user.save();	
			
			Comment c = new Comment();
			c.text = "Great! I can't wait to try it!";
			c.blog = blog;
			c.user = user;
			c.save();

			
			user = new User();
			user.email = "rui@rui.pt";
			user.password = "123";
			user.name = "Rui Lopes da Silva";
			user.account_type = "student";
			user.courses.add(course);
			user.courses.add(course_two);
			course.save();
			course_two.save();
			user.save();
			
			

			Comment c1 = new Comment();
			c1.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et justo enim. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
			c1.blog = blog;
			c1.user = user;
			c1.save();
			
			
			
			user = new User();
			user.email = "prof@prof.pt";
			user.password="123";
			user.name = "Professor";
			user.account_type = "professor";
			user.save();
			
			Category category_one = new Category();
			category_one.name="Biology & Life Sciences";
			category_one.keyword = "biology";
			category_one.description="";
			category_one.save();
			
			Category category_two = new Category();
			category_two.name="Mathematics";
			category_two.keyword = "mathematics";
			category_two.description="";
			course_two.save();
			category_two.save();
			
			Category category_three = new Category();
			category_three.name="Computer Science";
			category_three.keyword = "computerscience";
			category_three.description="";
			category_three.save();
			
			Category category_four = new Category();
			category_four.name="Social Sciences";
			category_four.keyword = "socialsciences";
			category_four.description="";
			course_three.save();
			category_four.save();

			course_three.categories.add(category_four);
			course_three.save();
			category_four.save();
			
			course.categories.add(category_two);
			course.save();
			category_two.save();
			
			course_two.categories.add(category_two);
			course_two.save();
			category_two.save();
			
		}
	}

	private void createCurriculum() {
		Curriculumtopic topic_one = new Curriculumtopic();
		topic_one.keyword = "discuss";
		topic_one.text = "Discuss what kind of problems can be computed.";
		topic_one.save();
		
		Curriculumtopic topic_two = new Curriculumtopic();
		topic_two.keyword = "describe";
		topic_two.text = "Describe and relate the notions of formal system.";
		topic_two.save();
		
		Curriculummodule cmodule_one= new Curriculummodule();
		cmodule_one.keyword = "Discussion";
		cmodule_one.name = "Discussion";
		cmodule_one.text = "Discussion module presented at the Master and Doctoral Programme in Complexity Sciences - Lisbon University Institute and Faculty of Sciences at the University of Lisbon.";
		cmodule_one.curriculumtopics.add(topic_one);
		cmodule_one.curriculumtopics.add(topic_two);
		topic_one.save();
		topic_two.save();
		cmodule_one.save();
		
		Curriculumcourse ccourse = new Curriculumcourse();
		ccourse.keyword = "algorithmic";
		ccourse.name = "Algorithmic Theory Basics";
		ccourse.text = "Algorithmic Theory basics Course presented at the Master and Doctoral Programme in Complexity Sciences - Lisbon University Institute and Faculty of Sciences at the University of Lisbon.";
		ccourse.curriculummodules.add(cmodule_one);
		cmodule_one.save();
		ccourse.save();
		
		Category cat = new Category();
		cat.keyword = "algorithmic";
		cat.name = "Algorithmic";
		cat.curriculumcourses.add(ccourse);
		ccourse.save();
		cat.save();
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");

	}
}
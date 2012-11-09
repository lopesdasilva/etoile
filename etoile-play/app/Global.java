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
	
	public Category mathcat;
	public Category phycat;
	public Category biocat;
	public Category soccat;
	public Category comcat;

	public void onStart(Application app) {
		System.out.println(Ebean.find(Blog.class).findRowCount());
		if (Ebean.find(Blog.class).findRowCount() == 0) {
			Logger.info("Init Data");			
			
			// NEWS ++++++++++++++++++++++++++++++++++++++++++
			
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
			
			
			// QUESTIONS ++++++++++++++++++++++++++++++++++++++++
			
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
			
			
			// TESTS ++++++++++++++++++++++++++++++++++++++++++++
			
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
			
			
			// MODULES +++++++++++++++++++++++++++++++++++++++++
			
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
			
			
			// COURSES +++++++++++++++++++++++++++++++++++++++++
			
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
			
			
			// CONTENT FOR COURSES ++++++++++++++++++++++++++++++
			
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
			
			
			// USERS ++++++++++++++++++++++++++++++++++++++++++++
			
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
			
			
			
			// CATEGORIES ++++++++++++++++++++++++++++++++++++++++++++++++++++
			
				// 1. MATHEMATICS ++++++++++++++++++++++++++++++++++++++
			
		    mathcat = new Category();
			mathcat.name = "Mathematics";
			mathcat.description="";
			mathcat.keyword = "mathematics";
			mathcat.save();
			
				// 2. PHYSICS ++++++++++++++++++++++++++++++++++++++
			
		    phycat = new Category();
			phycat.name = "Physics";
			phycat.description="";
			phycat.keyword = "physics";
			phycat.save();
			
				// 3. BIOLOGY AND LIFE SCIENCES ++++++++++++++++++++++++++++++++++++++
			
		    biocat = new Category();
			biocat.name = "Biology & Life Sciences";
			biocat.keyword = "biology";
			biocat.description="";
			biocat.save();
			
				// 4. SOCIAL SCIENCES ++++++++++++++++++++++++++++++++++++++
			
		    soccat = new Category();
			soccat.name = "Social Sciences";
			soccat.keyword = "social_sciences";
			soccat.description="";
			soccat.save();
			
				// 5. COMPUTER SCIENCE ++++++++++++++++++++++++++++++++++++++

		    comcat = new Category();
			comcat.name = "Computer Science";
			comcat.keyword = "computer_science";
			comcat.description="";
			comcat.save();

			// CURRICULUM
			createCurriculumComputerScience();
			
			// LINK COURSES -> CATEGORIES +++++++++++++++++++++++++++++++++++++
			/*
			course_three.categories.add(category_four);
			course_three.save();
			category_four.save();
			*/
			
		}
	}

	
	// 5. CURRICULUM COMPUTER SCIENCE ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	private void createCurriculumComputerScience() {
				
		/* */
		
		// 5.1.1.3 Topic - Logical System
		Curriculumtopic logicalSystemTopic= new Curriculumtopic();
		logicalSystemTopic.keyword = "logical_system";
		//logicalSystemTopic.name = "Logical System";
		logicalSystemTopic.text = "Logical system: formal system with semantics.";
		logicalSystemTopic.save();
		
		// 5.1.1.2 Topic - Formal Language
		Curriculumtopic formalLanguageTopic= new Curriculumtopic();
		formalLanguageTopic.keyword = "formal_language";
		//formalLanguageTopic.name = "Formal Language";
		formalLanguageTopic.text = "Formal language: symbols, grammar, and inference from axiom schemata. Syntax and semantics.";
		formalLanguageTopic.save();
		
		// 5.1.1.1 Topic - Formal System
		Curriculumtopic formalSystemsTopic= new Curriculumtopic();
		formalSystemsTopic.keyword = "formal_systems";
		//formalSystemsTopic.name = "Formal System";
		formalSystemsTopic.text = "Formal system:  notions of formal language, inference rule, premise, axiom and theorem.";
		formalSystemsTopic.save();
		
		// 5.1.1 Module - Formal Systems
		Curriculummodule formalSystemsModule= new Curriculummodule();
		formalSystemsModule.keyword = "formal_systems";
		formalSystemsModule.name = "Formal Systems";
		formalSystemsModule.text = "Learning Objectives: (1) Discuss what kind of problems can be computed, and what are the limitations of actual computers; (2) Describe and relate the notions of formal system, formal language, model and logical system.";
		formalSystemsModule.curriculumtopics.add(formalSystemsTopic);
		formalSystemsModule.curriculumtopics.add(formalLanguageTopic);
		formalSystemsModule.curriculumtopics.add(logicalSystemTopic);
		formalLanguageTopic.save();
		formalSystemsTopic.save();
		logicalSystemTopic.save();
		formalSystemsModule.save();
		
		
		// 5.1.2.3 Topic - Algorithmic Methods
		Curriculumtopic algorithmicMethodsTopic= new Curriculumtopic();
		algorithmicMethodsTopic.keyword = "algorithmic_methods";
		//algorithmicMethodsTopic.name = "Algorithmic Methods";
		algorithmicMethodsTopic.text = "Alternative algorithmic methods. Recursive methods.";
		algorithmicMethodsTopic.save();
		
		// 5.1.2.2 Topic - Example of an algorithm
		Curriculumtopic exampleAlgorithmTopic= new Curriculumtopic();
		exampleAlgorithmTopic.keyword = "hanoi_tower";
		//exampleAlgorithmTopic.name = "Example of an algorithm";
		exampleAlgorithmTopic.text = "Example of an algorithm: Hanoi Tower.";
		exampleAlgorithmTopic.save();
		
		// 5.1.2.1 Topic - Algorithms
		Curriculumtopic algorithmsTopic= new Curriculumtopic();
		algorithmsTopic.keyword = "algorithms";
		//algorithmsTopic.name = "Algorithms";
		algorithmsTopic.text = "Algorithms: basic notions, structure and data manipulation.";
		algorithmsTopic.save();
		
		// 5.1.2 Module - Algorithms
		Curriculummodule algorithmsModule= new Curriculummodule();
		algorithmsModule.keyword = "algorithms_module";
		algorithmsModule.name = "Algorithms";
		algorithmsModule.text = "Learning Objectives: (1) Discuss what it is an algorithm, what kind of data can be manipulated by algorithms, and whether algorithms are efficient; (2) List the different algorithmic methods.";
		algorithmsModule.curriculumtopics.add(algorithmsTopic);
		algorithmsModule.curriculumtopics.add(exampleAlgorithmTopic);
		algorithmsModule.curriculumtopics.add(algorithmicMethodsTopic);
		algorithmsTopic.save();
		exampleAlgorithmTopic.save();
		algorithmicMethodsTopic.save();
		algorithmsModule.save();
		
		
		// 5.1.2.3 Topic - Touring Test
		Curriculumtopic touringTestTopic= new Curriculumtopic();
		touringTestTopic.keyword = "touring_test";
		//touringTestTopic.name = "Touring Test";
		touringTestTopic.text = "The Touring test.";
		touringTestTopic.save();
		
		// 5.1.2.2 Topic - Unsolvable Problems
		Curriculumtopic unsolvableProblemsTopic= new Curriculumtopic();
		unsolvableProblemsTopic.keyword = "unsolvable_problems";
		//unsolvableProblemsTopic.name = "Unsolvable Problems";
		unsolvableProblemsTopic.text = "Unsolvable problems. Example: the Halting problem.";
		unsolvableProblemsTopic.save();
		
		// 5.1.3.1 Topic - Classification
		Curriculumtopic classificationTopic= new Curriculumtopic();
		classificationTopic.keyword = "classification";
		//classificationTopic.name = "Classification";
		classificationTopic.text = "Classification of problems according to their complexity.";
		classificationTopic.save();
		
		// 5.1.3 Module - Complexity Classes
		Curriculummodule complexityClassesModule= new Curriculummodule();
		complexityClassesModule.keyword = "complexity_classes";
		complexityClassesModule.name = "Complexity Classes";
		complexityClassesModule.text = "Learning Objectives: (1) Discuss the existing algorithmic complexity classes. (2) Identify the complexity class of a given algorithm.";
		complexityClassesModule.curriculumtopics.add(classificationTopic);
		complexityClassesModule.curriculumtopics.add(unsolvableProblemsTopic);
		complexityClassesModule.curriculumtopics.add(touringTestTopic);
		classificationTopic.save();
		unsolvableProblemsTopic.save();
		touringTestTopic.save();
		complexityClassesModule.save();
		

		// 5.1 Course - Algorithmics
		Curriculumcourse algocourse = new Curriculumcourse();
		algocourse.keyword = "algorithmics";
		algocourse.name = "Algorithmics";
		algocourse.text = "Understand and know how to apply the main concepts of Algorithmic Theory";
		algocourse.curriculummodules.add(formalSystemsModule);
		formalSystemsModule.save();
		algocourse.curriculummodules.add(algorithmsModule);
		algorithmsModule.save();
		algocourse.curriculummodules.add(complexityClassesModule);
		complexityClassesModule.save();
		algocourse.save();
		
		
		comcat.curriculumcourses.add(algocourse);
		algocourse.save();
		comcat.save();	
		
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");

	}
}
import java.util.LinkedList;

import play.*;

import com.avaje.ebean.*;

import controllers.sha1;

import models.*;
import models.continent.Continent;
import models.curriculum.Category;
import models.curriculum.Curriculummodule;
import models.curriculum.Curriculumlesson;
import models.curriculum.Curriculumtopic;
import models.forum.Topic;
import models.module.Bibliography;
import models.module.Content;
import models.module.Module;
import models.module.Lesson;
import models.module.Lessoncontent;
import models.module.University;
import models.test.Hypothesis;
import models.test.MultipleChoiceHypothesis;
import models.test.MultipleChoiceQuestion;
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
			
			//INHERITANCE TESTS

			
			// NEWS ++++++++++++++++++++++++++++++++++++++++++
			
			Blog blog = new Blog();
			blog.alternateHeader = "The Digital Campus goes to Latin-America";
			blog.header= "The Complex Systems Digital Campus goes to Latin-America and includes now 50 universities";
			blog.text = "The Complex Systems Digital Campus network is getting stronger. Following contacts with the best universities is Latin-America, the network received new and enthusiastic members, including universities, from Argentina, Brazil, Chile, Colombia, just to name a few countries. The  Complex Systems Digital Campus network includes now 50 founding institutions.";
			blog.alternateText = "The Complex Systems Digital Campus network is getting stronger... ";
			blog.articleImageURL="http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			
			blog.save();
			
			blog = new Blog();
			blog.alternateHeader = "New module on Emergence";
			blog.header= "Étoile module on “Emergence, Multi-Agent Simulation, and Network Theory”";
			blog.text = "A new Étoile module entitled “Emergence, Multi-Agent Simulation, and Network Theory” will be presented by Jorge Louçã at the Université Paris Dauphine, École doctorale EDDIMO (Décision, Informatique, Mathématiques et Organisation), in January/February 2013.";
			blog.alternateText = "A new Étoile module entitled “Emergence, Multi-Agent Simulation, and Network Theory”...";
			blog.articleImageURL="https://dl.dropbox.com/u/124850/emergence.jpg";
			
			blog.save();
			
			blog = new Blog();
			blog.alternateHeader = "Étoile will run on tablet!";
			blog.header= "Étoile will run on tablet !";
			blog.text = "The migration of the Étoile platform for tablet has started, both for iPad and Android. Be attentive to the next developments !";
			blog.alternateText = "The migration of the Étoile platform for tablet has started, both for iPad and Android...";
			blog.articleImageURL="http://velositor.com/wp-content/uploads/2012/03/Tablets-in-Education-eTextbooks-on-Tablets-Preferred-Over-Print-Books-Among-College-and-High-School-Students-e1332128400234-290x290.jpg";
			blog.save();
			
			
			// QUESTIONS ++++++++++++++++++++++++++++++++++++++++
			Hypothesis hypothesis_zero = new Hypothesis();
			hypothesis_zero.text = "No answer";
			hypothesis_zero.save();
			
			Hypothesis hypothesis_one = new Hypothesis();
			hypothesis_one.text = "H1";
			hypothesis_one.save();
			
			Hypothesis hypothesis_two = new Hypothesis();
			hypothesis_two.text = "H2";
			hypothesis_two.save();
			
			Hypothesis hypothesis_three = new Hypothesis();
			hypothesis_three.text = "H3";
			hypothesis_three.save();
			
			MultipleChoiceHypothesis hypothesis_zerozero = new MultipleChoiceHypothesis();
			hypothesis_zerozero.text = "No Answer";
			hypothesis_zerozero.save();
			
			MultipleChoiceHypothesis hypothesis_four = new MultipleChoiceHypothesis();
			hypothesis_four.text = "H4 - This is the first MultipleChoice Hypothesis";
			hypothesis_four.number = 0;
			hypothesis_four.save();
			
			MultipleChoiceHypothesis hypothesis_five = new MultipleChoiceHypothesis();
			hypothesis_five.text = "H5 - This is the second MultipleChoice Hypothesis";
			hypothesis_five.number = 1;
			hypothesis_five.save();
			
			
			OneChoiceQuestion onechoicequestion_one = new OneChoiceQuestion();
			onechoicequestion_one.question = "This is the First OneChoice Question.";
			onechoicequestion_one.correct_hypothesis = hypothesis_one.text;
			onechoicequestion_one.hypothesyslist.add(hypothesis_one);
			onechoicequestion_one.hypothesyslist.add(hypothesis_two);
			onechoicequestion_one.hypothesyslist.add(hypothesis_three);
			onechoicequestion_one.save();
			
			
			MultipleChoiceQuestion multiplechoicequestion_one = new MultipleChoiceQuestion();
			multiplechoicequestion_one.question = "This is the First MultipleChoice Question";
			multiplechoicequestion_one.hypothesislist.add(hypothesis_four);
			multiplechoicequestion_one.hypothesislist.add(hypothesis_five);
			multiplechoicequestion_one.save();
			
			OpenQuestion question_one = new OpenQuestion();
			question_one.question = "This is the First Open Question.";
			question_one.imageURL= "http://www.psdgraphics.com/wp-content/uploads/2009/04/growth-chart.jpg";
			question_one.videoURL= "http://www.youtube.com/v/AyPzM5WK8ys";
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
			
			multiplechoicequestion_one.test = test_one;
			multiplechoicequestion_one.save();
			
			Test test_two = new Test();
			test_two.name="Final Sum Test";
			test_two.text = "Final Evaluation of Sum Lesson";
			test_two.testImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			test_two.save();
			
			
			// MODULES +++++++++++++++++++++++++++++++++++++++++
			
			Lessoncontent mcontent = new Lessoncontent();
			mcontent.name = "Content";
			mcontent.text = "This is a new Content.";
			mcontent.url = "http://www.benkler.org/Benkler_Wealth_Of_Networks.pdf";
			mcontent.lessonContentImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			mcontent.save();
			
			Lesson lesson_one = new Lesson();
			lesson_one.name = "Multiplication Lesson";
			lesson_one.acronym = "multiplication";
			lesson_one.description = "Multiplication (often denoted by the cross symbol ×) is the mathematical operation of scaling one number by another. It is one of the four basic operations in ...";
			lesson_one.shortDescription = "Multiplication (often denoted by the cross symbol ×) is the mathematical operation of scaling one number by another. It is one of the four basic operations in ...";
			lesson_one.imageURL = "http://imguol.com/2012/07/09/saiba-como-usar-tabela-do-word-para-somar-itens-1341868753923_956x500.jpg";
			lesson_one.tests.add(test_one);
			lesson_one.tests.add(test_two);
			lesson_one.lessoncontents.add(mcontent);
			mcontent.save();
			lesson_one.save();
			
			Lesson lesson_two = new Lesson();
			lesson_two.name = "Division Lesson";
			lesson_two.acronym = "divison";
			lesson_two.description = "In the expression a ÷ b = c, a is called the dividend or numerator, b the divisor or denominator and the result c is called the quotient. Conceptually, division describes two distinct but related settings. Partitioning involves taking a set of size a and forming b groups that are equal in size. The size of each group formed, c, is the quotient of a and b. Quotative division involves taking a set of size a and forming groups of size b. The number of groups of this size that can be formed, c, is the quotient of a and b.[1]";
			lesson_two.shortDescription = "In mathematics, especially in elementary arithmetic, division (÷) is an arithmetic operation";
			lesson_two.imageURL = "http://www.coolmath4kids.com/long-division/images/long-division-30.gif";
			lesson_two.save();
			
		
			
			
			// COURSES +++++++++++++++++++++++++++++++++++++++++
			
			Module module = new Module();
			module.name = "Mathematics 101";
			module.acronym="math101";
			module.imageURL = "http://www.naomiture.com/wp-content/uploads/2010/12/video-shoplocal-play.jpg";
			module.videoURL = "http://www.youtube.com/v/AyPzM5WK8ys";
			module.description = "Mathematics lesson presented at the Master and Doctoral Programme in Complexity Sciences - Lisbon University Institute and Faculty of Sciences at the University of Lisbon. Professor Diogo Pinheiro";
			module.lessons.add(lesson_one);
			module.lessons.add(lesson_two);
			module.save();
			/* 
			Module module_two = new Module();
			module_two.name = "Statistics 101";
			module_two.acronym="stats101";
			module_two.imageURL = "http://www.vintank.com/wp-content/uploads/2012/04/stat.jpg";
			module_two.description = "Statistics is the study of the collection, organization, analysis, interpretation, and presentation of data. It deals with all aspects of this, including the planning of ..";
			module_two.save();
			*/
			
			Module module_three = new Module();
			module_three.name = "Social Science";
			module_three.acronym="socscience";
			module_three.imageURL = "http://www.vaniercollege.qc.ca/social-science/images/social-science.jpg";
			module_three.description = "Social science refers to the academic disciplines concerned with society and human behavior. Social science is commonly used as an umbrella term to refer to ...";
			module_three.save();
			
			
			Module module_four = new Module();
			module_four.name = "Network Science";
			module_four.acronym="netsci_01";
			module_four.imageURL = "http://herd.typepad.com/.a/6a00d83451e1dc69e2011278fba78928a4-pi";
			module_four.videoURL = "http://www.youtube.com/v/10oQMHadGos ";
			module_four.description = "Module presented during Spring 2012 at the Center for Complex Network Research, Northeastern University Physics Department, by Prof. Albert-László Barabási, assisted by Dr. Baruch Barzel, and for network visualizations by Prof. Mauro Martino";
			//module_four.lessons.add(lesson_one);
			//module_four.lessons.add(lesson_two);
			module_four.save();
			
			// Bibliography +++++++++++++++++++++++++++++++++++++++++
			Bibliography bibliography = new Bibliography();
			bibliography.title="T. Bynum and S. Rogerdson, eds., Computer Ethics and Professional Res- ponsibility, (Wiley, 2003).";
			bibliography.link="http://books.google.co.uk/books/about/Computer_Ethics_and_Professional_Respons.html?id=FOxqAjC8iHkC";
			bibliography.description="This clear and accessible textbookand its associated website offer a state of the art introduction to the burgeoning field of computer ethics and professional responsibility. ";
			bibliography.imageURL="http://bks5.books.google.co.uk/books?id=FOxqAjC8iHkC&printsec=frontcover&img=1&zoom=1&imgtk=AFLRE71rwGQTfyj97JTxptwM-URC0Iy5eY0fJwpy3wsNs0pYpeltEtL334XnHw2ZigcJ8ylnypoG91BHhVYFSQoh5ID13NZ_eGEk4FfyLvZC3tb15cbuOIWj5PAFEte6MsjiB8-QaxZL";
			bibliography.module=module;
			bibliography.save();
			
			bibliography = new Bibliography();
			bibliography.title="Mathematics: Teaching School Subjects 11-19.";
			bibliography.link="http://books.google.co.uk/books?id=wb3X3xNSntkC&dq=mathematics&hl=pt-PT&source=gbs_navlinks_s";
			bibliography.description="This accessible and thought-provoking book considers what beginning teachers need to know about learning, teaching, assessment, curriculum and professional development, in the context of teaching mathematics to eleven to nineteen year olds. It is part of a new series of books that has as its starting point the fact that PGCE students are already subject specialists.";
			bibliography.imageURL="http://bks9.books.google.co.uk/books?id=wb3X3xNSntkC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70zfpHHuBmkjMmKgszdfIZYNSN2UKVkhMjtAbAGEqDsePekXhaSmjxwqM2g8PBtzvomBeclI1ovd3ofdXAm_iasy6k2YY8MGyQJEP9HLd-EfOL8XPQiDxSSukg_oN3yDg4ElZ7q";
			bibliography.module=module;
			bibliography.save();
			
			// CONTENT FOR COURSES ++++++++++++++++++++++++++++++
			
			Content content;
			
			content = new Content();
			content.module=module_four;
			content.title="Textbook (Recommended)";
			content.text="Linked – The New Science of Networks, Albert-László Barabási. Perseus Publishing /n Networks – an Introduction, Mark Newman, Oxford University Press";
			content.save();
			
			content = new Content();
			content.module=module_four;
			content.title="Module description and objectives";
			content.text="The module is an interdisciplinary module, focused on the emerging science of complex networks and their applications. The material includes the mathematics of networks, their applications to biology, sociology, technology and other fields, and their use in the research of real complex systems in nature and in man made systems. The students will learn about the ongoing research in the field, and apply their knowledge in the analysis of real network systems, as the main objective of their final project.";
			content.save();
			
			content = new Content();
			content.module=module_three;
			content.title="About the Professor";
			content.text="J. Alex Haldermanis an assistant professor of computer science and engineering at the University of Michigan. His research spans computer security and tech-centric public policy, including topics such as software security, data privacy, electronic voting, censorship resistance, and cybercrime, as well as technological aspects of intellectual property law and government regulation. He holds a Ph.D. from Princeton University. A noted expert on electronic voting security, Prof. Halderman helped demonstrate the first voting machine virus, participated in California's top-to-bottom electronic voting review, and exposed election security flaws in India, the world's largest democracy. He recently led a team from the University of Michigan that hacked into Washington D.C.'s Internet voting system. In his spare time, he reprogrammed a touch-screen voting machine to play Pac-Man ";
			content.save();
			
			content= new Content();
			content.module=module_three;
			content.title="Module format";
			content.text="The class will consist of lecture videos totaling about 2 hours a week. These will several enrichment and evaluation questions. There will also be optional reading and a final essay.";
			content.save();
			
			content= new Content();
			content.module=module;
			content.title="Recommended Background";
			content.text="Most of this module will be accessible to non-technical students. We will provide optional materials for those with some college-level computer science background.";
			content.save();
			
			
			// USERS ++++++++++++++++++++++++++++++++++++++++++++
			
			User user = new User();
			user.email = "rub@rub.pt";
			user.username="rub";
			user.password = sha1.parseSHA1Password("123");
			user.name = "Ruben";
			user.account_type = "student";
//			user.modules.add(module);
			//user.modules.add(module_two);
//			user.modules.add(module_three);
//			module.save();
			//module_two.save();
//			module_three.save();
			user.save();	
			
			Comment c = new Comment();
			c.text = "Great! I can't wait to try it!";
			c.blog = blog;
			c.user = user;
			c.save();

			
			user = new User();
			user.email = "rui@rui.pt";
			user.username="rui";
			user.password = sha1.parseSHA1Password("123");
			user.name = "Rui Lopes da Silva";
			user.account_type = "student";
//			user.modules.add(module);
			//user.modules.add(module_two);
//			module.save();
			//module_two.save();
			user.save();
			
			// Professors +++++++++++++++++++++++++++++++++++++++++++++++++
			
			// 1. Jorge Louçã
			
			ProfessorContent pc = new ProfessorContent();
			pc.description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas suscipit auctor velit sit amet consequat. In turpis augue, scelerisque a malesuada euismod, mollis eu enim. Maecenas sapien tellus, vehicula non aliquet id, pretium commodo risus. Donec enim augue, lacinia in varius quis, luctus a nibh. Fusce pretium viverra neque, ac congue tortor pretium et. Nam vel quam quis nulla euismod mollis. Cras eget lectus at ipsum blandit dictum. Nam ac nisi sapien. Phasellus tristique dui vel nunc viverra eu vestibulum enim tempus. Phasellus adipiscing dolor vulputate velit bibendum quis laoreet mi lacinia. Donec at elit sem, vel iaculis libero. Etiam vestibulum libero at mauris rutrum faucibus. Curabitur metus odio, aliquet at tristique eu, commodo sed nibh.";
			pc.title="Lorem ipsum";
			pc.imageURL="http://profesorbaker.files.wordpress.com/2011/02/cas.jpg";
			pc.save();
			
			ProfessorContent pc2 = new ProfessorContent();
			pc2.description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas suscipit auctor velit sit amet consequat. In turpis augue, scelerisque a malesuada euismod, mollis eu enim. Maecenas sapien tellus, vehicula non aliquet id, pretium commodo risus. Donec enim augue, lacinia in varius quis, luctus a nibh. Fusce pretium viverra neque, ac congue tortor pretium et. Nam vel quam quis nulla euismod mollis. Cras eget lectus at ipsum blandit dictum. Nam ac nisi sapien. Phasellus tristique dui vel nunc viverra eu vestibulum enim tempus. Phasellus adipiscing dolor vulputate velit bibendum quis laoreet mi lacinia. Donec at elit sem, vel iaculis libero. Etiam vestibulum libero at mauris rutrum faucibus. Curabitur metus odio, aliquet at tristique eu, commodo sed nibh.";
			pc2.title="Lorem ipsum";
			pc2.imageURL="http://profesorbaker.files.wordpress.com/2011/02/cas.jpg";
			pc2.save();
			
			Professor p= new Professor();
			p.firstname="Jorge";
			p.lastname="Louçã";
			p.acronym="JorgeL";
			p.email="email[at]gmail.com";
			p.degree="Professor";
			p.shortdescription="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas suscipit auctor velit sit amet consequat. In turpis augue, scelerisque a malesuada euismod, mollis eu enim. Maecenas sapien tellus, vehicula non aliquet id, pretium commodo risus. Donec enim augue, lacinia in varius quis, luctus a nibh. Fusce pretium viverra neque, ac congue tortor pretium et.";
			p.contact="SCTE - Instituto Universitário de Lisboa Departamento de Ciências e Tecnologias de Informação" +
					" Av. das Forças Armadas" +
					"1649-026" +
					"Lisboa Portugal";
			p.imageURL="http://iscte.pt/~jmal/index_files/image003.jpg";
			p.save();
			
			p.modules.add(module);
			p.save();
			module.save();
			
			p.contents.add(pc);
			pc.professor=p;
			p.save();
			pc.save();
			
			p.contents.add(pc2);
			pc2.professor=p;
			p.save();
			pc2.save();
			
			

			Comment c1 = new Comment();
			c1.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et justo enim. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
			c1.blog = blog;
			c1.user = user;
			c1.save();
			
			
			user = new User();
			user.email = "prof@prof.pt";
			user.username="prof";
			user.password=sha1.parseSHA1Password("123");
			user.name = "Professor";
			user.account_type = "professor";
			user.save();
	
			// Continents ++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			// 1. Europe ++++++++++++++++++++++++++++++++++++++
			Continent europe = new Continent();
			europe.name="Europe";
			europe.acronym="europe";
			europe.imageURL="assets/images/continent/europe.png";
			europe.save();
			
			// 2. America ++++++++++++++++++++++++++++++++++++++
			Continent northamerica = new Continent();
			northamerica.name="North America";
			northamerica.acronym="northamerica";
			northamerica.imageURL="assets/images/continent/northamerica.png";
			northamerica.save();
			
			// 3. America ++++++++++++++++++++++++++++++++++++++
			Continent southamerica = new Continent();
			southamerica.name="South America";
			southamerica.acronym="southamerica";
			southamerica.imageURL="assets/images/continent/southamerica.png";
			southamerica.save();
			
			// 4. Asia ++++++++++++++++++++++++++++++++++++++
			Continent asia = new Continent();
			asia.name="Asia";
			asia.acronym="asia";
			asia.imageURL="assets/images/continent/asia.png";
			asia.save();
			
			// 5. Africa ++++++++++++++++++++++++++++++++++++++
			Continent africa = new Continent();
			africa.name="Africa";
			africa.acronym="africa";
			africa.imageURL="assets/images/continent/africa.png";
			africa.save();
			
			// 6. Oceania ++++++++++++++++++++++++++++++++++++++
			Continent oceania = new Continent();
			oceania.name="Oceania";
			oceania.acronym="oceania";
			oceania.imageURL="assets/images/continent/australia.png";
			oceania.save();
			
			
			
			
			
			// Universities ++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				// 1. ISCTE-IUL ++++++++++++++++++++++++++++++++++++++
			University university = new University();
			university.name="ISCTE-IUL";
			university.acronym="iscte-iul";
			university.imageURL="http://noticias.universia.pt/pt/images/universia/i/is/isc/iscte_iul_logo.jpg";
			university.continent=europe;
			university.save();
			
			module.university=university;
			module_three.university=university;
			//module_two.university=university;
			module_four.university=university;
			module.save();
			//module_two.save();
			module_three.save();
			module_four.save();
			
		
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
			
			module_three.categories.add(soccat);
			module_three.save();
			soccat.save();
			
			
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
		
		// 5.1.1 Lesson - Formal Systems
		Curriculumlesson formalSystemsLesson= new Curriculumlesson();
		formalSystemsLesson.keyword = "formal_systems";
		formalSystemsLesson.name = "Formal Systems";
		formalSystemsLesson.text = "Learning Objectives: (1) Discuss what kind of problems can be computed, and what are the limitations of actual computers; (2) Describe and relate the notions of formal system, formal language, model and logical system.";
		formalSystemsLesson.curriculumtopics.add(formalSystemsTopic);
		formalSystemsLesson.curriculumtopics.add(formalLanguageTopic);
		formalSystemsLesson.curriculumtopics.add(logicalSystemTopic);
		formalLanguageTopic.save();
		formalSystemsTopic.save();
		logicalSystemTopic.save();
		formalSystemsLesson.save();
		
		
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
		
		// 5.1.2 Lesson - Algorithms
		Curriculumlesson algorithmsLesson= new Curriculumlesson();
		algorithmsLesson.keyword = "algorithms_lesson";
		algorithmsLesson.name = "Algorithms";
		algorithmsLesson.text = "Learning Objectives: (1) Discuss what it is an algorithm, what kind of data can be manipulated by algorithms, and whether algorithms are efficient; (2) List the different algorithmic methods.";
		algorithmsLesson.curriculumtopics.add(algorithmsTopic);
		algorithmsLesson.curriculumtopics.add(exampleAlgorithmTopic);
		algorithmsLesson.curriculumtopics.add(algorithmicMethodsTopic);
		algorithmsTopic.save();
		exampleAlgorithmTopic.save();
		algorithmicMethodsTopic.save();
		algorithmsLesson.save();
		
		
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
		
		// 5.1.3 Lesson - Complexity Classes
		Curriculumlesson complexityClassesLesson= new Curriculumlesson();
		complexityClassesLesson.keyword = "complexity_classes";
		complexityClassesLesson.name = "Complexity Classes";
		complexityClassesLesson.text = "Learning Objectives: (1) Discuss the existing algorithmic complexity classes. (2) Identify the complexity class of a given algorithm.";
		complexityClassesLesson.curriculumtopics.add(classificationTopic);
		complexityClassesLesson.curriculumtopics.add(unsolvableProblemsTopic);
		complexityClassesLesson.curriculumtopics.add(touringTestTopic);
		classificationTopic.save();
		unsolvableProblemsTopic.save();
		touringTestTopic.save();
		complexityClassesLesson.save();
		

		// 5.1 Module - Algorithmics
		Curriculummodule algomodule = new Curriculummodule();
		algomodule.keyword = "algorithmics";
		algomodule.name = "Algorithmics";
		algomodule.text = "Understand and know how to apply the main concepts of Algorithmic Theory";
		algomodule.curriculumlessons.add(formalSystemsLesson);
		formalSystemsLesson.save();
		algomodule.curriculumlessons.add(algorithmsLesson);
		algorithmsLesson.save();
		algomodule.curriculumlessons.add(complexityClassesLesson);
		complexityClassesLesson.save();
		algomodule.save();
		
		
		comcat.curriculummodules.add(algomodule);
		algomodule.save();
		comcat.save();	
		
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");

	}
}
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.joda.time.DateTime;

import play.*;

import com.avaje.ebean.*;

import controllers.extra.sha1;

import models.*;
import models.continent.Continent;
import models.curriculum.Category;
import models.curriculum.Curriculummodule;
import models.curriculum.Curriculumlesson;
import models.curriculum.Curriculumtopic;
import models.forum.Forum;
import models.forum.Reply;
import models.forum.Topic;
import models.manytomany.Usertopic;
import models.module.Bibliography;
import models.module.Content;
import models.module.Lessonalert;
import models.module.Module;
import models.module.Lesson;
import models.module.Lessoncontent;
import models.module.University;
import models.test.Hypothesis;
import models.test.Test;
import models.test.question.Question;
import models.test.question.QuestionGroup;
import models.test.question.URL;

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
			
			Question q = new Question();
			q.question = " What is the name of this formula:  `x = (-b +- sqrt(b^2-4ac))/(2a)` ?";
			q.imageURL= "http://www.polymath-software.com/PolymathHelp/MatlabGraph.jpg";
			q.videoURL= "http://www.youtube.com/v/mvOkMYCygps";
			q.typeOfQuestion = 0;
			q.number = 1;
			q.weight = 50;
			q.weightToLose = 0;
			q.keywords = "quadratic formula,mathematics,hard excercise, this is too much for me";
			q.save();
			
			Hypothesis hyp_one = new Hypothesis();
			hyp_one.text = "a) `d/dxf(x)=lim_(h->0)(f(x+h)-f(x))/h`	";
			hyp_one.number = 0;
			hyp_one.save();
			
			Hypothesis hyp_two = new Hypothesis();
			hyp_two.text = "b) `f(x)=sum_(n=0)^oo(f^((n))(a))/(n!)(x-a)^n`";
			hyp_two.number = 1;
			hyp_two.isCorrect = true;
			hyp_two.save();
			
			Hypothesis hyp_three = new Hypothesis();
			hyp_three.text = "c) `(a,b]={x in RR | a < x <= b}`";
			hyp_three.number = 2;
			hyp_three.save();
			
			Hypothesis hyp_four = new Hypothesis();
			hyp_four.text = "d) `hat(ab) bar(xy) ulA vec v dotx ddot y`";
			hyp_four.number = 3;
			hyp_four.save();
			
			Question q_two = new Question();
			q_two.question= "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce lobortis semper felis a laoreet. Aliquam at massa nec mi mattis porttitor, `bb{AB3}.bbb(AB].cc(AB).fr{AB}.tt[AB].sf(AB)`.";
			q_two.typeOfQuestion =1;
			q_two.hypothesislist.add(hyp_one);
			q_two.hypothesislist.add(hyp_two);
			q_two.hypothesislist.add(hyp_three);
			q_two.hypothesislist.add(hyp_four);
			q_two.weight = 25;
			q_two.weightToLose = 10;
			q_two.number = 2;
			q_two.keywords = "complex science";
			q_two.save();
			
			hyp_one.question = q_two;
			hyp_one.save();
			hyp_two.question = q_two;
			hyp_two.save();
			hyp_three.question = q_two;
			hyp_three.save();
			hyp_four.question = q_two;
			hyp_four.save();
			
			Question q_three = new Question();
			q_three.question= "What is ComplexNetworks?";
			q_three.typeOfQuestion = 2;
			q_three.number = 1;
			q_three.weight = 25;
			q_three.weightToLose = 10;
			q_three.keywords = "complex networks";
			q_three.save();
			
			
			// NEWS ++++++++++++++++++++++++++++++++++++++++++
						
			Blog blog = new Blog();
			
			blog = new Blog();
			blog.alternateHeader = "Étoile will run on tablets !";
			blog.header= "Étoile will run on tablets !";
			blog.text = "The migration of the Étoile platform for tablet has started, both for iPad and Android. Be attentive to the next developments !";
			blog.alternateText = "The migration of the Étoile platform for tablet has started, both for iPad and Android...";
			blog.articleImageURL="http://velositor.com/wp-content/uploads/2012/03/Tablets-in-Education-eTextbooks-on-Tablets-Preferred-Over-Print-Books-Among-College-and-High-School-Students-e1332128400234-290x290.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("12/10/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.alternateHeader = "The Digital Campus goes to Latin-America";
			blog.header= "The Complex Systems Digital Campus goes to Latin-America and includes now 50 universities";
			blog.text = "The Complex Systems Digital Campus network is getting stronger. Following contacts with the best universities is Latin-America, the network received new and enthusiastic members, including universities, from Argentina, Brazil, Chile, Colombia, just to name a few countries. The  Complex Systems Digital Campus network includes now 50 founding institutions.";
			blog.alternateText = "The Complex Systems Digital Campus network is getting stronger... ";
			blog.articleImageURL="http://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Latin_America_(orthographic_projection).svg/250px-Latin_America_(orthographic_projection).svg.png";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("28/08/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "Étoile course on Complex Networks and Graph Theory";
			blog.text = "Jeff Johnson presented the Complex Networks and Graph Theory component of the 2nd Ph.D. School on  ”Mathematical Modeling of Complex Systems”, Pescara, Italy, 16-28 July, 2012. The aim of the Étoile method to explore new methods of crowd sourcing resources for education and reseach. Unusually, students are set the test before the begin studying. The idea is that if the student can answer a question already, that”s fine! If they cannot answer the question they have to search the internet to find a website that gives sufficient information to answer the question. An important aspect of the Etoile method is that it tests students” metaknowledge. To answer the question the student needs knowledge. Metaknowledge is here defined as “knowledge about the knowledge”, and generally it is deeper and shows greater understanding. Students demonstrate their metaknowledge by returning a web reference (URL) with their answer.  For example, if the question were “What is a clique in a social network” a student might answer “A clique is a (maximal) subset of vertices in the network that are all linked to each other” and provide the URL http://en.wikipedia.org/wiki/Social_network . Is http://en.wikipedia.org/wiki/Social_network a good URL for this question? In Etoile the URLs that student return enter an ecology of learning resources. Those best adapted to answer the question will thrive, while those least adapted will not. The ecology is driven by other students” experience of trying to use the URL to answer the question. For example, a student might give the URLs a star rating, with 5 stars meaning that the URL provided material that was excellently adapated to answering the question, with zero stars meaning the material was very badly adapted to the question – as far as that particular student was concerned. Of course, we are all different, and one student may find a website particularly good for answering a question, while another student my find it useful. This could reflect the style of the material on the site, the level of prior knowledge that it expects, or something as similar as the language used. For example, a site in Portugese could be excellent for a person speeking that language, and useful for someone who did not. But the reasons for finding sites useful or not may be more subtle than that. In general clusters of students (e.g. Portugese speakers, those knowing a lot of mathematics, those liking pictures) will find clusters of site useful (e.g. good pedagogical Portugese sites, terse mathematical sites, well illustrated sites). These emergent clusters can be considered to be relation simplices in hypenetworks. Hypergraphs, simplicial complexes and hypernetworks will be the focus of my lectures on Complex Networks and Graph Theory the example of this Etoile will (hopefully) provide hands-on data for us to explore the ideas. Jeffrey Johnson, The Open University, UK. 15th July 2012. http://www.cs-dc.net/Complex_Networks_Pescara_School_16-28_July_2012H.html";
			blog.alternateHeader = "Étoile course on Complex Networks";
			blog.alternateText = "Jeff Johnson presented the Complex Networks and Graph Theory...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/pescara.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("15/07/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "Étoile course on Statistics";
			blog.text = "The Étoile course on Statistics has been presented by Diogo Pinheiro for the Master and Doctoral Programme in Complexity Sciences - Lisbon University Institute and Faculty of Sciences at the University of Lisbon. The programme included: 1. Probability theory - 1.1 Probability measure; 1.2 Random variables; 1.3 Discrete distributions; 1.4 Continuous distributions; 1.5 The Law of Large Numbers; 1.6 The Central Limit Theorem; 2. Statistics - 2.1 Sampling; 2.2 Estimation; 2.3 Confidence intervals; 2.4 Hypothesis Testing; Stochastic Processes - 3.1 Basic properties; 3.2 Poisson Process; 3.3 Markov Chain; 3.4 Markov Process; 3.5 Brownian motion; 3.6 Lévy process";
			blog.alternateHeader = "Étoile course on “Statistics";
			blog.alternateText = "The Étoile course on Statistics has been presented ...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/stats.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("16/06/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "Étoile course on Complex Networks";
			blog.text = "The Étoile course on Complex Networks has been presented by Pedro Lind for the PhD students at the Faculty of Sciences / Lisbon University Institute. The programme included stochastic methods in complex systems, auto-organised criticality, and complex networks.";
			blog.alternateHeader = "Étoile course on Complex Networks";
			blog.alternateText = "The Étoile course on Complex Networks has been presented...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/nets.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("02/06/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "Étoile course on Mathematics";
			blog.text = "The Étoile course on Mathematics has been presented by Diogo Pinheiro for the Master and Doctoral Programme in Complexity Sciences - Lisbon University Institute and Faculty of Sciences at the University of Lisbon. The programme included (1) Differential equations and Dynamical Systems; (2) Chaotic behaviour, and (3) Bifurcations.";
			blog.alternateHeader = "Étoile course on Mathematics";
			blog.alternateText = "The Étoile course on Mathematics has been presented...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/maths.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("20/05/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "The Digital Campus reaches Africa";
			blog.text = "Inspired by the contacts that Paul Bourgine, from the Étoile team, developed with some of the best African universities, the African Conference on Complex Systems took place in Dakar, Senegal. The main subject of the conference was the African Roadmap and its Digital Campus - towards a UNESCO Unitwin Network. Participants from 34 different countries where present and discussed the African Roadmap. Since then the participants have been contributing to an African roadmap.";
			blog.alternateHeader = "Digital Campus in Africa";
			blog.alternateText = "The African Conference on Complex Systems took place in Dakar...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/africa.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("03/05/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "The Étoile Platform is on-line";
			blog.text = "The étoile Platform is available for professors, researchers and students in Complex Systems Science. The courses available include Hypernetworks, Mathematics for Complex Systems Studies, and Computational Complexity. Please feel free to contact the étoile team for more information.";
			blog.alternateHeader = "Étoile Platform";
			blog.alternateText = "The étoile Platform is available for professors, researchers, and...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/etoile_2.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "The Complex Systems Digital Campus has been launched";
			blog.text = "The European Coordination Action ASSYST with help of the Complex Systems Society (CSS) is forming an international Complex Systems Digital Campus as a UniTwin network between universities from all continents. The aim of the network is to spread knowledge and know-how of the new science through education and research collaborations. The Complex Systems Digital Campus will federate the Research and Educational Institutions worldwide addressing the challenges of complex systems science. It will coordinate an evolving international network of scientists to identify the scientific challenges though ‘living complex systems roadmaps’, facilitating the sharing of research and educative resources to address these challenges. The Digital Campus will have virtual departments federating the e-community addressing each challenge. The Digital Campus will be open to all citizens of the world and enable them to participate in the creation and application of the new Complex Systems Science to help solve the local and global challenges that lie ahead. The Étoile community is deeply committed to the success of the Complex Systems Digital Campus.";
			blog.alternateHeader = "Complex Systems Digital Campus";
			blog.alternateText = "The Complex Systems Digital Campus will federate the research ...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/unesco.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("16/03/2012");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "Jeff Johnson presented étoile at the ECCS’11 Conference";
			blog.text = "Jeff Johnson presented the étoile Cascades Ideas at ECCS’11 – European Conference on Complex Systems. You are invited to watch the presentation on video.";
			blog.alternateHeader = "Étoile at  ECCS’11";
			blog.alternateText = "Jeff Johnson presented the Étoile Cascades Ideas project at ECCS’11...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/Jeff_at_eccs11.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("04/10/2011");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.header= "The Étoile experimental version is on-line";
			blog.text = "You are welcome to try it… and return later to test the new functionalities that are being prepared!";
			blog.alternateHeader = "Étoile experimental version";
			blog.alternateText = "You are welcome to try it, and return later to test the new functionalities...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/monge.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("04/10/2011");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.alternateHeader = "Étoile used in PhDs";
			blog.header= "Étoile used in Master and PhD Programmes";
			blog.text = "The Master and Doctoral Programmes in Complexity Sciences (Lisbon) agreed to evaluate the experimental version of étoile. Classes will start on Friday, 7th of October 2011.";
			blog.alternateText = "The Doctoral Programmes in Complexity Sciences (Lisbon) agreed to...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/phd_lisbon.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("04/10/2011");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.alternateHeader = "Kickoff meeting";
			blog.header= "Kickoff meeting held at the OU";
			blog.text = "The étoile project held its kickoff meeting at the Open University in Milton Keynes in the beginning of march. The event lasted 3 days where the members of the team had the opportunity to see presentations on several projects run by the Open University that are highly related to distance learning, e-learning systems, and automatic marking system, among others.";
			blog.alternateText = "The étoile project held its kickoff meeting at the Open University...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/kick-off_jeff.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("23/03/2011");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.alternateHeader = "Étoile is about to start !";
			blog.header= "Étoile is about to start !";
			blog.text = "The étoile project will test a remarkable information architecture using social intelligence to provide ultra-low-cost education and support the rapid dissemination of scientific ideas. It will be tested in with postgraduate students in Europe and around the world. It is intended to support programmes of education in domains related to complex systems science to large numbers of students around the world at almost no cost per student.";
			blog.alternateText = "The étoile project will test a remarkable information architecture using ...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/education.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("11/02/2011");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			blog = new Blog();
			blog.alternateHeader = "Kickoff scheduled for 9-12 March 2011";
			blog.header= "Kickoff scheduled for 9-12 March 2011";
			blog.text = "The étoile kickoff meeting will take place at the Hub Lecture Theatre, The Open University, Walton Hall, Milton Keynes, MK7 6AA, UK, between the 9th and the 12nd March 2011.";
			blog.alternateText = "The étoile kickoff meeting will take place at the Hub Lecture Theatre...";
			blog.articleImageURL="http://www.iscte.pt/~jmal/etoile/images/OU_milton_keynes_campus.jpg";
			try{
				blog.date = new SimpleDateFormat("dd/MM/yyyy").parse("11/02/2011");
			} catch (Exception ex){
				ex.printStackTrace();
			}
			blog.save();
			
			
			
			
			// QUESTIONS ++++++++++++++++++++++++++++++++++++++++
			Hypothesis hypothesis_zero = new Hypothesis();
			hypothesis_zero.text = "No answer";
			hypothesis_zero.save();
			
			Hypothesis hypothesis_one = new Hypothesis();
			hypothesis_one.text = "a) `int_0^1f(x)dx`";
			hypothesis_one.number = 0;
			hypothesis_one.question = q_three;
			hypothesis_one.isCorrect = true;
			hypothesis_one.save();
			
			Hypothesis hypothesis_two = new Hypothesis();
			hypothesis_two.text = "b) `[[a,b],[c,d]]((n),(k))`	";
			hypothesis_two.number = 1;
			hypothesis_two.question = q_three;
			hypothesis_two.isCorrect = true;
			hypothesis_two.save();
			
			Hypothesis hypothesis_three = new Hypothesis();
			hypothesis_three.text = "c) `x/x={(1,if x!=0),(text{undefined},if x=0):}`	";
			hypothesis_three.number = 2;
			hypothesis_three.question = q_three;
			hypothesis_three.save();
			
			
			// TESTS ++++++++++++++++++++++++++++++++++++++++++++
			QuestionGroup g = new QuestionGroup();
			g.question = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce lobortis semper felis a laoreet. Aliquam at massa nec mi mattis porttitor.";
			g.questions.add(q);
			g.questions.add(q_two);
			g.number = 1;
			g.save();
			
			QuestionGroup g_two = new QuestionGroup();
			g_two.question = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce lobortis semper felis a laoreet. Aliquam at massa nec mi mattis porttitor.";
			g_two.questions.add(q_three);
			g_two.number = 2;
			g_two.save();
			
			 Calendar calendar1 = Calendar.getInstance();
			 calendar1.set(2013, 2, 01);
			 Long date_milis1 = calendar1.getTimeInMillis();
			 
			 Calendar calendar2 = Calendar.getInstance();
			 calendar1.set(2013, 2, 05);
			 Long date_milis2 = calendar1.getTimeInMillis();
			
			Test test_one = new Test();
			test_one.published=true;
			test_one.name = "First Sum Test";
			test_one.expectedDuration="1,5 hours";
			test_one.text = "Improve your Sum Skills!";
			test_one.testImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			test_one.begin_date = new Date(date_milis1);
			test_one.finish_date = new Date(date_milis2);
			test_one.markers_limit_date = new Date(date_milis2+640800000);
			test_one.save();
			
			g.test = test_one;
			g.save();
			
			g_two.test = test_one;
			g_two.save();
			

			
			Test test_two = new Test();
			test_two.begin_date = new Date(date_milis1);
			test_two.finish_date = new Date(date_milis2);
			test_two.markers_limit_date = new Date(date_milis2+640800000);
			test_two.published=true;
			test_two.name="Final Sum Test";
			test_two.expectedDuration="45 min";
			test_two.text = "Final AnswerMarker of Sum Lesson";
			test_two.testImageURL = "http://www.etoilecascadesideas.eu/wp-content/uploads/2012/10/img_globe4-294x300.jpg";
			test_two.save();
			
			
			
			
			// LESSONS  +++++++++++++++++++++++++++++++++++++++++
			
			Lessoncontent mcontent = new Lessoncontent();
			mcontent.name = "Multiplication table";
			mcontent.text = "Multiplication values can be represented in a table.";
			mcontent.url = "http://www.vaughns-1-pagers.com/computer/multiplication-tables.htm";
			mcontent.lessonContentImageURL = "http://www.vaughns-1-pagers.com/computer/multiplication-tables/times-table-10x10.gif";
			mcontent.save();
			
			Lesson lesson_demo_one = new Lesson();
			lesson_demo_one.name = "Multiplication";
			lesson_demo_one.acronym = "multiplicationDemo";
			lesson_demo_one.description = "Multiplication (often denoted by the cross symbol ×) is the mathematical operation of scaling one number by another. It is one of the four basic operations in ...";
			lesson_demo_one.shortDescription = "Multiplication (often denoted by the cross symbol ×) is the mathematical operation of scaling one number by another. It is one of the four basic operations in ...";
			lesson_demo_one.imageURL = "http://imguol.com/2012/07/09/saiba-como-usar-tabela-do-word-para-somar-itens-1341868753923_956x500.jpg";
			lesson_demo_one.lessoncontents.add(mcontent);
			lesson_demo_one.save();
			lesson_demo_one.save();
			
			
			Lesson lesson_one = new Lesson();
			lesson_one.name = "Multiplication";
			lesson_one.number=1;
			lesson_one.acronym = "multiplication";
			lesson_one.description = "Multiplication (often denoted by the cross symbol ×) is the mathematical operation of scaling one number by another. It is one of the four basic operations in ...";
			lesson_one.shortDescription = "Multiplication (often denoted by the cross symbol ×) is the mathematical operation of scaling one number by another. It is one of the four basic operations in ...";
			lesson_one.imageURL = "http://upload.wikimedia.org/wikipedia/commons/thumb/5/57/Simple_multiplication.png/300px-Simple_multiplication.png";
			lesson_one.save();
			
			mcontent.lesson = lesson_one;
			mcontent.save();
			
			Lessonalert alert_one = new Lessonalert();
			alert_one.name = "New Test!";
			alert_one.text = "The Multiplication test is now available!";
			alert_one.imageURL = "https://www.clevernotes.ie/wp-content/uploads/2012/11/106395737.jpg";
			alert_one.lesson = lesson_one;
			alert_one.save();
			
			Lessonalert alert_two = new Lessonalert();
			alert_two.name = "New Resources available!";
			alert_two.text = "Please read the new resources! ";
			alert_two.imageURL = "http://us.123rf.com/400wm/400/400/zybr/zybr1204/zybr120400005/13195450-vector-illustration-pile-of-books-isolated-on-white.jpg";
			alert_two.lesson = lesson_one;
			alert_two.save();
			
			createSecondTest(test_two, lesson_one);
			
			test_one.lesson = lesson_one;
			test_two.lesson = lesson_one;
			test_two.save();
			test_one.save();
			
			Lesson lesson_two = new Lesson();
			lesson_two.name = "Division";
			lesson_two.number=2;
			lesson_two.acronym = "divison";
			lesson_two.description = "In the expression a ÷ b = c, a is called the dividend or numerator, b the divisor or denominator and the result c is called the quotient. Conceptually, division describes two distinct but related settings. Partitioning involves taking a set of size a and forming b groups that are equal in size. The size of each group formed, c, is the quotient of a and b. Quotative division involves taking a set of size a and forming groups of size b. The number of groups of this size that can be formed, c, is the quotient of a and b.[1]";
			lesson_two.shortDescription = "In mathematics, especially in elementary arithmetic, division (÷) is an arithmetic operation";
			lesson_two.imageURL = "http://www.coolmath4kids.com/long-division/images/long-division-30.gif";
			lesson_two.save();
			
			// Lessons for the Network Theory module
			
			Lesson lesson_net1 = new Lesson();
			lesson_net1.name = "Introduction";
			lesson_net1.number=3;
			lesson_net1.acronym = "netIntro";
			lesson_net1.description = "This lesson introduces the Network Theory module.";
			lesson_net1.shortDescription = "This lesson introduces the Network Theory module.";
			lesson_net1.imageURL = "http://www.mba.master.unipi.it/images/Linkedin_web.jpg";
			lesson_net1.save();
			
			Lesson lesson_net2 = new Lesson();
			lesson_net2.name = "Graphs, Networks, Trees and Lattices";
			lesson_net2.number=4;
			lesson_net2.acronym = "netGraphs";
			lesson_net2.description = "This lesson presents... (to complete)";
			lesson_net2.shortDescription = "This lesson presents... (to complete)";
			lesson_net2.imageURL = "http://upload.wikimedia.org/wikipedia/commons/thumb/3/39/Directed_acyclic_graph_3.svg/356px-Directed_acyclic_graph_3.svg.png";
			lesson_net2.save();
			
			Lesson lesson_net3 = new Lesson();
			lesson_net3.name = "Random Networks, Scale-free Networks & Power Laws";
			lesson_net3.number=5;
			lesson_net3.acronym = "netRandom";
			lesson_net3.description = "This lesson presents... (to complete)";
			lesson_net3.shortDescription = "This lesson presents... (to complete)";
			lesson_net3.imageURL = "http://readwrite.com/files/files/opengraph.png";
			lesson_net3.save();
			
			Lesson lesson_net4 = new Lesson();
			lesson_net4.name = "Connectivity: Small Worlds, Clustering and Community Detection";
			lesson_net4.number=6;
			lesson_net4.acronym = "netConnect";
			lesson_net4.description = "This lesson presents... (to complete)";
			lesson_net4.shortDescription = "This lesson presents... (to complete)";
			lesson_net4.imageURL = "http://bio.informatik.uni-jena.de/wp/wp-content/uploads/2010/11/leukemia3_vor_pidr.png";
			lesson_net4.save();
			
			Lesson lesson_net5 = new Lesson();
			lesson_net5.name = "Percolation, Cascades and Epidemics";
			lesson_net5.number=7;
			lesson_net5.acronym = "netPercol";
			lesson_net5.description = "This lesson presents... (to complete)";
			lesson_net5.shortDescription = "This lesson presents... (to complete)";
			lesson_net5.imageURL = "http://pilarlopezsophrologue.files.wordpress.com/2011/01/k-listo-sophrologie-pilar-lopez-sexo-domino-atelier-saint-valentin.jpg";
			lesson_net5.save();
			
			Lesson lesson_net6 = new Lesson();
			lesson_net6.name = "Assessment-1";
			lesson_net6.number=8;
			lesson_net6.acronym = "netAssess-1";
			lesson_net6.description = "This lesson presents... (to complete)";
			lesson_net6.shortDescription = "This lesson presents... (to complete)";
			lesson_net6.imageURL = "http://myenglishpages.com/blog/wp-content/uploads/2009/09/assessment.gif";
			lesson_net6.save();
			
			Lesson lesson_net7 = new Lesson();
			lesson_net7.name = "Hypergraphs, Hypernetworks";
			lesson_net7.number=9;
			lesson_net7.acronym = "netHyper";
			lesson_net7.description = "This lesson presents... (to complete)";
			lesson_net7.shortDescription = "This lesson presents... (to complete)";
			lesson_net7.imageURL = "http://upload.wikimedia.org/wikipedia/commons/thumb/5/57/Hypergraph-wikipedia.svg/262px-Hypergraph-wikipedia.svg.png";
			lesson_net7.save();
			
			Lesson lesson_net8 = new Lesson();
			lesson_net8.name = "Networks of Networks & Multilevel Systems";
			lesson_net8.number=10;
			lesson_net8.acronym = "netNet";
			lesson_net8.description = "This lesson presents... (to complete)";
			lesson_net8.shortDescription = "This lesson presents... (to complete)";
			lesson_net8.imageURL = "http://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Diagram_of_a_social_network.jpg/220px-Diagram_of_a_social_network.jpg";
			lesson_net8.save();
			
			Lesson lesson_net9 = new Lesson();
			lesson_net9.name = "Examples, Big Data & Visualization";
			lesson_net9.number=11;
			lesson_net9.acronym = "netExamples";
			lesson_net9.description = "This lesson presents... (to complete)";
			lesson_net9.shortDescription = "This lesson presents... (to complete)";
			lesson_net9.imageURL = "http://t1.gstatic.com/images?q=tbn:ANd9GcTT3GWRYa957yqKCeZhlRkZu0UKQROTZi-4BNs0qvKEM1kP4yL2SA";
			lesson_net9.save();
			
			Lesson lesson_net10 = new Lesson();
			lesson_net10.name = "Assessment-2";
			lesson_net10.number=12;
			lesson_net10.acronym = "netAssess-2";
			lesson_net10.description = "This lesson presents... (to complete)";
			lesson_net10.shortDescription = "This lesson presents... (to complete)";
			lesson_net10.imageURL = "http://myenglishpages.com/blog/wp-content/uploads/2009/09/assessment.gif";
			lesson_net10.save();
		
			//LANGUAGES +++++++++++++++++++++++++++++
			Language l_one = new Language();
			l_one.name = "Português";
			l_one.save();
			
			Language l_two = new Language();
			l_two.name = "English";
			l_two.save();
			
			Language l_three = new Language();
			l_three.name = "Français";
			l_three.save();
			
			Language l_four = new Language();
			l_four.name = "Español";
			l_four.save();
			
			// MODULES +++++++++++++++++++++++++++++++++++++++++
			
			/*
			Module module_demo = new Module();
			module_demo.name = "Demo for testing the platform";
			module_demo.acronym="demo1";
			module_demo.imageURL = "http://www.naomiture.com/wp-content/uploads/2010/12/video-shoplocal-play.jpg";
			module_demo.videoURL = "http://www.youtube.com/v/tvN1dp0qVcY";
			module_demo.description = "This module demonstrates all the main functionalities of the Étoile Platform, both for professors and students. The module includes 2 lessons, one in mathematics and another in social sciences. Both lessons have tests, with different kind of questions. The contents of this module are only for demonstration, without any sort of pedagogical goal or coherence.";
			module_demo.short_description = "This module demonstrates all the main functionalities of the Étoile Platform";
			module_demo.lessons.add(lesson_demo_one);
			module_demo.lessons.add(lesson_one);
			module_demo.lessons.add(lesson_two);
			module_demo.language = l_two;
			module_demo.save();
			 */
			
			/*
			Forum forum_one = new Forum();
			forum_one.module=module_demo;
			forum_one.save();
			module_demo.forum=forum_one;
			module_demo.save();
		
			lesson_one.module = module_demo;
			lesson_one.save();
			
			lesson_two.module = module_demo;
			lesson_two.save();
			*/

			
			/*
			Module module = new Module();
			module.name = "Mathematics";
			module.acronym="math101";
			module.imageURL = "http://www.naomiture.com/wp-content/uploads/2010/12/video-shoplocal-play.jpg";
			module.videoURL = "http://www.youtube.com/v/AyPzM5WK8ys";
			module.description = "This will be the Étoile Mathematics module. This module is empty for now. Would you like to propose a module on Mathematics? Please contact the Étoile administrators (see 'Contacts' in the menu above).";
			//module.lessons.add(lesson_one);
			//module.lessons.add(lesson_two);
			module.save();
			*/
			 
			/*
			Module module_two = new Module();
			module_two.name = "Statistics";
			module_two.acronym="stats101";
			module_two.imageURL = "http://www.vintank.com/wp-content/uploads/2012/04/stat.jpg";
			module_two.description = "Statistics is the study of the collection, organization, analysis, interpretation, and presentation of data. It deals with all aspects of this, including the planning of... This module is empty for now. Would you like to propose a module on Statistics? Please contact the Étoile administrators (see 'Contacts' in the menu above).";
			module_two.save();
			*/
			/*
			Module module_three = new Module();
			module_three.name = "Non-Equilibrium Social Science";
			module_three.acronym="ness";
			module_three.imageURL = "http://img138.imageshack.us/img138/9061/nesslogounequilibriumve.jpg";
			module_three.description = "This module introduces the main concepts in Non-Equilibrium Social Science.";
			module_three.language = l_two;
			module_three.short_description ="This module introduces the main concepts in Non-Equilibrium Social Science";
			module_three.save();
			*/
			Forum forum = new Forum();
			/*
			forum.module=module_three;
			forum.save();
			module_three.forum=forum;
			module_three.save();
			*/
			
			// MODULE NETWORK SCIENCE
			
			Module module_four = new Module();
			module_four.name = "Network Science";
			module_four.acronym="netsci_01";
			module_four.imageURL = "http://herd.typepad.com/.a/6a00d83451e1dc69e2011278fba78928a4-pi";
			module_four.videoURL = "http://www.youtube.com/v/10oQMHadGos ";
			module_four.description = "This module presents the State-of-the-Art in Network Science, including both theory and examples. ";
			module_four.short_description = "This module presents the State-of-the-Art in Network Science";
			module_four.lessons.add(lesson_net1);
			module_four.lessons.add(lesson_net2);
			module_four.lessons.add(lesson_net3);
			module_four.lessons.add(lesson_net4);
			module_four.lessons.add(lesson_net5);
			module_four.lessons.add(lesson_net6);
			module_four.lessons.add(lesson_net7);
			module_four.lessons.add(lesson_net8);
			module_four.lessons.add(lesson_net9);
			module_four.lessons.add(lesson_net10);
			module_four.language = l_two;
			module_four.save();
			
			
			Forum forum_four = new Forum();
			forum_four.module=module_four;
			forum_four.save();
			module_four.forum=forum_four;
			module_four.save();
			
			lesson_net1.module = module_four;
			lesson_net1.save();
			/*
			lesson_net2.module = module_four;
			lesson_net2.save();	
			lesson_net3.module = module_four;
			lesson_net3.save();
			lesson_net4.module = module_four;
			lesson_net4.save();
			lesson_net5.module = module_four;
			lesson_net5.save();
			lesson_net6.module = module_four;
			lesson_net6.save();
			lesson_net7.module = module_four;
			lesson_net7.save();
			lesson_net8.module = module_four;
			lesson_net8.save();
			lesson_net9.module = module_four;
			lesson_net9.save();
			lesson_net10.module = module_four;
			lesson_net10.save();
			*/
			
			Module module_five = new Module();
			module_five.name = "Bioinformatics";
			module_five.acronym="bioinformatics";
			module_five.imageURL = "http://www.sussex.ac.uk/Users/andywu/gallery/rm_f.gif";
			module_five.description = "<p>Attractors and robustness in Boolean automata networks. </p> <p>Application to living systems.</p>";
			module_five.description = "Attractors and robustness in Boolean automata networks. Application to living systems.";
			module_five.short_description = "Attractors and robustness in Boolean automata networks";
			module_five.language = l_two;
			module_five.save();
			
			Forum forum_five = new Forum();
			forum_five.module=module_five;
			forum_five.save();
			module_five.forum=forum_five;
			module_five.save();
			
			
			
			Module module_six = new Module();
			module_six.name = "Réseaux génétiques et morphodynamique cellulaire";
			module_six.acronym="resgen";
			module_six.imageURL = "http://www.icra.ca/home.nsf/pages/reseaux-genetiques/$file/Genetic_Networks.jpg";
			module_six.videoURL = "";
			module_six.description = "Etude comparée du rôle des structures médianes de l'embryon (mésoderme et plaque préchordale), et des signaux qui en dépendent dans la régionalisation précoce du tube neural. Analyse de dynamique spatiale et temporelle de la morphogénèse cellulaire au cours de la formation du tube neural.";
			module_six.short_description = "Etude comparée du rôle des structures médianes de l'embryon";
			module_six.language = l_three;
			module_six.save();
					
			Forum forum_six = new Forum();
			forum_six.module=module_six;
			forum_six.save();
			module_six.forum=forum_six;
			module_six.save();
			
			
			// Bibliography +++++++++++++++++++++++++++++++++++++++++
			/*
			Bibliography bibliography = new Bibliography();
			bibliography.title="T. Bynum and S. Rogerdson, eds., Computer Ethics and Professional Res- ponsibility, (Wiley, 2003).";
			bibliography.link="http://books.google.co.uk/books/about/Computer_Ethics_and_Professional_Respons.html?id=FOxqAjC8iHkC";
			bibliography.description="This clear and accessible textbookand its associated website offer a state of the art introduction to the burgeoning field of computer ethics and professional responsibility. ";
			bibliography.imageURL="http://bks5.books.google.co.uk/books?id=FOxqAjC8iHkC&printsec=frontcover&img=1&zoom=1&imgtk=AFLRE71rwGQTfyj97JTxptwM-URC0Iy5eY0fJwpy3wsNs0pYpeltEtL334XnHw2ZigcJ8ylnypoG91BHhVYFSQoh5ID13NZ_eGEk4FfyLvZC3tb15cbuOIWj5PAFEte6MsjiB8-QaxZL";
			bibliography.module=module_demo;
			bibliography.save();
			
			bibliography = new Bibliography();
			bibliography.title="Mathematics: Teaching School Subjects 11-19.";
			bibliography.link="http://books.google.co.uk/books?id=wb3X3xNSntkC&dq=mathematics&hl=pt-PT&source=gbs_navlinks_s";
			bibliography.description="This accessible and thought-provoking book considers what beginning teachers need to know about learning, teaching, assessment, curriculum and professional development, in the context of teaching mathematics to eleven to nineteen year olds. It is part of a new series of books that has as its starting point the fact that PGCE students are already subject specialists.";
			bibliography.imageURL="http://bks9.books.google.co.uk/books?id=wb3X3xNSntkC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70zfpHHuBmkjMmKgszdfIZYNSN2UKVkhMjtAbAGEqDsePekXhaSmjxwqM2g8PBtzvomBeclI1ovd3ofdXAm_iasy6k2YY8MGyQJEP9HLd-EfOL8XPQiDxSSukg_oN3yDg4ElZ7q";
			bibliography.module=module_demo;
			bibliography.save();
			
			bibliography = new Bibliography();
			bibliography.title="Linked: The New Science of Networks, Albert-László Barabási. Perseus Publishing";
			bibliography.link="http://en.wikipedia.org/wiki/Linked:_The_New_Science_of_Networks";
			bibliography.description="Linked: The New Science of Networks is a popular science book written by the Hungarian physicist Albert-László Barabási and first published by the Perseus Books Group in 2002.Barabási has changed the way of thinking about real-world networks and largely contributed to making networks the revolutionary science of the 21st century. Linked is his first book that introduces the highly developing field of Network Science to the broad audience.";
			bibliography.imageURL="http://www3.nd.edu/~networks/Linked/real-cover.jpg";
			bibliography.module=module_demo;
			bibliography.save();
			
			bibliography = new Bibliography();
			bibliography.title="Networks – an Introduction, Mark Newman, Oxford University Press, 2010.";
			bibliography.link="http://www.oup.com/us/catalog/general/subject/Physics/?view=usa&ci=9780199206650#";
			bibliography.description="Subjects covered include the measurement and structure of networks in many branches of science, methods for analyzing network data, including methods developed in physics, statistics, and sociology, the fundamentals of graph theory, computer algorithms, and spectral methods, mathematical models of networks, including random graph models and generative models, and theories of dynamical processes taking place on networks.";
			bibliography.imageURL="http://www-personal.umich.edu/~mejn/networks-an-introduction/cover-s.jpg";
			bibliography.module=module_demo;
			bibliography.save();
			*/
			
			Bibliography bibzebraf = new Bibliography();
			bibzebraf.title="The Zebrafish Book a Guide for the Laboratory Use of Zebrafish Danio* (Brachydanio) Rerio, 5th Edition";
			bibzebraf.link="http://zebrafish.org/zirc/orders/buyBookQ.php?item=Book&id=book&detail=The%20Zebrafish%20Book";
			bibzebraf.description="A guide for the laboratory use of zebrafish.";
			bibzebraf.imageURL="http://zebrafish.org/zirc/images/zfishbook.png";
			bibzebraf.module=module_six;
			bibzebraf.save();
			
			
			
			
			// CONTENT FOR COURSES ++++++++++++++++++++++++++++++
			
			Content content;
			/*
			content = new Content();
			content.module=module_demo;
			content.title="Textbook (Recommended)";
			content.text="Name of the recommended textbook";
			content.save();
			
			content = new Content();
			content.module=module_demo;
			content.title="Module description and objectives";
			content.text="This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. This is the module description and objectives. ";
			content.save();
			*/
			
			/* 
			content = new Content();
			content.module=module_demo;
			content.title="About the Professor";
			content.text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas suscipit auctor velit sit amet consequat. In turpis augue, scelerisque a malesuada euismod, mollis eu enim. Maecenas sapien tellus, vehicula non aliquet id, pretium commodo risus. Donec enim augue, lacinia in varius quis, luctus a nibh. Fusce pretium viverra neque, ac congue tortor pretium et.";
			content.save();
			*/
			
			/*
			content= new Content();
			content.module=module_demo;
			content.title="Module format";
			content.text="The module will include 10 lessons. Each lesson will be associated to a different test. ";
			content.save();
			
			content= new Content();
			content.module=module_demo;
			content.title="Recommended Background";
			content.text="Most of this module will be accessible to non-technical students. We will provide optional materials for those with some computer science background.";
			content.save();
			*/
			
			Content content_four_1 = new Content();
			content_four_1.module=module_four;
			content_four_1.title="This module is not ready yet, please return soon.";
			content_four_1.text="Most of this module will be accessible to non-technical students. We will provide optional materials for those with some computer science background.";
			content_four_1.save();
			
			Content content_five_1 = new Content();
			content_five_1.module=module_five;
			content_five_1.title="This module is not ready yet, please return soon.";
			content_five_1.text="Most of this module will be accessible to non-technical students. We will provide optional materials for those with some computer science background.";
			content_five_1.save();
			
			Content content_six_1 = new Content();
			content_six_1.module=module_six;
			content_six_1.title="This module is not ready yet, please return soon.";
			content_six_1.text="Most of this module will be accessible to non-technical students. We will provide optional materials for those with some computer science background.";
			content_six_1.save();
			
			
			// USERS ++++++++++++++++++++++++++++++++++++++++++++
			Student student = new Student();
			student.acronym = "rub";
			student.firstname = "Rúben";
			student.lastname = "Paixão";
			student.webpage = "etoileplatform.net";
			student.imageURL = "http://2.bp.blogspot.com/-7uG34ulFY1E/TpETsOglfwI/AAAAAAAAA5s/_60xlIvDLgs/s1600/Aprenda+a+Aprender_2.jpg";
			student.scientific_area = "Tecnology";
			student.contact = "rub@rub.pt";
			student.date_of_birth = new Date();
			student.description = "Esta é a minha descrição. Esta é a minha descrição. Esta é a minha descrição. Esta é a minha descrição. Esta é a minha descrição. Esta é a minha descrição. Esta é a minha descrição. Esta é a minha descrição. ";
			student.male = true;
			student.CSSId = 99999;
			student.shortdescription = "Esta é a minha pequena descrição. ";
			student.address = "Rua Antonio Jose Francisco nº20 2ºesq - 4851-152 Lisboa";
			student.degree = "Master in telecomunications";
			student.save();
			
			User user_rub = new User();
			user_rub.studentProfile = student;
			user_rub.email = "rub@rub.pt";
			user_rub.username="rub";
			user_rub.password = sha1.parseSHA1Password("123");
			user_rub.name = "Ruben";
			user_rub.account_type = 0;
			user_rub.country = "PT";
			user_rub.globalReputation = new Long(0);
//			user.modules.add(module);
			//user.modules.add(module_two);
//			user.modules.add(module_three);
//			module.save();
			//module_two.save();
//			module_three.save();
//			user.answersToMark.add();
//			user.answersToMark.add();
			user_rub.save();
			
			student.user=user_rub;
			student.save();
			

			
			Comment c = new Comment();
			c.text = "Great! I can't wait to try it!";
			c.blog = blog;
			c.user = user_rub;
			c.date = new Date();
			c.save();

			
			student = new Student();
			student.acronym = "";
			student.firstname = "Rui";
			student.lastname = "Lopes da Silva";
			student.webpage = "http://etoileplatform.net";
			student.imageURL = "http://placehold.it/150x150";
			student.scientific_area = "Telecommunications";
			student.contact = "rui@rui.pt";
			student.acronym="rui";
			student.date_of_birth = new Date();
			student.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus accumsan libero non enim commodo egestas. Quisque semper dapibus augue aliquam semper. Sed laoreet vestibulum condimentum.";
			student.male = true;
			student.CSSId = -1;
			student.shortdescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus accumsan libero non enim commodo egestas";
			student.address = "Av. Forças Armadas - 4851-152 Lisboa";
			student.degree = "Master in telecomunications";
			student.save();
			
//			
			User user = new User();
			user.studentProfile=student;
			user.email = "rui@rui.pt";
			user.username="rui";
			user.password = sha1.parseSHA1Password("123");
			user.name = "Rui Lopes da Silva";
			user.account_type = 0;
			user.country = "PT";
			user.globalReputation = new Long(0);
			//user.modules.add(module_demo);
			user.save();
			student.user=user;
			student.save();
			
			Modulescore modulescore= new Modulescore();
			//modulescore.module=module_demo;
			modulescore.user=user;
			modulescore.score=0;
			modulescore.save();
//			
//			module_demo.users.add(user);
//			module_demo.save();
			
			
			/*
			Usertopic usertopic = new Usertopic();
			usertopic.user=user;
			usertopic.save();
//			
			Topic topic= new Topic();
			topic.forum=forum_one;
			topic.starter=user;
			topic.title="First Topic";
			topic.date=new Date();
			topic.topicsubscriptions.add(usertopic);
			topic.save();
//			
//			usertopic.topic=topic;
//			usertopic.save();
			
			
			Reply reply = new Reply();
			reply.text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quis tellus libero, nec suscipit elit. Pellentesque vehicula felis nec ipsum dignissim imperdiet. Nulla facilisi. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec condimentum, diam et rutrum ultricies, justo elit tempor elit, at tempor sapien tellus ac erat. Phasellus tempus fermentum ullamcorper. Quisque lacinia lorem eu risus aliquet commodo. Aliquam leo enim, scelerisque sed euismod ut, porttitor at lorem. Vestibulum euismod convallis tempor. Aliquam lacus libero, vehicula id porta nec, dignissim vel elit. Curabitur et dolor vel nisi mattis blandit. Nullam ornare lorem eu dui tempor luctus.";
			reply.user=user;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
//			user.replies.add(reply);
//			user.save();
//			
			reply = new Reply();
			reply.text="In hac habitasse platea dictumst. Quisque non nisl bibendum lectus luctus mollis. Donec hendrerit ullamcorper tortor dignissim mollis. Pellentesque a arcu eget sapien ultrices scelerisque ac ut augue. Praesent arcu magna, euismod nec rutrum nec, eleifend vitae tellus. Proin posuere suscipit blandit. In hac habitasse platea dictumst. Nam convallis nunc a dui dapibus in vulputate lectus vulputate. Suspendisse nec ligula leo, at pellentesque ligula. Maecenas consectetur metus eget sem consequat et vehicula ipsum pharetra. Nulla vel diam nisl, in aliquet felis. Cras non tempor justo. Donec eleifend ligula nec odio mollis vitae adipiscing elit congue. Curabitur ut turpis quis lorem malesuada ultricies. Sed magna mauris, consequat vel tincidunt non, ullamcorper ut sapien. Duis ut lacus quis dui condimentum venenatis eget id lorem.";
			reply.user=user_rub;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
//			user_rub.replies.add(reply);
//			user_rub.save();
//			topic.replies.add(reply);
//			topic.save();
//			
			reply = new Reply();
			reply.text="Praesent ut neque nec turpis lobortis pretium a vel leo. Sed viverra varius justo. Phasellus commodo nibh ut erat sollicitudin convallis. Duis lectus tortor, sagittis ultrices viverra eu, porta vel justo. In lobortis, tortor et dapibus pellentesque, mauris orci placerat libero, at posuere felis turpis vel elit. Donec tellus massa, condimentum in placerat ac, consectetur consequat nulla. Maecenas at odio a arcu consequat luctus. Integer id lectus lorem, quis aliquet magna. Integer ornare, massa a elementum viverra, tortor lectus interdum arcu, laoreet dapibus augue risus egestas metus. Vestibulum vel purus nec dolor ullamcorper venenatis a quis purus. Suspendisse dictum eleifend augue quis ultrices. Integer lacinia pulvinar dui non venenatis. Ut convallis tincidunt adipiscing. Donec id orci nec nisi pharetra iaculis sit amet vitae odio. Sed eget magna diam, vel placerat nunc.";
			reply.user=user;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
//			user.replies.add(reply);
//			user.save();
//			topic.replies.add(reply);
//			topic.save();
//			
			reply = new Reply();
			reply.text="Ut ultrices tristique nisi et mollis. Phasellus nec dolor laoreet risus malesuada aliquam sit amet eget massa. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Praesent condimentum fermentum gravida. Fusce vel sapien massa, vitae tristique nibh. Integer sit amet elementum ante. Cras vel magna in ipsum faucibus convallis. In pellentesque pulvinar quam, nec laoreet sapien dignissim non. Quisque lacinia lorem velit, non dictum lorem.";
			reply.user=user_rub;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
//			user_rub.replies.add(reply);
//			user_rub.save();
//			topic.replies.add(reply);
//			topic.save();
//			
			reply = new Reply();
			reply.text="Phasellus vulputate libero sit amet elit tincidunt rhoncus. Donec arcu sem, adipiscing sit amet pretium in, sollicitudin eu orci. Morbi ac auctor tortor. Integer eu felis lacus, in dapibus quam. Curabitur elit elit, pharetra vitae dignissim non, euismod nec dolor. Nunc molestie laoreet ipsum sit amet suscipit. Cras non augue eget est faucibus auctor. Nulla pellentesque, neque ac condimentum scelerisque, odio eros facilisis nisl, in ornare purus nulla convallis lectus. Phasellus non odio nisl. Maecenas varius venenatis diam quis sagittis. Morbi dapibus, nisi ac convallis egestas, nulla leo tincidunt sem, sit amet consequat massa tortor ut libero.";
			reply.user=user;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
//			user.replies.add(reply);
//			user.save();
//			topic.replies.add(reply);
//			topic.save();
			*/

			
			// Professors +++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			// 5. Jeffrey Johnson
			
			ProfessorContent jjc = new ProfessorContent();
			jjc.description="Jeffrey Johnson is Professor of Complexity Science and Design. He joined the Open University in 1980 after three years as Senior Research Associate in the Geography Department of Cambridge University, and six years as Research Fellow in the Mathematics Department of Essex University.";
			jjc.title="Description";
			jjc.imageURL="";
			jjc.save();
			
			Professor jj = new Professor();
			jj.firstname="Jeffrey";
			jj.lastname="Johnson";
			jj.acronym="JJohnson";
			jj.email="Jeff.Johnson@open.ac.uk";
			jj.degree="Professor";
			jj.shortdescription="is Professor of Complexity Science and Design. He joined the Open University in 1980 after three years as Senior Research Associate in the Geography Department of Cambridge University, and six years as Research Fellow in the Mathematics Department of Essex University.";
			jj.contact="The Design Group, Faculty of Maths, Computing and Technology, The Open University, Walton Hall, Milton Keynes, MK7 6AA, UK  -  http://design.open.ac.uk/johnson/";
			jj.imageURL="http://www.intellectbooks.co.uk/MediaManager/Image/User/Jeff%204_9535_1281717004.jpg";
			jj.save();
			
			jj.modules.add(module_four);
			//jj.modules.add(module_three);
			jj.save();
			//module.save();
			
			jj.contents.add(jjc);
			jjc.professor=jj;
			jj.save();
			jjc.save();
			
			
			// 1. Jorge Louçã
			
			ProfessorContent pc = new ProfessorContent();
			pc.description = "Jorge Louçã teaches Computer Science at the IUL - Lisbon University Institute, where he runs the Master and Doctoral Programs in Complexity Sciences. His PhD is in Artificial Intelligence. He coordinates The Observatorium research team and his research interests concern modelling complex social systems through intensive data collection and analysis. He is particularly interested by knowledge generation models in large-scale communication networks. Recently he participated in the creation of the Unitwin network for the Complex Systems Digital Campus, involving institutions from Africa, Latin America and Europe.";
			pc.title="Description";
			pc.imageURL="";
			pc.save();
			
			Professor p= new Professor();
			p.firstname="Jorge";
			p.lastname="Louçã";
			p.acronym="JorgeL";
			p.email="Jorge.L@iscte.pt";
			p.degree="Professor";
			p.shortdescription=" teaches Computer Science at the IUL - Lisbon University Institute, where he runs the Master and Doctoral Programs in Complexity Sciences. His PhD is in Artificial Intelligence. He coordinates The Observatorium research team and his research interests concern modelling complex social systems through intensive data collection and analysis. He is particularly interested by knowledge generation models in large-scale communication networks. Recently he participated in the creation of the Unitwin network for the Complex Systems Digital Campus, involving institutions from Africa, Latin America and Europe.";
			p.contact="ISCTE-IUL - Instituto Universitário de Lisboa - Departamento de Ciências e Tecnologias de Informação - Av. das Forças Armadas - 1649-026 - Lisboa Portugal";
			p.imageURL="http://iscte.pt/~jmal/index_files/image003.jpg";
			p.save();
			
			/*
			p.modules.add(module_demo);
			p.modules.add(module_four);
			p.modules.add(module_three);
			p.save();
			*/
			//module.save();
			
			p.contents.add(pc);
			pc.professor=p;
			p.save();
			pc.save();
			
			/*p.contents.add(pc2);
			pc2.professor=p;
			p.save();
			pc2.save();
			*/
			
			// 2. PROFESSOR FOR TESTING
			
			// Prof
			User prof = new User();
			prof.email = "prof@prof.pt";
			prof.username="prof";
			prof.password=sha1.parseSHA1Password("123");
			prof.name = "Professor";
			prof.account_type = 1;
			prof.professorProfile=p;
			prof.save();
			p.user=prof;
			p.save();
			
			// Jeff Johnson
			User jeff = new User();
			jeff.email = "Jeff.Johnson@open.ac.uk";
			jeff.username="jeff";
			jeff.password=sha1.parseSHA1Password("123");
			jeff.name = "Professor";
			jeff.account_type = 1;
			jeff.professorProfile=jj;
			jeff.save();
			jj.user=jeff;
			jj.save();
			

			
			// 3. Nadine Peyriéras
			
			ProfessorContent npc = new ProfessorContent();
			npc.description="Nadine Peyriéras - Institut de Neurobiologie Alfred Fessard, CNRS UPR 3294, Av. de la Terrasse, 91198 Gif-sur-Yvette Cedex, France";
			npc.title="Contact";
			npc.imageURL="http://www.nbcm.cnrs-gif.fr/index_clip_image004.jpg";
			npc.save();
			
			ProfessorContent npc2 = new ProfessorContent();
			npc2.description="";
			npc2.title="URL";
			npc2.imageURL="";
			npc2.save();
			
			
			Professor np = new Professor();
			np.firstname="Nadine";
			np.lastname="Peyriéras";
			np.acronym="NadineP";
			np.email="peyriéras@inaf.cnrs-gif.fr";
			np.degree="Professor";
			np.shortdescription="";
			np.contact="Institut de Neurobiologie Alfred Fessard, CNRS UPR 3294 - Av. de la Terrasse, 91198 Gif-sur-Yvette Cedex, France - tel: (33) 1-69 82 41 42 fax: (33) 1-69 82 34 47 - http://www.inaf.cnrs-gif.fr/ned/equipe07/accueil_07.html";
			np.imageURL="http://public-files.prbb.org/prbb_actual/imatges/nadine.png";
			np.save();
			
			np.modules.add(module_six);
			np.save();
			
			//module.save();
			
			/*np.contents.add(npc);
			npc.professor=np;
			np.contents.add(npc2);
			npc2.professor=np;
			np.save();
			npc.save();
			npc2.save();
			*/
			
			// 4. Jacques Demongeot
			
			ProfessorContent jdc = new ProfessorContent();
			jdc.description="Jacques Demongeot is presently director of the Laboratory TIMC (CNRS 5525) Techniques of Imaging, Modelling & Cognition and he is also head of the Institute of Bio-engineering (IFRT 130 IpV) at the University Joseph Fourier, Grenoble, France. He has MD and PhD in mathematics and has been appointed Chairman of Biomathematics at the Institut Universitaire de France in 1994. Jacques Demongeot is also responsible for the Department of Medical Information at the University Hospital of Grenoble (CHUG) and for the doctoral school of bio-engineering Health, Cognition & Environment. His main Research Interests are: medical imaging; bio-informatics;bio-modeling; biological complexity; systems biology.";
			jdc.title="Contact";
			jdc.imageURL="";
			jdc.save();
			
			Professor jd = new Professor();
			jd.firstname="Jacques";
			jd.lastname="Demongeot";
			jd.acronym="JDemongeot";
			jd.email="";
			jd.degree="Professor";
			jd.shortdescription=" is presently director of the Laboratory TIMC (CNRS 5525) Techniques of Imaging, Modelling & Cognition and he is also head of the Institute of Bio-engineering (IFRT 130 IpV) at the University Joseph Fourier, Grenoble, France. He has MD and PhD in mathematics and has been appointed Chairman of Biomathematics at the Institut Universitaire de France in 1994. Jacques Demongeot is also responsible for the Department of Medical Information at the University Hospital of Grenoble (CHUG) and for the doctoral school of bio-engineering Health, Cognition & Environment. His main Research Interests are: medical imaging; bio-informatics;bio-modeling; biological complexity; systems biology.";
			jd.contact="University Joseph Fourier of Grenoble - Laboratoire TIMC - Institut d'Ingénierie de l'Information de Santé - Faculté de Médecine - 38706 La Tronche cedex - France";
			jd.imageURL="http://egealmeeting.lasalle-beauvais.fr/images/demongeot_jacques.jpg";
			jd.save();
			
			jd.modules.add(module_five);
			jd.save();

			//module.save();
			
			jd.contents.add(jdc);
			jdc.professor=jd;
			jd.save();
			jdc.save();
			
			
			
			// Comments  +++++++++++++++++++++++++++++++++++++++++++++++++


//			Comment c1 = new Comment();
//			c1.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et justo enim. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
//			c1.blog = blog;
//			c1.user = user;
//			c1.date = new Date();
//			c1.save();
			
			
			
	
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
			
			
			
			
			
			// UNIVERSITIES ++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			University iscte = new University();
			iscte.name="ISCTE-IUL";
			iscte.acronym="iscte-iul";
			iscte.adressURL="http://iscte.iul.pt";
			iscte.imageURL="http://noticias.universia.pt/pt/images/universia/i/is/isc/iscte_iul_logo.jpg";
			iscte.modulebannerURL = "http://4.bp.blogspot.com/-IfeNT6e6mRQ/T6fSIa7xJdI/AAAAAAAAdPA/VHMCg_Mg7Go/s1600/ISCTE.jpg";
			iscte.continent=europe;
			iscte.save();
			
			/*
			University university_fct = new University();
			university_fct.name="FCT";
			university_fct.adressURL="http://fct.unl.pt";
			university_fct.acronym="fct";
			university_fct.imageURL="http://tele1.dee.fct.unl.pt/mst_2010_2011/figs/logo_quadrado%20JMF.gif";
			university_fct.continent=europe;
			university_fct.save();
			*/
			
			University ou = new University();
			ou.name="Open University UK";
			ou.acronym="ou";
			ou.adressURL="http://www.open.ac.uk";
			ou.imageURL="http://www.open.ac.uk/includes/headers-footers/oulogo-56.jpg";
			ou.continent=europe;
			ou.save();
			
			University AIMSSenegal = new University();
			AIMSSenegal.name="AIMS-Senegal";
			AIMSSenegal.acronym="aims-Senegal";
			AIMSSenegal.adressURL = "http://www.aims-senegal.sn"; 
			AIMSSenegal.imageURL="http://aims-senegal.sn/assets/images/logo-news2.png";
			AIMSSenegal.modulebannerURL = "";
			AIMSSenegal.continent=africa;
			AIMSSenegal.save();
			
			University CergyPontoise = new University();
			CergyPontoise.name="Cergy-Pontoise";
			CergyPontoise.acronym="cergy-pontoise";
			CergyPontoise.adressURL = "http://www.u-cergy.fr";
			CergyPontoise.imageURL="http://www.u-cergy.fr/_contents/ametys-internal%253Asites/www/ametys-internal%253Acontents/telechargement-galerie-de-photos/_metadata/photos/2/image/UCP_logo_violet.jpg";
			CergyPontoise.modulebannerURL = "";
			CergyPontoise.continent=europe;
			CergyPontoise.save();
			
			University nias = new University();
			nias.name="NIAS - National Institute of Advanced Studies";
			nias.acronym="NIAS";
			nias.adressURL = "http://www.nias.res.in/aboutnias-people-faculty-rajeshkasturirangan.php";
			nias.imageURL="http://www.iscte.pt/~jmal/etoile/images/nias.jpg";
			nias.modulebannerURL = "";
			nias.continent=asia;
			nias.save();
			/*
			University X = new University();
			X.name="X";
			X.acronym="X";
			x.adressURL = "X";
			X.imageURL="X";
			X.modulebannerURL = "X";
			X.continent=X;
			X.save();
			
			University X = new University();
			X.name="X";
			X.acronym="X";
			x.adressURL = "X";
			X.imageURL="X";
			X.modulebannerURL = "X";
			X.continent=X;
			X.save();
			
			University X = new University();
			X.name="X";
			X.acronym="X";
			x.adressURL = "X";
			X.imageURL="X";
			X.modulebannerURL = "X";
			X.continent=X;
			X.save();
			*/
			
			student.university = iscte;
			student.save();

			/*
			//module.university=university;
			module_three.university=iscte;
			//module_two.university=university;
			module_four.university=ou;
			//module.save();
			//module_two.save();
			module_three.save();
			module_four.save();
			*/
		
			// CATEGORIES ++++++++++++++++++++++++++++++++++++++++++++++++++++
			
				// 1. Questions ++++++++++++++++++++++++++++++++++++++
				Category questions = new Category();
				questions.name = "1. Questions";
				questions.description="The transversal questions of interdisciplinary integrative science.";
				questions.keyword = "1_questions";
				questions.save();
				
				/*
				 * Exemplo de tópico
				// 1.1.2.3. Formal epistemology, experimentation, machine learning
				Curriculumtopic formalEpistemologyTopic= new Curriculumtopic();
				formalEpistemologyTopic.keyword = "formal_systems";
				//formalSystemsTopic.name = "Formal System";
				formalEpistemologyTopic.text = "Formal system:  notions of formal language, inference rule, premise, axiom and theorem.";
				formalEpistemologyTopic.save();
				*/
				
				// 1_1_1 Computer tools for exploration and formalization
				Curriculumlesson lesson1_1_1 = new Curriculumlesson();
				lesson1_1_1.keyword = "1_1_1_computer_tools";
				lesson1_1_1.name = "1.1.1 Computer tools for exploration and formalization";
				lesson1_1_1.text = "";
				//lesson1_1_1.curriculumtopics.add(tp1);
				//tp1.save();
				lesson1_1_1.save();
				
				// 1_1_2 Computer assisted human interactions
				Curriculumlesson lesson1_1_2 = new Curriculumlesson();
				lesson1_1_2.keyword = "1_1_2_computer_human";
				lesson1_1_2.name = "1.1.2 Computer assisted human interactions";
				lesson1_1_2.text = "";
				//lesson1_1_2.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_1_2.save();
				
				// 1.1 Formal epistemology, experimentation, machine learning
				Curriculummodule module1_1 = new Curriculummodule();
				module1_1.keyword = "1.1_epistemology";
				module1_1.name = "1.1 Formal epistemology, experimentation, machine learning";
				module1_1.text = "";
				module1_1.curriculumlessons.add(lesson1_1_1);
				lesson1_1_1.save();
				module1_1.curriculumlessons.add(lesson1_1_2);
				lesson1_1_2.save();
				module1_1.save();
				
				// Adicionar um módulo a uma categoria
				//questions.curriculummodules.add(module1_1);
				//module1_1.save();
				//questions.save();
				
				
				// 1_2_1 The cascade paradigm
				Curriculumlesson lesson1_2_1 = new Curriculumlesson();
				lesson1_2_1.keyword = "1_2_1_cascade_paradigm";
				lesson1_2_1.name = "1.2.1 The cascade paradigm";
				lesson1_2_1.text = "";
				//lesson1_2_1.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_2_1.save();
				
				// 1_2_2 Random dynamical systems and stochastic bifurcations
				Curriculumlesson lesson1_2_2 = new Curriculumlesson();
				lesson1_2_2.keyword = "1_2_2_random_dynamical_systems";
				lesson1_2_2.name = "1.2.2 Random dynamical systems and stochastic bifurcations";
				lesson1_2_2.text = "";
				//lesson1_2_2.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_2_2.save();
				
				// 1_2_3 Phase transitions, emerging patterns and behavior
				Curriculumlesson lesson1_2_3 = new Curriculumlesson();
				lesson1_2_3.keyword = "1_2_3_phase_transitions";
				lesson1_2_3.name = "1.2.3 Phase transitions, emerging patterns and behavior";
				lesson1_2_3.text = "";
				//lesson1_2_3.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_2_3.save();
							
				// 1_2_4 Space-time scaling in physics and biology
				Curriculumlesson lesson1_2_4 = new Curriculumlesson();
				lesson1_2_4.keyword = "1_2_4_space-time_scaling";
				lesson1_2_4.name = "1.2.4 Space-time scaling in physics and biology";
				lesson1_2_4.text = "";
				//lesson1_2_3.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_2_4.save();
				
				
				// 1_2 Stochastic and multiscale dynamics, instabilities and robustness
				Curriculummodule module1_2 = new Curriculummodule();
				module1_2.keyword = "1_2_multiscale_dynamics";
				module1_2.name = "1.2 Stochastic and multiscale dynamics, instabilities and robustness";
				module1_2.text = "";
				module1_2.curriculumlessons.add(lesson1_2_1);
				lesson1_2_1.save();
				module1_2.curriculumlessons.add(lesson1_2_2);
				lesson1_2_2.save();
				module1_2.curriculumlessons.add(lesson1_2_3);
				lesson1_2_3.save();
				module1_2.curriculumlessons.add(lesson1_2_4);
				lesson1_2_4.save();
				module1_2.save();

				// Adicionar um módulo a uma categoria
				//questions.curriculummodules.add(module1_2);
				//module1_2.save();
				//questions.save();

				
				// 1_3_1 Collective dynamics of homogeneous and/or heterogeneous units
				Curriculumlesson lesson1_3_1 = new Curriculumlesson();
				lesson1_3_1.keyword = "1_3_1_collective_dynamics";
				lesson1_3_1.name = "1.3.1 Collective dynamics of homogeneous and/or heterogeneous units";
				lesson1_3_1.text = "";
				//lesson1_3_1.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_3_1.save();
				
				// 1_3_2 Collective dynamics in heterogeneous environments
				Curriculumlesson lesson1_3_2 = new Curriculumlesson();
				lesson1_3_2.keyword = "1_3_2_collective_dynamics_het_env";
				lesson1_3_2.name = "1.3.2 Collective dynamics in heterogeneous environments";
				lesson1_3_2.text = "";
				//lesson1_3_2.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_3_2.save();
				
				// 1_3_3 Emergence of heterogeneity and differentiation processes, dynamical heterogeneity, information diffusion
				Curriculumlesson lesson1_3_3 = new Curriculumlesson();
				lesson1_3_3.keyword = "1_3_3_emergence_heterogeneity";
				lesson1_3_3.name = "1.3.3 Emergence of heterogeneity and differentiation processes, dynamical heterogeneity, information diffusion";
				lesson1_3_3.text = "";
				//lesson1_3_3.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_3_3.save();
				
				// 1_3 Collective behavior in homogeneous and heterogeneous systems 
				Curriculummodule module1_3 = new Curriculummodule();
				module1_3.keyword = "1_3_collective_behavior";
				module1_3.name = "1.3 Collective behavior in homogeneous and heterogeneous systems";
				module1_3.text = "";
				module1_3.curriculumlessons.add(lesson1_3_1);
				lesson1_3_1.save();
				module1_3.curriculumlessons.add(lesson1_3_2);
				lesson1_3_2.save();
				module1_3.curriculumlessons.add(lesson1_3_3);
				lesson1_3_3.save();
				module1_3.save();
				
				// Adicionar um módulo a uma categoria
				//questions.curriculummodules.add(module1_3);
				//module1_3.save();
				//questions.save();
				
				// 1_4_1 Extending the scope of optimal control
				Curriculumlesson lesson1_4_1 = new Curriculumlesson();
				lesson1_4_1.keyword = "1_4_1_optimal_control";
				lesson1_4_1.name = "1.4.1 Extending the scope of optimal control";
				lesson1_4_1.text = "";
				//lesson1_4_1.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_4_1.save();
				
				// 1_4_2 Projecting complex dynamics into spaces of smaller dimension
				Curriculumlesson lesson1_4_2 = new Curriculumlesson();
				lesson1_4_2.keyword = "1_4_2_complex_dynamics";
				lesson1_4_2.name = "1.4.2 Projecting complex dynamics into spaces of smaller dimension";
				lesson1_4_2.text = "";
				//lesson1_4_2.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_4_2.save();
				
				// 1_4_3 Projecting optimal control into high and multiscale dimension space
				Curriculumlesson lesson1_4_3 = new Curriculumlesson();
				lesson1_4_3.keyword = "1_4_3_optimal_control";
				lesson1_4_3.name = "1.4.3 Projecting optimal control into high and multiscale dimension space";
				lesson1_4_3.text = "";
				//lesson1_4_3.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_4_3.save();
				
				// 1_4_4 Extending exploration / exploitation compromise to problem reformulation
				Curriculumlesson lesson1_4_4 = new Curriculumlesson();
				lesson1_4_4.keyword = "1_4_4_exploration_exploitation";
				lesson1_4_4.name = "1.4.4 Extending exploration / exploitation compromise to problem reformulation";
				lesson1_4_4.text = "";
				//lesson1_4_4.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_4_4.save();
				
				// 1_4_5 Co-adaptation of governance and stakeholders’ objectives
				Curriculumlesson lesson1_4_5 = new Curriculumlesson();
				lesson1_4_5.keyword = "1_4_5_co-adaptation_governance";
				lesson1_4_5.name = "1.4.5 Co-adaptation of governance and stakeholders’ objectives";
				lesson1_4_5.text = "";
				//lesson1_4_5.curriculumtopics.add(tpx);
				//tpx.save();
				lesson1_4_5.save();
				
				// 1_4_5_1 The static dimension: governance in the context of heterogeneity of stakeholders, points of view and interests
				Curriculumtopic topic1_4_5_1= new Curriculumtopic();
				topic1_4_5_1.keyword = "1_4_5_1_static_dimension";
				//topic1_4_5_1.name = "Static Dimension;
				topic1_4_5_1.text = "1.4.5.1 The static dimension: governance in the context of heterogeneity of stakeholders, points of view and interests";
				topic1_4_5_1.save();
				
				// 1_4_5_2 The dynamical dimension: evolution of stakeholders' objectives and viewpoints in the governance process
				Curriculumtopic topic1_4_5_2= new Curriculumtopic();
				topic1_4_5_2.keyword = "1_4_5_2_dynamical_dimension";
				//topic1_4_5_2.name = "Dynamical Dimension;
				topic1_4_5_2.text = "1.4.5.2 The dynamical dimension: evolution of stakeholders' objectives and viewpoints in the governance process";
				topic1_4_5_2.save();
				
				lesson1_4_5.curriculumtopics.add(topic1_4_5_1);
				topic1_4_5_1.save();
				lesson1_4_5.curriculumtopics.add(topic1_4_5_2);
				topic1_4_5_2.save();
				lesson1_4_5.save();

				
				// 1_4 From optimal control to multiscale governance 
				Curriculummodule module1_4 = new Curriculummodule();
				module1_4.keyword = "1_4_from_optimal_control_to_multiscale_governance";
				module1_4.name = "1.4 From optimal control to multiscale governance";
				module1_4.text = "";
				module1_4.curriculumlessons.add(lesson1_4_1);
				lesson1_4_1.save();
				module1_4.curriculumlessons.add(lesson1_4_2);
				lesson1_4_2.save();
				module1_4.curriculumlessons.add(lesson1_4_3);
				lesson1_4_3.save();
				module1_4.curriculumlessons.add(lesson1_4_4);
				lesson1_4_4.save();
				module1_4.curriculumlessons.add(lesson1_4_5);
				lesson1_4_5.save();
				module1_4.save();
				
				// 1.5.1 Building common and pertinent references in the life sciences
				Curriculumlesson lesson1_5_1 = new Curriculumlesson();
				lesson1_5_1.keyword = "1_5_1_building_common_references";
				lesson1_5_1.name = "1.5.1 Building common and pertinent references in the life sciences";
				lesson1_5_1.text = "";
				lesson1_5_1.save();
				
				// 1.5.2 Achieving coherence in the modeling of complex systems
				Curriculumlesson lesson1_5_2 = new Curriculumlesson();
				lesson1_5_2.keyword = "1_5_2_coherence_modeling_complex_systems";
				lesson1_5_2.name = "1.5.2 Achieving coherence in the modeling of complex systems";
				lesson1_5_2.text = "";
				lesson1_5_2.save();		
				
				// 1.5.3 Development of mathematical and computer formalisms for modeling multi-level and multiscales systems
				Curriculumlesson lesson1_5_3 = new Curriculumlesson();
				lesson1_5_3.keyword = "1_5_3_modeling_multi-level_systems";
				lesson1_5_3.name = "1.5.3 Development of mathematical and computer formalisms for modeling multi-level and multiscales systems";
				lesson1_5_3.text = "";
				lesson1_5_3.save();				
				
				// 1_5 Reconstruction of multiscale dynamics, emergence and immergence processes
				Curriculummodule module1_5 = new Curriculummodule();
				module1_5.keyword = "1_5_multiscale_dynamics";
				module1_5.name = "1.5 Reconstruction of multiscale dynamics, emergence and immergence processes";
				module1_5.text = "";
				module1_5.curriculumlessons.add(lesson1_5_1);
				lesson1_5_1.save();
				module1_5.curriculumlessons.add(lesson1_5_2);
				lesson1_5_2.save();
				module1_5.curriculumlessons.add(lesson1_5_3);
				lesson1_5_3.save();
				module1_5.save();

				// 1.6.1 Using artificial complex systems for the understanding and regulation of natural complex systems
				Curriculumlesson lesson1_6_1 = new Curriculumlesson();
				lesson1_6_1.keyword = "1_6_1_using_artificial_systems";
				lesson1_6_1.name = "1.6.1 Using artificial complex systems for the understanding and regulation of natural complex systems";
				lesson1_6_1.text = "";
				lesson1_6_1.save();		
				
				// 1.6.2 Finding inspiration in natural complex systems for the design of artificial complex systems
				Curriculumlesson lesson1_6_2 = new Curriculumlesson();
				lesson1_6_2.keyword = "1_6_2_finding_inspiration";
				lesson1_6_2.name = "1.6.2 Finding inspiration in natural complex systems for the design of artificial complex systems";
				lesson1_6_2.text = "";
				lesson1_6_2.save();
				
				// 1.6.3 Building hybrid complex systems
				Curriculumlesson lesson1_6_3 = new Curriculumlesson();
				lesson1_6_3.keyword = "1_6_3_building_hybrid_complex_systems";
				lesson1_6_3.name = "1.6.3 Building hybrid complex systems";
				lesson1_6_3.text = "";
				lesson1_6_3.save();
				
				// 1.6 Designing artificial complex systems
				Curriculummodule module1_6 = new Curriculummodule();
				module1_6.keyword = "1_6_designing_artificial_complex_systems";
				module1_6.name = "1.6 Designing artificial complex systems";
				module1_6.text = "";
				module1_6.curriculumlessons.add(lesson1_6_1);
				lesson1_6_1.save();
				module1_6.curriculumlessons.add(lesson1_6_2);
				lesson1_6_2.save();
				module1_6.curriculumlessons.add(lesson1_6_3);
				lesson1_6_3.save();
				module1_6.save();
				
				// 1.7.1 Coordinating huge open-access databases
				Curriculumlesson lesson1_7_1 = new Curriculumlesson();
				lesson1_7_1.keyword = "1_7_1_open-access_databases";
				lesson1_7_1.name = "1.7.1 Coordinating huge open-access databases";
				lesson1_7_1.text = "";
				lesson1_7_1.save();
				
				// 1.7.2 Data security and privacy – understanding reputation and trust dynamics 
				Curriculumlesson lesson1_7_2 = new Curriculumlesson();
				lesson1_7_2.keyword = "1_7_2_data_security_privacy";
				lesson1_7_2.name = "1.7.2 Data security and privacy – understanding reputation and trust dynamics";
				lesson1_7_2.text = "";
				lesson1_7_2.save();
				
				// 1.7.3 The Ultimate Google: new methods of search and data synthesis 
				Curriculumlesson lesson1_7_3 = new Curriculumlesson();
				lesson1_7_3.keyword = "1_7_3_ultimate_google";
				lesson1_7_3.name = "1.7.3 The Ultimate Google: new methods of search and data synthesis";
				lesson1_7_3.text = "";
				lesson1_7_3.save();
				
				// 1.7.4 New computational architectures for massively parallel and distributed computation 
				Curriculumlesson lesson1_7_4 = new Curriculumlesson();
				lesson1_7_4.keyword = "1_7_4_parallel_distributed_computation";
				lesson1_7_4.name = "1.7.4 New computational architectures for massively parallel and distributed computation";
				lesson1_7_4.text = "";
				lesson1_7_4.save();
				
				// 1.7.5 Self-configuring self-repairing ICT systems 
				Curriculumlesson lesson1_7_5 = new Curriculumlesson();
				lesson1_7_5.keyword = "1_7_5_self-repairing_ict";
				lesson1_7_5.name = "1.7.5 Self-configuring self-repairing ICT systems";
				lesson1_7_5.text = "";
				lesson1_7_5.save();
				
				// 1.7.6 The dynamics of computation – sequential system dynamics 
				Curriculumlesson lesson1_7_6 = new Curriculumlesson();
				lesson1_7_6.keyword = "1_7_6_sequential_system_dynamics";
				lesson1_7_6.name = "1.7.6 The dynamics of computation – sequential system dynamics";
				lesson1_7_6.text = "";
				lesson1_7_6.save();
				
				// 1.7 Petascale Computing
				Curriculummodule module1_7 = new Curriculummodule();
				module1_7.keyword = "1_7_petascale_computing";
				module1_7.name = "1.7 Petascale Computing";
				module1_7.text = "";
				module1_7.curriculumlessons.add(lesson1_7_1);
				lesson1_7_1.save();
				module1_7.curriculumlessons.add(lesson1_7_2);
				lesson1_7_2.save();
				module1_7.curriculumlessons.add(lesson1_7_3);
				lesson1_7_3.save();
				module1_7.curriculumlessons.add(lesson1_7_4);
				lesson1_7_4.save();
				module1_7.curriculumlessons.add(lesson1_7_5);
				lesson1_7_5.save();
				module1_7.curriculumlessons.add(lesson1_7_6);
				lesson1_7_6.save();
				module1_7.save();
				
				// 1.8.1 New statistical theories for predication, experimentation and testing in complex systems
				Curriculumlesson lesson1_8_1 = new Curriculumlesson();
				lesson1_8_1.keyword = "1_8_1_new_statistical_theories";
				lesson1_8_1.name = "1.8.1 New statistical theories for predication, experimentation and testing in complex systems";
				lesson1_8_1.text = "";
				lesson1_8_1.save();
				
				// 1.8.2 New mathematics for representing huge heterogeneous multilevel dynamics
				Curriculumlesson lesson1_8_2 = new Curriculumlesson();
				lesson1_8_2.keyword = "1_8_2_heterogeneous_multilevel_dynamics";
				lesson1_8_2.name = "1.8.2 New mathematics for representing huge heterogeneous multilevel dynamics";
				lesson1_8_2.text = "";
				lesson1_8_2.save();
				
				// 1.8.3 From phenomenology to scientific theory: from correlation to entailment
				Curriculumlesson lesson1_8_3 = new Curriculumlesson();
				lesson1_8_3.keyword = "1_8_3_from_phenomenology_to_scientific_theory";
				lesson1_8_3.name = "1.8.3 From phenomenology to scientific theory: from correlation to entailment";
				lesson1_8_3.text = "";
				lesson1_8_3.save();
				
				// 1.8.4 Logics and metalogics for integrative science
				Curriculumlesson lesson1_8_4 = new Curriculumlesson();
				lesson1_8_4.keyword = "1_8_4_logics_and_metalogics";
				lesson1_8_4.name = "1.8.4 Logics and metalogics for integrative science";
				lesson1_8_4.text = "";
				lesson1_8_4.save();
				
				// 1.8.5 Creating demonstrators of complex systems science and its applications
				Curriculumlesson lesson1_8_5 = new Curriculumlesson();
				lesson1_8_5.keyword = "1_8_5_demonstrators";
				lesson1_8_5.name = "1.8.5 Creating demonstrators of complex systems science and its applications";
				lesson1_8_5.text = "";
				lesson1_8_5.save();
				
				// 1.8.6 Devising new methods for rapid synthesis of heterogeneous domain-based science
				Curriculumlesson lesson1_8_6 = new Curriculumlesson();
				lesson1_8_6.keyword = "1_8_6_synthesis_of_heterogeneous_science";
				lesson1_8_6.name = "1.8.6 Devising new methods for rapid synthesis of heterogeneous domain-based science";
				lesson1_8_6.text = "";
				lesson1_8_6.save();
				
				// 1.8.7 Devising new methods for information aggregation and processing, and social learning
				Curriculumlesson lesson1_8_7 = new Curriculumlesson();
				lesson1_8_7.keyword = "1_8_7_information_aggregation";
				lesson1_8_7.name = "1.8.7 Devising new methods for information aggregation and processing, and social learning";
				lesson1_8_7.text = "";
				lesson1_8_7.save();
				
				// 1.8.8 New theories of risk and dynamical models of extreme events
				Curriculumlesson lesson1_8_8 = new Curriculumlesson();
				lesson1_8_8.keyword = "1_8_8_models_of_extreme_events";
				lesson1_8_8.name = "1.8.8 New theories of risk and dynamical models of extreme events";
				lesson1_8_8.text = "";
				lesson1_8_8.save();
				
				// 1.8 Formal Aspects of Complex Systems Science
				Curriculummodule module1_8 = new Curriculummodule();
				module1_8.keyword = "1_8_formal_aspects";
				module1_8.name = "1.8 Formal Aspects of Complex Systems Science";
				module1_8.text = "";
				module1_8.curriculumlessons.add(lesson1_8_1);
				lesson1_8_1.save();
				module1_8.curriculumlessons.add(lesson1_8_2);
				lesson1_8_2.save();
				module1_8.curriculumlessons.add(lesson1_8_3);
				lesson1_8_3.save();
				module1_8.curriculumlessons.add(lesson1_8_4);
				lesson1_8_4.save();
				module1_8.curriculumlessons.add(lesson1_8_5);
				lesson1_8_5.save();
				module1_8.curriculumlessons.add(lesson1_8_6);
				lesson1_8_6.save();
				module1_8.curriculumlessons.add(lesson1_8_7);
				lesson1_8_7.save();
				module1_8.curriculumlessons.add(lesson1_8_8);
				lesson1_8_8.save();
				module1_8.save();
				
				 				
				// FIM DE QUESTIONS
				// Adicionar os módulos à categoria "Questions"
				questions.curriculummodules.add(module1_1);
				module1_1.save();
				questions.curriculummodules.add(module1_2);
				module1_2.save();
				questions.curriculummodules.add(module1_3);
				module1_3.save();
				questions.curriculummodules.add(module1_4);
				module1_4.save();
				questions.curriculummodules.add(module1_5);
				module1_5.save();
				questions.curriculummodules.add(module1_6);
				module1_6.save();
				questions.curriculummodules.add(module1_7);
				module1_7.save();
				questions.curriculummodules.add(module1_8);
				module1_8.save();
				questions.save();

				
				
				// 2. Objects  ++++++++++++++++++++++++++++++++++++++
				Category objects = new Category();
				objects.name = "2. Objects";
				objects.description="Related to specific objects and vertical domains of study.";
				objects.keyword = "2_objects";
				objects.save();
				
				// 2.1.1 Non-equilibrium statistical physics
				Curriculumlesson lesson2_1_1 = new Curriculumlesson();
				lesson2_1_1.keyword = "2_1_1_n_e_statistical_physics";
				lesson2_1_1.name = "2.1.1 Non-equilibrium statistical physics";
				lesson2_1_1.text = "";
				lesson2_1_1.save();
				
				// 2.1.2. Damage and fracture of heterogeneous materials
				Curriculumlesson lesson2_1_2 = new Curriculumlesson();
				lesson2_1_2.keyword = "2_1_2_heterogeneous_materials";
				lesson2_1_2.name = "2.1.2 Damage and fracture of heterogeneous materials";
				lesson2_1_2.text = "";
				lesson2_1_2.save();
				
				// 2.1.3. Glassy dynamics: glasses, spin glasses and granular media
				Curriculumlesson lesson2_1_3 = new Curriculumlesson();
				lesson2_1_3.keyword = "2_1_3_glassy_dynamics";
				lesson2_1_3.name = "2.1.3 Glassy dynamics: glasses, spin glasses and granular media";
				lesson2_1_3.text = "";
				lesson2_1_3.save();
				
				// 2.1.4. Bifurcations in turbulence: from dynamo action to slow dynamics
				Curriculumlesson lesson2_1_4 = new Curriculumlesson();
				lesson2_1_4.keyword = "2_1_4_bifurcations_in_turbulence";
				lesson2_1_4.name = "2.2.4 Bifurcations in turbulence: from dynamo action to slow dynamics";
				lesson2_1_4.text = "";
				lesson2_1_4.save();
				
				// 2.1 Complex matter
				Curriculummodule module2_1 = new Curriculummodule();
				module2_1.keyword = "2_1_complex_matter";
				module2_1.name = "2.1 Complex matter";
				module2_1.text = "";
				module2_1.curriculumlessons.add(lesson2_1_1);
				lesson2_1_1.save();
				module2_1.curriculumlessons.add(lesson2_1_2);
				lesson2_1_2.save();
				module2_1.curriculumlessons.add(lesson2_1_3);
				lesson2_1_3.save();
				module2_1.curriculumlessons.add(lesson2_1_4);
				lesson2_1_4.save();
				module2_1.save();
				
				// 2.2.1 Fluctuations and noise in biological systems
				Curriculumlesson lesson2_2_1 = new Curriculumlesson();
				lesson2_2_1.keyword = "2_2_1_fluctuations_and_noise";
				lesson2_2_1.name = "2.2.1 Fluctuations and noise in biological systems";
				lesson2_2_1.text = "";
				lesson2_2_1.save();
				
				// 2.2.2 Stability in biology
				Curriculumlesson lesson2_2_2 = new Curriculumlesson();
				lesson2_2_2.keyword = "2_2_2_stability_in_biology";
				lesson2_2_2.name = "2.2.2 Stability in biology";
				lesson2_2_2.text = "";
				lesson2_2_2.save();
				
				// 2.2.3 Multiscaling
				Curriculumlesson lesson2_2_3 = new Curriculumlesson();
				lesson2_2_3.keyword = "2_2_3_multiscaling";
				lesson2_2_3.name = "2.2.3 Multiscaling";
				lesson2_2_3.text = "";
				lesson2_2_3.save();
				
				// 2.2.4 Human physiopathology
				Curriculumlesson lesson2_2_4 = new Curriculumlesson();
				lesson2_2_4.keyword = "2_2_4_human_physiopathology";
				lesson2_2_4.name = "2.2.4 Human physiopathology";
				lesson2_2_4.text = "";
				lesson2_2_4.save();
				
				// 2.2 From molecules to organisms
				Curriculummodule module2_2 = new Curriculummodule();
				module2_2.keyword = "2_2_molecules_to_organisms";
				module2_2.name = "2.2 From molecules to organisms";
				module2_2.text = "";
				module2_2.curriculumlessons.add(lesson2_2_1);
				lesson2_2_1.save();
				module2_2.curriculumlessons.add(lesson2_2_2);
				lesson2_2_2.save();
				module2_2.curriculumlessons.add(lesson2_2_3);
				lesson2_2_3.save();
				module2_2.curriculumlessons.add(lesson2_2_4);
				lesson2_2_4.save();
				module2_2.save();
				
				// 2.3.1 Investigating the relationship between the ontogenesis of a physiological function and its potential disorders
				Curriculumlesson lesson2_3_1 = new Curriculumlesson();
				lesson2_3_1.keyword = "2_3_1_ontogenesis_physiological_function";
				lesson2_3_1.name = "2.3.1 Investigating the relationship between the ontogenesis of a physiological function and its potential disorders";
				lesson2_3_1.text = "";
				lesson2_3_1.save();
				
				// 2.3.2 Characterizing the contextual features determining the onset of operation, maintenance and modulation of a physiological function
				Curriculumlesson lesson2_3_2 = new Curriculumlesson();
				lesson2_3_2.keyword = "2_3_2_contextual_features";
				lesson2_3_2.name = "2.3.2 Characterizing the contextual features determining the onset of operation, maintenance and modulation of a physiological function";
				lesson2_3_2.text = "";
				lesson2_3_2.save();
				
				// 2.3.3 Integrating multimodal measurements and observations of physiological activities at different spatial and temporal scales
				Curriculumlesson lesson2_3_3 = new Curriculumlesson();
				lesson2_3_3.keyword = "2_3_3_multimodal_measurements";
				lesson2_3_3.name = "2.3.3 Integrating multimodal measurements and observations of physiological activities at different spatial and temporal scales";
				lesson2_3_3.text = "";
				lesson2_3_3.save();
				
				// 2.3 Physiological functions
				Curriculummodule module2_3 = new Curriculummodule();
				module2_3.keyword = "2_X_physiological_functions";
				module2_3.name = "2.3 Physiological functions";
				module2_3.text = "";
				module2_3.curriculumlessons.add(lesson2_3_1);
				lesson2_3_1.save();
				module2_3.curriculumlessons.add(lesson2_3_2);
				lesson2_3_2.save();
				module2_3.curriculumlessons.add(lesson2_3_3);
				lesson2_3_3.save();
				module2_3.save();
				
				// 2.4.1 Develop observation and experimental systems for the reconstruction of the long-term dynamics of ecosystems
				Curriculumlesson lesson2_4_1 = new Curriculumlesson();
				lesson2_4_1.keyword = "2_4_1_long-term_dynamics_of_ecosystems";
				lesson2_4_1.name = "2.4.1 Develop observation and experimental systems for the reconstruction of the long-term dynamics of ecosystems";
				lesson2_4_1.text = "";
				lesson2_4_1.save();
				
				// 2.4.2 Model the relationships between biodiversity, functioning and dynamics of the ecosystems
				Curriculumlesson lesson2_4_2 = new Curriculumlesson();
				lesson2_4_2.keyword = "2_4_2_biodiversity_functioning_and_dynamics";
				lesson2_4_2.name = "2.4.2 Model the relationships between biodiversity, functioning and dynamics of the ecosystems";
				lesson2_4_2.text = "";
				lesson2_4_2.save();
				
				// 2.4.3 Associate integrative biology and ecology to decipher evolutionary mechanisms
				Curriculumlesson lesson2_4_3 = new Curriculumlesson();
				lesson2_4_3.keyword = "2_4_3_evolutionary_mechanisms";
				lesson2_4_3.name = "2.4.3 Associate integrative biology and ecology to decipher evolutionary mechanisms";
				lesson2_4_3.text = "";
				lesson2_4_3.save();
				
				// 2.4.4 Simulate virtual landscapes (integration and coupling of biogeochemical and ecological models into dynamic landscape mock-ups)
				Curriculumlesson lesson2_4_4 = new Curriculumlesson();
				lesson2_4_4.keyword = "2_4_4_virtual_landscapes";
				lesson2_4_4.name = "2.4.4 Simulate virtual landscapes (integration and coupling of biogeochemical and ecological models into dynamic landscape mock-ups)";
				lesson2_4_4.text = "";
				lesson2_4_4.save();
				
				// 2.4.5 Design decision-support systems for multifunctional ecosystems
				Curriculumlesson lesson2_4_5 = new Curriculumlesson();
				lesson2_4_5.keyword = "2_4_5_multifunctional_ecosystems";
				lesson2_4_5.name = "2.4.5 Design decision-support systems for multifunctional ecosystems";
				lesson2_4_5.text = "";
				lesson2_4_5.save();
				
				// 2.4 Ecosystemic complexity
				Curriculummodule module2_4 = new Curriculummodule();
				module2_4.keyword = "2_4_ecosystemic_complexity";
				module2_4.name = "2.4 Ecosystemic complexity";
				module2_4.text = "";
				module2_4.curriculumlessons.add(lesson2_4_1);
				lesson2_4_1.save();
				module2_4.curriculumlessons.add(lesson2_4_2);
				lesson2_4_2.save();
				module2_4.curriculumlessons.add(lesson2_4_3);
				lesson2_4_3.save();
				module2_4.curriculumlessons.add(lesson2_4_4);
				lesson2_4_4.save();
				module2_4.curriculumlessons.add(lesson2_4_5);
				lesson2_4_5.save();
				module2_4.save();
				
				// 2.5.1 Individual cognition, cognitive constraints and decision proceses
				Curriculumlesson lesson2_5_1 = new Curriculumlesson();
				lesson2_5_1.keyword = "2_5_1_individual_cognition";
				lesson2_5_1.name = "2.5.1 Individual cognition, cognitive constraints and decision proceses";
				lesson2_5_1.text = "";
				lesson2_5_1.save();
				
				// 2.5.2 Modeling the dynamics of scientific communities
				Curriculumlesson lesson2_5_2 = new Curriculumlesson();
				lesson2_5_2.keyword = "2_5_2_dynamics_of_scientific_communities";
				lesson2_5_2.name = "2.5.2 Modeling the dynamics of scientific communities";
				lesson2_5_2.text = "";
				lesson2_5_2.save();
				
				// 2.5.3 Society of the Internet, Internet of the society
				Curriculumlesson lesson2_5_3 = new Curriculumlesson();
				lesson2_5_3.keyword = "2_5_3_society_of_the_internet";
				lesson2_5_3.name = "2.5.3 Society of the Internet, Internet of the society";
				lesson2_5_3.text = "";
				lesson2_5_3.save();
				
				// 2.5 From individual cognition to social cognition
				Curriculummodule module2_5 = new Curriculummodule();
				module2_5.keyword = "2_5_individual_social_cognition";
				module2_5.name = "2.5 From individual cognition to social cognition";
				module2_5.text = "";
				module2_5.curriculumlessons.add(lesson2_5_1);
				lesson2_5_1.save();
				module2_5.curriculumlessons.add(lesson2_5_2);
				lesson2_5_2.save();
				module2_5.curriculumlessons.add(lesson2_5_3);
				lesson2_5_3.save();
				module2_5.save();
				
				// 2.6.1 Understanding dynamic conditions of innovation
				Curriculumlesson lesson2_6_1 = new Curriculumlesson();
				lesson2_6_1.keyword = "2_X_X_dynamic_conditions_of_innovation";
				lesson2_6_1.name = "2.6.1 Understanding dynamic conditions of innovation";
				lesson2_6_1.text = "";
				lesson2_6_1.save();
				
				// 2.6.2 Modeling innovations and their rhythms
				Curriculumlesson lesson2_6_2 = new Curriculumlesson();
				lesson2_6_2.keyword = "2_X_X_innovations_and_their_rhythms";
				lesson2_6_2.name = "2.6.2 Modeling innovations and their rhythms";
				lesson2_6_2.text = "";
				lesson2_6_2.save();
				
				// 2.6.3 Understanding the relation between cognition and innovation
				Curriculumlesson lesson2_6_3 = new Curriculumlesson();
				lesson2_6_3.keyword = "2_6_3_cognition_innovation";
				lesson2_6_3.name = "2.6.3 Understanding the relation between cognition and innovation";
				lesson2_6_3.text = "";
				lesson2_6_3.save();
				
				// 2.6 Innovation, learning and co-evolution
				Curriculummodule module2_6 = new Curriculummodule();
				module2_6.keyword = "2_6_innovation_learning_co-evolution";
				module2_6.name = "2.6 Innovation, learning and co-evolution";
				module2_6.text = "";
				module2_6.curriculumlessons.add(lesson2_6_1);
				lesson2_6_1.save();
				module2_6.curriculumlessons.add(lesson2_6_2);
				lesson2_6_2.save();
				module2_6.curriculumlessons.add(lesson2_6_3);
				lesson2_6_3.save();
				module2_6.save();
				
				// 2.7.1 Understanding territorial differentiation
				Curriculumlesson lesson2_7_1 = new Curriculumlesson();
				lesson2_7_1.keyword = "2_7_1_territorial_differentiation";
				lesson2_7_1.name = "2.7.1 Understanding territorial differentiation";
				lesson2_7_1.text = "";
				lesson2_7_1.save();

				// 2.7.2 Towards a reflexive territorial governance
				Curriculumlesson lesson2_7_2 = new Curriculumlesson();
				lesson2_7_2.keyword = "2_7_2_reflexive_territorial_governance";
				lesson2_7_2.name = "2.7.2 Towards a reflexive territorial governance";
				lesson2_7_2.text = "";
				lesson2_7_2.save();
				
				// 2.7.3 Viability and observation of territories
				Curriculumlesson lesson2_7_3 = new Curriculumlesson();
				lesson2_7_3.keyword = "2_7_3_territories";
				lesson2_7_3.name = "2.7.3 Viability and observation of territories";
				lesson2_7_3.text = "";
				lesson2_7_3.save();
				
				// 2.7 Territorial intelligence and sustainable development
				Curriculummodule module2_7 = new Curriculummodule();
				module2_7.keyword = "2_7_sustainable_development";
				module2_7.name = "2.7 Territorial intelligence and sustainable development";
				module2_7.text = "";
				module2_7.curriculumlessons.add(lesson2_7_1);
				lesson2_7_1.save();
				module2_7.curriculumlessons.add(lesson2_7_2);
				lesson2_7_2.save();
				module2_7.curriculumlessons.add(lesson2_7_3);
				lesson2_7_3.save();
				module2_7.save();
				
				// 2.8.1 Local design for global properties (routing, control, confidentiality)
				Curriculumlesson lesson2_8_1 = new Curriculumlesson();
				lesson2_8_1.keyword = "2_8_1_local_design_for_global_properties";
				lesson2_8_1.name = "2.8.1 Local design for global properties (routing, control, confidentiality)";
				lesson2_8_1.text = "";
				lesson2_8_1.save();
				
				// 2.8.2 Autonomic Computing (robustness, redundancy, fault tolerance)
				Curriculumlesson lesson2_8_2 = new Curriculumlesson();
				lesson2_8_2.keyword = "2_8_2_autonomic_computing";
				lesson2_8_2.name = "2.8.2 Autonomic Computing (robustness, redundancy, fault tolerance)";
				lesson2_8_2.text = "";
				lesson2_8_2.save();
				
				// 2.8.3 New computational models: distributing processing and storage, fusion of spatial, temporal and/or multi-modal data, abstraction emergence
				Curriculumlesson lesson2_8_3 = new Curriculumlesson();
				lesson2_8_3.keyword = "2_8_3_new_computational_models";
				lesson2_8_3.name = "2.8.3 New computational models: distributing processing and storage, fusion of spatial, temporal and/or multi-modal data, abstraction emergence";
				lesson2_8_3.text = "";
				lesson2_8_3.save();
				
				// 2.8.4 New programming paradigms: creation and grounding of symbols (including proof and validation)
				Curriculumlesson lesson2_8_4 = new Curriculumlesson();
				lesson2_8_4.keyword = "2_8_4_new_programming_paradigms";
				lesson2_8_4.name = "2.8.4 New programming paradigms: creation and grounding of symbols (including proof and validation)";
				lesson2_8_4.text = "";
				lesson2_8_4.save();
				
				// 2.8. Ubiquitous computing
				Curriculummodule module2_8 = new Curriculummodule();
				module2_8.keyword = "2_8_ubiquitous_computing";
				module2_8.name = "2.8 Ubiquitous computing";
				module2_8.text = "";
				module2_8.curriculumlessons.add(lesson2_8_1);
				lesson2_8_1.save();
				module2_8.curriculumlessons.add(lesson2_8_2);
				lesson2_8_2.save();
				module2_8.curriculumlessons.add(lesson2_8_3);
				lesson2_8_3.save();
				module2_8.curriculumlessons.add(lesson2_8_4);
				lesson2_8_4.save();
				module2_8.save();
								
				// 2.9.1 Understanding and reducing uncertainties
				Curriculumlesson lesson2_9_1 = new Curriculumlesson();
				lesson2_9_1.keyword = "2_9_1_understanding_and_reducing_uncertainties";
				lesson2_9_1.name = "2.9.1 Understanding and reducing uncertainties";
				lesson2_9_1.text = "";
				lesson2_9_1.save();
				
				// 2.9.2 Out-of-equilibrium statistical physics of the Earth system
				Curriculumlesson lesson2_9_2 = new Curriculumlesson();
				lesson2_9_2.keyword = "2_9_2_statistical_physics_of_the_earth_system";
				lesson2_9_2.name = "2.9.2 Out-of-equilibrium statistical physics of the Earth system";
				lesson2_9_2.text = "";
				lesson2_9_2.save();
				
				// 2.9.3 Geoscience, the Environment, Policy and Citizens
				Curriculumlesson lesson2_9_3 = new Curriculumlesson();
				lesson2_9_3.keyword = "2_9_3_geoscience_environment_policy_citizens";
				lesson2_9_3.name = "2.9.3 Geoscience, the Environment, Policy and Citizens";
				lesson2_9_3.text = "";
				lesson2_9_3.save();
				
				// 2.9. Geosciences and the environment
				Curriculummodule module2_9 = new Curriculummodule();
				module2_9.keyword = "2_9_geosciences_and_the_environment";
				module2_9.name = "2.9. Geosciences and the environment";
				module2_9.text = "";
				module2_9.curriculumlessons.add(lesson2_9_1);
				lesson2_9_1.save();
				module2_9.curriculumlessons.add(lesson2_9_2);
				lesson2_9_2.save();
				module2_9.curriculumlessons.add(lesson2_9_3);
				lesson2_9_3.save();
				module2_9.save();
				
				
				// FIM DE OBJECTS
				// Adicionar os módulos à categoria "Objects"
				objects.curriculummodules.add(module2_1);
				module2_1.save();
				objects.curriculummodules.add(module2_2);
				module2_2.save();
				objects.curriculummodules.add(module2_3);
				module2_3.save();
				objects.curriculummodules.add(module2_4);
				module2_4.save();
				objects.curriculummodules.add(module2_5);
				module2_5.save();
				objects.curriculummodules.add(module2_6);
				module2_6.save();
				objects.curriculummodules.add(module2_7);
				module2_7.save();
				objects.curriculummodules.add(module2_8);
				module2_8.save();
				objects.curriculummodules.add(module2_9);
				module2_9.save();
				objects.save();
				
				
				
				// 3. Education, Training and Professional Practice ++++++++++++++++++++++++++++++++++++++
				Category education = new Category();
				education.name = "3. Education";
				education.description="Education, training and professional practice in science.";
				education.keyword = "education";
				education.save();
				
				// 3.1 Education and Training
				Curriculummodule module3_1 = new Curriculummodule();
				module3_1.keyword = "3_1_education_and_training";
				module3_1.name = "3.1 Education and Training";
				module3_1.text = "";
				module3_1.save();
				
				// 3.2 Professional Practice
				Curriculummodule module3_2 = new Curriculummodule();
				module3_2.keyword = "3_2_professional_practice";
				module3_2.name = "3.2 Professional Practice";
				module3_2.text = "";
				module3_2.save();
				
				// FIM DE EDUCATION
				// Adicionar os módulos à categoria "Education"
				education.curriculummodules.add(module3_1);
				module3_1.save();
				education.curriculummodules.add(module3_2);
				module3_2.save();
				education.save();
				
			
				// Ligar uma questão a um módulo
				//q.subtopic = algomodule;
				//q.save();
			
			/*
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
			*/
			
			

			// CURRICULUM
			createCurriculumComputerScience(user_rub,q);
			
			// LINK COURSES -> CATEGORIES +++++++++++++++++++++++++++++++++++++
			/*
			module_three.categories.add(soccat);
			module_three.save();
			soccat.save();
			*/
			
			
			URL url1= new URL();
			url1.adress="http://www.google.com";
			url1.description="A missão da Google é organizar a informação do mundo e torná-la universalmente acessível e útil.";
			url1.imageURL="http://icons.iconarchive.com/icons/graphics-vibe/simple-rounded-social/256/google-icon.png";
			url1.name="Google";
			url1.added=new DateTime();
			url1.likes=9;
			url1.question=q;
			url1.user=user_rub;
			url1.save();
			
			
			URL url2= new URL();
			url2.adress="http://www.twitter.com/";
			url2.description="A forma mais rápida e simples de ficar perto de tudo que você gosta.";
			url2.imageURL="http://cdn1.iconfinder.com/data/icons/yooicons_set01_socialbookmarks/512/social_twitter_box_blue.png";
			url2.name="Twitter";
			url2.added=new DateTime();
			url2.likes=53;
			url2.question=q;
			url2.user=user_rub;
			url2.save();
			
			URL url3= new URL();
			url3.adress="http://www.facebook.com/";
			url3.description="O Facebook é uma aplicação social que liga pessoas a amigos e a outros com quem trabalham, estudam ou vivem.";
			url3.imageURL="http://2.bp.blogspot.com/-52-7unEYeD0/TWr7rccZ_KI/AAAAAAAAAhk/qOqfeFD8FzM/s1600/Facebook-icon.png";
			url3.name="Facebook";
			url3.added=new DateTime();
			url3.likes=50;
			url3.question=q;
			url3.user=user_rub;
			url3.save();
			
			URL url4= new URL();
			url4.adress="http://www.sapo.pt/";
			url4.description="Portal com directório que usa o Open Directory Project.";
			url4.imageURL="https://www.lusoaloja.pt/images/sapo.png";
			url4.name="Sapo";
			url4.added=new DateTime();
			url4.question=q;
			url4.user=user_rub;
			url4.save();
			
			URL url5= new URL();
			url5.adress="http://www.paypal.com/";
			url5.description="Send money and shop online. Shop securely without revealing your credit card or bank account information; Pay conveniently and quickly when you shop online.";
			url5.imageURL="http://www.stoneage.pt/content/tiny_images/Paypal_stoneage%20technology.JPG";
			url5.name="Paypal";
			url5.added=new DateTime();
			url5.likes=23;
			url5.question=q;
			url5.user=user_rub;
			url5.save();
			
			URL url6= new URL();
			url6.adress="http://www.amazon.co.uk/";
			url6.description="Online retailer of books, movies, music and games along with electronics, toys, apparel, sports, tools, groceries and general home and garden items.";
			url6.imageURL="http://www.eastbayexpress.com/binary/2449/1347917999-amazon.jpg";
			url6.name="Amazon";
			url6.added=new DateTime();
			url6.likes=100;
			url6.question=q;
			url6.user=user_rub;
			url6.save();
		}
	}

	
	private void createSecondTest(Test test, Lesson lesson) {
		QuestionGroup g1 = new QuestionGroup();

		
		Question q1 = new Question();
		q1.typeOfQuestion = 0;
		q1.question = "What is Summation in Math?";
		q1.number=1;
		q1.lesson = lesson;
		q1.weight = 50;
		q1.weightToLose = 25;
		q1.keywords = "math summation";
		q1.save();
		
		Question q2 = new Question();
		q2.typeOfQuestion = 1;
		q2.question = "What is the symbol '+' in Math?";
		q2.number= 2;
		q2.lesson = lesson;
		q2.weight = 50;
		q2.weightToLose = 25;
		q2.keywords = "math summation";
		q2.save();
		
		Hypothesis h1 = new Hypothesis();
		h1.question = q2;
		h1.number = 0;
		h1.text = "-";
		h1.save();
		
		Hypothesis h2 = new Hypothesis();
		h2.question = q2;
		h2.number = 1;
		h2.text = "+";
		h2.isCorrect = true;
		h2.save();
		
		g1.number = 1;
		g1.question = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce lobortis semper felis a laoreet.";
		g1.test = test;
		g1.questions.add(q1);
		g1.questions.add(q2);
		g1.save();
		
	}


	// 5. CURRICULUM COMPUTER SCIENCE ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	private void createCurriculumComputerScience(User user_rub, Question q) {
				
		/* */
		/*
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
		
		q.subtopic = algomodule;
		q.save();
		
		comcat.curriculummodules.add(algomodule);
		algomodule.save();
		comcat.save();	
		*/
	
		
		
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");

	}
}

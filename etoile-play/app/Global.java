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
			
			Module module_demo = new Module();
			module_demo.name = "Demo for testing the platform";
			module_demo.acronym="demo1";
			module_demo.imageURL = "http://www.naomiture.com/wp-content/uploads/2010/12/video-shoplocal-play.jpg";
			module_demo.videoURL = "http://www.youtube.com/v/tvN1dp0qVcY";
			module_demo.description = "This module demonstrates all the main functionalities of the Étoile Platform, both for professors and students. The module includes 2 lessons, one in mathematics and another in social sciences. Both lessons have tests, with different kind of questions. The contents of this module are only for demonstration, without any sort of pedagogical goal or coherence.";
			module_demo.lessons.add(lesson_demo_one);
			module_demo.lessons.add(lesson_one);
			module_demo.lessons.add(lesson_two);
			module_demo.language = l_two;
			module_demo.save();
			
			Forum forum_one = new Forum();
			forum_one.module=module_demo;
			forum_one.save();
			module_demo.forum=forum_one;
			module_demo.save();
			
			
			
			
			
			
			lesson_one.module = module_demo;
			lesson_one.save();
			
			lesson_two.module = module_demo;
			lesson_two.save();
			
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
			
			Module module_three = new Module();
			module_three.name = "Non-Equilibrium Social Science";
			module_three.acronym="ness";
			module_three.imageURL = "http://img138.imageshack.us/img138/9061/nesslogounequilibriumve.jpg";
			module_three.description = "This module introduces the main concepts in Non-Equilibrium Social Science.";
			module_three.language = l_two;
			module_three.save();
			
			Forum forum = new Forum();
			forum.module=module_three;
			forum.save();
			module_three.forum=forum;
			module_three.save();
			
			Module module_four = new Module();
			module_four.name = "Network Science";
			module_four.acronym="netsci_01";
			module_four.imageURL = "http://herd.typepad.com/.a/6a00d83451e1dc69e2011278fba78928a4-pi";
			module_four.videoURL = "http://www.youtube.com/v/10oQMHadGos ";
			module_four.description = "This module presents the State-of-the-Art in Network Science, including both theory and examples. ";
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
			
			
			 forum = new Forum();
			forum.module=module_four;
			forum.save();
			module_four.forum=forum;
			module_four.save();
			
			lesson_net1.module = module_four;
			lesson_net1.save();
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
			
			Module module_five = new Module();
			module_five.name = "Bioinformatics";
			module_five.acronym="bioinformatics";
			module_five.imageURL = "http://www.sussex.ac.uk/Users/andywu/gallery/rm_f.gif";
			module_five.description = "<p>Attractors and robustness in Boolean automata networks. </p> <p>Application to living systems.</p>";
			module_five.description = "Attractors and robustness in Boolean automata networks. Application to living systems.";
			module_five.language = l_two;
			module_five.save();
			
			forum = new Forum();
			forum.module=module_five;
			forum.save();
			module_five.forum=forum;
			module_five.save();
			
			Module module_six = new Module();
			module_six.name = "Réseaux génétiques et morphodynamique cellulaire";
			module_six.acronym="resgen";
			module_six.imageURL = "http://www.icra.ca/home.nsf/pages/reseaux-genetiques/$file/Genetic_Networks.jpg";
			module_demo.videoURL = "";
			module_six.description = "Etude comparée du rôle des structures médianes de l'embryon (mésoderme et plaque préchordale), et des signaux qui en dépendent dans la régionalisation précoce du tube neural. Analyse de dynamique spatiale et temporelle de la morphogénèse cellulaire au cours de la formation du tube neural.";
			module_six.language = l_three;
			module_six.save();
					
			forum = new Forum();
			forum.module=module_six;
			forum.save();
			module_six.forum=forum;
			module_six.save();
			
			
			// Bibliography +++++++++++++++++++++++++++++++++++++++++
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
			
			bibliography = new Bibliography();
			bibliography.title="The Zebrafish Book a Guide for the Laboratory Use of Zebrafish Danio* (Brachydanio) Rerio, 5th Edition";
			bibliography.link="http://zebrafish.org/zirc/orders/buyBookQ.php?item=Book&id=book&detail=The%20Zebrafish%20Book";
			bibliography.description="A guide for the laboratory use of zebrafish.";
			bibliography.imageURL="http://zebrafish.org/zirc/images/zfishbook.png";
			bibliography.module=module_six;
			bibliography.save();
			
			
			
			
			// CONTENT FOR COURSES ++++++++++++++++++++++++++++++
			
			Content content;
			
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
			
			/* 
			content = new Content();
			content.module=module_demo;
			content.title="About the Professor";
			content.text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas suscipit auctor velit sit amet consequat. In turpis augue, scelerisque a malesuada euismod, mollis eu enim. Maecenas sapien tellus, vehicula non aliquet id, pretium commodo risus. Donec enim augue, lacinia in varius quis, luctus a nibh. Fusce pretium viverra neque, ac congue tortor pretium et.";
			content.save();
			*/
			
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
			
			content= new Content();
			content.module=module_six;
			content.title="This module is not ready yet, please return soon.";
			content.text="Most of this module will be accessible to non-technical students. We will provide optional materials for those with some computer science background.";
			content.save();
			
			
			// USERS ++++++++++++++++++++++++++++++++++++++++++++
			Student student = new Student();
			student.acronym = "rpaixao";
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
			student.date_of_birth = new Date();
			student.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus accumsan libero non enim commodo egestas. Quisque semper dapibus augue aliquam semper. Sed laoreet vestibulum condimentum.";
			student.male = true;
			student.CSSId = -1;
			student.shortdescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus accumsan libero non enim commodo egestas";
			student.address = "Av. Forças Armadas - 4851-152 Lisboa";
			student.degree = "Master in telecomunications";
			student.save();
			
			
			User user = new User();
			user.studentProfile=student;
			user.email = "rui@rui.pt";
			user.username="rui";
			user.password = sha1.parseSHA1Password("123");
			user.name = "Rui Lopes da Silva";
			user.account_type = 0;
			user.country = "PT";
			user.globalReputation = new Long(0);
//			user.modules.add(module);
			//user.modules.add(module_two);
//			module.save();
			//module_two.save();
			user.modules.add(module_demo);
			user.save();
			
			Modulescore modulescore= new Modulescore();
			modulescore.module=module_demo;
			modulescore.user=user;
			modulescore.score=0;
			modulescore.save();
			
			module_demo.users.add(user);
			module_demo.save();
			
			
			
			Usertopic usertopic = new Usertopic();
			usertopic.user=user;
			usertopic.save();
			
			Topic topic= new Topic();
			topic.forum=forum_one;
			topic.starter=user;
			topic.title="First Topic";
			topic.date=new Date();
			topic.topicsubscriptions.add(usertopic);
			topic.save();
			
			usertopic.topic=topic;
			usertopic.save();
			
			Reply reply = new Reply();
			reply.text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quis tellus libero, nec suscipit elit. Pellentesque vehicula felis nec ipsum dignissim imperdiet. Nulla facilisi. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec condimentum, diam et rutrum ultricies, justo elit tempor elit, at tempor sapien tellus ac erat. Phasellus tempus fermentum ullamcorper. Quisque lacinia lorem eu risus aliquet commodo. Aliquam leo enim, scelerisque sed euismod ut, porttitor at lorem. Vestibulum euismod convallis tempor. Aliquam lacus libero, vehicula id porta nec, dignissim vel elit. Curabitur et dolor vel nisi mattis blandit. Nullam ornare lorem eu dui tempor luctus.";
			reply.user=user;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
			user.replies.add(reply);
			user.save();
			
			reply = new Reply();
			reply.text="In hac habitasse platea dictumst. Quisque non nisl bibendum lectus luctus mollis. Donec hendrerit ullamcorper tortor dignissim mollis. Pellentesque a arcu eget sapien ultrices scelerisque ac ut augue. Praesent arcu magna, euismod nec rutrum nec, eleifend vitae tellus. Proin posuere suscipit blandit. In hac habitasse platea dictumst. Nam convallis nunc a dui dapibus in vulputate lectus vulputate. Suspendisse nec ligula leo, at pellentesque ligula. Maecenas consectetur metus eget sem consequat et vehicula ipsum pharetra. Nulla vel diam nisl, in aliquet felis. Cras non tempor justo. Donec eleifend ligula nec odio mollis vitae adipiscing elit congue. Curabitur ut turpis quis lorem malesuada ultricies. Sed magna mauris, consequat vel tincidunt non, ullamcorper ut sapien. Duis ut lacus quis dui condimentum venenatis eget id lorem.";
			reply.user=user_rub;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
			user_rub.replies.add(reply);
			user_rub.save();
			topic.replies.add(reply);
			topic.save();
			
			reply = new Reply();
			reply.text="Praesent ut neque nec turpis lobortis pretium a vel leo. Sed viverra varius justo. Phasellus commodo nibh ut erat sollicitudin convallis. Duis lectus tortor, sagittis ultrices viverra eu, porta vel justo. In lobortis, tortor et dapibus pellentesque, mauris orci placerat libero, at posuere felis turpis vel elit. Donec tellus massa, condimentum in placerat ac, consectetur consequat nulla. Maecenas at odio a arcu consequat luctus. Integer id lectus lorem, quis aliquet magna. Integer ornare, massa a elementum viverra, tortor lectus interdum arcu, laoreet dapibus augue risus egestas metus. Vestibulum vel purus nec dolor ullamcorper venenatis a quis purus. Suspendisse dictum eleifend augue quis ultrices. Integer lacinia pulvinar dui non venenatis. Ut convallis tincidunt adipiscing. Donec id orci nec nisi pharetra iaculis sit amet vitae odio. Sed eget magna diam, vel placerat nunc.";
			reply.user=user;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
			user.replies.add(reply);
			user.save();
			topic.replies.add(reply);
			topic.save();
			
			reply = new Reply();
			reply.text="Ut ultrices tristique nisi et mollis. Phasellus nec dolor laoreet risus malesuada aliquam sit amet eget massa. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Praesent condimentum fermentum gravida. Fusce vel sapien massa, vitae tristique nibh. Integer sit amet elementum ante. Cras vel magna in ipsum faucibus convallis. In pellentesque pulvinar quam, nec laoreet sapien dignissim non. Quisque lacinia lorem velit, non dictum lorem.";
			reply.user=user_rub;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
			user_rub.replies.add(reply);
			user_rub.save();
			topic.replies.add(reply);
			topic.save();
			
			reply = new Reply();
			reply.text="Phasellus vulputate libero sit amet elit tincidunt rhoncus. Donec arcu sem, adipiscing sit amet pretium in, sollicitudin eu orci. Morbi ac auctor tortor. Integer eu felis lacus, in dapibus quam. Curabitur elit elit, pharetra vitae dignissim non, euismod nec dolor. Nunc molestie laoreet ipsum sit amet suscipit. Cras non augue eget est faucibus auctor. Nulla pellentesque, neque ac condimentum scelerisque, odio eros facilisis nisl, in ornare purus nulla convallis lectus. Phasellus non odio nisl. Maecenas varius venenatis diam quis sagittis. Morbi dapibus, nisi ac convallis egestas, nulla leo tincidunt sem, sit amet consequat massa tortor ut libero.";
			reply.user=user;
			reply.topic=topic;
			reply.date=new Date();
			reply.save();
			user.replies.add(reply);
			user.save();
			topic.replies.add(reply);
			topic.save();
			
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
			p.contact="ISCTE - Instituto Universitário de Lisboa Departamento de Ciências e Tecnologias de Informação" +
					" Av. das Forças Armadas" +
					"1649-026" +
					"Lisboa Portugal";
			p.imageURL="http://iscte.pt/~jmal/index_files/image003.jpg";
			p.save();
			
			p.modules.add(module_demo);
			//p.modules.add(module_four);
			p.modules.add(module_three);
			p.save();
			//module.save();
			
			p.contents.add(pc);
			pc.professor=p;
			p.save();
			pc.save();
			
			p.contents.add(pc2);
			pc2.professor=p;
			p.save();
			pc2.save();
			
			// 2. PROFESSOR FOR TESTING
			
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
			
			
			
			
			// 3. Nadine Peyriéras
			
			ProfessorContent npc = new ProfessorContent();
			npc.description="Nadine Peyriéras - Institut de Neurobiologie Alfred Fessard, CNRS UPR 3294, Av. de la Terrasse, 91198 Gif-sur-Yvette Cedex, France";
			npc.title="Contact";
			npc.imageURL="http://www.nbcm.cnrs-gif.fr/index_clip_image004.jpg";
			npc.save();
			
			ProfessorContent npc2 = new ProfessorContent();
			npc2.description="http://www.inaf.cnrs-gif.fr/ned/equipe07/accueil_07.html";
			npc2.title="URL";
			npc2.imageURL="";
			npc2.save();
			
			
			Professor np = new Professor();
			np.firstname="Nadine";
			np.lastname="Peyriéras";
			np.acronym="NadineP";
			np.email="";
			np.degree="Professor";
			np.shortdescription="";
			np.contact="";
			np.imageURL="http://public-files.prbb.org/prbb_actual/imatges/nadine.png";
			np.save();
			
			np.modules.add(module_six);
			np.save();
			//module.save();
			
			np.contents.add(npc);
			npc.professor=np;
			np.contents.add(npc2);
			npc2.professor=np;
			np.save();
			npc.save();
			npc2.save();
			
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
			jj.save();
			//module.save();
			
			jj.contents.add(jjc);
			jjc.professor=jj;
			jj.save();
			jjc.save();
			
			
			// Comments  +++++++++++++++++++++++++++++++++++++++++++++++++


			Comment c1 = new Comment();
			c1.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et justo enim. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
			c1.blog = blog;
			c1.user = user;
			c1.date = new Date();
			c1.save();
			
			
			
	
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
			
			University university_fct = new University();
			university_fct.name="FCT";
			university_fct.acronym="fct";
			university_fct.imageURL="http://tele1.dee.fct.unl.pt/mst_2010_2011/figs/logo_quadrado%20JMF.gif";
			university_fct.continent=europe;
			university_fct.save();
			
			student.university = university;
			student.save();

			
			//module.university=university;
			module_three.university=university;
			//module_two.university=university;
			module_four.university=university;
			//module.save();
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
			createCurriculumComputerScience(user_rub,q);
			
			// LINK COURSES -> CATEGORIES +++++++++++++++++++++++++++++++++++++
			
			module_three.categories.add(soccat);
			module_three.save();
			soccat.save();
			
			
			URL url1= new URL();
			url1.adress="http://www.google.com";
			url1.description="A missão da Google é organizar a informação do mundo e torná-la universalmente acessível e útil.";
			url1.imageURL="http://icons.iconarchive.com/icons/graphics-vibe/simple-rounded-social/256/google-icon.png";
			url1.name="Google";
			url1.added=new DateTime();
			url1.likes=9;
			url1.question=q;
			url1.user=user;
			url1.save();
			
			
			URL url2= new URL();
			url2.adress="http://www.twitter.com/";
			url2.description="A forma mais rápida e simples de ficar perto de tudo que você gosta.";
			url2.imageURL="http://cdn1.iconfinder.com/data/icons/yooicons_set01_socialbookmarks/512/social_twitter_box_blue.png";
			url2.name="Twitter";
			url2.added=new DateTime();
			url2.likes=53;
			url2.question=q;
			url2.user=user;
			url2.save();
			
			URL url3= new URL();
			url3.adress="http://www.facebook.com/";
			url3.description="O Facebook é uma aplicação social que liga pessoas a amigos e a outros com quem trabalham, estudam ou vivem.";
			url3.imageURL="http://2.bp.blogspot.com/-52-7unEYeD0/TWr7rccZ_KI/AAAAAAAAAhk/qOqfeFD8FzM/s1600/Facebook-icon.png";
			url3.name="Facebook";
			url3.added=new DateTime();
			url3.likes=50;
			url3.question=q;
			url3.user=user;
			url3.save();
			
			URL url4= new URL();
			url4.adress="http://www.sapo.pt/";
			url4.description="Portal com directório que usa o Open Directory Project.";
			url4.imageURL="https://www.lusoaloja.pt/images/sapo.png";
			url4.name="Sapo";
			url4.added=new DateTime();
			url4.question=q;
			url4.user=user;
			url4.save();
			
			URL url5= new URL();
			url5.adress="http://www.paypal.com/";
			url5.description="Send money and shop online. Shop securely without revealing your credit card or bank account information; Pay conveniently and quickly when you shop online.";
			url5.imageURL="http://www.stoneage.pt/content/tiny_images/Paypal_stoneage%20technology.JPG";
			url5.name="Paypal";
			url5.added=new DateTime();
			url5.likes=23;
			url5.question=q;
			url5.user=user;
			url5.save();
			
			URL url6= new URL();
			url6.adress="http://www.amazon.co.uk/";
			url6.description="Online retailer of books, movies, music and games along with electronics, toys, apparel, sports, tools, groceries and general home and garden items.";
			url6.imageURL="http://www.eastbayexpress.com/binary/2449/1347917999-amazon.jpg";
			url6.name="Amazon";
			url6.added=new DateTime();
			url6.likes=100;
			url6.question=q;
			url6.user=user;
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
		
	
		
		
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");

	}
}

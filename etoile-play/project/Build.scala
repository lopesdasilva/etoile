import sbt._
import Keys._
import PlayProject._
import cloudbees.Plugin._

object ApplicationBuild extends Build {

    val appName         = "etoile-play"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "mysql" % "mysql-connector-java" % "5.1.18",
      "org.apache.commons" % "commons-email" % "1.2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA)
      // Add your own project settings here 
	    .settings(cloudBeesSettings :_*)
	    .settings(CloudBees.applicationId := Some("etoile"))     

}

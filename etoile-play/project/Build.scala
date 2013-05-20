import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "etoile-play"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
 	javaCore,
    javaJdbc,
    javaEbean,
    jdbc,
"mysql" % "mysql-connector-java" % "5.1.18",
"com.jolbox" % "bonecp" % "0.8.0-rc1",
   "net.sf.flexjson" % "flexjson" % "2.1",
      "org.apache.commons" % "commons-email" % "1.2",
  	    "pdf" % "pdf_2.10" % "0.4.1"

    )

    val main = play.Project(appName, appVersion, appDependencies)
      // PDF
      		.settings(resolvers += Resolver.url("Violas Play Modules", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns))

}
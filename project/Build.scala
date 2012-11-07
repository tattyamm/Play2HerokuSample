import sbt._
import Keys._
import PlayProject._
import com.typesafe.startscript.StartScriptPlugin

object ApplicationBuild extends Build {

  val appName = "Checktrend on Heroku"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,

    //memcached
    "com.github.mumoshu" %% "play2-memcached" % "0.2.1-SNAPSHOT"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    // Add your own project settings here

    //memcached
    resolvers += "Sonatype OSS Snapshots Repository" at "http://oss.sonatype.org/content/groups/public",
    resolvers += "Spy Repository" at "http://files.couchbase.com/maven2" // required to resolve `spymemcached`, the plugin's dependency.

  )

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = Defaults.defaultSettings ++ StartScriptPlugin.startScriptForClassesSettings ++ Seq(
    )
  )


}

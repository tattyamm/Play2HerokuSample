import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "TwitterProxy"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,

      //redis追加
      "com.typesafe" %% "play-plugins-redis" % "2.0.2",
      "org.sedis" %% "sedis" % "1.0.1"

      //redis用にjedisを追加
      //"Redis.clients" % "jedis" % "2.0.0"

      //oauthの為に導入
      //"net.databinder.dispatch" %% "core" % "0.9.1"

    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here

      //redisの為にsedisを入れる為
      //resolvers += "Sedis repository" at "http://guice-maven.googlecode.com/svn/trunk"
    )

}
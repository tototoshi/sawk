import sbt._
import Keys._

object SawkProject extends Build {

  lazy val root = Project (
    id = "sawk",
    base = file ("."),
    settings = Defaults.defaultSettings ++ Seq (
      scalaVersion := "2.10.0",
      crossScalaVersions := Seq("2.9.1", "2.9.2", "2.10.0"),
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "1.9.1" % "test"
      )
    )
  )
}



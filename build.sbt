name := "alacs"

version := "0.1-SNAPSHOT"

organization := "com.github.alacs"

//scalaVersion := "2.9.2"

scalacOptions in ThisBuild ++= Seq("-deprecation")

//testOptions in Test += Tests.Argument("xonly")

//testOptions in Test += Tests.Argument("html")

libraryDependencies ++= Seq(
   "org.scala-lang" % "scala-compiler" % "2.9.2",
   "org.specs2" %% "specs2" % "1.12" % "test",
   "junit" % "junit" % "4.7",
   "org.pegdown" % "pegdown" % "1.0.2"
 )

resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
                  "releases"  at "http://scala-tools.org/repo-releases",
                  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/")

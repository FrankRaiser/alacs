# Alacs - Bug finder for Scala #

## Goals ##
The aim of this project is to provide a tool for finding defects in Scala source code by
analyzing ASTs during compilation. Think [FindBugs](http://findbugs.sourceforge.net/) for Scala.

## Try it ##
Alacs is not ready for serious use, but feel free to try it out.

### Options ###

Once alacs is available to your compiler you can run `scalac -help` to get all available options. 

### sbt ###

Right now, the plugin is not available anywhere other than by source, so the simplest way
to get up and running is currently to clone the alacs repository locally and use `sbt package`
for creating the `.jar` file.

In your actual project, you can simply place the file into your `lib/` folder and update your
`build.sbt` to include the following:

    scalacOptions in ThisBuild ++= Seq(
      "-Xplugin:lib/alacs_<ScalaVersion>.jar",
      "-Xplugin-require:alacs")

### CLI ###
* Build the project with `sbt package` on the command line.
* `scalac -Xplugin:/path/to/alacs/target/scala_2.8.1/alacs_2.8.1-0.0.0-SNAPSHOT.jar -Xplugin-require:alacs foo.scala`

### IDEs ###
There is no special support for IDEs (Eclipse, IntelliJ, Netbeans) yet. For all I know it's really trivial to use compiler plugins with them.

## Contributing ##
There are two ways to contribute to Alacs:

* Submitting suggestions for bug reports. You can do this on [the wiki](https://github.com/FrankRaiser/alacs/wiki).
* Writing code (e.g., submitting patches, joining the team, etc.).

Thank you for helping improve Alacs!

## History ##

This project was originally forked from [Yuvi Masory](https://github.com/ymasory/alacs), but most parts have been completely rewritten after the fork.

### Changes/Updates to [ymasory/alacs](https://github.com/ymasory/alacs) ###

* Removed old pattern 003, as mod by 1 seems to result in 0 during early parsing already
* Fixed open issues of ymasory/alacs: #10, #11, #12, #13, #18
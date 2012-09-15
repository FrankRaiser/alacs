package com.github.alacs

import scala.tools.nsc.{ Global, Settings }
import scala.tools.nsc.reporters.{ Reporter, StoreReporter }
import java.io.{ File, StringWriter, PrintWriter }
import scala.tools.nsc.io.AbstractFile

object RunPlugin {

  val curDir = (new java.io.File(".")).getCanonicalPath
  val testPrefix = curDir.replace('\\', '/') + "/src/test/resources/"

  lazy val scalaVersion = {
    val matcher = """version (\d+\.\d+\.\d+).*""".r
    util.Properties.versionString match {
      case matcher(versionString) => Some(versionString)
      case _ => {
        Console.err println ("could not detect scala version")
        None
      }
    }
  } getOrElse "2.9.2"
  
  def getJar(res : String) = {
    val url = classOf[Global].getResource(res)
    url.getFile.substring(6, url.getFile.size-(res.size+1))
  }
  
  def getCompilerJar = getJar("/scala/tools/nsc")
  def getLibraryJar = getJar("/scala/collection")

  def runPlugin(fileName: String): List[PluginMessage] = {
    val settings = new Settings 
    settings.outputDirs setSingleOutput (curDir + "/target")
    settings.classpath.tryToSet(List(
        curDir + "/lib/scala-library-" + scalaVersion + ".jar"))
    val reporter = new StoreReporter
    val compiler = new Global(settings, reporter) {
      override protected def computeInternalPhases() {
        super.computeInternalPhases
        for (phase <- new AlacsPlugin(this).components)
          phasesSet += phase
      }
    }
    println("### " + testPrefix + fileName)
    (new compiler.Run).compile(List(testPrefix + fileName))
    reporter.infos.toList
  }
}

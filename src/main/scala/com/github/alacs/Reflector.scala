package com.github.alacs

import java.io.File
import java.net.URL
import scala.tools.nsc.Global
import com.github.alacs.patterns.PatternDetector
import java.lang.reflect.Constructor
import java.util.zip.ZipInputStream
import scala.collection.JavaConverters._
import scala.io.Source
import java.util.jar.JarInputStream
import java.io.FileInputStream

object Reflector {

  def discoverPatterns(): List[Constructor[PatternDetector]] = {
    val classNames = discoverPackageClasses("com.github.alacs.patterns").
                     filter(_ contains ".AlacsPattern").
                     sorted
    classNames map { name =>
      val clazz = Class.forName(name)
      val constructors = clazz.getConstructors().toList
      val con: Constructor[PatternDetector] =
        clazz.getConstructor(classOf[Global])
        .asInstanceOf[Constructor[PatternDetector]]
      con
    }
  }

  def discoverPackageClasses(packageName: String): List[String] = {
    val name = {
      val name =
        if (packageName.startsWith("/"))
          packageName
        else
          "/" + packageName
      name replace ('.', '/')
    }

    val urlOpt = Option(Reflector.getClass.getResource(name))
    urlOpt match {
      case Some(url) => {
        val allFiles = {
          try {
            // try jar first
            val jarName = url.getFile.substring(0, url.getFile.size-(name.size+1))
	    	val jar = new JarInputStream( (new URL(jarName)).openStream)
	        Stream.continually(jar.getNextEntry).takeWhile(_ != null).map(_.getName).toList
          } catch {
            case _ => // try normal file
              val directory = new File(url.getFile)
              directory.list().toList.map(s => name.substring(1) + "/" + s)
          }
        }
        
        val files = allFiles
          .filter(s => s.startsWith(name.substring(1)) 
                       && !s.contains("$") 
                       && s.endsWith(".class"))
          .map(s => s.substring(s.lastIndexOf("/")+1))
          .filter(_.startsWith("AlacsPattern"))
        val classes: List[String] = files flatMap { file =>
          val className = (
            packageName + "." +
            file.substring(0, file.length - 6)
          )
          try {
            Class.forName(className)
            Some(className)
          } catch {
            case _: ClassNotFoundException => None
          }
        }
        classes
      }
      case None => Nil
    }
  }

  // def main(args: Array[String]) {
  //   println("constructors: " + discoverPatterns())
  // }
}

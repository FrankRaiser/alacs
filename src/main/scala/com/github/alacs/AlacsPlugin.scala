package com.github.alacs

import scala.tools.nsc
import nsc.Global
import nsc.plugins.Plugin

import patterns._

class AlacsPlugin(val global: Global) extends Plugin {
  import global._

  override def processOptions(options : List[String], error: String => Unit) {
    for (option <- options) {
      option match {
        case o if o.startsWith("exclude:") =>
          try {
            val nums = AlacsPlugin.numbersParser(o.substring(8))
        	components.map(_.exclude(nums))
          } catch {
            case ex : NumberFormatException => 
              error("Invalid numbers in exclude option: " + o.substring(8))
          }
        case o =>
          error("Option not understood: "+o)
      }
    }
  }
  
  override val optionsHelp: Option[String] = Some(
    "  -P:alacs:exclude:[Ids]	exclude patterns given in [Ids] (f.ex. '3,5,7-9' )"    
  )
  
  override val name = "alacs"
  override val description = "finds bugs, hopefully"
  override val components = List(
    new ParserComponent(ParserPhase, global),
    new ParserComponent(TyperPhase, global))
}

object AlacsPlugin {
  /**
   * Helper method for number parsing used for option strings,
   * like "3,5,7-9"
   */
  def numbersParser(arg : String) : Seq[Int] = {
    val pat = """(\d+)-(\d+)""".r
    arg.split(",")
      .map(_.trim)
      .flatMap(_ match {
        case pat(a, b) => a.toInt to b.toInt
        case x => List(x.toInt)
      })
  } 
}
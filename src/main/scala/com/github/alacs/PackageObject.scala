import scala.tools.nsc.reporters.StoreReporter
import scala.tools.nsc.util.Position

package com.github {
  package object alacs {
    type PluginMessage = StoreReporter#Info

    implicit def messages2Bugs(messages: List[PluginMessage]): List[Bug] = {   
      messages flatMap {
        parseBug(_) match {
          case Some(bug) => List(bug)
          case None      => Nil
        }
      }
    }

    implicit def parseBug(msg: PluginMessage): Option[Bug] = {
      val AlacsPattern = """.*Alacs\-(\d\d\d).*""".r
      try {
        val AlacsPattern(numStr) = msg.toString
        Some(Bug(BugPattern(numStr.toInt, null), null))
      }
      catch {
        case e: MatchError => None
      }
    }
  }
}

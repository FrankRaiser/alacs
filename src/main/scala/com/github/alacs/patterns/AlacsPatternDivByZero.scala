package com.github.alacs.patterns

import scala.tools.nsc.Global
import com.github.alacs.{Bug, BugInfo, BugPattern}

class AlacsPatternDivByZero(global: Global) extends PatternDetector(global) {
  import global._

  override val pattern = BugPattern(2, BugInfo("divide by literal 0"))
  
  override val phase = com.github.alacs.TyperPhase
  
  override def analyzeTree(tree: Global#Tree) = {
    val bug = Bug(pattern, tree.pos)
    tree match {
      case Apply(Select(rcvr, nme.DIV), List(Literal(Constant(0))))
        if (rcvr.tpe <:< definitions.ByteClass.tpe
          ||rcvr.tpe <:< definitions.ShortClass.tpe
          ||rcvr.tpe <:< definitions.IntClass.tpe
          ||rcvr.tpe <:< definitions.LongClass.tpe) => report(bug) 
      case Apply(Select(rcvr, nme.MOD), List(Literal(Constant(0))))
        if (rcvr.tpe <:< definitions.ByteClass.tpe
          ||rcvr.tpe <:< definitions.ShortClass.tpe
          ||rcvr.tpe <:< definitions.IntClass.tpe
          ||rcvr.tpe <:< definitions.LongClass.tpe) => report(bug)
      // cannot check double/float, as typer will automatically translate it to Infinity
      case _ => None
    }
  }
}

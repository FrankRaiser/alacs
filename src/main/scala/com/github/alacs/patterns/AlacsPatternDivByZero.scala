package com.github.alacs.patterns

import scala.tools.nsc.Global
import com.github.alacs.{Bug, BugInfo, BugPattern}

class AlacsPatternDivByZero(global: Global) extends PatternDetector(global) {
  import global._

  override val pattern = BugPattern(2, BugInfo("divide by literal 0"))
  
  val phase = com.github.alacs.TyperPhase
  
  override def analyzeTree(tree: GTree) = {
    val bug = Bug(pattern, tree.pos)
    tree match {
      case Apply(Select(rcvr, nme.DIV), List(Literal(Constant(0))))
        if rcvr.tpe <:< definitions.IntClass.tpe => report(bug)
      case Apply(Select(rcvr, nme.MOD), List(Literal(Constant(0))))
        if rcvr.tpe <:< definitions.IntClass.tpe => report(bug)
      case _ => None
    }
  }
}

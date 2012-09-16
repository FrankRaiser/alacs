package com.github.alacs.patterns

import scala.tools.nsc.Global

import com.github.alacs.{Bug, BugInfo, BugPattern}

class AlacsPatternUnaryPlus(global: Global) extends PatternDetector(global) {
  import global._

  override val pattern = BugPattern(3, BugInfo("unary plus"))
  
  override val phase = com.github.alacs.TyperPhase
  
  override def analyzeTree(tree: Global#Tree) = {
    val bug = Bug(pattern, tree.pos)
    tree match {
      case Select(rcvr, nme.UNARY_+)
        if (rcvr.tpe <:< definitions.ByteClass.tpe
          ||rcvr.tpe <:< definitions.ShortClass.tpe
          ||rcvr.tpe <:< definitions.IntClass.tpe
          ||rcvr.tpe <:< definitions.LongClass.tpe
          ||rcvr.tpe <:< definitions.FloatClass.tpe
          ||rcvr.tpe <:< definitions.DoubleClass.tpe) => report(bug)
      case _ => None
    }
  }
}

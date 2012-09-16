package com.github.alacs.patterns

import scala.tools.nsc.Global
import com.github.alacs.{Bug, BugInfo, BugPattern}
import scala.annotation.tailrec

class AlacsPatternUnintentionalProcedure(global: Global) extends PatternDetector(global) {
  import global._

  override val pattern = BugPattern(1, BugInfo("unintentional procedure"))
  
  @tailrec
  private def getLastExprOfBlock(tree: Tree) : Tree = tree match {
    case Block(_, last) => getLastExprOfBlock(last)
    case x => x
  }
  
  override def analyzeTree(tree: GTree) = {
    val bug = Bug(pattern, tree.pos)
    tree match {
      case tree@DefDef(_, name, _, _, Select(_, n), rhs) if n.toString == "Unit" => {
        getLastExprOfBlock(rhs) match {
          case Literal(Constant(())) => None //empty procedures
          case Literal(_)  => report(bug)
          case Ident(_) => report(bug)
          case _ => None
        }
      }
      case _ => None
    }
  }
}

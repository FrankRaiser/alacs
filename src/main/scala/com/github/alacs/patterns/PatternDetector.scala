package com.github.alacs.patterns

import scala.tools.nsc
import nsc.Global
import com.github.alacs.{Bug, BugPattern}
import com.github.alacs.CompilerPhase

abstract class PatternDetector(global: Global) {
  import global._

  val pattern: BugPattern
  
  val phase : CompilerPhase

  def report(bug: Bug): Option[Bug] = {
    global.reporter.info(bug.pos, bug.pat.toString, false)
    Some(bug)
  }

  def analyzeTree(tree: GTree): Option[Bug]
}

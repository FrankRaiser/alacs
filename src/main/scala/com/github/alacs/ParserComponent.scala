package com.github.alacs

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.PluginComponent

import com.github.alacs.patterns.PatternDetector

class ParserComponent(val phase : CompilerPhase, val global: Global) extends PluginComponent {
  import global._
  override val runsAfter = List[String](phase.toString)
  override val phaseName = "alacs " + phase + " phase"
  override def newPhase(_prev: Phase) =
    new ParserPhase(_prev)
  
  val patterns : List[PatternDetector] = ParserComponent.patterns
    .map(_.newInstance(global))
    .filter(_.phase == phase)

  class ParserPhase(prev: Phase) extends StdPhase(prev) {
    override def name = ParserComponent.this.phaseName
    override def apply(unit: CompilationUnit) {
      for (tree <- unit.body) {
        try {
          val bugs: List[Bug] = patterns flatMap (_ analyzeTree tree )
        }
        catch {
          case e => e.printStackTrace
        }
      }
    }
  }
}

object ParserComponent {
  import java.lang.reflect.Constructor
  val patterns: List[Constructor[PatternDetector]] = Reflector.discoverPatterns()
}
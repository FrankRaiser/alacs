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
  
  def patterns : List[PatternDetector] = ParserComponent.patterns
    .map(_.newInstance(global))
    .filter(_.phase == phase)
    .filter(x => !excludeNums.contains(x.pattern.bugId))
    
  private var excludeNums = Set[Int]()
  
  def exclude(nums : Seq[Int]) {
    excludeNums = excludeNums ++ nums
  }

  class ParserPhase(prev: Phase) extends StdPhase(prev) {
    override def name = ParserComponent.this.phaseName
    
    // remember patterns that failed internally, so we don't blow up the
    // whole console with hundreds of info messages that the pattern
    // doesn't work.
    private var failedPatterns : List[PatternDetector] = Nil
    
    override def apply(unit: CompilationUnit) {
      for (tree <- unit.body) {
        try {
          val bugs: List[Bug] = patterns flatMap ( pat =>
              try {
                if (failedPatterns.contains(pat)) None
                else pat analyzeTree tree 
              } catch {
                case ex =>
                  failedPatterns = pat :: failedPatterns
                  global.reporter.info(tree.pos, 
                      "Internal implementation error in pattern: " + pat.getClass.getSimpleName,
                      false)
                  None
              })
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
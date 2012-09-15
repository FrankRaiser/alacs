package com.github.alacs

import org.specs2._
import org.specs2.matcher.MatchResult

trait AlacsPatternSpec extends Specification {
  val patternNumber : Int
  
  private def test(file : String) : Boolean = run(file)
    .collect { case b @ Bug(BugPattern(`patternNumber`, _), _) => b }
    .size > 0
  
  def positive(file : String) : MatchResult[Boolean] = test(file) must beTrue
    
  def negative(file : String) : MatchResult[Boolean] = test(file) must beFalse
    
  
  private def properFileName(file : String) = 
    "pattern" + BugPattern.bugNumFormatter.format(patternNumber) + "/" + file + ".scala"
  
  private def run(file : String) : List[Bug] = RunPlugin.runPlugin(properFileName(file))
}
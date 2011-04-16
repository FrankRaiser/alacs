package com.github.alacs

import org.scalatest.FunSuite

class AlacsPattern002Tests extends AlacsFunSuite {

  val id = BugPatterns.AlacsPattern002.bugId

  val run = {runBugs("002", _: String)}

  // test("missing equals, string literal") {
  //   val bugs: List[Bug] = run("MissingEqualsStringLiteral")
  //   check(bugs)
  // }

  // test("not missing equals, string literal") {
  //   expect(Nil) {
  //     run("NotMissingEqualsStringLiteral")
  //   }
  // }

  // test("empty procedures are fine") {
  //   expect(Nil) {
  //     commonRunBugs("EmptyProcedure")
  //   }
  // }

  // test("missing equals, function body is block") {
  //   val bugs: List[Bug] = run("MissingEqualsBlock")
  //   check(bugs)
  // }

  def check(bugs: List[Bug]) {
    assert(bugs.length === 1)
    assert(bugs(0).pat.bugId === id)
  }
}
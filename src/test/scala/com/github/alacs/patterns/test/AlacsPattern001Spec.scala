package com.github.alacs.test.patterns

import com.github.alacs.AlacsPatternSpec

class AlacsPattern001Spec extends AlacsPatternSpec {
  
  val patternNumber = 1
  
  def is =
  "Pattern 001: Unintentional Procedure should"		^
    "be positive for string literals" 				! positive("MissingEqualsStringLiteral")^
    "be positive for function body is block"		! positive("MissingEqualsBlock")^
    "be negative for not missing equals"			! negative("NotMissingEqualsStringLiteral")^
    "be negative for empty procedures"				! negative("EmptyProcedure")^
      												end
}
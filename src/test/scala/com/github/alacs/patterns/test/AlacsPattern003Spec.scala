package com.github.alacs.test.patterns

import com.github.alacs.AlacsPatternSpec

class AlacsPattern003Spec extends AlacsPatternSpec { 
  val patternNumber = 3
  
  def is =
  "Pattern 003 : Unary plus should"						^
    "detect unary plus"									! positive("UnaryPlus")^
    "detect unary plus for double"						! positive("UnaryPlusDouble")^
    													end
}
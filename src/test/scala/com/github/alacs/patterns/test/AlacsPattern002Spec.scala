package com.github.alacs.test.patterns

import com.github.alacs.AlacsPatternSpec

class AlacsPattern002Spec extends AlacsPatternSpec { 
  val patternNumber = 2
  
  def is =
  "Pattern 002 : Dividy by literal 0 should"			^
    "detect literal 0"									! positive("DivBy0")^
    "detect hex 0"										! positive("DivBy0Hex")^
    "detect mod 0"										! positive("Mod0")^
    "avoid div by 1"									! negative("DivBy1")^
    													end
}
package com.github.alacs.test

class AlacsPattern002Tests extends AlacsPatternSuite {

  positive("div by 0", "DivBy0")
  positive("div by hex 0", "DivBy0Hex")
  positive("mod 0", "Mod0")

  negative("div by 1", "DivBy1")
}

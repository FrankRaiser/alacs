package com.github.alacs

import org.specs2._
import com.github.alacs.test.patterns._

class AlacsPatternAllSpec extends Specification { def is =
  "Alacs patterns specification" 	^
    include(new AlacsPattern001Spec) ^
    include(new AlacsPattern002Spec) ^
    include(new AlacsPattern003Spec) ^
    end
}
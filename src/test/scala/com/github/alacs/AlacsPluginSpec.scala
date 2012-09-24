package com.github.alacs

import org.specs2.mutable.Specification

class AlacsPluginSpec extends Specification {

  "numbersParser" should {
    "work for single numbers" in {
      AlacsPlugin.numbersParser("1") must beEqualTo(List(1))
      AlacsPlugin.numbersParser("0") must beEqualTo(List(0))
      AlacsPlugin.numbersParser("6") must beEqualTo(List(6))
	}
    "work for multiple numbers" in {
      AlacsPlugin.numbersParser("0,1,2") must beEqualTo(List(0,1,2))
      AlacsPlugin.numbersParser("4,3,2,4") must beEqualTo(List(4,3,2,4))
    }
    "work for ranges" in {
      AlacsPlugin.numbersParser("1-3") must beEqualTo(List(1,2,3))
      AlacsPlugin.numbersParser("1-1") must beEqualTo(List(1))
      AlacsPlugin.numbersParser("4-3") must beEqualTo(List())
      AlacsPlugin.numbersParser("5-10") must beEqualTo(List(5,6,7,8,9,10))
    }
    "work for mixed ranges and numbers" in {
      AlacsPlugin.numbersParser("1,1-3") must beEqualTo(List(1,1,2,3))
      AlacsPlugin.numbersParser("1-3,1") must beEqualTo(List(1,2,3,1))
      AlacsPlugin.numbersParser("1-3,2-3") must beEqualTo(List(1,2,3,2,3))
      AlacsPlugin.numbersParser("0,1-3,4") must beEqualTo(List(0,1,2,3,4))
    }
    "throw IAE for invalid inputs" in {
      AlacsPlugin.numbersParser("a") must throwAn[NumberFormatException]
      AlacsPlugin.numbersParser("1,a") must throwAn[NumberFormatException]
      AlacsPlugin.numbersParser("a-2") must throwAn[NumberFormatException]
      AlacsPlugin.numbersParser("2-a") must throwAn[NumberFormatException]
      AlacsPlugin.numbersParser("1-3,2-a,4") must throwAn[NumberFormatException]
    }
  }
}
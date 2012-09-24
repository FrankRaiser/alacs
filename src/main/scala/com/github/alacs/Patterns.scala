package com.github.alacs

object Patterns {
  val UnintentionalProcedure	= BugPattern(1, BugInfo("unintentional procedure"))
  val DivByZero 				= BugPattern(2, BugInfo("divide by literal 0"))
  val UnaryPlus 				= BugPattern(3, BugInfo("unary plus"))
}
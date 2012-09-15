package com.github.alacs

sealed abstract class CompilerPhase

case object ParserPhase extends CompilerPhase {
  override def toString = "parser"
}
case object TyperPhase extends CompilerPhase {
  override def toString = "typer"
}
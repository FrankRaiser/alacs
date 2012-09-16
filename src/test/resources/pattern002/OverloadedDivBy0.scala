object OverloadedDivBy0 {
  class Special(s : String) {
    def `/`(x : Int) : Int = s(x).toInt
  }
  implicit def str2Special(s : String) = new Special(s)
  
  "test" / 0
}

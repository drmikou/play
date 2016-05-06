
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/phanthavong/play-java/conf/routes
// @DATE:Fri May 06 09:52:09 CEST 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}

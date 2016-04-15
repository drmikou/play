
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/phanthavong/play-java/conf/routes
// @DATE:Fri Apr 15 09:55:29 CEST 2016


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

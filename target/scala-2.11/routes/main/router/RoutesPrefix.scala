
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/phanthavong/play-java/conf/routes
// @DATE:Tue May 10 14:55:03 CEST 2016


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

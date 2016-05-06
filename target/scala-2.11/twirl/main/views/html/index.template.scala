
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object index_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template takes a single argument, a String containing a
 * message to display.
 */
  def apply/*5.2*/(message: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*5.19*/("""

"""),format.raw/*11.4*/("""
"""),_display_(/*12.2*/main("GLProject")/*12.19*/ {_display_(Seq[Any](format.raw/*12.21*/("""

    """),format.raw/*17.8*/("""
    """),_display_(/*18.6*/play20/*18.12*/.welcome(message, style = "Java")),format.raw/*18.45*/("""

"""),format.raw/*20.1*/("""<<<<<<< HEAD
=======

"""),format.raw/*23.40*/("""

"""),format.raw/*25.1*/(""">>>>>>> 9367059d88f3318127a86ea3744518797ca4573f
""")))}),format.raw/*26.2*/("""
"""))
      }
    }
  }

  def render(message:String): play.twirl.api.HtmlFormat.Appendable = apply(message)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (message) => apply(message)

  def ref: this.type = this

}


}

/*
 * This template takes a single argument, a String containing a
 * message to display.
 */
object index extends index_Scope0.index
              /*
                  -- GENERATED --
                  DATE: Fri Apr 15 10:14:35 CEST 2016
                  SOURCE: /Users/phanthavong/play-java/app/views/index.scala.html
                  HASH: f6eed3a0c21d41c25cbdd175dea1a171923ec771
                  MATRIX: 834->95|946->112|975->312|1003->314|1029->331|1069->333|1102->462|1134->468|1149->474|1203->507|1232->509|1282->570|1311->572|1391->622
                  LINES: 30->5|35->5|37->11|38->12|38->12|38->12|40->17|41->18|41->18|41->18|43->20|46->23|48->25|49->26
                  -- GENERATED --
              */
          
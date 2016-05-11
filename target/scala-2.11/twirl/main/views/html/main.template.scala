
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object main_Scope0 {
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

class main extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template3[String,Html,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String, scripts: Html = Html(""))(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.58*/("""



"""),format.raw/*5.1*/("""<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>"""),_display_(/*15.13*/title),format.raw/*15.18*/("""</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" media="screen" href=""""),_display_(/*18.50*/routes/*18.56*/.Assets.versioned("stylesheets/bootstrap.min.css")),format.raw/*18.106*/("""">

    <!-- Custom CSS -->
    <link rel="stylesheet" media="screen" href=""""),_display_(/*21.50*/routes/*21.56*/.Assets.versioned("stylesheets/sb-admin.css")),format.raw/*21.101*/("""">

    <!-- Morris Charts CSS -->
    <link rel="stylesheet" media="screen" href=""""),_display_(/*24.50*/routes/*24.56*/.Assets.versioned("stylesheets/morris.css")),format.raw/*24.99*/("""">

    <!-- Custom Fonts -->
    <link rel="stylesheet" media="screen" href=""""),_display_(/*27.50*/routes/*27.56*/.Assets.versioned("font-awesome/css/font-awesome.min.css")),format.raw/*27.114*/("""">
</head>

<body>
<div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href=""""),_display_(/*42.44*/routes/*42.50*/.AccueilController.accueil),format.raw/*42.76*/("""">GL Project</a>
            <a class="navbar-brand" href=""""),_display_(/*43.44*/routes/*43.50*/.AccueilController.accueil),format.raw/*43.76*/("""">
                <i class="fa fa-home"></i>
            </a>
        </div>
        <!-- Top Menu Items -->
        <ul class="nav navbar-right top-nav">

            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> User <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                    </li>
                </ul>
            </li>
        </ul>
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav">
                <li>
                    <a href="#"><i class="fa fa-users"></i> Mon groupe</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-calendar"></i> Calendrier</a>
                </li>
                <li class="">
                    <a href="#"><i class="fa fa-folder-open"></i> Dépôt de documents</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-envelope-o"></i> Messagerie</a>
                </li>

            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>

    <div id="page-wrapper">
        """),_display_(/*81.10*/content),format.raw/*81.17*/("""
    """),format.raw/*82.5*/("""</div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src=""""),_display_(/*89.15*/routes/*89.21*/.Assets.versioned("javascripts/jquery.js")),format.raw/*89.63*/("""" type="text/javascript"></script>


<script src=""""),_display_(/*92.15*/routes/*92.21*/.Assets.versioned("javascripts/bootstrap.min.js")),format.raw/*92.70*/("""" type="text/javascript"></script>

</body>

</html>
"""))
      }
    }
  }

  def render(title:String,scripts:Html,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title,scripts)(content)

  def f:((String,Html) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title,scripts) => (content) => apply(title,scripts)(content)

  def ref: this.type = this

}


}

/**/
object main extends main_Scope0.main
              /*
                  -- GENERATED --
                  DATE: Wed May 11 20:44:44 CEST 2016
                  SOURCE: /Users/phanthavong/play-java/app/views/main.scala.html
                  HASH: f919f11e676ced460f4b4582f6ab21c26a21b57d
                  MATRIX: 753->1|904->57|934->61|1250->350|1276->355|1394->446|1409->452|1481->502|1585->579|1600->585|1667->630|1778->714|1793->720|1857->763|1963->842|1978->848|2058->906|2714->1535|2729->1541|2776->1567|2863->1627|2878->1633|2925->1659|4351->3058|4379->3065|4411->3070|4531->3163|4546->3169|4609->3211|4687->3262|4702->3268|4772->3317
                  LINES: 27->1|32->1|36->5|46->15|46->15|49->18|49->18|49->18|52->21|52->21|52->21|55->24|55->24|55->24|58->27|58->27|58->27|73->42|73->42|73->42|74->43|74->43|74->43|112->81|112->81|113->82|120->89|120->89|120->89|123->92|123->92|123->92
                  -- GENERATED --
              */
          
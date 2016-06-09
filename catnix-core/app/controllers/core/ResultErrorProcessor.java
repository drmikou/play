package controllers.core;

import play.mvc.Controller;
import play.mvc.*;

/**
 * Redirect all functional errors to this class
 *
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class ResultErrorProcessor extends Controller {

    public static Result throwForbidden() {
        return forbidden(views.html.core.resultError.forbidden.render());
    }

    public static Result throwNotFound() {
        return notFound(views.html.core.page.throw404.render());
    }

}

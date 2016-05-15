package controllers;

import play.mvc.*;
import views.html.reunion;

/**
 * Created by benoitdufour on 13/05/2016.
 */
public class ReunionController extends Controller {

    public Result reunion(){

        String test = "dd";

        return ok(reunion.render(test));
    }

}

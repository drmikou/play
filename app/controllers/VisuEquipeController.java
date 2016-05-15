package controllers;

import play.mvc.*;
import views.html.*;

/**
 * Created by benoitdufour on 13/05/2016.
 */
public class VisuEquipeController extends Controller {

    public Result visuEquipe(){

        String test = "dd";

        return ok(visuEquipe.render(test));
    }

}

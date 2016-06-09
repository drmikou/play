package controllers;

import models.Groupe;
import models.Equipe;
import models.User;

import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;

/**
 * Created by Thomas on 19/05/2016.
 */
public class AdminRegisterController extends Controller {

    public Result adminRegister(){

    return ok(adminRegister.render());

    }
}

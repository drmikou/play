package controllers;

import models.Groupe;
import models.Equipe;
import models.User;

import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;
import play.api.data._;
import play.api.data.Forms._;
/**
 * Created by Thomas on 19/05/2016.
 */
public class AdminRegisterController extends Controller {

    public Result adminRegister(){

    return ok(adminRegister.render());

    }
}

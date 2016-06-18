package controllers;

import models.Promotion;
import models.Session;
import models.Groupe;
import models.Equipe;
import models.User;
import models.Projet;
import models.Depot;
import models.Reunion;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;


public class MessagerieController extends Controller {


    public Result messagerieSubmit(){

        return ok(messagerie.render());
    }


    public Result messagerie(){

        return ok(messagerie.render());
    }

}



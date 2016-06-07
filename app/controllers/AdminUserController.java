package controllers;

import models.Promotion;
import models.Session;
import models.Groupe;
import models.Equipe;
import models.User;
import models.Projet;
import models.Depot;
import models.Reunion;

import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;


public class AdminUserController extends Controller {


    public Result adminUser(){

        //  -----------------
        // |      Client     |
        //  -----------------
        List<User> viewClient = null;
        // On récupère tous les utilisateurs
        viewClient = User.find.where().eq("role","client").findList();


        //  -----------------
        // |      Eleves     |
        //  -----------------
        List<User> viewEleve = null;
        // On récupère tous les utilisateurs
        viewEleve = User.find.where().eq("role","user").findList();



        return ok(adminUser.render(viewClient,viewEleve));
    }


}



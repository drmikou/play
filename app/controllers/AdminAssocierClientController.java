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


public class AdminAssocierClientController extends Controller {


    public Result adminAssocierClient(){

        boolean formSend = false;

        //  ------------------------------
        // |     Affichage des clients    |
        //  ------------------------------
        List<User> viewClient = null;
        viewClient = User.find.where().eq("role", "client").findList();

        //  ------------------------------
        // |     Affichage des équipes    |
        //  ------------------------------
        List<Equipe> viewEquipe = null;
        viewEquipe = Equipe.find.findList();



        return ok(adminAssocierClient.render(viewClient, viewEquipe, formSend));
    }


    public Result adminAssocierClientSubmit(){

        boolean formSend = false;

        //  ------------------------------
        // |     Affichage des clients    |
        //  ------------------------------
        List<User> viewClient = null;
        viewClient = User.find.where().eq("role", "client").findList();

        //  ------------------------------
        // |     Affichage des équipes    |
        //  ------------------------------
        List<Equipe> viewEquipe = null;
        viewEquipe = Equipe.find.findList();


        //  ------------------------------
        // |    Traitement du formulaire  |
        //  ------------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formClient = dynamicForm.get("client");
        String formEquipe = dynamicForm.get("equipe");

        if( !formClient.equals("Aucun") && !formEquipe.equals("Aucun")){
            User associationClient = (User) User.find.where().eq("nom", formClient).findUnique();
            Equipe associationEquipe = (Equipe) Equipe.find.where().eq("nom", formEquipe).findUnique();

            associationClient.setEquipe(associationEquipe);
            associationClient.save();

            formSend = true;
        }


        return ok(adminAssocierClient.render(viewClient, viewEquipe, formSend));
    }

}



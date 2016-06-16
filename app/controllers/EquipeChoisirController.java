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


public class EquipeChoisirController extends Controller {


    public Result equipeChoisir(){

        boolean formSend = false;

        //  ------------------------------
        // |     Affichage des élèves    |
        //  ------------------------------
        List<User> viewEleve = null;
        viewEleve = User.find.where().eq("role", "eleve").findList();

        //  ------------------------------
        // |     Affichage des équipes    |
        //  ------------------------------
        List<Equipe> viewEquipe = null;
        viewEquipe = Equipe.find.findList();



        return ok(equipeChoisir.render(viewEleve, viewEquipe, formSend));
    }


    public Result equipeChoisirSubmit(){

        boolean formSend = false;
        String userId = "2";

        //  ------------------------------
        // |     Affichage des élèves    |
        //  ------------------------------
        List<User> viewEleve = null;
        viewEleve = User.find.where().eq("role", "eleve").findList();

        //  ------------------------------
        // |     Affichage des équipes    |
        //  ------------------------------
        List<Equipe> viewEquipe = null;
        viewEquipe = Equipe.find.findList();


        //  ------------------------------
        // |    Traitement du formulaire  |
        //  ------------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formEquipe = dynamicForm.get("equipe");

        if( formEquipe != "Aucun"){
            User associationEleve = (User) User.find.where().eq("id", userId).findUnique();
            Equipe associationEquipe = (Equipe) Equipe.find.where().eq("nom", formEquipe).findUnique();

            associationEleve.setEquipe(associationEquipe);
            Ebean.save(associationEleve);

            formSend = true;
        }


        return ok(equipeChoisir.render(viewEleve, viewEquipe, formSend));
    }

}



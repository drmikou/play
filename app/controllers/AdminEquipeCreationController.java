package controllers;

import models.Groupe;
import models.Equipe;
import models.User;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;

public class AdminEquipeCreationController extends Controller {


    public Result adminEquipeCreation(){

        boolean formSend = false;

        //  ----------------------------
        // |      Liste des équipes     |
        //  ----------------------------
        List<Equipe> viewEquipe = null;
        // On récupère toutes les équipes
        viewEquipe = Equipe.find.findList();

        return ok(adminEquipeCreation.render(formSend, viewEquipe));

    }


    public Result adminEquipeCreationSubmit(){

        boolean formSend = false;

        //  ----------------------------
        // |      Liste des équipes     |
        //  ----------------------------
        List<Equipe> viewEquipe = null;
        // On récupère toutes les équipes
        viewEquipe = Equipe.find.findList();



        // Récupération des champs du formulaire-
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formNom = dynamicForm.get("nom");

        // Création d'un nouveau client
        Equipe equipeObj = new Equipe();
        equipeObj.nom = formNom;
        equipeObj.save();

        // On passe la variable formSend pour afficher le bon envoi du formulaire
        formSend = true;

        return ok(adminEquipeCreation.render(formSend, viewEquipe));

    }
}

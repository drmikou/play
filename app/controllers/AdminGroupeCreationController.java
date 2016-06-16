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

public class AdminGroupeCreationController extends Controller {


    public Result adminGroupeCreation(){

        boolean formSend = false;

        //  ----------------------------
        // |      Liste des groupes     |
        //  ----------------------------
        List<Groupe> viewGroupe = null;
        // On récupère tous les groupes
        viewGroupe = Groupe.find.findList();

        return ok(adminGroupeCreation.render(formSend, viewGroupe));

    }


    public Result adminGroupeCreationSubmit(){

        boolean formSend = false;

        //  ----------------------------
        // |      Liste des groupes     |
        //  ----------------------------
        List<Groupe> viewGroupe = null;
        // On récupère tous les groupes
        viewGroupe = Groupe.find.findList();



        // Récupération des champs du formulaire-
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formNom = dynamicForm.get("nom");

        // Création d'un nouveau client
        Groupe groupeObj = new Groupe();
        groupeObj.nom = formNom;
        groupeObj.save();

        // On passe la variable formSend pour afficher le bon envoi du formulaire
        formSend = true;

        return ok(adminGroupeCreation.render(formSend, viewGroupe));

    }
}

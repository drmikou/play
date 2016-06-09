package controllers;

import models.Groupe;
import models.Equipe;
import models.Projet;
import models.User;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;

public class AdminProjetCreationController extends Controller {


    public Result adminProjetCreation(){

        boolean formSend = false;
        String formEquipe1 = null;
        String formEquipe2 = null;
        String formEquipe3 = null;
        String formEquipe4 = null;
        //  ----------------------------
        // |      Liste des projets     |
        //  ----------------------------
        List<Projet> viewProjet = null;
        // On récupère tous les projets
        viewProjet = Projet.find.findList();

        //  ----------------------------
        // |      Liste des équipes     |
        //  ----------------------------
        List<Equipe> viewEquipe = null;
        // On récupère toutes les équipes
        viewEquipe = Equipe.find.findList();

        return ok(adminProjetCreation.render(formSend, viewProjet, viewEquipe,formEquipe1,formEquipe2,formEquipe3,formEquipe4));

    }


    public Result adminProjetCreationSubmit(){

        boolean formSend = false;

        //  ----------------------------
        // |      Liste des projets     |
        //  ----------------------------
        List<Projet> viewProjet = null;
        // On récupère tous les projets
        viewProjet = Projet.find.findList();

        //  ----------------------------
        // |      Liste des équipes     |
        //  ----------------------------
        List<Equipe> viewEquipe = null;
        // On récupère toutes les équipes
        viewEquipe = Equipe.find.findList();


        // Récupération des champs du formulaire
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formNom = dynamicForm.get("nom");

        String formEquipe1 = dynamicForm.get("equipe1");
        String formEquipe2 = dynamicForm.get("equipe2");
        String formEquipe3 = dynamicForm.get("equipe3");
        String formEquipe4 = dynamicForm.get("equipe4");

        // On mets les équipes du formulaire dans un tableau
        List<String> formEquipeArray = new ArrayList<>();

            formEquipeArray.add(formEquipe1);
            formEquipeArray.add(formEquipe2);
            formEquipeArray.add(formEquipe3);
            formEquipeArray.add(formEquipe4);




        // Création d'un nouveau projet pour chaque équipe
        if( formNom != null) {
            for (String value : formEquipeArray) {
                // On ne crée pas de projet lorsque le champs est null
                if (value != "Aucun") {
                    Equipe equipeObj = (Equipe) Equipe.find.where().eq("nom", value).findUnique();
                    Projet projetObj = new Projet();
                    projetObj.nom = formNom;
                    projetObj.equipe = equipeObj;
                    projetObj.save();
                }

            }
        }



        // On passe la variable formSend pour afficher le bon envoi du formulaire
        formSend = true;

        return ok(adminProjetCreation.render(formSend, viewProjet, viewEquipe, formEquipe1,formEquipe2,formEquipe3,formEquipe4));

    }
}

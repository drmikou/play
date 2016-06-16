package controllers;

import models.Equipe;
import models.User;
import models.Projet;
import models.Reunion;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class ClientFixerReunionController extends Controller {

    public Result clientFixerReunion(){

        int clientId = 52;
        String date = null;
        User viewClient = (User) User.find.where().eq("id",clientId).findUnique();

        //  ----------------------------
        // |      Liste des élèves      |
        //  ----------------------------
        List<User> viewEleve = null;
        // On récupère toutes les équipes
        viewEleve = User.find.where().eq("role","eleve").findList();

        //  ----------------------------
        // |      Liste des équipes     |
        //  ----------------------------
        List<Equipe> viewMesequipes = null;
        // On récupère les équipes du clients
        viewMesequipes = Equipe.find.where().findList();

        //  ----------------------------
        // |      Liste des projets     |
        //  ----------------------------
        List<Projet> viewProjet = null;
        // On récupère tous les projets
        viewProjet = Projet.find.findList();

        return ok(clientFixerReunion.render(viewClient, viewEleve, viewMesequipes, viewProjet,date));
    }




    public Result clientFixerReunionSubmit(){

        int clientId = 52;

        User viewClient = (User) User.find.where().eq("id",clientId).findUnique();

        //  ----------------------------
        // |      Liste des élèves      |
        //  ----------------------------
        List<User> viewEleve = null;
        // On récupère toutes les équipes
        viewEleve = User.find.where().eq("role","eleve").findList();

        //  ----------------------------
        // |      Liste des équipes     |
        //  ----------------------------
        List<Equipe> viewMesequipes = null;
        // On récupère toutes les équipes
        viewMesequipes = Equipe.find.findList();

        //  ----------------------------
        // |      Liste des projets     |
        //  ----------------------------
        List<Projet> viewProjet = null;
        // On récupère tous les projets
        viewProjet = Projet.find.findList();

        //  ----------------------------
        // |        Formulaire          |
        //  ----------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formNom = dynamicForm.get("nom");
        String formEquipe = dynamicForm.get("equipe");
        String formDate = dynamicForm.get("date");
        String formHeure = dynamicForm.get("heure");

        String date = formDate;

        Equipe equipeResult = (Equipe) Equipe.find.where().eq("nom", formNom).findUnique();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Date date2 = formatter.parse(formDate);
        //Date dateResult = formatter.format(date2);

        Reunion reunionObj = new Reunion();
        reunionObj.nom = formNom;
        reunionObj.equipe = equipeResult;
        //reunionObj.date = dateResult;


        return ok(clientFixerReunion.render(viewClient, viewEleve, viewMesequipes, viewProjet, date));
    }

}


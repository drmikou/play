package controllers;

import models.Equipe;
import models.User;
import models.Projet;
import models.Reunion;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ClientFixerReunionController extends Controller {

    public Result clientFixerReunion(){

        Boolean formSend = false;
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
        // On récupère les équipes du clients
        viewMesequipes = Equipe.find.where().findList();

        //  ----------------------------
        // |      Liste des projets     |
        //  ----------------------------
        List<Projet> viewProjet = null;
        // On récupère tous les projets
        viewProjet = Projet.find.findList();

        return ok(clientFixerReunion.render(viewClient, viewEleve, viewMesequipes, viewProjet, formSend));
    }




    public Result clientFixerReunionSubmit(){

        Boolean formSend = false;
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

        String date = formHeure;

        Date date2 = null;
        Date date3 = null;

        Equipe equipeResult = (Equipe) Equipe.find.where().eq("nom", formEquipe).findUnique();

        try{
             DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
             date2 = format.parse(formDate);

            DateFormat format2 = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            date3 = format2.parse(formHeure);

        }
        catch(ParseException e)
        {

        }

        try{
            DateFormat format2 = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            date3 = format2.parse(formHeure);
        }
        catch(ParseException e)
        {

        }



        Reunion reunionObj = new Reunion();
        reunionObj.nom = formNom;
        reunionObj.equipe = equipeResult;
        reunionObj.date = date2;
        reunionObj.dateDebut = date3;
        reunionObj.save();

        formSend = true;
        return ok(clientFixerReunion.render(viewClient, viewEleve, viewMesequipes, viewProjet, formSend));
    }

}


package controllers;

import models.*;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;

public class ClientFonctionnaliteController extends Controller {


    public Result clientFonctionnalite(){

        Boolean formSend = false;
        int clientId = 52;
        User clientObj = (User) User.find.where().eq("id",clientId).findUnique();

        Equipe clientEquipe = clientObj.getEquipe();

        List<Fonctionnalite> fonctionnaliteList = null;
        fonctionnaliteList = Fonctionnalite.find.where().eq("equipe", clientEquipe).findList();

        return ok(clientFonctionnalite.render(fonctionnaliteList, formSend));

    }


    public Result clientFonctionnaliteSubmit(){

        Boolean formSend = false;
        int clientId = 52;
        User clientObj = (User) User.find.where().eq("id",clientId).findUnique();

        Equipe clientEquipe = clientObj.getEquipe();

        List<Fonctionnalite> fonctionnaliteList = null;
        fonctionnaliteList = Fonctionnalite.find.where().eq("equipe", clientEquipe).findList();


        //  ----------------------------
        // |        Formulaire         |
        //  ----------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formNom = dynamicForm.get("nom");

        Fonctionnalite fonctionnaliteObj = new Fonctionnalite();
        fonctionnaliteObj.nom = formNom;
        fonctionnaliteObj.equipe = clientEquipe;
        fonctionnaliteObj.trouve = false;
        fonctionnaliteObj.save();

        formSend = true;

        return ok(clientFonctionnalite.render(fonctionnaliteList, formSend));

    }
}

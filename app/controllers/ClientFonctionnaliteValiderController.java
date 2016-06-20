package controllers;

import models.*;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;

public class ClientFonctionnaliteValiderController extends Controller {


    public Result clientFonctionnaliteValider(){

        Boolean formSend = false;
        int clientId = 52;
        User clientObj = (User) User.find.where().eq("id",clientId).findUnique();

        Equipe clientEquipe = clientObj.getEquipe();

        Equipe equipeObj = (Equipe) Equipe.find.where().eq("id",clientEquipe.getId()).findUnique();

        List<Fonctionnalite> fonctionnaliteList = null;
        fonctionnaliteList = Fonctionnalite.find.where().eq("equipe", clientEquipe).findList();

        return ok(clientFonctionnaliteValider.render(fonctionnaliteList, equipeObj, formSend));

    }


    public Result clientFonctionnaliteValiderSubmit(){

        Boolean formSend = false;
        int clientId = 52;
        User clientObj = (User) User.find.where().eq("id",clientId).findUnique();

        Equipe clientEquipe = clientObj.getEquipe();

        List<Fonctionnalite> fonctionnaliteList = null;
        fonctionnaliteList = Fonctionnalite.find.where().eq("equipe", clientEquipe).findList();

        Equipe equipeObj = (Equipe) Equipe.find.where().eq("id",clientEquipe.getId()).findUnique();


        //  ----------------------------
        // |        Formulaire         |
        //  ----------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formEquipe = dynamicForm.get("equipe");
        String formNom = dynamicForm.get("nom");

        Equipe formEquipeObj = (Equipe) Equipe.find.where().eq("nom",formEquipe).findUnique();

        Fonctionnalite fonctionnaliteForm = (Fonctionnalite) Fonctionnalite.find.where().eq("nom", formNom).eq("equipe", formEquipeObj).findUnique();
        fonctionnaliteForm.setTrouve(true);
        fonctionnaliteForm.save();

        formSend = true;


        return ok(clientFonctionnaliteValider.render(fonctionnaliteList, equipeObj, formSend));

    }
}

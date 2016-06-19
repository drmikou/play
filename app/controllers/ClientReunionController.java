package controllers;

import models.Groupe;
import models.Equipe;
import models.Reunion;
import models.User;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;

public class ClientReunionController extends Controller {


    public Result clientReunion(){

        int clientId = 52;
        User clientObj = null;
        clientObj = (User) User.find.where().eq("id",clientId).findUnique();
        Equipe clientEquipe = null;
        clientEquipe = clientObj.getEquipe();

        //  ----------------------------
        // |      Liste des réunions     |
        //  ----------------------------
        List<Reunion> mesReunions = null;
        if( clientEquipe != null) {
            mesReunions = Reunion.find.where().eq("equipe", clientEquipe).findList();
        }


        return ok(clientReunion.render(mesReunions));

    }


    public Result clientReunionSubmit(){

        int clientId = 52;
        User clientObj = null;
        clientObj = (User) User.find.where().eq("id",clientId).findUnique();

        //  ----------------------------
        // |      Liste des réunions     |
        //  ----------------------------
        List<Reunion> mesReunions = null;
        mesReunions = Reunion.find.where().eq("equipe",clientObj.getEquipe()).findList();

        return ok(clientReunion.render(mesReunions));

    }
}

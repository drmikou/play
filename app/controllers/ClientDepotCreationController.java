package controllers;

import models.*;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.avaje.ebean.Ebean;

public class ClientDepotCreationController extends Controller {


    public Result clientDepotCreation(){

        boolean formSend = false;
        int clientId = 52;
        User clientObj = (User) User.find.where().eq("id",clientId).findUnique();

        Equipe clientEquipe = clientObj.getEquipe();

        Equipe equipeObj = (Equipe) Equipe.find.where().eq("id",clientEquipe.getId()).findUnique();


        return ok(clientDepotCreation.render(equipeObj,formSend));

    }


    public Result clientDepotCreationSubmit(){

        boolean formSend = false;

        int clientId = 52;
        User clientObj = (User) User.find.where().eq("id",clientId).findUnique();

        Equipe clientEquipe = clientObj.getEquipe();

        Equipe equipeObj = (Equipe) Equipe.find.where().eq("id",clientEquipe.getId()).findUnique();


        //  ----------------------------
        // |        Formulaire         |
        //  ----------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formEquipe = dynamicForm.get("equipe");
        String formNom = dynamicForm.get("nom");
        String formDate = dynamicForm.get("date");
        String formCommentaire = dynamicForm.get("commentaire");

        Equipe formEquipeObj = null;
        formEquipeObj = (Equipe) Equipe.find.where().eq("nom",formEquipe).findUnique();

        Date formDateObj = null;
        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            formDateObj = format.parse(formDate);
        }
        catch(ParseException e)
        {

        }

        Depot depotObj = new Depot();
        depotObj.equipe = formEquipeObj;
        depotObj.nom = formNom;
        depotObj.commentaire = formCommentaire;
        depotObj.datedepot = formDateObj;
        depotObj.save();

        formSend = true;

        return ok(clientDepotCreation.render(equipeObj, formSend));

    }
}

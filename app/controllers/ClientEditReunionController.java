package controllers;

import models.Groupe;
import models.Equipe;
import models.Reunion;
import models.User;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.avaje.ebean.Ebean;

public class ClientEditReunionController extends Controller {


    public Result clientEditReunion(){

        int clientId = 52;
        User clientObj = null;
        clientObj = (User) User.find.where().eq("id",clientId).findUnique();
        Equipe clientEquipe = null;
        clientEquipe = clientObj.getEquipe();

        Boolean formSend = false;
        Boolean formSend2 = false;

        Reunion reunionEdit = null;

        //  ----------------------------
        // |      Liste des réunions     |
        //  ----------------------------
        List<Reunion> mesReunions = null;
        if( clientEquipe != null) {
            mesReunions = Reunion.find.where().eq("equipe", clientEquipe).findList();
        }


        return ok(clientEditReunion.render(mesReunions, formSend, reunionEdit, formSend2));

    }


    public Result clientEditReunionSubmit(){

        int clientId = 52;
        User clientObj = null;
        clientObj = (User) User.find.where().eq("id",clientId).findUnique();
        Equipe clientEquipe = null;
        clientEquipe = clientObj.getEquipe();

        Boolean formSend = false;
        Boolean formSend2 = false;

        //  ----------------------------
        // |      Liste des réunions     |
        //  ----------------------------
        List<Reunion> mesReunions = null;
        if( clientEquipe != null) {
            mesReunions = Reunion.find.where().eq("equipe", clientEquipe).findList();
        }


        //  ----------------------------
        // |         Formulaire         |
        //  ----------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formReunion = dynamicForm.get("reunion");

        Reunion reunionEdit = (Reunion) Reunion.find.where().eq("nom",formReunion).findUnique();


        formSend = true;
        formSend2 = false;




        return ok(clientEditReunion.render(mesReunions, formSend, reunionEdit, formSend2));

    }

    public Result clientEdit2ReunionSubmit(){

        int clientId = 52;
        User clientObj = null;
        clientObj = (User) User.find.where().eq("id",clientId).findUnique();
        Equipe clientEquipe = null;
        clientEquipe = clientObj.getEquipe();

        Boolean formSend = false;
        Boolean formSend2 = false;

        //  ----------------------------
        // |      Liste des réunions     |
        //  ----------------------------
        List<Reunion> mesReunions = null;
        if( clientEquipe != null) {
            mesReunions = Reunion.find.where().eq("equipe", clientEquipe).findList();
        }


        //  ----------------------------
        // |         Formulaire         |
        //  ----------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formReunion = dynamicForm.get("reunion");
        String formDate = dynamicForm.get("date");
        String formDateDebut = dynamicForm.get("dateDebut");
        String formDateFin = dynamicForm.get("dateFin");
        String formCommentaire = dynamicForm.get("formCommentaire");

        Date date3 = null;
        Date date4 = null;
        DateFormat format = new SimpleDateFormat("HH:mm");

        try{
            date3 = format.parse(formDateDebut);
        }
        catch(ParseException e)
        {

        }
        try{
            date4 = format.parse(formDateFin);
        }
        catch(ParseException e)
        {

        }
        System.out.println("formDateDebut"+formDateDebut);
        System.out.println("date3"+date3);
        System.out.println("formdatefin"+formDateFin);
        System.out.println("date4"+date4);

        Reunion reunionEdit = (Reunion) Reunion.find.where().eq("nom",formReunion).findUnique();
        if( date3 != null) {
            reunionEdit.setDatedebut(date3);
       }
        if( date4 != null){
            reunionEdit.setDatefin(date4);
        }
        if( !"".equals(formCommentaire) ){
            reunionEdit.setCommentaire(formCommentaire);
        }
        reunionEdit.save();

        System.out.println("reunionEdit"+reunionEdit.getDatefin());

        //  ----------------------------------------
        // |           Variable d'affichage          |
        //  ----------------------------------------
        formSend = true;
        formSend2 = true;

        return ok(clientEditReunion.render(mesReunions, formSend, reunionEdit, formSend2));

    }
}

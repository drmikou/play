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

        Reunion reunionEdit = null;

        //  ----------------------------
        // |      Liste des réunions     |
        //  ----------------------------
        List<Reunion> mesReunions = null;
        if( clientEquipe != null) {
            mesReunions = Reunion.find.where().eq("equipe", clientEquipe).findList();
        }


        return ok(clientEditReunion.render(mesReunions, formSend, reunionEdit));

    }


    public Result clientEditReunionSubmit(){

        int clientId = 52;
        User clientObj = null;
        clientObj = (User) User.find.where().eq("id",clientId).findUnique();
        Equipe clientEquipe = null;
        clientEquipe = clientObj.getEquipe();

        Boolean formSend = false;

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




        return ok(clientEditReunion.render(mesReunions, formSend, reunionEdit));

    }

    public Result clientEdit2ReunionSubmit(){

        int clientId = 52;
        User clientObj = null;
        clientObj = (User) User.find.where().eq("id",clientId).findUnique();
        Equipe clientEquipe = null;
        clientEquipe = clientObj.getEquipe();

        Boolean formSend = false;

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

        Reunion reunionEdit = (Reunion) Reunion.find.where().eq("nom",formReunion).findUnique();

        Date date2 = null;
        Date date3 = null;
        Date date4 = null;

        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date2 = format.parse(formDate);

            DateFormat format2 = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            date3 = format2.parse(formDateDebut);

            date4 = format2.parse(formDateFin);
        }
        catch(ParseException e)
        {

        }

        if( formDateDebut.equals("")){
            reunionEdit.setDatedebut(date3);
        }
        if( formDateFin.equals("")){
            reunionEdit.setDatefin(date4);
        }
        if( !formCommentaire.equals("") ){
            reunionEdit.setCommentaire(formCommentaire);
        }

        reunionEdit.save();

        formSend = true;




        return ok(clientEditReunion.render(mesReunions, formSend, reunionEdit));

    }
}

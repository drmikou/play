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

/**
 * Created by Thomas on 19/05/2016.
 */
public class AdminRegisterController extends Controller {

    public Result adminRegister(){

        boolean formSend = false;

        return ok(adminRegister.render(formSend));

    }


    public Result adminRegisterSubmit(){

        boolean formSend = false;

        // Récupération des champs du formulaire-
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formNom = dynamicForm.get("nom");
        String formPrenom = dynamicForm.get("prenom");

        // Création d'un nouveau client
        User userObj = new User();
        userObj.nom = formNom;
        userObj.prenom = formPrenom;
        userObj.role = "client";
        userObj.save();

        // On passe la variable formSend pour afficher le bon envoi du formulaire
        formSend = true;

        return ok(adminRegister.render(formSend));

    }
}

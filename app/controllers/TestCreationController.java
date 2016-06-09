package controllers;

import models.Promotion;
import models.Session;
import models.Groupe;
import models.Equipe;
import models.User;
import models.Projet;
import models.Depot;
import models.Reunion;

import play.mvc.*;

import views.html.*;
import java.util.*;
import com.avaje.ebean.Ebean;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class TestCreationController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>. test
     */

    public Result testCreation(){
        //  -----------------------------------
        // |     Création de variables test    |
        //  -----------------------------------

        User userPresence = (User) User.find.byId("1");
        if( userPresence == null) {
            // On crée un promotion
            Promotion promotionObj = new Promotion();
            int annee = 2016;
            promotionObj.annee = annee;
            promotionObj.save();

            // On crée deux sessions associées à la promotion
            Session sessionObj1 = new Session();
            sessionObj1.nom = "Session1";
            sessionObj1.promotion = promotionObj;
            sessionObj1.save();

            Session sessionObj2 = new Session();
            sessionObj2.nom = "Session2";
            sessionObj2.promotion = promotionObj;
            sessionObj2.save();

            // On crée deux groupes par session
            Groupe groupeObj11 = new Groupe();
            groupeObj11.nom = "Groupe11";
            groupeObj11.session = sessionObj1;
            groupeObj11.save();

            Groupe groupeObj12 = new Groupe();
            groupeObj12.nom = "Groupe12";
            groupeObj12.session = sessionObj1;
            groupeObj12.save();

            Groupe groupeObj21 = new Groupe();
            groupeObj21.nom = "Groupe21";
            groupeObj21.session = sessionObj2;
            groupeObj21.save();

            Groupe groupeObj22 = new Groupe();
            groupeObj22.nom = "Groupe22";
            groupeObj22.session = sessionObj2;
            groupeObj22.save();

            for( int i = 0; i <= 50; i++){
                User userObj = new User();
                userObj.nom = "userNom"+i;
                userObj.prenom = "userPrenom"+i;
                userObj.role = "eleve";
                userObj.save();
            }

        }



        return ok(testCreation.render());
    }

}



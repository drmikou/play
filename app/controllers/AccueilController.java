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
public class AccueilController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>. test
     */

    public Result accueil(){

        //  -----------------
        // |     Variable    |
        //  -----------------
        // Utilisateur
            // On récupère la variable de session userId
            int userId = 2;
            // On recherche l'utilisateur à partir de userId
            User user = (User) User.find.byId(userId);


        //  -----------------
        // |    Mon équipe   |
        //  -----------------
        List<User> viewMonequipe = null;
        Equipe userEquipe = null;
        // On récupère tous les utilisateurs d'une même équipe
            // On vérifie si user existe bien dans la bdd
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            userEquipe = user.getEquipe();
            // On crée une liste à partir des élèves d'une même équipe
            viewMonequipe = User.find.where().eq("equipe", userEquipe).findList();
        }

        //  -----------------
        // |    Mes dates    |
        //  -----------------
        List<Reunion> viewMesdates = null;
        // On récupère tous les utilisateurs d'une même équipe
        // On vérifie si user existe bien dans la bdd
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            userEquipe = user.getEquipe();
            // On crée une liste à partir des élèves d'une même équipe
            viewMesdates = Reunion.find.where().eq("equipe", userEquipe).findList();
        }

        String[] visualisationArray = new String[2];
        visualisationArray[0] = "0";
        visualisationArray[1] = "1";


        return ok(accueil.render(viewMonequipe, viewMesdates, visualisationArray, userEquipe));
    }

}



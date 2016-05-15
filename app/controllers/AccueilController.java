package controllers;

import models.Projet;
import models.User;
import models.Equipe;
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

        // Ajouter
        /*Projet projet = new Projet();
        projet.nom = "test";
        projet.save();

        Equipe equipeObj = new Equipe();
        equipeObj.nom = "test";
        equipeObj.save();

        User userObj = new User();
        userObj.nom = "test";
        userObj.prenom = "test";
        userObj.equipe = equipeObj;
        userObj.save();*/

        // Editer
        /*Projet editProjet = (Projet) Projet.find.byId("1");
        editProjet.nom = "edi1t";
        editProjet.save();*/

        // Supprimer
        /*Projet suppProjet = (Projet) Projet.find.byId("2");
        if( suppProjet != null)
        {
            Ebean.delete(suppProjet);
        }*/

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
        // On récupère tous les utilisateurs d'une même équipe
            // On vérifie si user existe bien dans la bdd
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            Equipe userEquipe = user.getEquipe();
            // On crée une liste à partir des élèves d'une même équipe
            viewMonequipe = User.find.where().eq("equipe", userEquipe).findList();
        }


        String[] visualisationArray = new String[2];
        visualisationArray[0] = "0";
        visualisationArray[1] = "1";


        return ok(accueil.render(viewMonequipe, visualisationArray));
    }

}



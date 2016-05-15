package controllers;

import models.Projet;
import models.User;
import models.Equipe;
import play.mvc.*;

import views.html.*;

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
        Projet projet = new Projet();
        projet.nom = "test";
        projet.save();

        // Editer
        Projet editProjet = (Projet) Projet.find.byId("1");
        editProjet.nom = "edi1t";
        editProjet.save();

        // Supprimer
        Projet suppProjet = (Projet) Projet.find.byId("2");
        if( suppProjet != null)
        {
            Ebean.delete(suppProjet);
        }

        //  -----------------
        // |     Variable    |
        //  -----------------
        // Utilisateur
            // On récupère la variable de session userId
            int userId = 0;
            // On recherche l'utilisateur à partir de userId
            User user = (User) User.find.byId(userId);
        // Equipe
            // On récupère l'équipe de l'utilisateur
            Equipe userEquipe = user.getEquipe();


        //  -----------------
        // |    Mon équipe   |
        //  -----------------
        // On récupère tous les utilisateurs d'une même équipe
            User[] usersEquipe = ;
        // Déclaration de l'array à passer dans la vue
            String[] viewMonequipe = new String[2];

            viewMonequipe[0] = "0";
            viewMonequipe[1] = "1";



        String[] visualisationArray = new String[2];
        visualisationArray[0] = "0";
        visualisationArray[1] = "1";


        return ok(accueil.render(viewMonequipe, visualisationArray));
    }

}



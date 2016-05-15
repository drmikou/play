package controllers;

import models.Projet;
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



        String test = "dd";

        return ok(accueil.render(test));
    }

}



package controllers;

import models.Equipe;
import models.User;
import models.Projet;
import play.mvc.*;

import views.html.*;

import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ClientController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>. test
     */

    public Result accueil(){

        int clientId = 52;
        User viewClient = (User) User.find.where().eq("id",clientId).findUnique();
        //  ----------------------------
        // |      Liste des élèves      |
        //  ----------------------------
        List<User> viewEleve = null;
        // On récupère toutes les équipes
        viewEleve = User.find.where().eq("role","eleve").findList();

        //  ----------------------------
        // |      Liste des équipes     |
        //  ----------------------------
        List<Equipe> viewEquipe = null;
        // On récupère toutes les équipes
        viewEquipe = Equipe.find.findList();

        //  ----------------------------
        // |      Liste des projets     |
        //  ----------------------------
        List<Projet> viewProjet = null;
        // On récupère tous les projets
        viewProjet = Projet.find.findList();

        return ok(client.render(viewClient, viewEleve, viewEquipe, viewProjet));
    }

}


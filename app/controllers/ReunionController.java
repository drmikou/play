package controllers;

import models.Equipe;
import models.Reunion;
import models.User;
import play.mvc.*;
import views.html.reunion;

import java.util.List;

/**
 * Created by benoitdufour on 13/05/2016.
 */
public class ReunionController extends Controller {

    public Result reunion(){

        //  -----------------
        // |     Variable    |
        //  -----------------
        // Utilisateur
        // On récupère la variable de session userId
        int userId = 2;
        // On recherche l'utilisateur à partir de userId
        User user = (User) User.find.byId(userId);

        //  --------------------------------
        // |     Equipe de l'utilisateur    |
        //  --------------------------------
        Equipe userEquipe = null;
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            userEquipe = user.getEquipe();
        }

        //  -----------------
        // |     Réunion     |
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

        return ok(reunion.render(viewMesdates));
    }

}

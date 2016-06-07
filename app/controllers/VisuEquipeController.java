package controllers;

import models.*;
import play.mvc.*;
import views.html.*;

import java.util.List;

/**
 * Created by benoitdufour on 13/05/2016.
 */
public class VisuEquipeController extends Controller {

    public Result visuEquipe(){

        String test = "dd";

        //  -----------------
        // |     Variable    |
        //  -----------------
        // Utilisateur
        // On récupère la variable de session userId
        int userId = 1;
        // On recherche l'utilisateur à partir de userId
        User user = (User) User.find.byId(userId);


        //  ---------------------
        // |       Nom équipe    |
        //  ---------------------
        String nomEquipe = null;
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            Equipe userEquipe = user.getEquipe();
            nomEquipe = userEquipe.getNom();
        }

        //  -----------------
        // |     Equipe      |
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

        //  ---------------------
        // |       Réunion       |
        //  ---------------------
        List<Reunion> viewMesreunions = null;
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            Equipe userEquipe = user.getEquipe();
            viewMesreunions = Reunion.find.where().eq("equipe", userEquipe).findList();
        }

        //  ----------------------------
        // |       Fonctionnalité       |
        //  ----------------------------
        List<Fonctionnalite> viewMesfonctionnalites = null;
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            Equipe userEquipe = user.getEquipe();
            viewMesfonctionnalites = Fonctionnalite.find.where().eq("equipe", userEquipe).findList();
        }

        //  ----------------------------
        // |       Compte rendu         |
        //  ----------------------------
        List<Depot> viewMesdepots = null;
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            Equipe userEquipe = user.getEquipe();
            viewMesdepots = Depot.find.where().eq("equipe", userEquipe).findList();
        }

        return ok(visuEquipe.render(viewMonequipe, nomEquipe, viewMesreunions, viewMesfonctionnalites, viewMesdepots));
    }

}

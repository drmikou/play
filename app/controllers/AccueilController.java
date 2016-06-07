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
        //  -----------------------------------
        // |     Création de variables test    |
        //  -----------------------------------
        User userPresence = (User) User.find.byId("1");
        if( userPresence == null)
        {
            Promotion promotionObj = new Promotion();
            //promotionObj.annee = int 2016;
            promotionObj.save();

            Session sessionObj = new Session();
            sessionObj.nom = "testSessionNom";
            sessionObj.promotion = promotionObj;
            sessionObj.save();

            Groupe groupeObj = new Groupe();
            groupeObj.nom = "testGroupeNom";
            groupeObj.session = sessionObj;
            groupeObj.save();

            Equipe equipeObj = new Equipe();
            equipeObj.nom = "testEquipeNom";
            equipeObj.groupe = groupeObj;
            equipeObj.save();

            User userObj = new User();
            userObj.nom = "testNom";
            userObj.prenom = "testPrenom";
            userObj.equipe = equipeObj;
            userObj.role = "user";
            userObj.save();

            User user2Obj = new User();
            user2Obj.nom = "test2Nom";
            user2Obj.prenom = "test2Prenom";
            user2Obj.equipe = equipeObj;
            user2Obj.role = "user";
            user2Obj.save();

            User user3Obj = new User();
            user3Obj.nom = "test3Nom";
            user3Obj.prenom = "test3Prenom";
            user3Obj.equipe = equipeObj;
            user3Obj.role = "user";
            user3Obj.save();

            Projet projet = new Projet();
            projet.nom = "testProjetNom";
            projet.equipe = equipeObj;
            projet.save();

            Depot depot = new Depot();
            depot.nom = "testDepotNom";
            depot.equipe = equipeObj;
            depot.commentaire = "testDepotCommentaire commentaire";
            depot.save();

            Reunion reunion = new Reunion();
            reunion.nom = "reunionNom";
            reunion.equipe = equipeObj;
            reunion.date = new Date(2016, 01, 01);
            reunion.save();
        }



        // Editer
        /*Projet editProjet = (Projet) Projet.find.byId("1");
        editProjet.nom = "azert";
        editProjet.save();*/
        int userId2 = 3;
        User usertest = (User) User.find.byId(userId2);
        usertest.prenom="dedede";
        usertest.update();

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
            int userId = 1;
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

        //  -----------------
        // |    Mes dates    |
        //  -----------------
        List<Reunion> viewMesdates = null;
        // On récupère tous les utilisateurs d'une même équipe
        // On vérifie si user existe bien dans la bdd
        if(user != null) {
            // On récupère l'équipe de l'utilisateur
            Equipe userEquipe = user.getEquipe();
            // On crée une liste à partir des élèves d'une même équipe
            viewMesdates = Reunion.find.where().eq("equipe", userEquipe).findList();
        }

        String[] visualisationArray = new String[2];
        visualisationArray[0] = "0";
        visualisationArray[1] = "1";


        return ok(accueil.render(viewMonequipe, viewMesdates, visualisationArray));
    }

}



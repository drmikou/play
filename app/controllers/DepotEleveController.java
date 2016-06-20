package controllers;

import models.Depot;
import models.Equipe;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import java.io.File;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class DepotEleveController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>. test
     */

    public Result depot(){

        Boolean formSend = false;

        //  -------------------------------------
        // |      Variable de l'utilisateur      |
        //  -------------------------------------
        String userId = "2";
        User userObj = (User) User.find.where().eq("id", userId).findUnique();
        Equipe equipeObj = userObj.getEquipe();

        //  ----------------------------
        // |      Liste des dépots     |
        //  ----------------------------
        List<Depot> viewDepot = null;
        // On récupère toutes les équipes
        viewDepot = Depot.find.where().eq("equipe", equipeObj).findList();

        return ok(depot.render(formSend, viewDepot));
    }

    public Result depotSubmit(){

        Boolean formSend = false;

        //  -------------------------------------
        // |      Variable de l'utilisateur      |
        //  -------------------------------------
        String userId = "2";
        User userObj = (User) User.find.where().eq("id", userId).findUnique();
        Equipe equipeObj = userObj.getEquipe();

        //  ---------------------------
        // |      Liste des dépots     |
        //  ---------------------------
        List<Depot> viewDepot = null;
        // On récupère toutes les équipes
        viewDepot = Depot.find.where().eq("equipe", equipeObj).findList();

        //  ----------------------------------
        // |      Traitement du formulaire     |
        //  -----------------------------------
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String formNom = dynamicForm.get("nom");


        Http.MultipartFormData body = request().body().asMultipartFormData();
        if(body == null)
        {
            return badRequest("Invalid request, required is POST with enctype=multipart/form-data.");
        }

        Http.MultipartFormData.FilePart filePart = body.getFile("file");
        if(filePart == null)
        {
            return badRequest("Invalid request, no file has been sent.");
        }


        File file = (File) filePart.getFile();

        return ok(depot.render(formSend, viewDepot));
    }


}


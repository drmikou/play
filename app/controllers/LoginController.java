package controllers;

import play.mvc.*;

import views.html.*;
import play.data.DynamicForm;
import play.data.Form;
import java.util.Arrays;
import java.util.List;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
/**
import security.EPoste;
import be.objectify.deadbolt.java.actions.SubjectNotPresent;
import be.objectify.deadbolt.java.actions.SubjectPresent;**/



/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class LoginController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>. test
     */
    public Result login() {




        String test = "test";

        return ok(login.render(test));
    }

    public Result logUser(String userId, String role) {
        session("userId", userId);
        //j'ai rajouté role pour pouvoir en fonction qu'il prenne la valeur "admin","eleve" ou "client"
        // çà affiche des pges ou pas/permissions
        session("role", role);
        User user = User.find.byId(userId);
        return ok(user.firstName + " logged");
    }

    public Result logout() {
        session().clear();
        return redirect(controllers.AccueilController.accueil());
    }


    public static User getCurrentUser() {
        String userId = session().get("userId");
        String role = session().get("role");

        if (userId == null)
            return null;

        User currentUser = User.find.byId(userId);

        if (currentUser == null)
            return null;
        return currentUser;

    }
//Cette partie çà gère la durée des sessions je crois, mais le code qui définit la durée et tt est pas dans ce controller
    @SubjectPresent
    public Result sp()
    {
        String message = "subj IS present";
        return ok(message);
    }

    @SubjectNotPresent
    public Result snp()
    {
        return ok(session("userId") + "subj NOT present");
    }

    @SubjectPresent
    public Result who()
    {
        String msg = "";
        User u = User.find.byId(session("userId"));

        msg += u.toString() + "\n";
        msg += u.firstName + "\n";

        return ok(msg);
    }

    public Result listRole()
    {
        List<EPoste> allList = Arrays.asList(EPoste.values());

        return ok(allList.toString());
    }
}


    /*public Result loginSubmit()
    {

        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String test = dynamicForm.get("Email");

        return ok(login.render(test));
    }*/


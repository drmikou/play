package controllers;

import play.mvc.*;

import views.html.*;
import play.data.DynamicForm;
import play.data.Form;
//import javax.servlet.http.*;
//import javax.servlet.http.HttpServlet;
import models.UserRegister;
import ldap.LDAPObject;
import ldap.LDAPaccess;


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

    public Result loginSubmit()
    {
        String test = "test";

        // Formulaire
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        //on récupère les valeurs entrées login et password
        String l = dynamicForm.get("login");
        String p = dynamicForm.get("password");


        LDAPObject reponse =null;
        //on test leur existence dans ldap
        try {
            reponse = LDAPaccess.LDAPget(l, p);
        } catch (Exception e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }

        long id = Integer.parseInt(reponse.getNumber());
        //si ils existent
        if(reponse!=null){
            //instanciation de l'obj nwUser de la table "User"
            UserRegister nwUserRegister = new UserRegister();

            //on remplie l'objet nwUser de type "User" avec les données récupérées dans Ldap
            nwUserRegister.setId(id);
            String firstname = reponse.getNom().split(" ")[0];
            nwUserRegister.setFirstname(firstname);
            nwUserRegister.setLastname(reponse.getNomFamille());
            nwUserRegister.setMail(reponse.getMail());
            nwUserRegister.setRole(reponse.getType());
            nwUserRegister.setLoginIsep(reponse.getLogin());
            nwUserRegister.setPassword(p);
            nwUserRegister.setNumberIsep(reponse.getNumber());

            // On crée un nouveau User dans notre table "User"
            UserRegister.createUserRegister(id, firstname, reponse.getNomFamille(), reponse.getMail(), reponse.getType(), reponse.getLogin(), p, reponse.getNumber());

            // On associe les variables de sessions
            UserRegister createSessionUserRegister = (UserRegister) UserRegister.find.where().eq("id", nwUserRegister.getId()).findUnique();
            session("connected", "true");
            String sessionId = Long.toString(createSessionUserRegister.getId());
            session("id", sessionId);
            session("firstname", createSessionUserRegister.getFirstname());
            session("lastname", createSessionUserRegister.getLastname());
            session("role", createSessionUserRegister.getRole() );

            test = sessionId;
            return ok(login.render(test));
        }
        else
        {
            return ok(login.render(test));
        }

    }

    public Result logout() {
        session().clear();
        return ok(login.render(""));
    }
}

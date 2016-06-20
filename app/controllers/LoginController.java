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

            //on crée un nouveau User dans notre table "User"
            UserRegister.createUserRegister(id, firstname, reponse.getNomFamille(), reponse.getMail(), reponse.getType(), reponse.getLogin(), p, reponse.getNumber());

            //on créé une nouvelle session et le mec est co
            session("connected", "true");
            /*HttpSession s = request.getSession(true);
            UserRegister createSessionUserRegister = UserRegister.find(nwUserRegister.getId());
            s.setAttribute("connected", "true");
            s.setAttribute("Id",createSessionUserRegister.getId() );
            s.setAttribute("firstname",createSessionUserRegister.getFirstname());
            s.setAttribute("name", createSessionUserRegister.getLastname());
            s.setAttribute("role", createSessionUserRegister.getRole());*/

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
    // Code Ldap du poto
    public User Connect(String login, String mdp) throws ServiceException {
        // Need to be tested
        error = null;
        LDAPaccess access = new LDAPaccess();
        try {
            LDAPObject userLdap = access.LDAPget(login, mdp);
            if (userLdap == null) {
                error = "Login invalide";
                return null;
            }
            User userDb = userRepository.findByLogin(login);
            if(userDb == null){
                error = "Utilisateur non inscrit dans la base de donnée. Veuillez contacter votre responsable de parcours.";
                return null;
            }
            if((userDb.getFname() == null)|| (userDb.getLname() == null))
                userDb.Complete(userLdap.getNumber(), userLdap.getPrenom(), userLdap.getNomFamille(), userLdap.getMail(), userLdap.getType());
            return userDb;
        } catch (Exception e) {
            error = "Connexion impossible. Veuillez contacter l'administrateur.";
            e.printStackTrace();
            return null;
        }
    }


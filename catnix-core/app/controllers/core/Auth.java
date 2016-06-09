package controllers.core;


import java.util.Arrays;
import java.util.List;

import models.core.User;
import play.mvc.Controller;
import play.mvc.Result;
import security.EPoste;
import views.html.core.login;
import be.objectify.deadbolt.java.actions.SubjectNotPresent;
import be.objectify.deadbolt.java.actions.SubjectPresent;


public class Auth extends Controller {


	public Result login() {
        return ok(login.render());
    }

    /*public Result authenticate() {

        return null;
    }*/

    public Result logUser(String userId) {
    	session("userId", userId);
    	User user = User.find.byId(userId);
        return ok(user.firstName + " logged");
    }

    public Result logout() {
        session().clear();
        return redirect(controllers.core.routes.Demo.index());
    }


    public static User getCurrentUser() {
    	String userId = session().get("userId");

    	if (userId == null)
            return null;

    	User currentUser = User.find.byId(userId);

    	if (currentUser == null)
            return null;
    	return currentUser;

    }

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

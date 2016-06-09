package controllers.Login;

import com.fasterxml.jackson.databind.JsonNode;
import ldapix.LdapixAccess;
import models.User;
import play.Logger;
import play.data.Form;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

public class LdapixLogin extends Controller {

    public F.Promise<Result> authenticate() {
        String username = Form.form().bindFromRequest().get("username");
        String password = Form.form().bindFromRequest().get("password");

        LdapixAccess loggedUser = new LdapixAccess();
        F.Promise<JsonNode> resultPromise = loggedUser.connect(username, password);

        return resultPromise.map(
            new F.Function<JsonNode, Result>() {
                public Result apply(JsonNode response) {
                    if (response.get("result") != null &&
                            String.valueOf(response.get("result")).equals("true")) {
                        User user = User.findByLoginISEP(username);
                        Logger.info("LDAPIX Login user " + username);
                        if (user != null) {
                            session("userId", String.valueOf(user.userId));
                            if (session().get("redirectTo") != null) {
                                return redirect(session().get("redirectTo"));
                            } else {
                                return redirect(controllers.core.routes.Demo.index());
                            }
                        }
                    } else {
                        flash("connectionError", "Identifiants invalides");
                    }
                    return redirect(controllers.core.routes.Auth.login());
                }
            }
        );
    }

    public F.Promise<Result> fetchInfo() {
        JsonNode jsonNode = request().body().asJson();
        String username = String.valueOf(jsonNode.get("username"));
        String password = String.valueOf(jsonNode.get("password"));

        username = username.substring(1, username.length()-1);
        password = password.substring(1, username.length()-1);

        LdapixAccess loggedUser = new LdapixAccess();
        F.Promise<JsonNode> resultPromise = loggedUser.connect(username, password);

        return resultPromise.map(
                new F.Function<JsonNode, Result>() {
                    public Result apply(JsonNode response) {
                        return ok(response);
                    }
                }
        );
    }

}

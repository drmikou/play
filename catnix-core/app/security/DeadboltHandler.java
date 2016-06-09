package security;

import controllers.core.routes;
import models.core.User;

import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Optional;

/**
 * DeadBolt 2 is the authorisation system implemented.
 */
/*
 * All request pass throught this handler to controll access.
 * It is set in root project (application.conf)
 */
public class DeadboltHandler extends AbstractDeadboltHandler
{

	public Promise<Optional<Result>> beforeAuthCheck(final Http.Context context) {
        // returning null means that everything is OK.  Return a real result if you want a redirect to a login page or
        // somewhere else
        return Promise.promise(() -> Optional.of(redirect(controllers.core.routes.Auth.login())));
    }
	
	public Promise<Optional<Subject>> getSubject(final Http.Context context)
    {
		String userId = context.session().get("userId");
        // in a real application, the user name would probably be in the session following a login process
		if (userId != null) {
			return Promise.promise(() -> Optional.ofNullable(User.find.byId(userId)));
		} else {
			return Promise.promise(() -> Optional.ofNullable(User.find.byId("-1")));
		}
    }

	@Override
	public Promise<Result> onAuthFailure(final Http.Context context, final String content) {
		String userId = context.session().get("userId");
		if (userId == null || User.find.byId(userId) == null) {
			context.session().put("redirectTo", context.request().path());
			// you can return any result from here - forbidden, etc
			return Promise.promise(() -> redirect(controllers.core.routes.Auth.login()));
		} else {
			return Promise.promise(Results::forbidden);
		}
	}
}

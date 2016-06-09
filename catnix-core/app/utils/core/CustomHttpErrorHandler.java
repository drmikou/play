package utils.core;

import com.typesafe.config.ConfigFactory;
import controllers.core.Auth;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabIssue;
import org.gitlab.api.models.GitlabSession;
import play.Configuration;
import play.Environment;
import play.Logger;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;
import java.util.List;

/**
 * Http error handling, useful in order to get some stats
 * TODO implements these methods
 *
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class CustomHttpErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public CustomHttpErrorHandler(Configuration configuration, Environment environment, OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(configuration, environment, sourceMapper, routes);
    }

    @Override
    public F.Promise<Result> onClientError(Http.RequestHeader request, int statusCode, String message) {
        return super.onClientError(request, statusCode, message);
    }

    @Override
    protected F.Promise<Result> onBadRequest(Http.RequestHeader request, String message) {
        return super.onBadRequest(request, message);
    }

    @Override
    protected F.Promise<Result> onForbidden(Http.RequestHeader request, String message) {
        return super.onForbidden(request, message);
    }

    @Override
    protected F.Promise<Result> onNotFound(Http.RequestHeader request, String message) {
        if (Auth.getCurrentUser() != null) {
            return F.Promise.<Result>pure(Results.notFound(
                    views.html.core.page.throw404.render()
            ));
        } else {
            return F.Promise.<Result>pure(Results.redirect(controllers.core.routes.Auth.login()));
        }
    }

    @Override
    public F.Promise<Result> onServerError(Http.RequestHeader request, Throwable exception) {
        if (ConfigFactory.load("application.conf").getBoolean("gitlab.reportIssues")) {
            try {
                GitlabSession gitlabSession = GitlabAPI.connect("http://git.dev.juniorisep.com", "lourdix", "catnixissuereporter");
                GitlabAPI gitlabAPI = GitlabAPI.connect("http://git.dev.juniorisep.com", gitlabSession.getPrivateToken());
                String description = ExceptionUtils.getStackTrace(exception) + "<br/>";
                if (exception.getCause() != null) {
                    description += "Caused by : <br/>";
                    description += exception.getCause().getMessage() + "<br/>";
                }
                List<GitlabIssue> issues = gitlabAPI.getIssues(gitlabAPI.getProject(11));
                int i = 0;
                boolean issueAlreadyExist = false;
                String issueTitle = "[BUG][PRODUCTION] " + request.method() + " " + request.uri() + " " + exception.toString();
                while (i < issues.size() && !issueAlreadyExist) {
                    GitlabIssue gitlabIssue = issues.get(i);
                    issueAlreadyExist = issueTitle.equals(gitlabIssue.getTitle());
                    i++;
                }
                if (!issueAlreadyExist) {
                    gitlabAPI.createIssue(11, 25, 1, "bug,critical", description, issueTitle);
                } else {
                    GitlabIssue gitlabIssue = issues.get(i - 1);
                    gitlabIssue.setDescription(gitlabIssue.getDescription() + "\n" + "------- \n" + description);
                    gitlabAPI.editIssue(11, gitlabIssue.getId(), 25, 1, "bug,critical", gitlabIssue.getDescription(), gitlabIssue.getTitle(), GitlabIssue.Action.REOPEN);
                }
            } catch (IOException e) {
                Logger.error("An error occurred while trying to log error on gitlab", e);
            }
        }
        return super.onServerError(request, exception);
    }

    @Override
    protected void logServerError(Http.RequestHeader request, UsefulException usefulException) {
        super.logServerError(request, usefulException);
    }

    @Override
    protected F.Promise<Result> onDevServerError(Http.RequestHeader request, UsefulException exception) {
        return super.onDevServerError(request, exception);
    }

    @Override
    protected F.Promise<Result> onProdServerError(Http.RequestHeader request, UsefulException exception) {
        return super.onProdServerError(request, exception);
    }
}

package controllers.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.core.User;
import models.core.UserRegistration;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import formData.core.userRegister.*;
import utils.core.FormFetcher;
import utils.core.graph.GraphSerie;
import views.html.core.page.userRegister.*;
import views.html.core.modelDocument.userRegistration.*;

import javax.inject.Inject;


/**
 * This controller handle user registration
 * 				- add user
 * 				- gather document and informations associated
 * 				- create and save CET
 * 				- keep control on registration requirements
 *
 *
 * @author pelo
 *
 */
/*
 * There are 2 Steps in registration
 * -------------------------------
 * 		1/ Registration -> Documents and Identity
 * 		2/ Confirmation -> Cet Upload
 * 
 * Complete folder is hard coded, it is a constraint but avoid futher
 * issue on consultant mission.
 * 
 * 
 * 
 * Needed: Isep Id
 * ---------------
 * Isep Ldap is a killer feature since no additional password is needed.
 * Beside this, it avoid mistake on name or other informations.
 * 
 * 
 *  
 * Optional but strongly recommanded: File Upload
 * ----------------------------------------------
 * Justificative pieces are by default needed.
 * You can turn it off as president but both are really usefull.
 * 		- File Upload for justifiying ISO audit
 *
 */
@SubjectPresent
public class UserRegister extends Controller {
	
	/*
     * Async Widget
     */
	public Result renderRegistrationConfigurationWidget() {
		Form<NewUserConfigurationData> userConfForm = Form.form(NewUserConfigurationData.class).fill(getCurrentConf());
        return ok(views.html.core.fragment.userRegister.registrationConfigurationWidget.render(userConfForm));
	}
	
	public Result renderTopIncompleteRegistrationWiget(int topCount) {
    	List<UserRegistration> incompleteRegistration = UserRegistration.findTopIncompleteRegistration(topCount);
        return ok(views.html.core.fragment.userRegister.topIncompleteRegistrationWidget.render(incompleteRegistration));
    }
	
	public Result renderTopMissingCetWidget(int topCount) {
    	List<UserRegistration> missingCetList = UserRegistration.findTopMissingCet(topCount);
        return ok(views.html.core.fragment.userRegister.topMissingCetWidget.render(missingCetList));
	}
	
	public Result renderCurrentCotisationGraphWidget() {
        return ok(views.html.core.fragment.userRegister.currentCotisationGraphWidget.render());
    }
	
	public Result renderRegistrationEvolutionGraphWidget() {
        return ok(views.html.core.fragment.userRegister.registrationEvolutionGraphWidget.render());
	}
	
	/*
     * Fragment
     */
	public Result renderCurrentCotisationGraph() {
    	Map<GraphSerie, Double> data = UserRegistration.currentCotisation();
        return ok(views.html.core.fragment.userRegister.currentCotisationGraph.render(data));
    }
	
	public Result renderRegistrationEvolutionGraph(long startTimeStamp, long endTimeStamp) {
		Date startDate = new Date(startTimeStamp);
    	Date endDate = new Date(endTimeStamp);
    	
    	Map<GraphSerie, Map<Double, Double>> data = UserRegistration.registrationEvolution(startDate, endDate);
    	
        return ok(views.html.core.fragment.userRegister.registrationEvolutionGraph.render(data));
	}
	
	
	/* Index
	 * -------
	 */
	/**
	 * Display index
	 */
    public Result index() {
        return ok(index.render());
    }
	
	/*
	 * User Registration Configuration
	 * -------------------------
	 */
	/**
	 * Retrieve current Configuration of new user constraints
	 */
	public static NewUserConfigurationData getCurrentConf() {
		NewUserConfigurationData conf = new NewUserConfigurationData();
		conf.forcedJustifUpload = NewUserData.forcedJustifUpload;
		return conf;
	}
	
	/**
	 * Apply change on Registration Constraints 
	 */
	public Result editRegisterConfigurationPost() {
		Form<NewUserConfigurationData> userConfForm = Form.form(NewUserConfigurationData.class).bindFromRequest();

		if(userConfForm.hasErrors()) {
			return badRequest(index.render());
		} else {
			NewUserData.forcedJustifUpload = userConfForm.get().forcedJustifUpload;
			return redirect(controllers.core.routes.UserRegister.index());
		}
	}


	/*
	 * New User Registration
	 * ---------------------
	 */
	/**
	 * Display form to create new User
	 */
	/*
	 * On this view, there is also acces to the configuration constraints for the president
	 */
	public Result createUser() {
		Form<NewUserData> userForm = Form.form(NewUserData.class);

		return ok(userCreation.render(userForm));
	}

	/**
	 * Apply User Registration
	 */
	public Result createUserPost() {
		Form<NewUserData> userForm = Form.form(NewUserData.class).bindFromRequest();

		if(userForm.hasErrors()) {
			return badRequest(userCreation.render(userForm));
		} else {
			int newRegistrationId = userForm.get().saveRegistration();

			return redirect(controllers.core.routes.UserRegister.confirmUser(String.valueOf(newRegistrationId)));
		}
	}
	
	/**
	 * Display User RegistrationList 
	 */
	public Result registrationList() {
		List<UserRegistration> userRegistrationList = UserRegistration.find.all();
		return ok(registrationTable.render(userRegistrationList));
	}


	/*
	 * New User Confirmation
	 * ---------------------
	 */
	/**
	 * Display user confirmation form
	 */
	public Result confirmUser(String registrationId) {
		UserRegistration registration = UserRegistration.find.byId(registrationId);

		NewUserConfirmationData userConfirmationData = new NewUserConfirmationData(registration);
		Form<NewUserConfirmationData> userConfirmForm = Form.form(NewUserConfirmationData.class).fill(userConfirmationData);

		return ok(confirmUser.render(registration, userConfirmForm));
	}

	/**
	 * Add user confirmation to freshly created user
	 */
	public Result confirmUserPost() {
		Form<NewUserConfirmationData> userConfirmForm = Form.form(NewUserConfirmationData.class).bindFromRequest();
		UserRegistration registration = FormFetcher.fetchById(UserRegistration.find, userConfirmForm, "registrationId");

		if(userConfirmForm.hasErrors()) {
			return badRequest(confirmUser.render(registration, userConfirmForm));
		} else {
			userConfirmForm.get().applyToRegistration(registration);

			flash().put("success", "la cet est validé");

			return redirect(controllers.core.routes.UserRegister.registrationList());
		}
	}

	@Inject	DocumentProcessing documentProcessing;

	/**
	 * Retrieve Cet document form user registration
	 */
	public Result buildCet(String registrationId) {
		UserRegistration newUser = UserRegistration.find.byId(registrationId);
		User president = User.findActivePresident();

		return documentProcessing.asPdf(cet.render(newUser, president));
	}



	/*
	 * Edit user registration
	 * ----------------------
	 */
	/**
	 * Display edit User Registration form 
	 */
	public Result editUserRegistration(String registrationId) {
		UserRegistration registration = UserRegistration.find.byId(registrationId);

		UserRegistrationData registrationData = new UserRegistrationData(registration);
		Form<UserRegistrationData> registrationForm = Form.form(UserRegistrationData.class).fill(registrationData);

		return ok(userRegistrationEdit.render(registration, registrationForm));
	}

	/**
	 * Update user registration
	 */
	public Result editUserRegistrationPost() {
		Form<UserRegistrationData> registrationForm = Form.form(UserRegistrationData.class).bindFromRequest();
		UserRegistration registration = FormFetcher.fetchById(UserRegistration.find, registrationForm, "registrationId");

		if(registrationForm.hasErrors()) {
			return badRequest(userRegistrationEdit.render(registration, registrationForm));
		} else {

			String flashMessage = registrationForm.get().applyToRegistration(registration);

			if (!flashMessage.isEmpty()) {
				//TODO: doesn't work ??
				flash().put("success", "Les documents suivants ont été mis à jours: " + flashMessage);
			} else {
				flash().put("warning", "Aucun document mis à jour");
			}

			return redirect(controllers.core.routes.UserRegister.index());
		}
	}

}

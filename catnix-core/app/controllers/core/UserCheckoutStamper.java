package controllers.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import formData.core.userCheckoutStamper.MandatoryCheckoutData;
import formData.core.userCheckoutStamper.UserCheckoutData;
import models.core.User;
import models.core.UserCheckout;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import security.EPoste;
import utils.core.FormFetcher;
import utils.core.formHelper.KeyValueObject;
import views.html.core.page.userCheckoutStamper.*;


/**
 * This controller manage user leaving the structure:
 * 				- Demissions
 * 				- Demissions Revert
 * 				- End of mandatory
 *
 * @author pelo
 *
 */
/*
 * The leave note
 * --------------
 * This information aim to leave a trace of every old user achievements.
 * It is not a revenge for leaving the structure but rather a way to know who dit what.
 */
@SubjectPresent
public class UserCheckoutStamper extends Controller {

	/* Index
	 * -------
	 */
	/**
	 * Display index
	 */
    public Result index() {
        return ok(index.render());
    }
	
	/* Display
	 * -------
	 */
	/**
	 * Display old User List
	 */
    public Result oldTeamUserList() {
        List<UserCheckout> oldUserList = UserCheckout.findOldTeamUserRegistrationList();
        return ok(oldUserTable.render(oldUserList));
    }


    /*
     * Record Demission
     * ----------------
     */
    /**
     * Display List of user who can quit
     */
    public Result demissionableUserList() {
        List<User> currentTeam = User.findActiveTeam();
        return ok(demissionableUserTable.render(currentTeam));
    }
    
    /**
     * Record a quit for a selected user 
     */
    public Result recordUserDemission(String userId) {
        User user = User.find.byId(userId);
        Form<UserCheckoutData> demissionForm = Form.form(UserCheckoutData.class)
        		.fill(new UserCheckoutData(user));

        return ok(userDemissionRecord.render(user, demissionForm));
    }

    /**
     * Apply a demission 
     */
    public Result recordUserDemissionPost() {
        Form<UserCheckoutData> demissionForm = Form.form(UserCheckoutData.class).bindFromRequest();
        User user = FormFetcher.fetchById(User.find, demissionForm, "userId");

        if(demissionForm.hasErrors()) {
        	return badRequest(userDemissionRecord.render(user, demissionForm));
        } else {
	        demissionForm.get().applyDemissionTo(user);

	        return redirect(controllers.core.routes.UserCheckoutStamper.demissionableUserList());
        }
    }




    /*
     * Revert demission
     * ----------------
     */
    /**
     * Display list of revertable demissison 
     */
    public Result demissionList() {
         List<UserCheckout> demissionList = UserCheckout.findUserRegistrationDemissionList();
         return ok(demissionTable.render(demissionList));
    }
    
    /**
     * Revert a demission 
     */
    public Result revertUserDemission(String checkoutId) {
        UserCheckout checkout = UserCheckout.find.byId(checkoutId);
        checkout.revertDemission();

        return redirect(controllers.core.routes.UserCheckoutStamper.demissionList());
    }

    
    
    /*
     * End of Mandatory
     * ----------------
     */
    /**
     * Display End Of mandatory Form
     */
    public Result endOfMandatory() {
        List<User> a2List = User.findActiveA2();
        User president = User.findActivePresident();
        int leavingConsultantCount = User.findLeavingConsultant(president.promotion).size();
        
        Map<KeyValueObject, Boolean> a1LoginMap = new HashMap<KeyValueObject, Boolean>();
        for(User user : User.findActiveA1()) {
        	a1LoginMap.put(new KeyValueObject(user.login(), user.login() + " - " + user.firstName + " " + user.lastName + " - " + user.posteCsv), false);
        }
        
        Form<MandatoryCheckoutData> endOfMandatoryForm = Form.form(MandatoryCheckoutData.class)
        		.fill(new MandatoryCheckoutData(a2List));

        return ok(endOfMandatoryRecord.render(leavingConsultantCount, a1LoginMap, a2List, endOfMandatoryForm));
    }

    /**
     * Record End of Mandatory 
     */
    public Result endOfMandatoryPost() {
        Form<MandatoryCheckoutData> endOfMandatoryForm = Form.form(MandatoryCheckoutData.class).bindFromRequest();
        List<User> a2List = FormFetcher.fetchByIdList(User.find, endOfMandatoryForm, "userCheckoutList", "userId");
        User president = User.findActivePresident();
        List<User> leavingConsultant = User.findLeavingConsultant(president.promotion);
        List<User> a1List = User.findActiveA1();
        
        Map<KeyValueObject, Boolean> a1LoginMap = new HashMap<KeyValueObject, Boolean>();
        for(User user : a1List) {
        	a1LoginMap.put(new KeyValueObject(user.login(), user.login() + " - " + user.firstName + " " + user.lastName + " - " + user.posteCsv), false);
        }

        if(endOfMandatoryForm.hasErrors()) {
        	return badRequest(endOfMandatoryRecord.render(leavingConsultant.size(), a1LoginMap, a2List, endOfMandatoryForm));
        } else {
        	endOfMandatoryForm.get().applyToUserList(a2List);
        	UserCheckout.checkoutUserList(leavingConsultant, new Date());
        	
        	for(User user : a1List)
        		user.upgradeToA2();
        	
        	User newPresident = User.findInTeamByLogin(endOfMandatoryForm.get().newPresidentLogin);
        	newPresident.addPoste(EPoste.prez);

	        return redirect(controllers.core.routes.UserManager.teamUserRoleList());
        }
    }
}

package controllers.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.core.User;
import models.core.UserCheckout;
import models.core.UserRegistration;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import security.EPoste;
import utils.core.EnumCsvLine;
import utils.core.FormFetcher;
import views.html.core.page.userManager.*;
import formData.core.userManager.UserContactData;
import formData.core.userManager.UserInfoData;


/**
 * This controller manage user account:
 * 				- User Contact and informations
 * 				- User Security Roles
 * 				- Add User to Team
 *
 * @author pelo
 *
 */
/*
 * Secutiry Roles or Status edit and Dynamic Form 
 * ----------------------------------------------
 * We used here Dynamic Form instead of classical
 * Form<formData> combo.
 * It sould be updated.
 * 
 * 
 * 
 * User Status edit
 * ----------------
 * Status should not be edited since other function sould do the modification.
 * Other functionality may be broken by this manual change, you're warned.
 * 
 *
 * 
 * Contact and Information Updates
 * -------------------------------
 * Only an adminstrator can update informations about an user.
 * The user can edit its contact informations.
 *
 */
@SubjectPresent
public class UserManager extends Controller {
	
	/*
     * Async Widget
     */
    public Result renderCurrentUserAccount() {
    	User currentUser = Auth.getCurrentUser();
    	return ok(views.html.core.fragment.userManager.userAccount.render(currentUser));
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
	 * User Security Roles
	 * -------------------
	 */
	/**
	 * Display current team user in order to edit poste
	 */
	public Result teamUserRoleList() {
        List<User> userList = User.findActiveTeam();
		return ok(teamRoleEditTable.render(userList));
	}

	/**
	 * Update security roles for an user
	 */
	public Result updateRolePost() {
		DynamicForm requestData = Form.form().bindFromRequest();

		User user = User.find.byId(requestData.get("userId"));
		
		Set<String> newPosteStrSet = new HashSet<String>();

		for(EPoste role: EPoste.values())
			if(requestData.get(role.toString()) != null)
				newPosteStrSet.add(role.toString());

		user.updatePosteSet(EnumCsvLine.getEnumSet(newPosteStrSet, EPoste.class));

		return redirect(controllers.core.routes.UserManager.allUserList());
	}
	
	
	/*
     * User Status edit
     * ----------------
     */
	/**
	 * Display all user in order to edit status
	 */
	public Result allUserStatusList() {
        List<User> userList = User.find.all();
		return ok(userStatusEditTable.render(userList));
	}

	/**
	 * Update security status for an user
	 */
	public Result updateStatusPost() {
		DynamicForm requestData = Form.form().bindFromRequest();

		User user = User.find.byId(requestData.get("userId"));
        user.updateStatus(requestData.get("active") != null ? true : false,
                          requestData.get("stranger") != null ? true : false,
                          requestData.get("sectInter") != null ? true : false);

		return redirect(controllers.core.routes.UserManager.allUserStatusList());
	}
	
	
	/*
	 * Add User to team
	 * ----------------
	 */
	/**
	 * Display non member user to add CDP
	 */
	public Result potentialA1MemberList() {
        List<UserRegistration> registrationList = UserRegistration.findPotentialA1();
		return ok(addA1Table.render(registrationList));
	}
	
    /**
     * Upgrade security role to a1
     */
    public Result upgradeUserToA1(String userId) {
		User user = User.find.byId(userId);
        user.upgradeToA1();

        return redirect(controllers.core.routes.UserManager.potentialA1MemberList());
    }
    
    /*
	 * Add User to cos
	 * ----------------
	 */
	/**
	 * Display old member user to add Cos
	 */
	public Result potentialCosMemberList() {
        List<UserCheckout> oldUserList = UserCheckout.findPotentialCos();
		return ok(addCosTable.render(oldUserList));
	}
	
    /**
     * Upgrade security role to a1
     */
    public Result upgradeUserToCos(String userId) {
		User user = User.find.byId(userId);
        user.upgradeToCos();

        return redirect(controllers.core.routes.UserManager.potentialCosMemberList());
    }

    
    
	/*
	 * User Profile
	 * ------------
	 */
	/**
	 * Display given user's profile
	 */
	public Result viewUser(String userId) {
		User user = User.find.byId(userId);
		return ok(userView.render(user));
	}


	
	
	/*
	 * User Contact And Information
	 * ------------
	 */
	/**
	 * Display all user in order to edit contact, info
	 */
	public Result allUserList() {
		List<User> userList = User.find.all();
		return ok(userEditTable.render(userList));
	}
	
	/**
	 * Display form to edit user contact info
	 */
	public Result editUserContact(String userId) {
		User user = User.find.byId(userId);
        UserContactData userContactData = new UserContactData(user);
    	Form<UserContactData> userContactForm = Form.form(UserContactData.class).fill(userContactData);

		return ok(userContactEdit.render(user, userContactForm));
	}

	/**
	 * Update user contact info
	 */
	public Result editUserContactPost() {
        Form<UserContactData> userContactForm = Form.form(UserContactData.class).bindFromRequest();
        User user = FormFetcher.fetchById(User.find, userContactForm, "userId");

        if(userContactForm.hasErrors()) {
            return badRequest(userContactEdit.render(user, userContactForm));
        } else {
        	userContactForm.get().applyToUser(user);
            flash().put("success", "Contact mis à jour");

            return redirect(controllers.core.routes.Demo.index());
    	}
    }

	/**
	 * Display form to edit user informations
	 */
	public Result editUserInfo(String userId) {
		User user = User.find.byId(userId);
        UserInfoData userInfoData = new UserInfoData(user);
        
    	Form<UserInfoData> userInfoForm = Form.form(UserInfoData.class).fill(userInfoData);

		return ok(userInfoEdit.render(user, userInfoForm));
	}

	/**
	 * Update user informations
	 */
	public Result editUserInfoPost() {
        Form<UserInfoData> infoForm = Form.form(UserInfoData.class).bindFromRequest();
        User user = FormFetcher.fetchById(User.find, infoForm, "userId");

        if(infoForm.hasErrors()) {
            return badRequest(userInfoEdit.render(user, infoForm));
        } else {
        	infoForm.get().applyToUser(user);
            flash().put("success", "Informations mises à jour");

            return redirect(controllers.core.routes.Demo.index());
    	}
	}

}

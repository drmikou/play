package formData.core.userRegister;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.core.User;
import models.core.UserRegistration;
import play.data.format.Formats;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.Required;
import controllers.core.FileManager;

/**
 * Represents new User informations for form
 */
public class NewUserData {

    public static boolean forcedJustifUpload = true;

	@Required
	@Formats.DateTime(pattern = "dd-MM-yyyy")
	public Date registrationDate;

    //Info
	public String isepId;
    @Required
    public String firstName;
    @Required
    public String lastName;
    @Required
    public int promotion;
    @Required
    public long noSecu;

    @Required
    public boolean withPartner;
    
    @Required
    public boolean isSectInter;
    @Required
    public boolean isStranger;

    //Contact
    @Required
    public String phone;
    @Required
    public String mail;
    @Required
    public String mailAlt;


    @Required
    public String adresse;
    @Required
    public String cp;
    @Required
    public String country;

    //Justification
    public String idJustifFile;
    public String secuJustifFile;
    public String certifScolariteFile;
    public String cotisationFile;
    public String visaFile;

    public NewUserData() {}

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();

        if (NewUserData.forcedJustifUpload) {
            if(!(new File(idJustifFile).exists()))
            	errors.add(new ValidationError("idJustifFile", "Missing Justification pieces"));
            
            if(!(new File(secuJustifFile)).exists())
            	errors.add(new ValidationError("secuJustifFile", "Missing Justification pieces"));
            
            if(!(new File(certifScolariteFile).exists()))
            	errors.add(new ValidationError("certifScolariteFile", "Missing Justification pieces"));
            
            if(!(new File(cotisationFile)).exists())
            	errors.add(new ValidationError("cotisationFile", "Missing Justification pieces"));
            
            if(isStranger && !(new File(visaFile)).exists())
                errors.add(new ValidationError("visaFile", "Missing Justification pieces"));
        }

        return errors.isEmpty() ? null : errors;
    }

    public User buildUser() {
    	User newUser = new User(firstName, lastName, promotion,
                phone, mail, mail, User.buildAdresse(adresse, cp, country),
                "",
                isepId);

        newUser.stranger = isStranger;
        newUser.sectInter = isSectInter;

        return newUser;
    }

    public UserRegistration buildRegistration(User newUser) {
    	int newUserId = newUser.userId;
    	return new UserRegistration(
	        		registrationDate,
					noSecu,
					withPartner,
				 	FileManager.archiveData("idJustif", newUserId, new File(idJustifFile)),
				 	FileManager.archiveData("secuJustif", newUserId, new File(secuJustifFile)),
	            	FileManager.archiveData("certifScolarite", newUserId, new File(certifScolariteFile)),
	            	FileManager.archiveData("cotisation", newUserId, new File(cotisationFile)),
	            	FileManager.archiveData("visa", newUserId, new File(visaFile)),
	            	newUser);
    }

    public int saveRegistration() {
        User newUser = this.buildUser();
        newUser.save(); //needed to build userId, null as long as not saved

        UserRegistration userRegistration = this.buildRegistration(newUser);
        userRegistration.save();

        return userRegistration.registrationId;
    }

}

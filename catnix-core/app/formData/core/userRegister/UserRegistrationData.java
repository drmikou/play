package formData.core.userRegister;

import java.io.File;

import controllers.core.FileManager;
import models.core.User;
import models.core.UserRegistration;
import play.data.validation.Constraints.Required;

/**
 * Represent user registration for form
 */
public class UserRegistrationData {
	
	public int registrationId;
	
    @Required
    public boolean isSectInter;
    @Required
    public boolean isStranger;

    public String idJustifFile;
    public String secuJustifFile;
    public String certifScolariteFile;
    public String cotisationFile;
    public String visaFile;

    public UserRegistrationData() {  }

    public UserRegistrationData(UserRegistration registration) {
    	registrationId = registration.registrationId;
    	
    	User user = registration.user;
        isSectInter = user.sectInter;
        isStranger = user.stranger;
    }
    
    public String applyToRegistration(UserRegistration registration) {
    	int registrationId = registration.registrationId;
    	
    	String flashMessage = "";

    	File tmpFile = null;
    	
        tmpFile = new File(idJustifFile);
        if (tmpFile.exists()) {
            registration.identite = FileManager.archiveData("idJustif", registrationId, tmpFile);
            flashMessage += "Pièce d'identité, ";
        }

        tmpFile = new File(secuJustifFile);
        if (tmpFile.exists()) {
            registration.secu = FileManager.archiveData("secu", registrationId, tmpFile);
            flashMessage += "Justif Sécurité sociale, ";
        }

        tmpFile = new File(certifScolariteFile);
        if (tmpFile.exists()) {
            registration.certifScolarite = FileManager.archiveData("certifScolarite", registrationId, tmpFile);
            flashMessage += "Certificat de scolatité, ";
        }

        tmpFile = new File(cotisationFile);
        if (tmpFile.exists()) {
            registration.cotisation = FileManager.archiveData("cotisation", registrationId, tmpFile);
            flashMessage += "Cotisation, ";
        }

        if (isStranger) {
            tmpFile = new File(visaFile);
            if (tmpFile.exists()) {
                registration.visa = FileManager.archiveData("visa", registrationId, tmpFile);
                flashMessage += "Visa";
            }
        } else {
            registration.visa = null;
        }

        registration.user.sectInter = isSectInter;
        registration.user.stranger = isStranger;

        registration.save();
        
        return flashMessage;
    }

}
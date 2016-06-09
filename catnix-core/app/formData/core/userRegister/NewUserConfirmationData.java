package formData.core.userRegister;

import java.io.File;

import models.core.UserRegistration;
import play.data.validation.Constraints.Required;
import controllers.core.FileManager;

/**
 * Represents user confirmation for form
 */
	public class NewUserConfirmationData {

	public int registrationId;

    @Required
	public String cetFile;

	public NewUserConfirmationData() {}

	public NewUserConfirmationData(UserRegistration registration) {
		registrationId = registration.registrationId;
	}

	public void applyToRegistration(UserRegistration registration) {
		registration.cet = FileManager.archiveDocument("CET",
													registration.registrationId,
													new File(cetFile),
													String.valueOf(registration.user.userId)
													);

		registration.save();
	}
}



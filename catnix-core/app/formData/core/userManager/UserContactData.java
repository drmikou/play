package formData.core.userManager;

import models.core.User;
import play.data.validation.Constraints.Required;

/**
 * Represent user contact info for form
 */
public class UserContactData {

    public int userId;

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

    public UserContactData() {}

    public UserContactData(User user) {
        userId = user.userId;

        phone = user.phone;
        mail = user.mail;
        mailAlt = user.mailAlt;

        adresse = user.getAdresse();
        cp = user.getCp();
        country = user.getCountry();
    }

    public void applyToUser(User user) {
    	user.updateContact(phone, mail, mailAlt, adresse, cp, country);
    }

}

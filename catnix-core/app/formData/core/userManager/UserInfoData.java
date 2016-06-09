package formData.core.userManager;

import models.core.User;
import play.data.validation.Constraints.Required;

public class UserInfoData {

    public int userId;

    @Required
    public String firstName;
    @Required
    public String lastName;
    @Required
    public int promotion;

    public UserInfoData() {}

    public UserInfoData(User user) {
        userId = user.userId;

    	firstName = user.firstName;
    	lastName = user.lastName;
    	promotion = user.promotion;
    }

    public void applyToUser(User user) {
    	user.updateInfo(firstName, lastName, promotion);
    }
}

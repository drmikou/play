package formData.core.userCheckoutStamper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import models.core.User;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;

public class MandatoryCheckoutData {

    @Required
	@Formats.DateTime(pattern = "dd-MM-yyyy")
    public Date endOfMandatoryDate;

    @Valid
    public List<UserCheckoutData> userCheckoutList;

    @Required
    public String newPresidentLogin;

    public MandatoryCheckoutData() {}

    public MandatoryCheckoutData(List<User> userList) {
        userCheckoutList = new ArrayList<UserCheckoutData>();

        for (User user : userList) {
        	UserCheckoutData checkout = new UserCheckoutData(user);
        	checkout.checkoutDate = new Date();
            userCheckoutList.add(checkout);
        }
    }
    
    public void applyToUserList(List<User> userList) {
    	for(int i = 0; i < userList.size(); i++) {
    		UserCheckoutData checkout = userCheckoutList.get(i);
    		checkout.checkoutDate = endOfMandatoryDate;
    		User user = userList.get(i);
    		
    		checkout.applyCheckoutTo(user);
    	}
    }
}

package formData.core.userCheckoutStamper;

import java.util.Date;

import models.core.User;
import models.core.UserCheckout;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;

public class UserCheckoutData {

    public int userId;

    @Required
	@Formats.DateTime(pattern = "dd-MM-yyyy")
    public Date checkoutDate;

    @Required
    public String leaveNote;

    public UserCheckoutData() {}

    public UserCheckoutData(User user) {
    	this.userId = user.userId;
    }

    public void applyDemissionTo(User user) {
    	UserCheckout demission =  new UserCheckout(checkoutDate,
				 								   leaveNote,
				 								   true,
				 								   user);
    	demission.save();
    }

    public void applyCheckoutTo(User user) {
    	UserCheckout checkout =  new UserCheckout(checkoutDate,
				 								   leaveNote,
				 								   false,
				 								   user);
    	checkout.save();
    }
}

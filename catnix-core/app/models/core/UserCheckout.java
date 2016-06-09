package models.core;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;


/**
 * UserCheckout Table represent user departure.
 */
@Entity
public class UserCheckout extends Model {
    public static final Find<String, UserCheckout> find = new Find<String, UserCheckout>(){};

    public void save() {
        user.save();
        super.save();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int checkoutId;

    public Date checkoutDate;
    public boolean demission;

    @Column(columnDefinition = "TEXT")
    public String leaveNote;

    @OneToOne
    public User user;


    /**
     * Constructor
     */
    public UserCheckout(Date checkoutDate, String leaveNote, boolean demission, User user) {
        this.checkoutDate = checkoutDate;

        this.leaveNote = leaveNote;
        this.demission = demission;

        this.user = user;
        user.active = false;
    }
    
    public void revertDemission() {
        this.delete();
        user.active = true;
        user.save();
    }
    
    /**
     * Checkout User List
     */
    public static void checkoutUserList(List<User> userList, Date checkoutDate) {
    	for(User user : userList) {
    		UserCheckout checkout = new UserCheckout(checkoutDate, "regular checkout", false, user);
    		checkout.save();
    	}
    }
    

    /**
	 * Finder
	 */
    /*
     * Expression
     * ----------
     */
    public static List<UserCheckout> descList(ExpressionList<UserCheckout> expressionList) {
    	return expressionList.orderBy("checkoutId").findList();
    }
    
    public static Expression isDemission() {
    	return Expr.eq("demission", true);
	}
    
    
    /*
     * Find
     * ----
     */
    public static List<UserCheckout> findUserRegistrationDemissionList() {
		return descList(find.where().add(isDemission()));
	}

    public static List<UserCheckout> findOldTeamUserRegistrationList() {
		return descList(find.where().in("user", User.findOldTeam()));
	}
    
    public static List<UserCheckout> findPotentialCos() {
		return descList(find.where().and(Expr.not(isDemission()), Expr.in("user", User.findOldTeamExceptCos())));
	}
}

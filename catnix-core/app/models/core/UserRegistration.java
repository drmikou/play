package models.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import utils.core.graph.GraphSerie;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;


/**
 * UserRegistration Table represent user registration.
 */
@Entity
public class UserRegistration extends Model
{
    public static final Find<String, UserRegistration> find = new Find<String, UserRegistration>(){};

    public void save() {
    	this.inscriptionComplete = isInscriptionComplete();
    	
    	user.save();
        super.save();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int registrationId;

    public Date registrationDate;
    
    public long noSecu;
    public boolean withPartner;

    public String cet;

    public String identite;
    public String secu;
    public String certifScolarite;
    public String cotisation;
    public String visa;
    
    //Calculated Data
    private boolean inscriptionComplete;

    @OneToOne
    public User user;


    /**
     * Constructor
     */
    public UserRegistration(Date registrationDate, long noSecu, boolean withPartner, String identite, String secu, String certifScolarite, String cotisation, String visa, User user) {
        this.registrationDate = registrationDate;
        
        this.noSecu = noSecu;
        this.withPartner = withPartner;

        this.identite = identite;
        this.secu = secu;
        this.certifScolarite = certifScolarite;
        this.cotisation = cotisation;
        this.visa = visa;

        this.user = user;
        
        this.inscriptionComplete = isInscriptionComplete();
    }

    /**
     * Test is user inscription is complete
     */
    public boolean isInscriptionComplete() {
        if (this.cet == null || this.identite == null || this.secu == null || this.certifScolarite == null || this.cotisation == null) {
            return false;
        }

        if(this.user.stranger && this.visa == null) {
        	return false;
        }

        return true;
    }
    
    /**
     * Finder 
     */
    /*
     * Expression
     * ----------
     */
    public static List<UserRegistration> descList(ExpressionList<UserRegistration> expressionList) {
    	return expressionList.orderBy("registrationId").findList();
    }
    
    public static Expression noCet() {
    	return Expr.eq("cet", null);
	}
    
    public static Expression isReady() {
    	return Expr.eq("inscriptionComplete", true);
	}
    
    
    /*
     * Find
     * ----
     */
    public static UserRegistration findByUserId(int userId) {
    	return find.where().eq("user.userId", userId).findUnique();
	}

    public static List<UserRegistration> findPotentialA1() {
    	return descList(find.where().in("user", User.findActiveConsultant()));
	}
    
    public static List<UserRegistration> findTopMissingCet(int count) {
    	return descList(find.where().add(noCet()).setMaxRows(count).where());//);
	}
    
    public static List<UserRegistration> findTopIncompleteRegistration(int count) {
    	return descList(find.where().not(isReady()).setMaxRows(count).where());//.setMaxRows(count));
	}
    
    public static List<UserRegistration> findWithinDate(Date start, Date end) {
    	return find.where().between("registrationDate", start, end).findList();
	}
    
    
    /*
     * Graph
     * -----
     */
    public static Map<GraphSerie, Double> currentCotisation() {
    	double partenaireCount = find.where().eq("withPartner", true).findRowCount();
    	double rowCount = find.findRowCount();
    	
    	Map<GraphSerie, Double> cotisationData = new HashMap<GraphSerie, Double>();
    	cotisationData.put(new GraphSerie("Cotisation Partenaire"), partenaireCount);
    	cotisationData.put(new GraphSerie("Cotisation Classique"), rowCount - partenaireCount);
    	
    	return cotisationData;
    }
    
    public static Map<GraphSerie, Map<Double, Double>> registrationEvolution(Date startDate, Date endDate) {
	    Map<GraphSerie, Map<Double, Double>> registrationData = new HashMap<GraphSerie, Map<Double, Double>>();
		Map<Double, Double> registrationWithPartner = new HashMap<Double, Double>();
		Map<Double, Double> registrationWithoutPartner = new HashMap<Double, Double>();
		
		List<UserRegistration> registrationList = findWithinDate(startDate, endDate);
		
		double withPartnerCount = 0.0;
		double withoutPartnerCount = 0.0;
		
		for(UserRegistration registration: registrationList) {
			if(registration.withPartner)
				withPartnerCount++;
			else
				withoutPartnerCount++;
			
			registrationWithPartner.put(new Double(registration.registrationDate.getTime()), withPartnerCount);
			registrationWithoutPartner.put(new Double(registration.registrationDate.getTime()), withoutPartnerCount);
		}
		
		registrationData.put(new GraphSerie("Cotisation Partenaire"), registrationWithPartner);
		registrationData.put(new GraphSerie("Cotisation Classique"), registrationWithoutPartner);
		
		return registrationData;
    }
    
    

}

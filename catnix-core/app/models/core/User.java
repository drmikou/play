package models.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import models.core.security.UserPermission;
import security.EPoste;
import security.UserStatus;
import utils.core.EnumCsvLine;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;


/**
 * User Table represent user account registered.
 * Table was rename to users to fix an issue with postgresql
 */
@Entity
@Table(name = "users")
public class User extends Model implements Subject
{
    public static final Find<String, User> find = new Find<String, User>(){};

	public void save() {
        this.posteHierarchy = EPoste.getTopLevelHierarchy(EnumCsvLine.getEnumSet(posteCsv, EPoste.class));
        this.login = this.login();
		super.save();
	}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
	public int userId;

	public String firstName;
	public String lastName;
	public int promotion;

	public boolean active;
    public boolean stranger;
    public boolean sectInter;

	public String phone;
	public String mail;
	public String mailAlt;
    public String adresse;

	public String linkedinId;

	public String posteCsv = "";

	private int posteHierarchy;
	private String login;
	private String loginISEP;

	@ManyToMany
	public List<UserPermission> permissions = new ArrayList<UserPermission>();

    /**
	 * Constructor
	 */
    public User(String firstName, String lastName, int promotion, String phone, String mail, String mailAlt, String adresse, String linkedinId, String loginISEP) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.promotion = promotion;

        this.active = true;
        this.stranger = false;
        this.sectInter = false;

        this.phone = phone;
        this.mail = mail;
        this.mailAlt = mailAlt;
        this.adresse = adresse;

        this.linkedinId = linkedinId;

        this.posteCsv = EPoste.consultant.toString();
        this.loginISEP = loginISEP;

        //Calculated Data
        this.posteHierarchy = EPoste.consultant.hierarchyLevel;
        this.login = this.login();
    }


    /**
     * Accessor
     */
	public String login() {
		return (firstName.charAt(0) + lastName).toLowerCase();
	}

	public List<EPoste> getPosteList() {
		return new ArrayList<EPoste>(EnumCsvLine.getEnumSet(posteCsv, EPoste.class));
	}

	public List<EPoste> getHierarchicalPosteList() {
		Set<EPoste> posteSet = EnumCsvLine.getEnumSet(posteCsv, EPoste.class);
		EPoste topLevel = EPoste.findPosteFromHierarchy(EPoste.getTopLevelHierarchy(posteSet));

		Set<EPoste> hierarchicalPosteSet = EPoste.filterHierarchy(posteSet);
		hierarchicalPosteSet.add(topLevel);

		return new ArrayList<EPoste>(hierarchicalPosteSet);
	}

	public List<UserStatus> getStatusList() {
        List<UserStatus> userStatusList = new ArrayList<UserStatus>();

        userStatusList.add(active ? UserStatus.actif : UserStatus.ancien);

        if(!UserRegistration.findByUserId(userId).isInscriptionComplete())
        	userStatusList.add(UserStatus.dossierIncomplet);

        if (stranger)
            userStatusList.add(UserStatus.etranger);

        if (sectInter)
            userStatusList.add(UserStatus.sectionInter);

		return userStatusList;
	}

    public String getAdresse() {
        return adresse.split(" - ")[0];
    }

    public String getCp() {
        return adresse.split(" - ")[1];
    }

    public String getCountry() {
        return adresse.split(" - ")[2];
    }

    public String getLoginISEP() {
        return loginISEP;
    }

    /**
	 * Updater
	 */
    public void updatePosteSet(Set<EPoste> posteSet) {
        this.posteCsv = EnumCsvLine.buildCsvLine(posteSet);

        this.save();
    }

	public void updateInfo(String firstName, String lastName, int promotion) {
		this.firstName = firstName;
        this.lastName = lastName;
        this.promotion = promotion;

        this.save();
	}

    public void updateContact(String phone, String mail, String mailAlt, String adresse, String cp, String country) {
        this.phone = phone;
        this.mail = mail;
        this.mailAlt = mailAlt;
        this.adresse = buildAdresse(adresse, cp, country);

        this.save();
    }

    public void updateStatus(boolean active, boolean stranger, boolean sectInter) {
        this.active = active;
        this.stranger = stranger;
        this.sectInter = sectInter;

        this.save();
    }

    public void addPoste(EPoste poste) {
        this.posteCsv = EnumCsvLine.addToCsvLine(this.posteCsv, poste);
        this.save();

    }
    public void upgradeToA1() {
    	addPoste(EPoste.a1);
    }

    public void upgradeToA2() {
    	upgradeToA1();
    	addPoste(EPoste.a2);
    }

    public void upgradeToCos() {
    	upgradeToA2();
    	addPoste(EPoste.cos);
    }


	/**
	 * Builder
	 */
    public static String buildAdresse(String adresse, String cp, String country) {
        return adresse + " - " + cp + " - " + country.toUpperCase();
    }


    /**
	 * Finder
	 */
    /*
     * Expression
     * ----------
     */
    public static List<User> descList(ExpressionList<User> expressionList) {
     	return expressionList.orderBy("userId").findList();
    }

    public static Expression activeUser() {
		return Expr.eq("active", true);
	}

    public static Expression inTeam() {
		return isHierarchyMoreEqThan(EPoste.a1);
	}

    public static Expression havePoste(EPoste poste) {
		return Expr.contains("posteCsv", poste.getName());
	}

    public static Expression isHierarchy(EPoste poste) {
		return Expr.eq("posteHierarchy", poste.hierarchyLevel);
	}

    public static Expression isHierarchyMoreEqThan(EPoste poste) {
		return Expr.ge("posteHierarchy", poste.hierarchyLevel);
	}

    public static Expression isHierarchyLessEqThan(EPoste poste) {
		return Expr.le("posteHierarchy", poste.hierarchyLevel);
	}

    public static Expression isPromotion(int promotion) {
		return Expr.eq("promotion", promotion);
	}

    public static Expression hasLogin(String login) {
		return Expr.eq("login", login);
	}

    public static Expression hasLoginISEP(String login) {
        return Expr.eq("loginISEP", login);
    }

    /*
     * Find
     * ----
     */
    public static List<User> findActiveUser() {
		return descList(find.where().add(activeUser()));
	}

    public static List<User> findAll() {
		return descList(find.where());
	}

    public static List<User> findActiveTeam() {
		return descList(find.where().and(activeUser(), inTeam()));
	}

    public static List<User> findOldTeam() {
    	return descList(find.where().and(inTeam(), Expr.not(activeUser())));
    }
    
    public static List<User> findOldTeamExceptCos() {
    	return descList(find.where().and(Expr.and(inTeam(), isHierarchyLessEqThan(EPoste.bureau)), Expr.not(activeUser())));
    }

    public static User findActivePresident() {
    	return find.where().and(activeUser(), havePoste(EPoste.prez)).findUnique();
	}

    public static List<User> findActiveA2() {
    	return descList(find.where().and(activeUser(), isHierarchyMoreEqThan(EPoste.a2)));
	}

    public static List<User> findActiveA1() {
    	return descList(find.where().and(activeUser(), isHierarchy(EPoste.a1)));
	}

    public static List<User> findActiveConsultant() {
    	return descList(find.where().and(activeUser(), isHierarchy(EPoste.consultant)));
	}

    public static List<User> findLeavingConsultant(int presidentPromotion) {
    	return descList(find.where().and(Expr.not(inTeam()), isPromotion(presidentPromotion + 1)));
    }

    public static User findInTeamByLogin(String login) {
    	return find.where().and(inTeam(), hasLogin(login)).findUnique();
    }

    public static User findByLogin(String login) {
    	return find.where().add(hasLogin(login)).findUnique();
    }

    public static User findByLoginISEP(String loginISEP) {
        return find.where().add(hasLoginISEP(loginISEP)).findUnique();
    }





	/**
	 * As Subject
	 */
	@Override
	public List<? extends Role> getRoles() {
		List<Role> securityRole = new ArrayList<Role>();
		securityRole.addAll(getPosteList());
		securityRole.addAll(getStatusList());
		return securityRole;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		return permissions;
	}

	@Override
	public String getIdentifier() {
		return String.valueOf(userId);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId == user.userId;

    }

    @Override
    public int hashCode() {
        return userId;
    }
}

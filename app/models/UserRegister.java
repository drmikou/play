package models;
import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Thomas on 16/06/2016.
 */
public class UserRegister extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String firstname;

    public String lastname;

    public String mail;

    public String loginIsep;

    public String password;

    public String role;

    public String numberIsep;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() { return mail;}
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRole() { return role;}
    public void setRole(String role) {this.role = role;}

    public String getLoginIsep() { return loginIsep;}
    public void setLoginIsep(String loginIsep) {
        this.loginIsep = loginIsep;
    }

    public String getPassword() { return password;}
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberIsep() { return numberIsep;}
    public void setNumberIsep(String numberIsep) {
        this.numberIsep = numberIsep;
    }

    public static Finder find = new Finder(Long.class, UserRegister.class);

    public static void createUserRegister(Long id, String firstname, String lastname, String mail, String role, String loginIsep, String password, String numberIsep) {
        UserRegister user;
        //promotion = 2017;
        user = new UserRegister();
        user.save();
    }

    //méthode "find" pour chercher ds la bdd (normalement find c'est deja une methode de play.. bizarre)
    public static UserRegister find(Long id) {
    }
}

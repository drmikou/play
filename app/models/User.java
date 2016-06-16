package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
public class User extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String firstname;

    public String lastname;

    public int promotion;

    public String phone;

    public String mail;

    public String adresse;

    public String loginIsep;

    public String password;

    public String role;

    public String numberIsep;

    @ManyToOne
    public Equipe equipe;



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

   // public void setPromotion(Promotion promotion) { this.promotion = 2017; }

   /* public String getPhone() { return phone;}
    public void setPhone(String phone) {
        this.phone = phone;
    }*/

    public String getMail() { return mail;}
    public void setMail(String mail) {
        this.mail = mail;
    }

   /* public String getAdresse() { return adresse;}
    public void setAdresse(String adresse) {this.adresse = adresse;}*/

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

    public Equipe getEquipe(){ return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }

    public static Finder find = new Finder(Long.class, User.class);


//méthode "find" pour chercher ds la bdd (normalement find c'est deja une methode de play.. bizarre)
}
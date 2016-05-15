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
    public String nom;

    public String prenom;

    @ManyToOne
    public Equipe equipe;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String Nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String Prenom) {
        this.prenom = prenom;
    }

    public Equipe getEquipe(){ return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }

    public static Finder find = new Finder(Long.class, User.class);


}
package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Session extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nom;

    //@ManyToOne
    //public Promotion promotion;




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


}
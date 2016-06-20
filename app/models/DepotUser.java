package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.Date;
import java.util.List;

@Entity
public class DepotUser extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nom;

    public String commentaire;

    public Date datedepot;

    @ManyToOne
    public Depot depot;


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

    public String getCommentaire() {
        return commentaire;
    }
    public void setCommentaire(String Commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDatedepot() {
        return datedepot;
    }
    public void setDatedepot(Date datedepot) {
        this.datedepot = datedepot;
    }

    public Depot getDepot(){ return depot; }
    public void setDepot(Depot depot) { this.depot = depot; }


    public static Finder find = new Finder(Long.class, Depot.class);

}
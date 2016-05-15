package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Session extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nom;

    @ManyToOne
    public Promotion promotion;

    @OneToMany(mappedBy = "session")
    public List<Groupe> groupes;



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

    public Promotion getPromotion(){ return promotion; }
    public void setPromotion(Promotion promotion) { this.promotion = promotion; }

    public List<Groupe> getGroupes() { return groupes; }
    public void setGroupes(List<Groupe> groupes) { this.groupes = groupes; }


}
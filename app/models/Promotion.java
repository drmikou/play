package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Promotion extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public int annee;


    //@OneToMany(mappedBy = "promotion")
    //public List<Session> Session;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int Annee) {
        this.annee = annee;
    }



    public static Finder find = new Finder(Long.class, Promotion.class);

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Promotion");
        sb.append("{id=").append(id);
        sb.append(", annee='").append(annee).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
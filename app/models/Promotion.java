package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
public class Promotion extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public int annee;


    @OneToMany(mappedBy = "promotion")
    public List<Session> sessions;



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

    public List<Session> getSessions() { return sessions; }
    public void setSessions(List<Session> sessions) { this.sessions = sessions; }



}
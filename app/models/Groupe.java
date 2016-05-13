package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
public class Groupe extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nom;

    @ManyToOne
    public Session session;

    //@OneToMany(mappedBy = "promotion")
    //public List<Session> sessions;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(int Nom) {
        this.nom = nom;
    }

    public Session getSession(){ return session; }
    public void setSession(Session session) { this.session = session; }

    //public List<Session> getSessions() { return sessions; }
    //public void setSessions(List<Session> sessions) { this.sessions = sessions; }




}
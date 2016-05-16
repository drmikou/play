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
public class Reunion extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nom;

    public Date date;

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

    public Date getDate() {
        return date;
    }
    public void setDate(Date Date) {
        this.date = date;
    }

    public Equipe getEquipe(){ return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }


    public static Finder find = new Finder(Long.class, Reunion.class);


}
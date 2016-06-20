package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
public class Fonctionnalite extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nom;

    @ManyToOne
    public Equipe equipe;

    public Boolean trouve;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getTrouve() {
        return trouve;
    }
    public void setTrouve(boolean Trouve) {
        this.trouve = trouve;
    }

    public Equipe getEquipe(){ return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }


    public static Finder find = new Finder(Long.class, Fonctionnalite.class);


}
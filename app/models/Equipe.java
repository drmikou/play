package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
public class Equipe extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String nom;

    @ManyToOne
    public Groupe groupe;

    @OneToMany(mappedBy = "equipe")
    public List<Projet> projets;

    @OneToMany(mappedBy = "equipe")
    public List<Reunion> reunions;

    @OneToMany(mappedBy = "equipe")
    public List<Depot> depots;

    @OneToMany(mappedBy = "equipe")
    public List<Fonctionnalite> fonctionnalites;

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

    public Groupe getGroupe(){ return groupe; }
    public void setGroupe(Groupe groupe) { this.groupe = groupe; }

    public List<Projet> getProjets() { return projets; }
    public void setProjets(List<Projet> projets) { this.projets = projets; }

    public List<Reunion> getReunions() { return reunions; }
    public void setReunions(List<Reunion> reunions) { this.reunions = reunions; }

    public List<Depot> getDepots() { return depots; }
    public void setDepots(List<Depot> depots) { this.depots = depots; }

    public List<Fonctionnalite> getFonctionnalites() { return fonctionnalites; }
    public void setFonctionnalites(List<Fonctionnalite> fonctionnalites) { this.fonctionnalites = fonctionnalites; }

    public static Finder find = new Finder(Long.class, Equipe.class);
}
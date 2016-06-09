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

    public String role;


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

    public void setRole(Role role) { this.role = "eleve"; }

    public static Finder find = new Finder(Long.class, User.class);


}
    /*public static void createUser() {
        int promotion = 2017;

        createUser("Antonin", "PEDOTTI", promotion, "06 71 67 74 94", "apedotti@juniorisep.com", "apedotti@juniorisep.com", "24, rue de Vanves", "linkId", "apedotti", true);
        createUser("Thomas", "FENEON", promotion, "06 13 64 11 68", "tfeneon@juniorisep.com", "tfeneon@juniorisep.com", "3 rue de saint-simon porte14 6eme étage", "linkId", "tfeneon", true);
        createUser("Quentin", "MARCHIORI", promotion, "06 13 64 11 68", "qmarchiori@juniorisep.com", "qmarchiori@juniorisep.com", "3 rue de saint-simon porte14 6eme étage", "linkId", "qmarchio", true);
        createUser("Moctar", "GUEYE", promotion, "06 34 19 50 76", "mgueye@juniorisep.com", "mgueye@juniorisep.com", "2, rue des Bruyeres", "linkId", "mgueye", false);
        createUser("Alexandre", "MORAND", promotion, "06 86 76 80 54", "amorand@juniorisep.com", "amorand@juniorisep.com", "26, rue Marcheron", "linkId", "amorand", true);
        createUser("Pierre", "PALUSTRAN", promotion, "06 87 32 78 93", "ppalustran@juniorisep.com", "ppalustran@juniorisep.com", "29, avenue Bernard Palissy", "linkId", "ppalustr", true);
        createUser("Thibault", "THUILLIER", promotion, "06 95 79 54 08", "tthuillier@juniorisep.com", "tthuillier@juniorisep.com", "6, rue du Regard", "linkId", "tthuilli", false);
        createUser("Antoine", "DOYEN", promotion, "06 29 59 21 38", "adoyen@juniorisep.com", "adoyen@juniorisep.com", "49, rue de Babylone", "linkId", "adoyen", true);
        createUser("Antoine", "RAKOTOZAFY", promotion, "06 86 65 60 30", "arakotozafy@juniorisep.com",
                "arakotozafy@juniorisep.com", "26, Rue Raymond Marcheron", "linkId", "arakotozafy", true);
        createUser("Amine", "ALI", promotion, "06 27 92 36 59", "aali@juniorisep.com", "aali@juniorisep.com", "20, rue du Docteur Pinel", "linkId", "aali", false);
        createUser("Jean-Baptiste", "WATENBERG", promotion, "06 58 41 78 24", "jbwatenberg@juniorisep.com", "jbwatenberg@juniorisep.com", "43 Bis, rue Ledru Rollin", "linkId", "jwatenbe", false);
        createUser("Clement", "CREPY", 2016, "07 86 72 17 03", "ccrepy@juniorisep.com", "ccrepy@juniorisep.com", "29 rue Chanzy", "linkId", "ccrepy", true);
        createUser("Nicolas", "GLENISSON", promotion, "06 12 83 96 44", "nicolasglenisson@gmail.com", "nicolasglenisson@gmail.com", "153 Avenue de Versailles", "linkId", "ngleniss", false);
        createUser("Alexandre", "LAO-OUINE", promotion, "06 03 39 84 63", "alexlao974@gmail.com", "alexlao974@gmail.com", "26 rue raymond marcheron", "linkId", "alaoouine", false);
        createUser("Maximilien", "PESTEL", promotion, "06 66 66 15 43", "pestel.maximilien@gmail.com", "pestel.maximilien@gmail.com", "7 Avenue le Corbeiller Meudon", "linkId", "mpestel", false);
        createUser("Amaury", "GUIOT", promotion, "06 32 05 28 51", "amaury78.guiot@gmail.com", "amaury78.guiot@gmail.com", "10 rue André Gide", "linkId", "aguiot", false);
        createUser("Geoffrey", "MARIANY", promotion, "06 72 33 89 57", "geoffrey.mariany@gmail.com", "geoffrey.mariany@gmail.com", "6 rue des beauvettes", "linkId", "gmariany", false);
        Logger.info("User added");
    }

    private static void createUser(String firstname, String lastname, int promotion, String phone, String mail, String mailAlt, String adresse, String linkedinId, String loginISEP, boolean withPartner) {
        User user;
        user = new User(firstname, lastname, promotion, phone, mail, mailAlt, adresse, linkedinId, loginISEP);
        user.save();
    }*/
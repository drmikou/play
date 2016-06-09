
import models.core.User;
import models.core.UserCheckout;
import models.core.UserRegistration;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import security.EPoste;

import java.util.Date;


/**
 * This Class set a developpement Data Set.
 * It is reseted on server restart.
 */
public class Global extends GlobalSettings
{

    @Override
    public void onStart(Application application)
    {
		if (User.find.findRowCount() == 0) {
			//ADD USER
			createUser();

			//PUT POSTE
			addPoste();

			//REMOVE USER
			checkoutUser();
		}

	}
    
    public static void createUser() {
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
		UserRegistration registration;
		user = new User(firstname, lastname, promotion, phone, mail, mailAlt, adresse, linkedinId, loginISEP);
		registration = new UserRegistration(new Date(), 123456789, withPartner, null, null, null, null, null, user);
		registration.save();
	}

	public static void addPoste() {
    	
    	User user;
    	
    	user = User.findByLogin("apedotti");
		user.upgradeToA2();
		user.addPoste(EPoste.bureau);

		user = User.findByLogin("jwatenberg");
		user.upgradeToA2();
		user.addPoste(EPoste.prez);
		user.addPoste(EPoste.bureau);

		user = User.findByLogin("aali");
		user.upgradeToA2();
		
		user = User.findByLogin("amorand");
		user.upgradeToA2();
		
		System.out.println("A2 added");
		
    }
    
    public static void checkoutUser() {
    	
		User user;
		UserCheckout checkout;
		
    	user = User.findInTeamByLogin("aali");
        checkout = new UserCheckout(new Date(), "manual checkout", true, user);
        checkout.save();
        
        System.out.println("checkout added");
    }
}

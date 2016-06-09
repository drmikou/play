package security;

import be.objectify.deadbolt.core.models.Role;


/**
 * Status represent the current situation of an user in the JE.
 * It is also a securityRole for deadbolt 
 */
public enum UserStatus implements Role
{
	actif,
    ancien,

	demission,
    
	dossierIncomplet,
    
	etranger,
	sectionInter;
	
	/**
	 * As Role
	 */
	@Override
	public String getName() {
		return this.toString();
	}
}

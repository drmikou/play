package security;

import java.util.HashSet;
import java.util.Set;

import be.objectify.deadbolt.core.models.Role;

/**
 * Poste represent a role inside the JE and a security Role for deadBold 
 */
/*
 * Mutiple Poste
 * -------------
 * A single user may have mulitple Poste, for exemple:
 * You can be both in charge of the Communication and the Human Ressources
 * 
 * 
 * 
 * Vt, Vq, Vp
 * ----------
 * Represents the authorization for validate respectivly:
 * Technical, Quality and Process
 * 
 * 
 * 
 * HierarchyLevel
 * --------------
 * To represent hierarchy beetween Global Poste, we introduced an integer.
 * No meaning hierarchyLevel are represented by à -1
 *
 * 
 */
public enum EPoste implements Role
{
	//Global Poste
	consultant(10),
	a1(20),
    a2(30),
    bureau(40),
    cos(50),

    //Validation access
	vt,
	vq,
	vp,

	//Responsibility
	Comm,
	RC,
	RQ,
	RD,
	sysOp,
	sysAdmin,
	trezo,
	SG,
	vicePrez,
	prez;
	
	public int hierarchyLevel;
	
	
	/**
	 * Constructors
	 */
	private EPoste() {
		hierarchyLevel = -1;
	}
	
	private EPoste(int hierarchy) {
		hierarchyLevel = hierarchy;
	}
	
	
	/*
	 * Bijection between poste and hierarchyLevel 
	 */
	/**
	 * Retrieve maximum hierarchyLevel from Set of Poste  
	 */
	public static int getTopLevelHierarchy(Set<EPoste> posteSet) {
		if(posteSet.contains(cos))
			return cos.hierarchyLevel;
		
		if(posteSet.contains(bureau))
			return bureau.hierarchyLevel;

		if(posteSet.contains(a2))
			return a2.hierarchyLevel;
		
		if(posteSet.contains(a1))
			return a1.hierarchyLevel;
		
		if(posteSet.contains(consultant))
			return consultant.hierarchyLevel;
		
		return -1;
	}
	
	
	/**
	 * Provide the Poste corresponding to maximum hierachyLevel 
	 */
	public static EPoste findPosteFromHierarchy(int hierarchyLevel) {
		if(hierarchyLevel == consultant.hierarchyLevel)
			return consultant;
		
		if(hierarchyLevel == a1.hierarchyLevel)
			return a1;
		
		if(hierarchyLevel == a2.hierarchyLevel)
			return a2;
		
		if(hierarchyLevel == bureau.hierarchyLevel)
			return bureau;
		
		if(hierarchyLevel == cos.hierarchyLevel)
			return cos;

		return null;
	}
	
	/**
	 * Retrieve a set of Poste with useless poste removed 
	 */
	public static Set<EPoste> filterHierarchy(Set<EPoste> posteSet) {
		Set<EPoste> filteredPosteSet = new HashSet<EPoste>();
		
		for(EPoste poste : posteSet)
			if(poste.hierarchyLevel < consultant.hierarchyLevel)
				filteredPosteSet.add(poste);
		
		return filteredPosteSet;
	}
	
	/**
	 * As Role
	 */
	@Override
	public String getName() {
		return this.toString();
	}
}

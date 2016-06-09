package utils.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import security.EPoste;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * This Object handle bijection between Set of Enum and a csvLine
 * @author pelo
 *
 */
/*
 * Why ?
 * -----
 * 
 * For some strange reason we did not maneged to make ebean save a set on Enum
 * The solution have been to store the Set<Enum> in a String (a csv) 
 */
public class EnumCsvLine{

	/**
	 * Retrive a csvLine version with added enum   
	 */
	public static <E extends Enum<E>> String addToCsvLine(String csvLine, E e) {
		Set<String> csvSet = new HashSet<String>(Arrays.asList(csvLine.split(",")));
		
        if (csvSet.contains(e.toString())) {
            return Joiner.on(",").join(csvSet);
        } else {
            return Joiner.on(",").join(csvSet) + "," + e.toString();
        }
	}

	/**
	 * Retrive a csvLine version with removed enum   
	 */
	public static <E extends Enum<E>> String removeToCsvLine(String csvLine, E e) {
		Set<String> csvList = new HashSet<String>(Arrays.asList(csvLine.split(",")));
        while (csvList.contains(e.toString())) {
            csvList.remove(e.toString());
        }
		return Joiner.on(",").join(csvList);
	}

	/**
	 * Retrive a csvLine from a Set<Enum>   
	 */
	public static <E extends Enum<E>> String buildCsvLine(Set<E> enumSet) {
		Function<E, String> strList = new Function<E, String>() {
	           @Override
	           public String apply(E e) {
	        	  return e.toString();
	           }
	        };

		return Joiner.on(",").join(Lists.transform(new ArrayList<E>(enumSet), strList));
	}

	/**
	 * Retrive a Set<Enum> from a Set<String> and the enum class    
	 */
	public static <E extends Enum<E>> Set<E> getEnumSet(Set<String> csvSet, final Class<E> clazz) {
		Set<E> enumSet = new HashSet<E>();
		
		for(String str : csvSet)
			enumSet.add(Enum.valueOf(clazz, str));

		return enumSet;
	}
	
	/**
	 * Retrive a Set<Enum> from a csvLine and the enum class    
	 */
	public static <E extends Enum<E>> Set<E> getEnumSet(String csvLine, final Class<E> clazz) {
		Set<String> csvSet = new HashSet<String>(Arrays.asList(csvLine.split(",")));
		return getEnumSet(csvSet, clazz);
	}

	// To migrate in Unit test
	public static void main(String[] args) {
		String str = "a1,a2";

		System.out.println(getEnumSet(str, EPoste.class));

		System.out.println(buildCsvLine(getEnumSet(str, EPoste.class)));

		System.out.println(addToCsvLine(str, EPoste.sysAdmin));

		System.out.println(removeToCsvLine(str, EPoste.a2));


	}
}

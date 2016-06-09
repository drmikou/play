package utils.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import play.data.Form;
import com.avaje.ebean.Model;


/**
 * Retrieve model object from primary Key (PK) passed by Post Request
*/
/*
 * This is needed to retrieve and specific object after a form
 *  
 * The form is parametred by a formData who contains the PK stored as a String.
 * We use it in the controller to retrieve the Model Object for the PK.
 * 
 * We make the assumption ebean map key is a string.
 * 
 *  I Type of Key for ebean Table (String)
 *  T Model Class to fetch
 *  K FormData Class
 *  
 */

@SuppressWarnings("unchecked") //because of the cast between ebean Primary key and String
public class FormFetcher {
	
	/**
	 * Retrieve a model row from an Id
	 */
	public static <I, T, K> T fetchById(Model.Find<I, T> finder, Form<K> form, String formIdField) {
		
		I modelId = (I) form.data().get(formIdField);
		
		return finder.byId(modelId);
	}
	
	/**
	 * Retrieve a model row from an Id List
	 */
	public static <I, T, K> List<T> fetchByIdList(Model.Find<I, T> finder, Form<K> form, String formListField, String formIdField) {

		Map<String, String> formDataMap = form.data();
		
		List<I> idList = new ArrayList<I>();
		
		for(int i = 0; formDataMap.containsKey(formListField + "[" + String.valueOf(i) + "]." + formIdField); i++) {
			idList.add((I) formDataMap.get(formListField + "[" + String.valueOf(i) + "]." + formIdField));
		}
		
		return finder.where().idIn(idList).findList();
	}
}

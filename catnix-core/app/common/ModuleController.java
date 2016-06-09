package common;

import play.mvc.Controller;
import play.mvc.Result;


/**
 * Represent a topHead Module by a special Play controller.
 * It allow exposure of widget throught ajax request and serveur side routes for ajax request.
 * (You need to declare all route you want to be accessible from client side).
 *
 * It is also responsible to render the menu of the corresponding module.
 *  @author pelo
 */

/*
 * AsyncFragment
 * -------------
 * Since play does not support circular dependancy, we introduced the Fragment system.
 * 
 * AsyncFragment intended to be used to avoid dependencies between modules.
 * Otherwise you don not need to use it, a good architecture should do the trick.
 * 
 * -!- AsyncWidget are meaned to be called from the view. -!-
 * 
 * An Ajax request is called from the view to retrieve an Html bloc corresponding to a functionality (widget) of another module.
 * 
 * Exemple:
 * core need to display the search bar in the main layout but the logic is defined in another module.
 * Since Play doesn't support circular dependancies, core can't depends on the search module.
 * (However search module depends on core).
 *
 * The solution is to use an ajax request to retrieve Html Bloc corresponding to the search bar.
 * This bloc will provided the needed request to expose functionality.
 * 
 * Rq:
 * Since AsyncFragment are loaded after the DOM is complete (ie - documentReady),
 * you need to programaticly trigger some Js Libs Event
 * 
 * 
 * JavascriptRoutes
 * ----------------
 * In order to access routes from client side, we build a js object which contains needed routes.
 * This js object is called as a regular script during the init and allow you to chain Ajax request with parameters. 
 * 
 * Rq: AsyncFragment are a kind of javascriptRoutes but we chose to split in 2 object to avoid confusion.
 * 
 * 
 * 
 * SideMenu
 * --------
 * For each module, we need to expose an AsyncWidget containing access to the module's functionality.
 * It is the sideMenu.
 * 
 * 
 */
public abstract class ModuleController extends Controller {

	/**
	 * Each module expose it's menu to make it accessible by mainLayout.
	 */
	public Result renderSideMenu() {
		return null;
	}
	
	/**
	 * It is called by other modules to get a widget with low dependency.
	 * To do so, we use an ajax request to render an Html block.
	 *
	 * You have to list here all widget routes you want to expose in the module.
	 */
	public Result asyncFragmentRoutes() {
		return null;
	}
	
	/**
	 * It is called to generate a js object containing routes for ajax request.
	 *
	 * You have to list here all routes you want to expose in the module.
	 */
	public Result javascriptRoutes() {
		return null;
    }

}

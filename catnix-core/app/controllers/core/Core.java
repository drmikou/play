package controllers.core;

import play.Routes;
import play.mvc.Result;
import common.ModuleController;


/**
 * Core Module deal with regalian and reused fonctionality.
 * Each module depends of Core.
 * 
 * @author pelo
 *
 */
/*
 * Bootstrap integration
 * ---------------------
 * Core provide scala view helper for the view.
 * 				- Bootstrap and Style framework (macAdmin) integration
 * 				- FormTool, bundle of helper for form submission
 * 				- GrahpTool, to represent complex data in graphics
 *
 * 
 * 
 * Main / Login / Document Layout
 * ------------------------------
 * Core deal with several layout we can find on the website:
 * 				- mainLayout: represent the main global layout, only the content change
 * 				- loginLayout: represent the login layout, login, password and other login way (Facebook, google...)
 * 				- documentLayout: represent the offical document layout as html, header, footer, first page, content (which is modular)
 * 
 * 
 * File handle
 * -----------
 * Core handle file upload and retrieve, file are uploaded as .pdf or .html file who can be converted as .pdf.
 * File upload is asyncronous and need the file structure to be already created (a script is designed for if necessary) 
 * 
 * According to fileStructure on serveur, Each connected user have a temp storage folder to put assync files and can
 * "archive" them to their final storage emplacement.
 * 
 * 
 * User Account handle
 * -------------------
 * Core is also in charge of user account management, creation, changes, checkout.
 * And set security system.
 * 
 */
public class Core extends ModuleController {
	
	/*
     * Async Widget
     */
    public Result renderSideMenu() {
        return ok(views.html.core.fragment.sideMenu.render());
    }

	/*
     * Async Widget Router
     */
    public Result asyncFragmentRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("coreFragmentRoutes",

            	controllers.core.routes.javascript.Demo.renderDemoGraph(),
            	controllers.core.routes.javascript.Core.renderSideMenu(),
            	controllers.core.routes.javascript.Demo.renderDemoSearchBar(),
            	controllers.core.routes.javascript.UserManager.renderCurrentUserAccount(),
            	controllers.core.routes.javascript.UserRegister.renderRegistrationConfigurationWidget(),
            	controllers.core.routes.javascript.UserRegister.renderTopIncompleteRegistrationWiget(),
            	controllers.core.routes.javascript.UserRegister.renderTopMissingCetWidget(),
            	controllers.core.routes.javascript.UserRegister.renderCurrentCotisationGraph(),
            	controllers.core.routes.javascript.UserRegister.renderCurrentCotisationGraphWidget(),
            	controllers.core.routes.javascript.UserRegister.renderRegistrationEvolutionGraph(),
            	controllers.core.routes.javascript.UserRegister.renderRegistrationEvolutionGraphWidget()
            )
        );
    }
    
    /*
     * Javascript Router
     */
    public Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("coreJsRoutes",
            	controllers.core.routes.javascript.DocumentProcessing.renderDiscussion(),
            	controllers.core.routes.javascript.FileManager.asyncUploadFile(),
            	controllers.core.routes.javascript.FileManager.asyncCheckTmpFileExists(),
            	controllers.core.routes.javascript.FileManager.downloadFile(),
            	controllers.core.routes.javascript.FileManager.asyncDeleteTmpFile(),
            	controllers.core.routes.javascript.FileManager.asyncUploadDraft(),
				controllers.core.login.routes.javascript.LdapixLogin.fetchInfo()
            )
        );
    }
}

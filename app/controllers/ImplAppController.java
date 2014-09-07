package controllers;

import java.util.ArrayList;
import java.util.Timer;

import daos.ImplWebSocketTimer;
import appbasics.EnumControllerTypes;
import appbasics.InterApp;
import play.mvc.*;
import views.html.*;

/**
 * Main application controller serving common methods to children
 * @author Konstantinas
 *
 */
public class ImplAppController extends Controller implements InterAppControllers {
	
	/**
	 * Renders main page index page
	 * @return HTTP 200
	 */
	public static Result index() {
		return ok(index.render());
	}

	/**
	 * Redirects to main page depending on type of controller
	 * @param typesControllers, type o controller
	 * @return redirect
	 */
	public static Result redirectToApplication(EnumControllerTypes typesControllers){
		switch(typesControllers){
		case PERSON:// for now break through
		case JSON_MESSAGE:
			return redirect(routes.ImplAppController.index());
		default: throw new RuntimeException(EXEPTION_MISSING_IMPL); 	
		}
	}
}

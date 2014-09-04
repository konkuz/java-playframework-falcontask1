package controllers;

import play.mvc.*;
import views.html.*;

/**
 * Main application controller serving common methods to children
 * @author Konstantinas
 *
 */
public class ImplWebSocketTextController extends Controller implements InterAppControllers {
	
	/**
	 * Renders main page index page
	 * @return
	 */
	public static Result index() {
		return ok(websocket.render());
	}
}

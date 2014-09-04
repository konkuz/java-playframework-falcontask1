package controllers;

import play.mvc.*;
import views.html.*;

/**
 * TODO
 * @author Konstantinas
 *
 */
public class ImplAppController extends Controller implements InterAppControllers {

	public static Result index() {
		return ok(index.render());
	}

	public static Result redirectToApplication(EnumControllerTypes typesControllers){
		switch(typesControllers){
		case PERSON:// for now break through
		case JSON_MESSAGE:
			return redirect(routes.ImplAppController.index());
		default: throw new RuntimeException("Missing implementation in class Application"); 	
		}

	}
}

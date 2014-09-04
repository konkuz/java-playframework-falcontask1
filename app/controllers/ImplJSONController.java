package controllers;

import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;
import play.data.Form;
import static play.libs.Json.*;
import java.util.List;
import java.util.Set;
import models.JSONmessage;

/**
 * Controls JSONmessage Entity from frontend perspective and passes it to backend
 * @author Konstantinas
 */
public class ImplJSONController extends ImplAppController {

	/**
	 * Adds JSONmessage to Redis db
	 * TODO Consider adding validation against JSON format
	 * @return redirect
	 */
	public static Result addJSONMessage() {
		JSONmessage jsonMessage = Form.form(JSONmessage.class).bindFromRequest().get();
		jsonMessage.setId(JSON_MESSAGE_KEY + Long.toString(System.currentTimeMillis()));
		jsonMessage.saveToRedis(jsonMessage);
		return redirectToApplication(EnumControllerTypes.JSON_MESSAGE);
	}

	/**
	 * Retrieves JSON messages from Redis db and provides in JSON format
	 * @return HTTP 200 and JSON output
	 */
	public static Result getJSONMessages() {
		JSONmessage jsonMessage = new JSONmessage();
		List<JSONmessage> jsonMessages = jsonMessage.getFromRedis(REDIS_JSON_MESSAGE_KEY);
		return ok(toJson(jsonMessages));
	}
}
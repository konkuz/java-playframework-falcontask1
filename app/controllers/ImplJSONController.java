package controllers;

import play.mvc.*;
import play.data.Form;
import static play.libs.Json.*;

import java.util.List;

import daos.ImplRedisJSONmessageDao;
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
		new ImplRedisJSONmessageDao().saveToDefaulRedis(jsonMessage);
		return redirectToApplication();
	}

	/**
	 * Retrieves JSON messages from Redis db and provides in JSON format
	 * @return HTTP 200 and JSON output
	 */
	public static Result retrieveJSONMessages() {
		List<JSONmessage> jsonMessages = new ImplRedisJSONmessageDao().retrieveFromRedis(REDIS_JSON_MESSAGE_KEY);
		return ok(toJson(jsonMessages));
	}
}
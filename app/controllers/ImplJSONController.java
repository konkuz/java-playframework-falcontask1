package controllers;

import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;
import play.data.Form;
import static play.libs.Json.*;
import java.util.List;
import java.util.Set;
import models.Person;

/**
 * TODO
 * @author Konstantinas
 *
 */
public class ImplJSONController extends ImplAppController {

	/**
	 * TODO
	 * @return
	 */
	public static Result addJSONMessage() {
		Person person = Form.form(Person.class).bindFromRequest().get();
		person.setId(JSON_MESSAGE_KEY + Long.toString(System.currentTimeMillis()));
		person.saveToRedis(person);
		return redirectToApplication(EnumControllerTypes.JSON_MESSAGE);
	}

	/**
	 * TODO
	 * @return
	 */
	public static Result getJSONMessages() {
		Person person = new Person();
		List<Person> persons = person.getFromRedis(REDIS_JSON_MESSAGE_KEY);
		return ok(toJson(persons));
	}
}
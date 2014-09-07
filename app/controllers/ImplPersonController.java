package controllers;


import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;
import play.data.Form;
import static play.libs.Json.*;

import java.util.List;
import java.util.Set;

import daos.ImplRedisPersonDao;
import appbasics.EnumControllerTypes;
import models.Person;

/**
 * Controls Person Entity from frontend perspective and passes it to backend
 * @author Konstantinas
 *
 */
public class ImplPersonController extends ImplAppController {

	/**
	 * Adds Person to Redis db
	 * TODO Consider adding validation against JSON format
	 * @return redirect
	 */
	public static Result addPerson() {
		Person person = Form.form(Person.class).bindFromRequest().get();
		person.setId(PERSON_KEY + Long.toString(System.currentTimeMillis()));
		new ImplRedisPersonDao().saveToRedis(person);
		return redirectToApplication(EnumControllerTypes.PERSON);
	}

	/**
	 * Retrieves JSON messages from Redis db and provides in JSON format
	 * @return HTTP 200 and JSON output
	 */
	public static Result retrievePersons() {
		Person person = new Person();
		List<Person> persons = new ImplRedisPersonDao().retrieveFromRedis(REDIS_PERSON_KEY);
		return ok(toJson(persons));
	}
}
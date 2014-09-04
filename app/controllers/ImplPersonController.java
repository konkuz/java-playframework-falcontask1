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
public class ImplPersonController extends ImplAppController {

	/**
	 * TODO
	 * @return
	 */
	public static Result addPerson() {
		Person person = Form.form(Person.class).bindFromRequest().get();
		person.setId(PERSON_KEY + Long.toString(System.currentTimeMillis()));
		person.saveToRedis(person);
		return redirectToApplication(EnumControllerTypes.PERSON);
	}

	/**
	 * TODO
	 * @return
	 */
	public static Result getPersons() {
		Person person = new Person();
		List<Person> persons = person.getFromRedis(REDIS_PERSON_KEY);
		return ok(toJson(persons));
	}
}
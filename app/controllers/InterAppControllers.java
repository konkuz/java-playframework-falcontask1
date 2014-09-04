package controllers;

/**
 * Exposes number of constants for internal to component as wells as cross component
 * usage.
 * @author Konstantinas
 */
public interface InterAppControllers {

	/**
	 * Keys to store and search for messages in Redis
	 */
	public final static String JSON_MESSAGE_KEY = EnumControllerTypes.JSON_MESSAGE.toString(),
			PERSON_KEY = EnumControllerTypes.PERSON.toString(),
			REDIS_JSON_MESSAGE_KEY = JSON_MESSAGE_KEY + "*",
			REDIS_PERSON_KEY = PERSON_KEY + "*";
}

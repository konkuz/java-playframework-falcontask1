package controllers;

/**
 * Exposes number of constants for internal to component usage
 * @author Konstantinas
 */
public interface InterAppControllers extends InterApp {

	/**
	 * Keys to store and search for messages in Redis
	 */
	public final static String JSON_MESSAGE_KEY = EnumControllerTypes.JSON_MESSAGE.toString(),
			PERSON_KEY = EnumControllerTypes.PERSON.toString(),
			REDIS_JSON_MESSAGE_KEY = JSON_MESSAGE_KEY + "*",
			REDIS_PERSON_KEY = PERSON_KEY + "*"; 
}

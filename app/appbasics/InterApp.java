package appbasics;


/**
 * Exposes number of constants for internal to component as wells as cross package
 * usage.
 * @author Konstantinas
 */
public interface InterApp {

	public final static String REDIS_HOST = "localhost",
			REDIS_DEFAULT_CHANNELS = "jsonMessages";

	public final static String MESSAGE_CONNECTED = "Connected!",
			MESSAGE_DISCONNECTED = "Disconnected!";

	public final static String EXEPTION_MISSING_IMPL = "Implementation required";

	public final static String JSON_QUEUE_KEY = "jsonQueue";

	public final static int SUBMIT_TIMER_DELAY = 0,
			SUBMIT_TIMER_INTERVAL = 1,
			MESSAGE_TIMER_DELAY = 0,
			MESSAGE_TIMER_INTERVAL = 100;

	public final static String MESSAGE_SUBSCRIPTION_FAILED = "Subscription failed:";

	/**
	 * Keys to store and search for messages in Redis
	 */
	public final static String JSON_MESSAGE_KEY = EnumControllerTypes.JSON_MESSAGE.toString(),
			PERSON_KEY = EnumControllerTypes.PERSON.toString(),
			REDIS_JSON_MESSAGE_KEY = JSON_MESSAGE_KEY + "*",
			REDIS_PERSON_KEY = PERSON_KEY + "*"; 
}

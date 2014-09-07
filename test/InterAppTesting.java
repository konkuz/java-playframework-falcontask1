
/**
 * Contains common elements for testing
 * @author Konstantinas
 */
public interface InterAppTesting {

	final static String INDEX_PAGE_URL = "http://127.0.0.1:3333",
			INDEX_PAGE_CONTAINS = "Task";

	final static String JSON_MESSAGES_PAGE_URL ="http://127.0.0.1:3333/jsonMessages",
			JSON_CONTAINS = "[";
	
	final static String WEBSOCKET_PAGE_URL ="http://127.0.0.1:3333/websocketMessages",
			WEBSOCKET_PAGE_CONTAINS = "Websocket";
	
	final static String JSON_PERSONS_PAGE_URL = "http://127.0.0.1:9000/persons";
}

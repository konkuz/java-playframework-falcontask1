package controllers;

/**
 * Exposes number of constants for internal to component as wells as cross package
 * usage.
 * @author Konstantinas
 */
public interface InterApp {

	public final static String REDIS_HOST = "localhost",
			MESSAGE_WAITING = "Waiting for a message in the queue!",
			MESSAGE_RECEIVED = "Message received",
	        MESSAGE_KEY = "KEY:",
	        MESSAGE_VALUE = "VALUE:",
	        MESSAGE_CONNECTED = "Connected!";
}

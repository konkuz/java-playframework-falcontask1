package models;

import play.mvc.*;
import play.libs.F.*;
import java.util.*;
import daos.ImplWebSocketTimer;
import appbasics.InterApp;

/**
 * Responsible for starting websocket and its subtasks 
 * @author Konstantinas
 */
public class WebSocketConnections extends AbstractDomainEntities {

	/**
	 * Contains web socket connections 
	 */
	private static List<WebSocket.Out<String>> wsConnections = 
			new ArrayList<WebSocket.Out<String>>();
		
	/**
	 * Timer for sending new message to connections 
	 */
	private static Timer timer;
	
	/**
	 * Starts web socket processes
	 * @param in, a WebSocket in.
	 * @param out, A WebSocket out.
	 */
	public static void start(WebSocket.In<String> in, WebSocket.Out<String> out){
		//add to list of connections
		wsConnections.add(out);
        
		//start/restart timer for sending new message to connections on newly appearing connection
		startMessageTimer(timer, wsConnections);

		//Registers a message callback.
		in.onMessage(new Callback<String>(){
			public void invoke(String event){
				WebSocketConnections.notifyAll(event);
			}
		});

		//Registers a close callback.
		in.onClose(new Callback0(){
			public void invoke(){
				WebSocketConnections.notifyAll(InterApp.MESSAGE_DISCONNECTED);
			}
		});	
	}

	/**
	 * Iterate connection list and write incoming message back
	 * @param message, message to write
	 */
	public static void notifyAll(String message){
		for (WebSocket.Out<String> out : wsConnections) {
			out.write(message);
		}
	}
	
	/**
	 * Starts new timer for sending new message to all connections
	 * @param timer, current timer
	 * @param wsConnections, connections to send new message to
	 * @return timer, new timer
	 */
	public static Timer startMessageTimer(Timer timer, List<WebSocket.Out<String>> wsConnections){
		if(timer != null){//cancel previous timer
			timer.cancel();
		}
		//initialize new timer for every 0.1 seconds
		ImplWebSocketTimer webSocketTimer = 
				new ImplWebSocketTimer((ArrayList<WebSocket.Out<String>>)wsConnections);
		timer = new Timer(true);
		timer.scheduleAtFixedRate(webSocketTimer, MESSAGE_TIMER_DELAY, MESSAGE_TIMER_INTERVAL);
		return timer;
	}
}
package controllers;

import play.mvc.*;
import views.html.*;

import models.WebSocketConnections;

/**
 * Controls rendering of web socket messages page and serves messages
 * @author Konstantinas
 */
public class ImplWebSocketController extends ImplAppController {

	/**
	 * Renders websocket page for displaying messages
	 * @return HTTP 200
	 */
	public static Result index() {
		return ok(websocket.render());
	}

	/**
	 * Runs websocket
	 * @return websocket
	 */
	public static WebSocket<String> runWebsocket() {
		return new WebSocket<String>() {

			/**
			 * Called when WebSocket is ready (hanshake achieved)
			 * @param in
			 * @param out
			 */
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				WebSocketConnections.start(in,out);
			}
		};
	}
}
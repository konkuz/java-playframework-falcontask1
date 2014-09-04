package controllers;

import play.mvc.*;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * Web socket controller
 * @author Konstantinas
 */
public class ImplWebSocketController{

	public static WebSocket<String> index() {
		return new WebSocket<String>() {

			// Called when the Websocket Handshake is done.
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				System.out.println("Connected!");
				out.write("Connected!");
				Jedis jedis = new Jedis(InterApp.REDIS_HOST);   
		        List<String> messages = null;
		        while(true){
		          out.write("Waiting for a message in the queue!");
		          messages = jedis.blpop(0,"queue");
		          System.out.println("Got the message");
		          System.out.println("KEY:" + messages.get(0) + " VALUE:" + messages.get(1));		    
		        }
			}
		};
	}
}
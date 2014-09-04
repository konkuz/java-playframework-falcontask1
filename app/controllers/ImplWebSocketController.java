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
				out.write(InterApp.MESSAGE_CONNECTED);
				System.out.println(InterApp.MESSAGE_CONNECTED);
				
				Jedis jedis = new Jedis(InterApp.REDIS_HOST);   
		        List<String> messages = null;
		        while(true){
		          out.write(InterApp.MESSAGE_WAITING);	
		          System.out.println(InterApp.MESSAGE_WAITING);
		          messages = jedis.blpop(0,"queue");
		          out.write(InterApp.MESSAGE_RECEIVED);
		          System.out.println(InterApp.MESSAGE_RECEIVED);
		          out.write(InterApp.MESSAGE_KEY + messages.get(0) 
		        		  + InterApp.MESSAGE_VALUE + messages.get(1));
		          System.out.println(InterApp.MESSAGE_KEY + messages.get(0) 
		        		  + InterApp.MESSAGE_VALUE + messages.get(1));
		        } 
			}
		};
	}
}
package daos;

import play.mvc.*;
import play.libs.*;
import play.libs.F.*;

import java.util.*;

import appbasics.InterApp;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Checks Redis for new message in the list and if it exists pushes it
 * to all connections
 * @author Konstantinas
 */
public class ImplWebSocketTimer extends TimerTask implements InterRedisDao {

	ArrayList<WebSocket.Out<String>> wsConnections;

	/**
	 * Checks Redis for new message in the list and if it exists pushes it to all connections 
	 * @param wsConnections, connections to send message to
	 */
	public ImplWebSocketTimer(ArrayList<WebSocket.Out<String>> wsConnections){
		this.wsConnections = wsConnections;
	}

	@Override
	public void run() {
		JedisPool jedisPool = ImplRedisJSONmessageDao.getJedisPool();
		Jedis jedis = jedisPool.getResource();
		//take last message or block process
		List<String> messages = jedis.blpop(0,JSON_QUEUE_KEY);
		String message = messages.get(1);

		//TODO shallow clone only, because "out" does not support complete clone and
		//there is no way to check if "out" was closed
		List<WebSocket.Out<String>> wsClonedConnections = 
				(ArrayList<WebSocket.Out<String>>)getWsConnections().clone(); 
		for (WebSocket.Out<String> out : wsConnections) {
			/* TODO fires exception java.nio.channels.ClosedChannelException
			is fired by closed channel and is uncaught exception in playframework*/
			out.write(message);
		}
		
		ImplRedisJSONmessageDao.returnJedis(jedis);
	}

	public ArrayList<WebSocket.Out<String>> getWsConnections() {
		return wsConnections;
	}
}
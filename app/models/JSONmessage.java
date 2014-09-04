package models;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Models JSONmessage entity
 * @author Konstantinas
 */
public class JSONmessage {

	public String id;

	public String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Saves JSONmessage to Redis database
	 * TODO Encapsulate Jedis pooling details implementation
	 * @param jsonMessage, 
	 */
	public void saveToRedis(JSONmessage jsonMessage){
		if(jsonMessage.getContent() != null){
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");
			Jedis jedis = jedisPool.getResource(); 
			jedis.set(jsonMessage.getId(),jsonMessage.getContent());
			jedisPool.returnResource(jedis);
			jedisPool.destroy();
		}
	}
	
	/**
	 * Retrieves list of JSONmessages from Redis db 
	 * TODO Encapsulate Jedis pooling details implementation
	 * @param key, search key
	 * @return jsonMessages, filled or empty list of jsonMessages   
	 */
	public List<JSONmessage> getFromRedis(String key){
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");
		Jedis jedis = jedisPool.getResource();
	
		Set<String> jsonMessageKeys = jedis.keys(key);
		List<JSONmessage> jsonMessages = new ArrayList<JSONmessage>();
		
		if(!jsonMessageKeys.isEmpty()){
			Iterator<String> jsonMessageIterator  = jsonMessageKeys.iterator();
		    while (jsonMessageIterator.hasNext()) {
		        String jsonMessageKey = jsonMessageIterator.next();
		        String jsonMessageContent = jedis.get(jsonMessageKey);
		        
		        if(jsonMessageKey != null && jsonMessageContent != null){
		        	JSONmessage jsonMessage =  new JSONmessage();
		        	jsonMessage.setId(jsonMessageKey);
		        	jsonMessage.setContent(jsonMessageContent);			
		        	jsonMessages.add(jsonMessage);
		        }
		    }
		}
		jedisPool.returnResource(jedis);
		jedisPool.destroy();
		
		return jsonMessages;
	}
}
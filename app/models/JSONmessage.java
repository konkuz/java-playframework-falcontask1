package models;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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

	public void saveToRedis(JSONmessage jsonMessage){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");
		Jedis jedis = jedisPool.getResource(); 
		jedis.set(jsonMessage.getId(),jsonMessage.getContent());
		jedisPool.returnResource(jedis);
		jedisPool.destroy();
	}
	
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
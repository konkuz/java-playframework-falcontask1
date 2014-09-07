package daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import models.JRedisSubscriber;
import models.JSONmessage;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author Konstantinas
 *
 */
public class ImplRedisJSONmessageDao extends AbstractRedisDao {

	/**
	 * Saves JSONmessage to Redis database with separate key and adds
	 * to a list 
	 * to the end list of the list
	 * @param jsonMessage, message to save 
	 */
	public void saveToDefaulRedis(JSONmessage jsonMessage){
		JedisPool jedisPool = getJedisPool();
		new ImplRedisSubscriberDao().subscribeDefaultChannels();
		Jedis jedisPublish = jedisPool.getResource();
		jedisPublish.publish(REDIS_DEFAULT_CHANNELS, jsonMessage.getContent());
		returnJedis(jedisPublish);
	}

	/**
	 * Retrieves list of JSONmessages from Redis database
	 * @param key, search key
	 * @return jsonMessages, filled or empty list of jsonMessages   
	 */
	public List<JSONmessage> retrieveFromRedis(String key){

		JedisPool jedisPool = getJedisPool();
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
		returnJedis(jedis);
		return jsonMessages;
	}
}
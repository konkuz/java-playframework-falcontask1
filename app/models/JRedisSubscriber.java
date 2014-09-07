package models;

import daos.ImplRedisJSONmessageDao;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * Models subsriber from Redis
 * @author Konstantinas
 */
public class JRedisSubscriber extends JedisPubSub implements InterDomainEntities {
	
	/* (non-Javadoc)
	 * @see redis.clients.jedis.JedisPubSub#onMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public void onMessage(String channel, String message) {
		if(REDIS_DEFAULT_CHANNELS.equals(channel)){
			JedisPool jedisPool = ImplRedisJSONmessageDao.getJedisPool();
			Jedis jedis = jedisPool.getResource();
			//place at the end of the list
			jedis.rpush(JSON_QUEUE_KEY, message);
			//save as separate string with unique id
			jedis.set(JSON_MESSAGE_KEY + Long.toString(System.currentTimeMillis()), message);
			ImplRedisJSONmessageDao.returnJedis(jedis);
			// unsubscribe here due to dedicated sunscription thread 
			this.unsubscribe();
		}else{
			throw new RuntimeException(EXEPTION_MISSING_IMPL +" for channel:" + channel);
		}
	}

	@Override
	public void onPMessage(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
	}
}
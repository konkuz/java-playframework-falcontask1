package daos;

import appbasics.InterApp;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Konstantinas
 *
 */
public abstract class AbstractRedisDao implements InterRedisDao{

	private static final JedisPoolConfig jedisPoolConfig;
	
	private static final JedisPool jedisPool;
	
	static{
		jedisPoolConfig = new JedisPoolConfig();
		jedisPool = new JedisPool(jedisPoolConfig, InterApp.REDIS_HOST); 
	}

	public static JedisPoolConfig getJedispoolconfig() {
		return jedisPoolConfig;
	}

	public static JedisPool getJedisPool() {
		return jedisPool;
	}
	
	public static void returnJedis(Jedis jedis){
		jedisPool.returnResource(jedis);
	}
}
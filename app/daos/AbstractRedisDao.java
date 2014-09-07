package daos;

import appbasics.InterApp;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Responsible for common data access operations, currently on Redis. 
 * @author Konstantinas
 *
 */
public abstract class AbstractRedisDao implements InterRedisDao{

	/**
	 *  Main Redis configuration pool
	 */
	private static final JedisPoolConfig jedisPoolConfig;
	
	/**
	 *  Redis pool
	 */
	private static final JedisPool jedisPool;
	
	//initiale Redis configuration and pool on start up
	static{
		jedisPoolConfig = new JedisPoolConfig();
		jedisPool = new JedisPool(jedisPoolConfig, InterApp.REDIS_HOST); 
	}
	
	/**
	 * Convenience method for returning Jedis to pool
	 * @param jedis, jedis to be returned to pool.
	 */
	public static void returnJedis(Jedis jedis){
		jedisPool.returnResource(jedis);
	}

	public static JedisPoolConfig getJedispoolconfig() {
		return jedisPoolConfig;
	}

	public static JedisPool getJedisPool() {
		return jedisPool;
	}
}
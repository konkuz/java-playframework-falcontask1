package daos;

import models.JRedisSubscriber;
import redis.clients.jedis.Jedis;

/**
 * @author Konstantinas
 *
 */
public class ImplRedisSubscriberDao extends AbstractRedisDao {
		
	/**
	 * Executed dedicated subscription. Runs in separate thread because
	 * it is blocking operation
	 */
	public void subscribeDefaultChannels(){
		Thread subsribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	Jedis subscriberJedis = new Jedis(REDIS_HOST);
                try {
                	subscriberJedis.subscribe(new JRedisSubscriber(), REDIS_DEFAULT_CHANNELS);
                } catch (Exception ex) {
                    System.out.println(MESSAGE_SUBSCRIPTION_FAILED + ex.toString());
                }  
            }
        });
		subsribeThread.start();
	}
}
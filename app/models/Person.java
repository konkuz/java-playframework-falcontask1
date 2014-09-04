package models;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import controllers.InterApp;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Models Person entity
 * @author Konstantinas
 *
 */
public class Person {

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
	 * Saves person to Redis database
	 * @param person
	 */
	public void saveToRedis(Person person){
		if(person.getContent() != null){
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			JedisPool jedisPool = new JedisPool(jedisPoolConfig, InterApp.REDIS_HOST);
			Jedis jedis = jedisPool.getResource();
			jedis.rpush("queue", "id:"+person.getId() + ":content"+ person.getContent());
			jedis.set(person.getId(),person.getContent());
			jedisPool.returnResource(jedis);
			jedisPool.destroy();
		}
	}

	/**
	 * Retrieves list of Persons from Redis db 
	 * TODO Encapsulate Jedis pooling details implementation
	 * @param key, search key
	 * @return persons, filled or empty list of persons
	 */
	public List<Person> getFromRedis(String key){

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, InterApp.REDIS_HOST);
		Jedis jedis = jedisPool.getResource();

		Set<String> personKeys = jedis.keys(key);
		List<Person> persons = new ArrayList<Person>();

		if(!personKeys.isEmpty()){
			Iterator<String> personIterator  = personKeys.iterator();
			while (personIterator.hasNext()) {
				String personKey = personIterator.next();
				String personName = jedis.get(personKey);

				if(personKey != null && personName != null){
					Person person = new Person();
					person.setId(personKey);
					person.setContent(personName);			
					persons.add(person);
				}
			}
		}
		jedisPool.returnResource(jedis);
		jedisPool.destroy();

		return persons;
	}
}
package daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import models.Person;

/**
 * Responsible for data access operations for Person object
 * @author Konstantinas
 */
public class ImplRedisPersonDao extends AbstractRedisDao {

	/**
	 * Saves person to Redis database
	 * @param person
	 */
	public void saveToRedis(Person person){
		JedisPool jedisPool = getJedisPool(); 
		Jedis jedis = jedisPool.getResource();
		jedis.set(person.getId(),person.getName());
		returnJedis(jedis);
	}

	/**
	 * Retrieves list of Persons from Redis database
	 * @param key, search key
	 * @return persons, filled or empty list of persons
	 */
	public List<Person> retrieveFromRedis(String key){

		JedisPool jedisPool = getJedisPool(); 
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
					person.setName(personName);
					persons.add(person);
				}
			}
		}
		returnJedis(jedis);
		return persons;
	}
}
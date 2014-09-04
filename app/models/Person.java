package models;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
	
	public void saveToRedis(Person person){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");
		Jedis jedis = jedisPool.getResource(); 
		jedis.set(person.getId(),person.getContent());
		jedisPool.returnResource(jedis);
		jedisPool.destroy();
	}
	
	public List<Person> getFromRedis(String key){
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");
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
package models;

/**
 * Models Person entity
 * @author Konstantinas
 *
 */
public class Person extends AbstractDomainEntities {

	String id;

	String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
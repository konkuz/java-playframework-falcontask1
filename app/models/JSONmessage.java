package models;

/**
 * Models JSONmessage entity
 * @author Konstantinas
 */
public class JSONmessage extends AbstractDomainEntities {

	String id;

	String content;

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
}
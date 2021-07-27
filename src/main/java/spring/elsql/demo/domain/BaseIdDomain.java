package spring.elsql.demo.domain;

/**
 * Base domain with id details
 *
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * @since 1.0
 */
public class BaseIdDomain {
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
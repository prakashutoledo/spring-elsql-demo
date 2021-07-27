package spring.elsql.demo.domain;

import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Get request POJO for User
 * 
 * @author Prakash Khadka <br>
 *         Created on: July 25, 2021
 * @since 1.0
 */
public class UserGetRequest {
	private Set<Long> userIds;
	private Set<String> firstNames;
	private Set<String> lastNames;
	private Set<String> emails;

	public Set<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<Long> userIds) {
		this.userIds = userIds;
	}

	public Set<String> getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(Set<String> firstNames) {
		this.firstNames = firstNames;
	}

	public Set<String> getLastNames() {
		return lastNames;
	}

	public void setLastNames(Set<String> lastNames) {
		this.lastNames = lastNames;
	}

	public Set<String> getEmails() {
		return emails;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}
}

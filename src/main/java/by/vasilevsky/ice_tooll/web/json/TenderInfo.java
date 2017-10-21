package by.vasilevsky.ice_tooll.web.json;

import java.util.Set;

public class TenderInfo {
	private long tenderId;
	private Set<String> emails;

	public long getTenderId() {
		return tenderId;
	}

	public void setTenderId(long tenderId) {
		this.tenderId = tenderId;
	}

	public Set<String> getEmails() {
		return emails;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}
}

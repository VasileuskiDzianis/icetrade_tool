package by.vasilevsky.ice_tooll.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TenderItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String economicSector;
	private String purchaseBriefDescription;
	private Customer customer;
	private Set<String> emails;
	private Date expiryDate;

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getEconomicSector() {
		return economicSector;
	}

	public void setEconomicSector(String economicSector) {
		this.economicSector = economicSector;
	}

	public String getPurchaseBriefDescription() {
		return purchaseBriefDescription;
	}

	public void setPurchaseBriefDescription(String purchaseBriefDescription) {
		this.purchaseBriefDescription = purchaseBriefDescription;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<String> getEmails() {
		return emails;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}
}

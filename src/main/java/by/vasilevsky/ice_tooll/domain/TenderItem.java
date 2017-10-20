package by.vasilevsky.ice_tooll.domain;

import java.io.Serializable;

public class TenderItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String economicSector;
	private String purchaseBriefDescription;
	private Customer customer;

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
}

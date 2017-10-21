package by.vasilevsky.ice_tooll.domain;

import java.io.Serializable;

public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String address;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
			
		} else if (!address.equals(other.address)) {
			return false;
		}
		
		if (id != other.id) {
			return false;
		}
		
		if (name == null) {
			if (other.name != null) {
				return false;
			}
			
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
}

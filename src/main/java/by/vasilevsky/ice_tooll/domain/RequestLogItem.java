package by.vasilevsky.ice_tooll.domain;

import java.io.Serializable;
import java.util.Date;

public class RequestLogItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private int tenderId;
	private Date requestDate;

	public RequestLogItem() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTenderId() {
		return tenderId;
	}

	public void setTenderId(int tenderId) {
		this.tenderId = tenderId;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((requestDate == null) ? 0 : requestDate.hashCode());
		result = prime * result + tenderId;

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
		RequestLogItem other = (RequestLogItem) obj;
		if (id != other.id) {
			return false;
		}

		if (requestDate == null) {
			if (other.requestDate != null) {
				return false;
			}

		} else if (!requestDate.equals(other.requestDate)) {
			return false;
		}

		if (tenderId != other.tenderId) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "RequestLogItem [id=" + id + ", tenderId=" + tenderId + ", requestDate=" + requestDate + "]";
	}
}

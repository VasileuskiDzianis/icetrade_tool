package by.vasilevsky.ice_tooll.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="request_log")
public class RequestLogItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="tenderId")
	private long tenderId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_date")
	private Date requestDate;

	public RequestLogItem() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long argument) {
		this.tenderId = argument;
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
		result = prime * result + (int) (tenderId ^ (tenderId >>> 32));

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

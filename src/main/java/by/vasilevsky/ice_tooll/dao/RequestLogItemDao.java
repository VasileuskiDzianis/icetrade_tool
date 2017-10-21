package by.vasilevsky.ice_tooll.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import by.vasilevsky.ice_tooll.domain.RequestLogItem;

@Repository
public interface RequestLogItemDao extends CrudRepository<RequestLogItem, Long> {

	@SuppressWarnings("unchecked")
	RequestLogItem save(RequestLogItem item);
}

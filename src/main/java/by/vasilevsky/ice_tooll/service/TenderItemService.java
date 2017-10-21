package by.vasilevsky.ice_tooll.service;

import by.vasilevsky.ice_tooll.domain.TenderItem;

public interface TenderItemService {
	
	TenderItem getTenderItemById(long id);
	
	void save(TenderItem tenderItem);
}

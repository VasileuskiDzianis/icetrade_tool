package by.vasilevsky.ice_tooll.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.vasilevsky.ice_tooll.domain.TenderItem;
import by.vasilevsky.ice_tooll.service.TenderItemService;
import by.vasilevsky.ice_tooll.web.json.TenderInfo;

@RestController
@RequestMapping("/tenders/all/view")
public class IcetradeController {
	private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
	
	@Autowired
	private TenderItemService tenderItemService;

	@RequestMapping(path = "/{tenderId}", method = RequestMethod.GET)
	public @ResponseBody TenderInfo getTenderInfo(@PathVariable long tenderId) {
		TenderItem tenderItem = tenderItemService.getTenderItemById(tenderId);

		return buildTenderInfo(tenderItem);
	}

	private TenderInfo buildTenderInfo(TenderItem tenderItem) {
    	TenderInfo tenderInfo = new TenderInfo();
    	tenderInfo.setTenderId(tenderItem.getId());
    	tenderInfo.setEmails(tenderItem.getEmails());
    	DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
    	tenderInfo.setExpiryDate(dateFormat.format(tenderItem.getExpiryDate()));
    	tenderInfo.setDaysLeft(getDateDifference(new Date(), tenderItem.getExpiryDate(), TimeUnit.DAYS));
    	
    	return tenderInfo;
    }

	private int getDateDifference(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
	    
		return (int) timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS); 
	}
}
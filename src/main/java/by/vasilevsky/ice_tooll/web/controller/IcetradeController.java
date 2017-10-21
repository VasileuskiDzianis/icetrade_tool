package by.vasilevsky.ice_tooll.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.vasilevsky.ice_tooll.domain.TenderItem;
import by.vasilevsky.ice_tooll.service.TenderItemService;
import by.vasilevsky.ice_tooll.web.json.TenderInfo;

@RestController
@ComponentScan("by.vasilevsky.ice_tooll")
@EnableAutoConfiguration
@RequestMapping("/tenders/all/view")
public class IcetradeController {
	@Autowired
	private TenderItemService tenderItemService;

    @RequestMapping(path = "/{tenderId}", method = RequestMethod.GET)
    public @ResponseBody TenderInfo getTenderInfo(@PathVariable long tenderId) {
        TenderItem tenderItem = tenderItemService.getTenderItemById(tenderId);

        return buildTenderInfo(tenderItem);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(IcetradeController.class, args);
    }
    
    private TenderInfo buildTenderInfo(TenderItem tenderItem) {
    	TenderInfo tenderInfo = new TenderInfo();
    	tenderInfo.setTenderId(tenderItem.getId());
    	tenderInfo.setEmails(tenderItem.getEmails());
    	
    	return tenderInfo;
    }
}
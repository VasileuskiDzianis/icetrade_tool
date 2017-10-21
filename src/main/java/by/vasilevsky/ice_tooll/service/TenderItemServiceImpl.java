package by.vasilevsky.ice_tooll.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.vasilevsky.ice_tooll.dao.TenderItemDao;
import by.vasilevsky.ice_tooll.domain.Customer;
import by.vasilevsky.ice_tooll.domain.TenderItem;
import org.apache.commons.validator.routines.EmailValidator;

@Service
public class TenderItemServiceImpl implements TenderItemService {
	private static final String REQUEST = "http://www.icetrade.by/tenders/all/view/";
	
	private static final String LINE_SPLITTER = "<br>";
	private static final String EMPTY_STRING = "";
	private static final String WORD_DELIMITER_PATTERN = "[\\s\\t\\n\\r,]";
	private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";

	private static final int CUSTOMER_ADDRESS_ELEMENT_INDEX = 1;
	private static final int CUSTOMER_NAME_ELEMENT_INDEX = 0;
	
	private static final String DATA_COLUMN = "afv";
	private static final String ECONOMIC_SECTOR = "af-industry";
	private static final String BRIEF_OBJECT_DESCR = "af-title";
	private static final String CUSTOMER_DATA = "af-customer_data";
	private static final String CUSTOMER_CONTACTS = "af-customer_contacts";
	private static final String ORGANIZER_CONTACTS = "af-organizer_contacts";
	private static final String ORGANIZER_DATA = "af-organizer_data";
	private static final String EXPIRY_DATE = "af-request_end";
	
	@Autowired
	private TenderItemDao tenderItemDao;

	@Override
	public TenderItem getTenderItemById(long id) {
		Document document = null;
		try {
			document = Jsoup.connect(REQUEST + id).get();
		} catch (IOException e) {
			throw new RuntimeException("Page parsing error", e);
		}

		TenderItem tenderItem = new TenderItem();
		tenderItem.setId(id);
		tenderItem.setEconomicSector(getRowData(document, ECONOMIC_SECTOR));
		tenderItem.setPurchaseBriefDescription(getRowData(document, BRIEF_OBJECT_DESCR));
		tenderItem.setCustomer(buildCustomer(document));
		tenderItem.setExpiryDate(extractDateTime(getRowData(document, EXPIRY_DATE)));

		Set<String> emails = new HashSet<>();
		emails.addAll(extractEmails(getRowData(document, CUSTOMER_CONTACTS)));
		emails.addAll(extractEmails(getRowData(document, CUSTOMER_DATA)));
		emails.addAll(extractEmails(getRowData(document, ORGANIZER_CONTACTS)));
		emails.addAll(extractEmails(getRowData(document, ORGANIZER_DATA)));
		tenderItem.setEmails(emails);

		return tenderItem;
	}

	private Customer buildCustomer(Document document) {
		Customer customer = new Customer();
		String[] customerRawFormat = getRowData(document, CUSTOMER_DATA).split(LINE_SPLITTER);

		if (CUSTOMER_NAME_ELEMENT_INDEX < customerRawFormat.length) {
			customer.setName(customerRawFormat[CUSTOMER_NAME_ELEMENT_INDEX].trim());
		}
		if (CUSTOMER_ADDRESS_ELEMENT_INDEX < customerRawFormat.length) {
			customer.setAddress(customerRawFormat[CUSTOMER_ADDRESS_ELEMENT_INDEX].trim());
		}

		return customer;
	}

	private String getRowData(Document document, String cssClass) {
		Elements elements = document.getElementsByClass(cssClass);

		return elements.isEmpty() 
				? EMPTY_STRING
				: elements.first().getElementsByClass(DATA_COLUMN).first().html().trim();
	}

	private Set<String> extractEmails(String input) {

		String[] splitInput = input.split(WORD_DELIMITER_PATTERN);
		Set<String> emails = new HashSet<>();

		for (String word : splitInput) {
			if (isEmail(word.trim())) {
				emails.add(word.trim());
			}
		}

		return emails;
	}

	private boolean isEmail(String input) {
		EmailValidator emailValidator = EmailValidator.getInstance();

		return emailValidator.isValid(input);
	}

	private Date extractDateTime(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
		try {
			return dateFormat.parse(input.replaceAll("&nbsp;", " "));
			
		} catch (ParseException e) {
			throw new IllegalArgumentException("Error parsing date", e);
		}
	}

	@Override
	public void save(TenderItem tenderItem) {
		tenderItemDao.save(tenderItem);
	}
}

package by.vasilevsky.ice_tooll.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.vasilevsky.ice_tooll.dao.TenderItemDao;
import by.vasilevsky.ice_tooll.domain.Customer;
import by.vasilevsky.ice_tooll.domain.TenderItem;
import by.vasilevsky.ice_tooll.util.PatternCollection;

import org.apache.commons.validator.routines.EmailValidator;

@Service
public class TenderItemServiceImpl implements TenderItemService {
	private static final String REQUEST = "http://www.icetrade.by/tenders/all/view/";

	private static final String LINE_SPLITTER = "<br>";
	private static final String EMPTY_STRING = "";
	private static final String SPACE = " ";
	private static final String WORD_DELIMITER_PATTERN = "[\\s\\t\\n\\r,]";
	private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	private static final String NO_BREAK_SPACE = "&nbsp;";
	private static final Date DEFAULT_DATE = new Date(0);

	private static final int CUSTOMER_ADDRESS_ELEMENT_INDEX = 1;
	private static final int CUSTOMER_NAME_ELEMENT_INDEX = 0;

	private static final String TENDER_INFO_BLOCK_ID = "auctBlock";
	private static final String DATA_COLUMN = "afv";
	private static final String ECONOMIC_SECTOR = "af-industry";
	private static final String BRIEF_OBJECT_DESCR = "af-title";
	private static final String CUSTOMER_DATA = "af-customer_data";
	private static final String CUSTOMER_CONTACTS = "af-customer_contacts";
	private static final String ORGANIZER_CONTACTS = "af-organizer_contacts";
	private static final String ORGANIZER_DATA = "af-organizer_data";
	private static final String PLACEMENT_DATE = "af-created";
	private static final String EXPIRY_DATE = "af-request_end";

	@Autowired
	private TenderItemDao tenderItemDao;

	@Override
	public void save(TenderItem tenderItem) {
		tenderItemDao.save(tenderItem);
	}

	@Override
	public TenderItem getTenderItemById(long id) {
		Document document = parsePage(id);

		TenderItem tenderItem = new TenderItem();
		Set<String> emails = new HashSet<>();
		Set<String> phones = new HashSet<>();
		tenderItem.setId(id);
		if (isTenderInfoWasGot(document)) {
			tenderItem.setEconomicSector(getRowData(document, ECONOMIC_SECTOR));
			tenderItem.setPurchaseBriefDescription(getRowData(document, BRIEF_OBJECT_DESCR));
			tenderItem.setCustomer(buildCustomer(document));
			tenderItem.setExpiryDate(extractDateTime(getRowData(document, EXPIRY_DATE)));
			tenderItem.setPlacementDate(extractDateTime(getRowData(document, PLACEMENT_DATE)));

			emails.addAll(extractEmails(getRowData(document, CUSTOMER_CONTACTS)));
			emails.addAll(extractEmails(getRowData(document, CUSTOMER_DATA)));
			emails.addAll(extractEmails(getRowData(document, ORGANIZER_CONTACTS)));
			emails.addAll(extractEmails(getRowData(document, ORGANIZER_DATA)));

			phones.addAll(extractPhoneNumbers(getRowData(document, CUSTOMER_CONTACTS)));
			phones.addAll(extractPhoneNumbers(getRowData(document, CUSTOMER_DATA)));
			phones.addAll(extractPhoneNumbers(getRowData(document, ORGANIZER_CONTACTS)));
			phones.addAll(extractPhoneNumbers(getRowData(document, ORGANIZER_DATA)));
		} else {
			tenderItem.setCustomer(buildEmptyCustomer());
			tenderItem.setExpiryDate(DEFAULT_DATE);
		}
		tenderItem.setEmails(emails);
		tenderItem.setPhoneNumbers(phones);

		return tenderItem;
	}

	private Document parsePage(long id) {
		try {
			
			return Jsoup.connect(REQUEST + id).get();
		
		} catch (IOException e) {
			throw new RuntimeException("Page parsing error. Page id is: " + id, e);
		}
	}

	private Customer buildCustomer(Document document) {
		Customer customer = new Customer();
		String[] customerDescription = getRowData(document, CUSTOMER_DATA).split(LINE_SPLITTER);

		if (CUSTOMER_NAME_ELEMENT_INDEX < customerDescription.length) {
			customer.setName(customerDescription[CUSTOMER_NAME_ELEMENT_INDEX].trim());
		}
		if (CUSTOMER_ADDRESS_ELEMENT_INDEX < customerDescription.length) {
			customer.setAddress(customerDescription[CUSTOMER_ADDRESS_ELEMENT_INDEX].trim());
		}

		return customer;
	}

	private String getRowData(Document document, String cssClass) {
		Elements elements = document.getElementsByClass(cssClass);

		return elements.isEmpty() 
				? EMPTY_STRING
				: elements.first()
					.getElementsByClass(DATA_COLUMN)
					.first()
					.html()
					.trim();
	}

	private Set<String> extractEmails(String input) {
		String[] words = input.split(WORD_DELIMITER_PATTERN);
		Set<String> emails = new HashSet<>();

		for (String word : words) {
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
		SimpleDateFormat dateFormat;
		String clearedInput = input.replaceAll(NO_BREAK_SPACE, SPACE);

		if (clearedInput.length() == DATE_PATTERN.length()) {
			dateFormat = new SimpleDateFormat(DATE_PATTERN);
		} else if (clearedInput.length() == DATE_TIME_PATTERN.length()) {
			dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
		} else {
			throw new IllegalArgumentException("Illegal date string: " + clearedInput);
		}
		try {
			return dateFormat.parse(clearedInput);

		} catch (ParseException e) {
			throw new IllegalArgumentException("Error parsing date: " + clearedInput, e);
		}
	}

	private Set<String> extractPhoneNumbers(String input) {
		Set<String> numbers = new HashSet<>();
		Pattern pattern = Pattern.compile(PatternCollection.PHONE_NUMBER_BOTH, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			numbers.add(matcher.group().trim());
		}

		return numbers;
	}

	private boolean isTenderInfoWasGot(Document document) {

		return (document.getElementById(TENDER_INFO_BLOCK_ID) != null);
	}

	private Customer buildEmptyCustomer() {
		Customer emptyCustomer = new Customer();
		emptyCustomer.setAddress(EMPTY_STRING);
		emptyCustomer.setName(EMPTY_STRING);

		return emptyCustomer;
	}
}

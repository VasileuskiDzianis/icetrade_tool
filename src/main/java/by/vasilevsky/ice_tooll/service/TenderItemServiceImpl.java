package by.vasilevsky.ice_tooll.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import by.vasilevsky.ice_tooll.domain.Customer;
import by.vasilevsky.ice_tooll.domain.TenderItem;
import by.vasilevsky.ice_tooll.util.PatternCollection;

@Service
public class TenderItemServiceImpl implements TenderItemService {
	private static final int CUSTOMER_BANK_ELEMENT_INDEX = 3;
	private static final int CUSTOMER_ID_ELEMENT_INDEX = 2;
	private static final int CUSTOMER_ADDRESS_ELEMENT_INDEX = 1;
	private static final int CUSTOMER_NAME_ELEMENT_INDEX = 0;

	private static final String CUSTOMER_DATA_SPLITTER = "<br>";
	private static final String ECONOMIC_SECTOR_ROW_CLASS = "af-industry";
	private static final String BRIEF_OBJECT_DESCR_ROW_CLASS = "af-title";
	private static final String CUSTOMER_ROW_CLASS = "af-customer_data";
	private static final String CUSTOMER_CONTACTS = "af-customer_contacts";
	private static final String DATA_COLUMN_CLASS = "afv";

	private static final String REQUEST = "http://www.icetrade.by/tenders/all/view/";

	@Override
	public TenderItem getTenderItemById(long id) {
		Document document = null;
		try {
			document = Jsoup.connect(REQUEST + id).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		TenderItem tenderItem = new TenderItem();
		tenderItem.setEconomicSector(getRowData(document, ECONOMIC_SECTOR_ROW_CLASS));
		tenderItem.setPurchaseBriefDescription(getRowData(document, BRIEF_OBJECT_DESCR_ROW_CLASS));
		tenderItem.setCustomer(buildCustomer(document));

		tenderItem.setEmails(extractEmails(getRowData(document, CUSTOMER_CONTACTS)));

		return tenderItem;
	}

	private Customer buildCustomer(Document document) {
		Customer customer = new Customer();
		String[] customerRawFormat = getRowData(document, CUSTOMER_ROW_CLASS).split(CUSTOMER_DATA_SPLITTER);

		if (CUSTOMER_NAME_ELEMENT_INDEX < customerRawFormat.length) {
			customer.setName(customerRawFormat[CUSTOMER_NAME_ELEMENT_INDEX].trim());
		}
		if (CUSTOMER_ADDRESS_ELEMENT_INDEX < customerRawFormat.length) {
			customer.setAddress(customerRawFormat[CUSTOMER_ADDRESS_ELEMENT_INDEX].trim());
		}
		if (CUSTOMER_ID_ELEMENT_INDEX < customerRawFormat.length) {
			customer.setId(Long.parseLong(customerRawFormat[CUSTOMER_ID_ELEMENT_INDEX].trim()));
		}
		if (CUSTOMER_BANK_ELEMENT_INDEX < customerRawFormat.length) {
			customer.setBankInfo(customerRawFormat[CUSTOMER_BANK_ELEMENT_INDEX].trim());
		}

		return customer;
	}

	private String getRowData(Document document, String cssClass) {

		return document.getElementsByClass(cssClass).first().getElementsByClass(DATA_COLUMN_CLASS).first().html()
				.trim();
	}

	private Set<String> extractEmails(String input) {
		Set<String> emails = new HashSet<>();
		Pattern pattern = Pattern.compile(PatternCollection.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			emails.add(matcher.group().trim());
		}

		return emails;
	}
}

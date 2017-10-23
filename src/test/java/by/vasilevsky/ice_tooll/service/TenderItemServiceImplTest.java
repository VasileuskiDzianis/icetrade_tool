package by.vasilevsky.ice_tooll.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import by.vasilevsky.ice_tooll.domain.TenderItem;

public class TenderItemServiceImplTest extends TenderItemServiceImpl {
	private static final long ARCHIVE_TENDER_ID = 492203L;
	private static final String ECONOMIC_SECTOR = "Строительство / архитектура &gt; Инженерные сети";
	private static final String PURCHASE_BRIEF_DESCR = "Обородование для подачи и очистки воды";
	private static final String COMPANY_NAME = "Общество с ограниченной ответственностью \"Строительная фирма \"Барит\" г. Молодечно";
	private static final String COMPANY_ADDRESS = "Республика Беларусь, Минская обл., г. Молодечно, 222304, ул. Металлистов, 10";
	private static final Set<String> PHONES_492203 = new HashSet<>(Arrays.asList("+37529 3049385"));
	private static final Set<String> EMAILS_492203 = new HashSet<>(Arrays.asList("barit@tut.by"));

	private static final long ARCHIVE_TENDER_WITH_LONG_EMAIL_ID = 492912L;
	private static final Set<String> EMAILS_492912 = new HashSet<>(Arrays.asList("mtе.uko@bmz.gomel.by"));

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getTenderItemByIdTest() {
		TenderItemService tenderItemService = new TenderItemServiceImpl();
		TenderItem tenderItem = tenderItemService.getTenderItemById(ARCHIVE_TENDER_ID);
		
		assertEquals(ECONOMIC_SECTOR, tenderItem.getEconomicSector());
		assertEquals(PURCHASE_BRIEF_DESCR, tenderItem.getPurchaseBriefDescription());
		assertEquals(COMPANY_NAME, tenderItem.getCustomer().getName());
		assertEquals(COMPANY_ADDRESS, tenderItem.getCustomer().getAddress());
		assertEquals(EMAILS_492203, tenderItem.getEmails());
		assertEquals(PHONES_492203, tenderItem.getPhoneNumbers());
	}
	
	@Test
	public void emailRecognizingTest() {
		TenderItemService tenderItemService = new TenderItemServiceImpl();
		TenderItem tenderItem = tenderItemService.getTenderItemById(ARCHIVE_TENDER_WITH_LONG_EMAIL_ID);
		
		assertEquals(EMAILS_492912, tenderItem.getEmails());
	}
}

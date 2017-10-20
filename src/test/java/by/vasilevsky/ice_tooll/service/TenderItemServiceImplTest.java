package by.vasilevsky.ice_tooll.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import by.vasilevsky.ice_tooll.domain.TenderItem;

public class TenderItemServiceImplTest extends TenderItemServiceImpl {
	private static final long ARCHIVE_TENDER_ID = 492203L;
	private static final String ECONOMIC_SECTOR = "Строительство / архитектура &gt; Инженерные сети";
	private static final String PURCHASE_BRIEF_DESCR = "Обородование для подачи и очистки воды";
	private static final String COMPANY_NAME = "Общество с ограниченной ответственностью \"Строительная фирма \"Барит\" г. Молодечно";
	private static final String COMPANY_ADDRESS = "Республика Беларусь, Минская обл., г. Молодечно, 222304, ул. Металлистов, 10";
	private static final String COMPANY_BANK = "ОАО \"АСБ Беларусбанк\", р/c: BY15AKBB30122012100266100000 в фил. 601, код. AKBBBY21601";
	private static final long COMPANY_ID = 600419900L;
	
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
		assertEquals(COMPANY_BANK, tenderItem.getCustomer().getBankInfo());
		assertEquals(COMPANY_ID, tenderItem.getCustomer().getId());
	}
}

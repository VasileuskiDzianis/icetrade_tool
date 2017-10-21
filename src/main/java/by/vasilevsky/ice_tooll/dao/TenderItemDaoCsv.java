package by.vasilevsky.ice_tooll.dao;

import java.io.FileWriter;
import java.io.Writer;

import org.springframework.stereotype.Repository;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import by.vasilevsky.ice_tooll.domain.TenderItem;

@Repository
public class TenderItemDaoCsv implements TenderItemDao {
	private static final String CSV_FILE = "tenders.csv";

	@Override
	public void save(TenderItem tenderItem) {
		try (Writer writer = new FileWriter(CSV_FILE, true)) {
			StatefulBeanToCsv<TenderItem> beanWriter = new StatefulBeanToCsvBuilder<TenderItem>(writer).build();
			beanWriter.write(tenderItem);
		} catch (Exception e) {
			throw new RuntimeException("Saving to CSV error", e);
		}
	}
}

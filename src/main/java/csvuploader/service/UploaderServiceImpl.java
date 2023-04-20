package csvuploader.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import csvuploader.exception.UploaderException;
import csvuploader.model.DataModel;
import csvuploader.repository.UploaderRepository;

@Service
public class UploaderServiceImpl implements UploaderService {

	@Autowired
	UploaderRepository repository;

	@Override
	public void uploadData(MultipartFile file) throws UploaderException {
		List<DataModel> dataList = uploadDataFromFile(file);
		repository.saveAll(dataList);
	}

	@Override
	public List<DataModel> getAllData() {
		return repository.findAll();
	}

	@Override
	public Optional<DataModel> fetchByCode(String code) {
		return repository.findByCode(code);
	}

	@Override
	public void deleteAllData() {
		repository.deleteAll();
	}

	private List<DataModel> uploadDataFromFile(MultipartFile file) throws UploaderException {
		InputStream inputStream = getInputStream(file);

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			CSVParser csvParser = new CSVParser(bufferedReader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			csvParser.close();
			return parseDataList(csvRecords);

		} catch (IOException exception) {
			throw new UploaderException("Cannot save CSV: " + exception.getMessage());
		}
	}

	private InputStream getInputStream(MultipartFile file) throws UploaderException {
		InputStream inputStream;

		try {
			inputStream = file.getInputStream();
		} catch (IOException exception) {
			throw new UploaderException("Cannot save CSV: " + exception.getMessage());
		}

		return inputStream;
	}

	private List<DataModel> parseDataList(Iterable<CSVRecord> csvRecords) throws UploaderException {
		List<DataModel> dataList = new ArrayList<>();

		try {
			for (CSVRecord csvRecord : csvRecords) {
				String source = csvRecord.get("source");
				String codeListCode = csvRecord.get("codeListCode");
				String code = csvRecord.get("code");
				String displayValue = csvRecord.get("displayValue");
				String longDescription = csvRecord.get("longDescription");
				String fromDate = csvRecord.get("fromDate");
				String toDate = csvRecord.get("toDate");
				String sortingPriority = csvRecord.get("sortingPriority");
				Date fromDateParsed = null;
				Date toDateParsed = null;
				Integer sortingPriorityParsed = null;

				if (!fromDate.isEmpty()) {
					fromDateParsed = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);
				}

				if (!toDate.isEmpty()) {
					toDateParsed = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);
				}

				if (!sortingPriority.isEmpty()) {
					sortingPriorityParsed = Integer.parseInt(sortingPriority);
				}

				DataModel data = new DataModel(code, source, codeListCode, displayValue, longDescription,
						fromDateParsed, toDateParsed, sortingPriorityParsed);

				dataList.add(data);
			}
		} catch (ParseException exception) {
			throw new UploaderException("Date in wrong format. Use dd-MM-yyyy: " + exception.getMessage());
		}

		return dataList;
	}

}

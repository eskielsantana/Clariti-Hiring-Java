package infrastructure.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import infrastructure.reader.factory.CSVReaderFactory;
import infrastructure.reader.factory.DefaultCSVReaderFactory;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UseOpenCsv implements FileReader {
    private static final Logger LOGGER = Logger.getLogger(UseOpenCsv.class);
    private static final String FILE_NAME_IS_NULL_MESSAGE = "Method readFile received a null fileName. Result will be empty.";
    private static final String FILE_NOT_FOUND_MESSAGE = "File (%s) not found. Result will be empty.";
    private static final String FILE_INVALID_MESSAGE = "File (%s) is invalid or corrupted. Result will be emtpy.";

    private final CSVReaderFactory csvReaderFactory;

    public UseOpenCsv() {
        csvReaderFactory = new DefaultCSVReaderFactory();
    }

    public UseOpenCsv(CSVReaderFactory csvReaderFactory) {
        this.csvReaderFactory = csvReaderFactory;
    }

    @Override
    public List<String[]> read(String fileName) {
        List<String[]> records = new ArrayList<>();

        if(fileName == null) {
            LOGGER.warn(FILE_NAME_IS_NULL_MESSAGE);
            return records;
        }

        CSVReader csvReader;
        try {
            csvReader = csvReaderFactory.createCSVReader(fileName);
        } catch (FileNotFoundException e) {
            LOGGER.warn(String.format(FILE_NOT_FOUND_MESSAGE, fileName));
            return records;
        }

        String[] nextLine;
        while (true) {
            try {
                if ((nextLine = csvReader.readNext()) == null) { break; }
            } catch (CsvValidationException | IOException e) {
                LOGGER.error(String.format(FILE_INVALID_MESSAGE, fileName));
                return records;
            }
            records.add(nextLine);
        }

        return records;
    }
}

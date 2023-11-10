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

public class CSVFileReader {
    private static final Logger LOGGER = Logger.getLogger(CSVFileReader.class);
    private static final String FILE_NAME_IS_NULL_MESSAGE = "Method readFile received a null fileName. Result will be empty.";
    private static final String FILE_NOT_FOUND_MESSAGE = "File (%s) not found. Result will be empty.";
    private static final String FILE_READING_ERROR_MESSAGE = "Error reading file (%s). Result will be emtpy.";

    private final CSVReaderFactory csvReaderFactory;

    public CSVFileReader() {
        csvReaderFactory = new DefaultCSVReaderFactory();
    }

    public CSVFileReader(CSVReaderFactory csvReaderFactory) {
        this.csvReaderFactory = csvReaderFactory;
    }

    public List<String[]> read(String fileName) {
        List<String[]> records = new ArrayList<>();

        if (fileName == null) {
            LOGGER.warn(FILE_NAME_IS_NULL_MESSAGE);
            return records;
        }

        try (CSVReader csvReader = csvReaderFactory.createCSVReader(fileName)) {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                records.add(nextLine);
            }
        } catch (FileNotFoundException e) {
            LOGGER.warn(String.format(FILE_NOT_FOUND_MESSAGE, fileName));
            return new ArrayList<>();
        } catch (IOException | CsvValidationException e) {
            LOGGER.warn(String.format(FILE_READING_ERROR_MESSAGE, fileName), e);
            return new ArrayList<>();
        }

        return records;
    }
}

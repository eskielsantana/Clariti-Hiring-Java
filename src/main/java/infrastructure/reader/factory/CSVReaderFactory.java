package infrastructure.reader.factory;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;

public interface CSVReaderFactory {
    CSVReader createCSVReader(String file) throws FileNotFoundException;
}

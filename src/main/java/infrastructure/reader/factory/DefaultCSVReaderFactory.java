package infrastructure.reader.factory;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import infrastructure.reader.CSVFileReader;

import java.io.FileNotFoundException;
import java.net.URL;

public class DefaultCSVReaderFactory implements CSVReaderFactory {
    private static final CSVParser PARSER = new CSVParserBuilder().withSeparator(',').build();

    @Override
    public CSVReader createCSVReader(String file) throws FileNotFoundException {
        URL fileUrl = CSVFileReader.class.getClassLoader().getResource(file);
        if (fileUrl == null) {
            throw new FileNotFoundException(String.format("File not found: %s", file));
        }

        return new CSVReaderBuilder(new java.io.FileReader(fileUrl.getFile()))
                .withSkipLines(1)
                .withCSVParser(PARSER)
                .build();
    }
}

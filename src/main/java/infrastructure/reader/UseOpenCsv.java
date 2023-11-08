package infrastructure.reader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UseOpenCsv implements Reader {
    private static final Logger LOGGER = Logger.getLogger(UseOpenCsv.class.getName());
    private static final String FILE_NOT_FOUND_MESSAGE = "File %s not found, result will be empty.";
    private static final String FILE_INVALID_MESSAGE = "File %s is invalid or corrupted, result will be emtpy.";
    private static final CSVParser PARSER = new CSVParserBuilder().withSeparator(',').build();

    @Override
    public List<String[]> readFile(String file) {
        List<String[]> records = new ArrayList<>();

        URL fileUrl = UseOpenCsv.class.getClassLoader().getResource(file);

        if (fileUrl == null) {
            LOGGER.severe(() -> String.format(FILE_NOT_FOUND_MESSAGE, file));
            return records;
        }

        CSVReader csvReader;
        try {
            csvReader = new CSVReaderBuilder(new FileReader(fileUrl.getFile()))
                    .withSkipLines(1)
                    .withCSVParser(PARSER)
                    .build();
        } catch (FileNotFoundException e) {
            LOGGER.severe(() -> String.format(FILE_NOT_FOUND_MESSAGE, file));
            return records;
        }

        String[] nextLine;
        while (true) {
            try {
                if ((nextLine = csvReader.readNext()) == null) {
                    break;
                }
            } catch (CsvValidationException | IOException e) {
                LOGGER.severe(() -> String.format(FILE_INVALID_MESSAGE, file));
                return new ArrayList<>();
            }
            records.add(nextLine);
        }

        return records;
    }
}

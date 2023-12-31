package infrastructure.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import infrastructure.reader.factory.CSVReaderFactory;
import infrastructure.reader.factory.DefaultCSVReaderFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CSVFileReaderTest {
    private CSVFileReader reader;

    @Before
    public void setUp() {
        reader = new CSVFileReader();
    }

    @Test
    public void readFile_WhenGivenExistentFileName_ReturnListOfSetOfStrings() {
        List<String[]> result = reader.read("raw_fees.csv");
        assertTrue(result.size() > 0);
    }

    @Test
    public void readFile_WhenCSVReaderThrowsIOException_PrintErrorAndReturnsEmptyList() throws Exception {
        CSVReader csvReader = mock(CSVReader.class);
        CSVReaderFactory csvFactory = mock(DefaultCSVReaderFactory.class);
        reader = new CSVFileReader(csvFactory);

        when(csvFactory.createCSVReader(anyString())).thenReturn(csvReader);
        when(csvReader.readNext()).thenThrow(new IOException("IOException Test"));

        List<String[]> result = reader.read("raw_fees.csv");
        assertTrue(result.isEmpty());
    }

    @Test
    public void readFile_WhenCSVReaderThrowsCsvValidationException_PrintErrorAndReturnsEmptyList() throws Exception {
        CSVReader csvReader = mock(CSVReader.class);
        CSVReaderFactory csvFactory = mock(DefaultCSVReaderFactory.class);
        reader = new CSVFileReader(csvFactory);

        when(csvFactory.createCSVReader(anyString())).thenReturn(csvReader);
        when(csvReader.readNext()).thenThrow(new CsvValidationException("CsvValidationException Test"));

        List<String[]> result = reader.read("raw_fees.csv");
        assertTrue(result.isEmpty());
    }
}

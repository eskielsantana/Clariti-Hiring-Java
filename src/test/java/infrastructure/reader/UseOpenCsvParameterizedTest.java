package infrastructure.reader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class UseOpenCsvParameterizedTest {
    private final String fileName;

    public UseOpenCsvParameterizedTest(String fileName) {
        this.fileName = fileName;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { null },
                { "nonexistent.csv" },
                { "empty_file.csv" }
        });
    }

    @Test
    public void readFile_WhenGivenFileName_ReturnEmptyList() {
        UseOpenCsv reader = new UseOpenCsv();

        List<String[]> result = reader.read(fileName);
        assertTrue(result.isEmpty());
    }
}

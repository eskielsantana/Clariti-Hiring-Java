package infrastructure.reader;

import java.util.List;

public interface FileReader {

    List<String[]> read(String file);
}
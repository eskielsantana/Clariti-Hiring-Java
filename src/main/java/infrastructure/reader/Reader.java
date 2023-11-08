package infrastructure.reader;

import java.util.List;

public interface Reader {

    List<String[]> readFile(String file);
}

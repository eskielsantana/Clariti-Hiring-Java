package infrastructure;

import domain.fee.Fee;
import domain.fee.FeeReader;
import infrastructure.reader.Reader;
import infrastructure.reader.UseOpenCsv;

import java.util.List;

public class FeeRepository implements FeeReader {
    private static final String FILE_NAME = "raw_fees.csv";
    private final Reader reader;

    public FeeRepository() {
        reader = new UseOpenCsv();
    }

    @Override
    public List<Fee> fetchFeeList() {
        return reader.readFile(FILE_NAME).stream().map(this::deserialize).toList();
    }

    private Fee deserialize(String[] data) {
        return new Fee(data[0],                                   // ID
                       data[1],                                   // Name
                       data[2],                                   // Description
                       data[3],                                   // Department
                       data[4],                                   // Category
                       data[5],                                   // SubCategory
                       data[6],                                   // Type
                       Integer.parseInt(data[7]),                 // Quantity
                       Double.parseDouble(data[8])                // Unit Price
        );
    }
}
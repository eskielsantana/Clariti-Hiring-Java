package infrastructure;

import domain.fee.Fee;
import domain.fee.FeeReader;
import infrastructure.reader.Reader;
import infrastructure.reader.UseOpenCsv;

import java.util.List;
import java.util.stream.Collectors;

public class FeeRepository implements FeeReader {
    private static final String FILE_NAME = "raw_fees.csv";
    private final Reader reader;

    public FeeRepository() {
        reader = new UseOpenCsv();
    }

    @Override
    public List<Fee> fetchFeeList() {
        return reader.readFile(FILE_NAME).stream().map(this::deserialize).collect(Collectors.toList());
    }

    private Fee deserialize(String[] record) {
        return new Fee(record[0],                                   // ID
                       record[1],                                   // Name
                       record[2],                                   // Description
                       record[3],                                   // Department
                       record[4],                                   // Category
                       record[5],                                   // SubCategory
                       record[6],                                   // Type
                       Integer.parseInt(record[7]),                 // Quantity
                       Double.parseDouble(record[8])                // Unit Price
        );
    }
}
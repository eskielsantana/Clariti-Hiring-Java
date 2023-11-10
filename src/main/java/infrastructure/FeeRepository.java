package infrastructure;

import domain.fee.Fee;
import domain.fee.FeeReader;
import infrastructure.reader.CSVFileReader;

import java.util.List;

public class FeeRepository implements FeeReader {
    private static final String FILE_NAME = "raw_fees.csv";
    private final CSVFileReader reader;

    public FeeRepository() {
        reader = new CSVFileReader();
    }

    public FeeRepository(CSVFileReader reader) {
        this.reader = reader;
    }

    @Override
    public List<Fee> fetchFeeList() {
        return reader.read(FILE_NAME).stream().map(this::deserialize).toList();
    }

    private Fee deserialize(String[] data) {
        return new Fee(escape(data[0]),                                   // ID
                       escape(data[1]),                                   // Name
                       escape(data[2]),                                   // Description
                       escape(data[3]),                                   // Department
                       escape(data[4]),                                   // Category
                       escape(data[5]),                                   // SubCategory
                       escape(data[6]),                                   // Type
                       Integer.parseInt(escape(data[7])),                 // Quantity
                       Double.parseDouble(escape(data[8]))                // Unit Price
        );
    }

    private String escape(String value) {
        return value.trim();
    }
}
package domain.fee;

import java.util.List;

public interface FeeReader {
    List<Fee> fetchFeeList();
}

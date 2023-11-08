package domain.fee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeeNode {
    public String name;
    public double feeTotal;
    public List<Fee> feeList;

    public Map<String, FeeNode> children = new HashMap<>();

    public FeeNode(String name) {
        this.name = name;
    }

    public void addFee(Fee fee) {
        if (feeList == null) {
            feeList = new ArrayList<>();
        }

        feeList.add(fee);
    }
}

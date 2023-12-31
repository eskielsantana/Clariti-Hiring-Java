package domain.fee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static domain.helper.CurrencyHelper.currencyFormat;

public class FeeNode {
    private final String name;
    private final List<Fee> feeList = new ArrayList<>();
    private final Map<String, FeeNode> children = new HashMap<>();
    private double feeTotal;

    public FeeNode(String name) {
        this.name = name;
    }

    public void addFee(Fee fee) {
        feeTotal += fee.getSubChargedPrice();
        feeList.add(fee);
    }

    public void addNodeFee(Fee fee) {
        FeeNode departmentNode = children.computeIfAbsent(fee.getDepartment(), FeeNode::new);
        FeeNode categoryNode = departmentNode.getChildren().computeIfAbsent(fee.getCategory(), FeeNode::new);
        FeeNode subCategoryNode = categoryNode.getChildren().computeIfAbsent(fee.getSubCategory(), FeeNode::new);
        FeeNode typeNode = subCategoryNode.getChildren().computeIfAbsent(fee.getType(), FeeNode::new);

        addFee(fee);
        departmentNode.addFee(fee);
        categoryNode.addFee(fee);
        subCategoryNode.addFee(fee);
        typeNode.addFee(fee);
    }

    public List<Fee> getFeeList() {
        return feeList;
    }

    public String getName() {
        return name;
    }

    public double getTotal() {
        return feeTotal;
    }

    public boolean hasNoChild() {
        return children.isEmpty();
    }

    public Map<String, FeeNode> getChildren() {
        return children;
    }

    public Collection<FeeNode> getChildrenValues() {
        return children.values();
    }

    @Override
    public String toString() {
        return String.format("| %-15s | %s Total | %d Fees |", name, currencyFormat(feeTotal), feeList.size());
    }
}

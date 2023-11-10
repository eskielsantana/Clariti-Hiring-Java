package domain.fee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static domain.helper.CurrencyHelper.currencyFormat;

public class FeeNode {
    private final String name;
    private double feeTotal;
    private List<Fee> feeList;
    private final Map<String, FeeNode> children = new HashMap<>();

    public FeeNode(String name) {
        this.name = name;
    }

    public void addFee(Fee fee) {
        if (feeList == null) {
            feeList = new ArrayList<>();
        }
        feeList.add(fee);
    }

    public void addNodeFee(Fee fee) {
        FeeNode departmentNode = children.computeIfAbsent(fee.getDepartment(), FeeNode::new);
        FeeNode categoryNode = departmentNode.getChildren().computeIfAbsent(fee.getCategory(), FeeNode::new);
        FeeNode subCategoryNode = categoryNode.getChildren().computeIfAbsent(fee.getSubCategory(), FeeNode::new);
        FeeNode typeNode = subCategoryNode.getChildren().computeIfAbsent(fee.getType(), FeeNode::new);

        addTotal(fee.getSubChargedPrice());
        departmentNode.addTotal(fee.getSubChargedPrice());
        categoryNode.addTotal(fee.getSubChargedPrice());
        subCategoryNode.addTotal(fee.getSubChargedPrice());
        typeNode.addTotal(fee.getSubChargedPrice());

        typeNode.addFee(fee);
    }

    public boolean hasFees() {
        return feeList != null;
    }
    public List<Fee> getFeeList() {
        return feeList;
    }

    public String getName() {
        return name;
    }

    public void addTotal(double total) {
        feeTotal += total;
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
        return String.format("| %-15s | %s Total |", name, currencyFormat(feeTotal));
    }
}

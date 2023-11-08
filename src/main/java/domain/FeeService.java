package domain;

import infrastructure.FeeRepository;

import java.util.ArrayList;
import java.util.List;

import static config.util.CurrencyUtil.currencyFormat;

public class FeeService {
    private final FeeNode root;
    private final FeeReader repository;

    public FeeService() {
        repository = new FeeRepository();

        root = new FeeNode("Total");
    }

    public void assembleTree() {
        List<Fee> feeList = repository.fetchFeeList();

        for (Fee fee : feeList) {
            insertNode(root, fee);
        }
    }

    public void printTree() {
        printTree(root, " ");
    }

    public double getTotalFeesByLayer(String... layers) {
        FeeNode node = findLayerNode(root, layers);

        return (node != null) ? node.getTotal() : 0;
    }

    public List<Fee> searchFeesByLayer(String... layers) {
        List<Fee> feeList = new ArrayList<>();

        FeeNode current = findLayerNode(root, layers);

        if (current == null) {
            return feeList;
        }

        collectFees(current, feeList);

        return feeList;
    }

    private void collectFees(FeeNode node, List<Fee> feeList) {
        if (node.hasNoChild() && node.hasFees()) {
            feeList.addAll(node.getFeeList());
        }

        for (FeeNode child : node.getChildrenValues()) {
            collectFees(child, feeList);
        }
    }

    private void printTree(FeeNode node, String indent) {
        System.out.println(indent + node.getName() + ": " + currencyFormat(node.getTotal()));
        node.getChildrenValues().forEach(child -> printTree(child, indent + "  "));
    }

    private FeeNode findLayerNode(FeeNode node, String... layers) {
        for (String layer : layers) {
            node = node.getChildren().get(layer);

            if (node == null) {
                return null;
            }
        }
        return node;
    }

    private void insertNode(FeeNode root, Fee fee) {
        FeeNode departmentNode = root.getChildren().computeIfAbsent(fee.getDepartment(), FeeNode::new);
        FeeNode categoryNode = departmentNode.getChildren().computeIfAbsent(fee.getCategory(), FeeNode::new);
        FeeNode subCategoryNode = categoryNode.getChildren().computeIfAbsent(fee.getSubCategory(), FeeNode::new);
        FeeNode typeNode = subCategoryNode.getChildren().computeIfAbsent(fee.getType(), FeeNode::new);

        double feeTotal = fee.calculateFee();

        root.addTotal(feeTotal);
        departmentNode.addTotal(feeTotal);
        categoryNode.addTotal(feeTotal);
        subCategoryNode.addTotal(feeTotal);
        typeNode.addTotal(feeTotal);
        typeNode.addFee(fee);
    }
}

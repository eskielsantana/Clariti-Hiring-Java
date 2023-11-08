package domain;

import infrastructure.FeeRepository;

import java.util.ArrayList;
import java.util.List;

import static config.util.CurrencyUtil.CurrencyFormatter;

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

        return (node != null) ? node.feeTotal : 0;
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
        if (node.children.isEmpty() && node.feeList != null) {
            feeList.addAll(node.feeList);
        }

        for (FeeNode child : node.children.values()) {
            collectFees(child, feeList);
        }
    }

    private void printTree(FeeNode node, String indent) {
        System.out.println(indent + node.name + ": " + CurrencyFormatter(node.feeTotal));
        node.children.values().forEach(child -> printTree(child, indent + "  "));
    }

    private FeeNode findLayerNode(FeeNode node, String... layers) {
        for (String layer : layers) {
            node = node.children.get(layer);

            if (node == null) {
                return null;
            }
        }
        return node;
    }

    private void insertNode(FeeNode root, Fee fee) {
        FeeNode departmentNode = root.children.computeIfAbsent(fee.getDepartment(), FeeNode::new);
        FeeNode categoryNode = departmentNode.children.computeIfAbsent(fee.getCategory(), FeeNode::new);
        FeeNode subCategoryNode = categoryNode.children.computeIfAbsent(fee.getSubCategory(), FeeNode::new);
        FeeNode typeNode = subCategoryNode.children.computeIfAbsent(fee.getType(), FeeNode::new);

        double feeTotal = fee.calculateFee();

        root.feeTotal += feeTotal;
        departmentNode.feeTotal += feeTotal;
        categoryNode.feeTotal += feeTotal;
        subCategoryNode.feeTotal += feeTotal;
        typeNode.feeTotal += feeTotal;
        typeNode.addFee(fee);
    }
}

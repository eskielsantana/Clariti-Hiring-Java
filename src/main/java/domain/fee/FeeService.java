package domain;

import infrastructure.FeeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FeeService {
    private final FeeNode root;
    private final FeeReader repository;

    public FeeService() {
        repository = new FeeRepository();
        root = new FeeNode("Total");

        loadFees();
    }

    public void assembleTree() {
        List<Fee> feeList = repository.fetchFeeList();

        for (Fee fee : feeList) {
            insertNode(root, fee);
        }
    }

    public double getNodeTotalFees(String... layers) {
        Optional<FeeNode> node = findLayerNode(root, layers);

        return node.map(FeeNode::getTotal).orElse(0.0);
    }

    public List<FeeNode> getNodeList(String... layers) {
        Optional<FeeNode> node = findLayerNode(root, layers);

        return new ArrayList<>(node.orElse(root).getChildrenValues());
    }

    public List<Fee> getFeesByLayer(int limit, boolean isReversed, String... layers) {
        List<Fee> feeList = new ArrayList<>();
        Optional<FeeNode> node = findLayerNode(root, layers);

        collectFees(node.orElse(root), feeList);

        sortFeeList(feeList, isReversed);

        return feeList.stream().limit(limit).toList();
    }

    private void collectFees(FeeNode node, List<Fee> feeList) {
        if (node.hasNoChild() && node.hasFees()) {
            feeList.addAll(node.getFeeList());
        }
        for (FeeNode child : node.getChildrenValues()) {
            collectFees(child, feeList);
        }
    }

    private Optional<FeeNode> findLayerNode(FeeNode node, String... layers) {
        for (String layer : layers) {
            node = node.getChildren().get(layer);

            if (node == null) { return Optional.empty(); }
        }
        return Optional.of(node);
    }

    private void insertNode(FeeNode root, Fee fee) {
        FeeNode departmentNode = root.getChildren().computeIfAbsent(fee.getDepartment(), FeeNode::new);
        FeeNode categoryNode = departmentNode.getChildren().computeIfAbsent(fee.getCategory(), FeeNode::new);
        FeeNode subCategoryNode = categoryNode.getChildren().computeIfAbsent(fee.getSubCategory(), FeeNode::new);
        FeeNode typeNode = subCategoryNode.getChildren().computeIfAbsent(fee.getType(), FeeNode::new);

        root.addTotal(fee.getSubChargedPrice());
        departmentNode.addTotal(fee.getSubChargedPrice());
        categoryNode.addTotal(fee.getSubChargedPrice());
        subCategoryNode.addTotal(fee.getSubChargedPrice());
        typeNode.addTotal(fee.getSubChargedPrice());
        typeNode.addFee(fee);
    }

    private void sortFeeList(List<Fee> feeList, boolean isReversed) {
        Comparator<Fee> comparator = Comparator.comparingDouble(Fee::getSubChargedPrice);

        if(isReversed) { comparator = comparator.reversed(); }

        feeList.sort(comparator);
    }

    private void loadFees() {
        List<Fee> feeList = repository.fetchFeeList();

        for (Fee fee : feeList) {
            insertNode(root, fee);
        }
    }

    // Variables exposed for tests
    public FeeNode getRootForTesting() {
        return root;
    }
    public FeeReader getFeeReaderForTesting() {
        return repository;
    }
}

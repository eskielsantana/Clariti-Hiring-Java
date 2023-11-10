package domain.fee;

import infrastructure.FeeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FeeService {
    private static FeeService instance;
    private FeeNode root;
    private final FeeReader readerRepository;

    public FeeService() {
        readerRepository = new FeeRepository();
        loadFees();
    }

    public FeeService(FeeReader readerRepository) {
        this.readerRepository = readerRepository;
        loadFees();
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
        Optional<FeeNode> node = findLayerNode(root, layers);

        List<Fee> feeList = node.orElse(root).getFeeList();

        sortFeeList(feeList, isReversed);

        return feeList.stream().limit(limit).toList();
    }

    private Optional<FeeNode> findLayerNode(FeeNode node, String... layers) {
        for (String layer : layers) {
            node = node.getChildren().get(layer);

            if (node == null) { return Optional.empty(); }
        }
        return Optional.of(node);
    }

    private void sortFeeList(List<Fee> feeList, boolean isReversed) {
        Comparator<Fee> comparator = Comparator.comparingDouble(Fee::getSubChargedPrice);

        if(isReversed) { comparator = comparator.reversed(); }

        feeList.sort(comparator);
    }

    private void loadFees() {
        root = new FeeNode("Total");

        List<Fee> feeList = readerRepository.fetchFeeList();

        for (Fee fee : feeList) {
            root.addNodeFee(fee);
        }
    }

    public static void setInstance(FeeService instance) {
        FeeService.instance = instance;
    }
    public static FeeService getInstance() {
        if (instance == null) {
            instance = new FeeService();
        }
        return instance;
    }

    public FeeNode getRootNodeForTesting() {
        return root;
    }
    public FeeReader getFeeReaderForTesting() {
        return readerRepository;
    }
    public static void cleanInstance() { instance = null; }
    public static boolean isInstanceNull() {
        return instance == null;
    }
}

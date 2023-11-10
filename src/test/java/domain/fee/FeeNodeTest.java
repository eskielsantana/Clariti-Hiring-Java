package domain.fee;

import org.junit.Test;

import static domain.helper.CurrencyHelper.currencyFormat;
import static domain.fee.FeeFactory.mockFee;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FeeNodeTest {

    @Test
    public void feeNode_hasFees_WhenCalled_ProperlyReturnsIfNodeHasFeesAssigned() {
        FeeNode node1 = new FeeNode("Node1");
        node1.addFee(mockFee(1));

        FeeNode node2 = new FeeNode("Node2");

        assertTrue(node1.hasFees());
        assertFalse(node2.hasFees());
    }

    @Test
    public void toString_WhenCalled_ReturnsStringFormattedWithExpectedData() {
        String nodeName = "Department";
        double value = 15.00;

        Fee fee = new Fee("Fee1", "Fee1", "",
                          "Test Sales", "Sales Engineering", "Cat1", "TypeC", 1, value);

        FeeNode root = new FeeNode(nodeName);
        root.addNodeFee(fee);

        String formatted = root.toString();

        assertNotNull(formatted);
        assertTrue(formatted.contains(nodeName));
        assertTrue(formatted.contains(currencyFormat(value)));
    }
}

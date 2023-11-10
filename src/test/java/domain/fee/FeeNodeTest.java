package domain.fee;

import org.junit.Test;

import static domain.helper.CurrencyHelper.currencyFormat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FeeNodeTest {

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
        assertTrue(formatted.contains(String.format("%d Fees", root.getFeeList().size())));
    }
}

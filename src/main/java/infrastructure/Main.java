package infrastructure;

import domain.fee.FeeService;
import org.apache.log4j.Logger;

import static domain.helper.CurrencyHelper.currencyFormat;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        FeeService service = FeeService.getInstance();

        double totalFee1 = service.getNodeTotalFees("Development", "Quality Assurance", "Cat1");
        LOGGER.info("What are the total Cat1 fees within the Quality Assurance Category of the Development department?");
        LOGGER.info("Result: " + currencyFormat(totalFee1));
        LOGGER.info("");

        double totalFee2 = service.getNodeTotalFees("Operations", "Human Resources");
        LOGGER.info("What are the total fees for the Human Resources category of the Operations department?");
        LOGGER.info("Result: " + currencyFormat(totalFee2));
        LOGGER.info("");

        LOGGER.info("What are the total fees for each department?");
        service.getNodeList().forEach(node -> LOGGER.info(node.toString()));
        LOGGER.info("");

        LOGGER.info("What are the 5 most expensive total fees for the Development department?");
        service.getFeesByLayer(5, true, "Development").forEach(node -> LOGGER.info(node.toString()));
        LOGGER.info("");

        LOGGER.info("What are the cheapest 3 total fees for the Sales Engineering category of the Sales department?");
        service.getFeesByLayer(3, false, "Sales", "Sales Engineering").forEach(node -> LOGGER.info(node.toString()));
    }
}
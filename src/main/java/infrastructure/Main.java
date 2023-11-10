package infrastructure;

import domain.fee.FeeService;
import org.apache.log4j.Logger;


import static domain.currency.Currency.currencyFormat;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        FeeService service = new FeeService();

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

        LOGGER.info("What are the biggest 5 fees for the Development department?");
        service.getFeesByLayer(5, true,"Development").forEach(node -> LOGGER.info(node.toString()));
        LOGGER.info("");

        LOGGER.info("What are the smaller 3 fees for the Sales Engineering category of the Sales department?");
        service.getFeesByLayer(3, false, "Sales", "Sales Engineering").forEach(node -> LOGGER.info(node.toString()));
    }


}
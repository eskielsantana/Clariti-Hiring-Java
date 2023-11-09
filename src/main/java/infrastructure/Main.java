package infrastructure;

import domain.fee.FeeService;

import static config.util.CurrencyUtil.currencyFormat;

public class Main {
    public static void main(String[] args) {
        FeeService service = new FeeService();

        System.out.print("\n");

        double totalFee1 = service.getNodeTotalFees("Development", "Quality Assurance", "Cat1");

        System.out.println("What are the total Cat1 fees within the Quality Assurance Category of the Development department?");
        System.out.println("Result: " + currencyFormat(totalFee1));

        System.out.print("\n");

        double totalFee2 = service.getNodeTotalFees("Operations", "Human Resources");

        System.out.println("What are the total fees for the Human Resources category of the Operations department?");
        System.out.println("Result: " + currencyFormat(totalFee2));

        System.out.print("\n");

        System.out.println("What are the total fees for each department?\nResult:");
        service.getNodeList().forEach(node -> System.out.println(node.toString()));

        System.out.print("\n");

        System.out.println("What are the biggest 5 fees for the Development department?\nResult:");
        service.getFeesByLayer(5, true,"Development").forEach(node -> System.out.println(node.toString()));

        System.out.print("\n");

        System.out.println("What are the smaller 3 fees for the Sales Engineering category of the Sales department?\nResult:");
        service.getFeesByLayer(3, false, "Sales", "Sales Engineering").forEach(node -> System.out.println(node.toString()));

        System.out.print("\n");
    }
}
import domain.FeeService;

import static config.util.CurrencyUtil.currencyFormat;

public class Main {
    public static void main(String[] args) {
        FeeService manager = new FeeService();

        manager.assembleTree();

        manager.printTree();

        //List<Fee> feeList = manager.searchFeesByLayer("Operations", "Performance Management");

        double totalFee1 = manager.getTotalFeesByLayer("Development", "Quality Assurance", "Cat1");

        System.out.println("What are the total Cat1 fees within Quality Assurance Category of the Development department?");
        System.out.println("Expected: " + currencyFormat(110212) + " | Result: " + currencyFormat(totalFee1));

        System.out.println("\n");

        double totalFee2 = manager.getTotalFeesByLayer("Operations", "Human Resources");

        System.out.println("What are the total fees for the Human Resources category of the Operations department?");
        System.out.println("Expected: " + currencyFormat(229041) + " | Result: " + currencyFormat(totalFee2));
    }
}
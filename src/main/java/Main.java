import static config.util.CurrencyUtil.CurrencyFormatter;

public class Main {
    public static void main(String[] args) {
        FeeService manager = new FeeService();

        manager.AssembleTree();

        manager.printTree();

        //List<Fee> feeList = manager.searchFeesByLayer("Operations", "Performance Management");

        double totalFee1 = manager.getTotalFeesByLayer("Development", "Quality Assurance", "Cat1");

        System.out.println("What are the total Cat1 fees within Quality Assurance Category of the Development department?");
        System.out.println("Expected: " + CurrencyFormatter(110212) + " | Result: " + CurrencyFormatter(totalFee1));

        System.out.println("\n");

        double totalFee2 = manager.getTotalFeesByLayer("Operations", "Human Resources");

        System.out.println("What are the total fees for the Human Resources category of the Operations department?");
        System.out.println("Expected: " + CurrencyFormatter(229041) + " | Result: " + CurrencyFormatter(totalFee2));
    }
}
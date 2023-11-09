package domain.fee;

import static config.util.CurrencyUtil.currencyFormat;
import static config.util.CurrencyUtil.percentage;

public class Fee implements Comparable<Fee> {
    private final String id;
    private final String name;
    private final String description;
    private final String department;
    private final String category;
    private final String subCategory;
    private final String type;
    private final int quantity;
    private final double price;
    private final double subChargedPrice;

    public Fee(String id,
               String name,
               String description,
               String department,
               String category,
               String subCategory,
               String type,
               int quantity,
               double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.department = department;
        this.category = category;
        this.subCategory = subCategory;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.subChargedPrice = calculateSubcharge();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getType() {
        return type;
    }

    public double getSubChargedPrice() { return subChargedPrice; }

    @Override
    public String toString() {
        return String.format(" - %s | %-45s | %s/un | %2d Units | %s Charge | %s Total |",
                             id, name, currencyFormat(price), quantity, percentage(subCharge()) ,currencyFormat(subChargedPrice));
    }

    @Override
    public int compareTo(Fee o) {
        return Double.compare(this.subChargedPrice, o.subChargedPrice);
    }

    private double subCharge() {
        return switch (department) {
            case "Marketing" -> 1.1;
            case "Sales" -> 1.15;
            case "Development" -> 1.2;
            case "Operations" -> 0.85;
            case "Support" -> 0.95;
            default -> 1;
        };
    }

    private double calculateSubcharge() {
        return price * quantity * subCharge();
    }
}
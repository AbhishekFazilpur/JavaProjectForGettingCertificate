import java.util.Scanner;

public class ElectricityBillCalculator2 {
    // Constants for unit prices and slabs
    private static final double UNIT_PRICE_FIRST_SLAB_RESIDENTIAL = 2.50;
    private static final double UNIT_PRICE_SECOND_SLAB_RESIDENTIAL = 4.00;
    private static final double UNIT_PRICE_THIRD_SLAB_RESIDENTIAL = 6.50;
    private static final double UNIT_PRICE_FIRST_SLAB_COMMERCIAL = 5.00;
    private static final double UNIT_PRICE_SECOND_SLAB_COMMERCIAL = 6.50;
    private static final double UNIT_PRICE_THIRD_SLAB_COMMERCIAL = 8.00;
    private static final int SLAB_ONE_LIMIT = 100;
    private static final int SLAB_TWO_LIMIT = 300;
    private static final double TAX_RATE = 0.10;
    private static final double DISCOUNT_RESIDENTIAL = 0.05;
    private static final double DISCOUNT_COMMERCIAL = 0.10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Electricity Bill Calculator");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name + "!");

        // Choose billing type
        System.out.println("Please choose the type of billing:");
        System.out.println("1. Residential");
        System.out.println("2. Commercial");
        System.out.print("Enter your choice (1 or 2): ");
        int billingType = scanner.nextInt();

        // Loop to calculate multiple bills
        while (true) {
            // Input
            int unitsConsumed = getValidInput(scanner);

            // Calculate Bill
            double billAmount = calculateBill(unitsConsumed, billingType);

            // Display Bill Summary
            displayBillSummary(unitsConsumed, billAmount, billingType);

            // Ask if user wants to calculate another bill
            System.out.print("Do you want to calculate another bill? (yes/no): ");
            String choice = scanner.next();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("Thank you for using Electricity Bill Calculator");
        scanner.close();
    }

    // Method to get valid input from user
    private static int getValidInput(Scanner scanner) {
        int unitsConsumed;
        while (true) {
            try {
                System.out.print("Enter the number of units consumed: ");
                unitsConsumed = Integer.parseInt(scanner.next());
                if (unitsConsumed < 0) {
                    throw new IllegalArgumentException("Units consumed cannot be negative.");
                }
                break; // Input is valid, exit loop
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return unitsConsumed;
    }

    // Method to calculate the bill amount
    private static double calculateBill(int unitsConsumed, int billingType) {
        double billAmount = 0.0;
        double unitPriceFirstSlab = (billingType == 1) ? UNIT_PRICE_FIRST_SLAB_RESIDENTIAL : UNIT_PRICE_FIRST_SLAB_COMMERCIAL;
        double unitPriceSecondSlab = (billingType == 1) ? UNIT_PRICE_SECOND_SLAB_RESIDENTIAL : UNIT_PRICE_SECOND_SLAB_COMMERCIAL;
        double unitPriceThirdSlab = (billingType == 1) ? UNIT_PRICE_THIRD_SLAB_RESIDENTIAL : UNIT_PRICE_THIRD_SLAB_COMMERCIAL;

        if (unitsConsumed <= SLAB_ONE_LIMIT) {
            billAmount = unitsConsumed * unitPriceFirstSlab;
        } else if (unitsConsumed <= SLAB_TWO_LIMIT) {
            billAmount = (SLAB_ONE_LIMIT * unitPriceFirstSlab) +
                    ((unitsConsumed - SLAB_ONE_LIMIT) * unitPriceSecondSlab);
        } else {
            billAmount = (SLAB_ONE_LIMIT * unitPriceFirstSlab) +
                    ((SLAB_TWO_LIMIT - SLAB_ONE_LIMIT) * unitPriceSecondSlab) +
                    ((unitsConsumed - SLAB_TWO_LIMIT) * unitPriceThirdSlab);
        }

        // Apply discount for residential customers
        if (billingType == 1) {
            billAmount *= (1 - DISCOUNT_RESIDENTIAL);
        }

        // Apply taxes
        billAmount *= (1 + TAX_RATE);

        return billAmount;
    }

    // Method to display the bill summary
    private static void displayBillSummary(int unitsConsumed, double billAmount, int billingType) {
        String type = (billingType == 1) ? "Residential" : "Commercial";
        System.out.println("---- Bill Summary ----");
        System.out.println("Type of Billing: " + type);
        System.out.println("Units Consumed: " + unitsConsumed);
        System.out.printf("Bill Amount: $%.2f%n", billAmount);
        System.out.println("----------------------");
    }
}

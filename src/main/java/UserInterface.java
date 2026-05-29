import java.util.List;
import java.util.Scanner;

public class UserInterface{

    private Scanner kb = new Scanner(System.in);
    private Order currentOrder;

    public void displayHomeScreen() {
        while (true) {
            System.out.println("\n================================");
            System.out.println("      Welcome to StackHouse     ");
            System.out.println("================================");
            System.out.println("  1) New Order");
            System.out.println("  0) Exit");
            System.out.println("================================");

            int choice = InputValidator.getValidInt(kb, 0, 1);

            if (choice == 1) {
                currentOrder = new Order();
                displayOrderScreen();
            } else {
                System.out.println("\n  Thanks for visiting StackHouse. Goodbye!");
                System.exit(0);
            }
        }
    }

    private void displayOrderScreen() {
        while (true) {
            System.out.println("\n================================");
            System.out.println("          Your Order            ");
            System.out.println("================================");

            if (!currentOrder.isEmpty()) {
                System.out.println(currentOrder.getOrderDetails());
                System.out.println("--------------------------------");
            } else {
                System.out.println("  No items yet.");
                System.out.println("--------------------------------");
            }

            System.out.println("  1) Add Sandwich");
            System.out.println("  2) Add Drink");
            System.out.println("  3) Add Chips");
            System.out.println("  4) Checkout");
            System.out.println("  0) Cancel Order");
            System.out.println("================================");

            int choice = InputValidator.getValidInt(kb, 0, 4);

            switch (choice) {
                case 1 -> addSandwich();
                case 2 -> addDrink();
                case 3 -> addChips();
                case 4 -> {
                    checkout();
                    if (currentOrder == null) {
                        return;
                    }
                }
                case 0 -> {
                    System.out.println("  Order cancelled.");
                    currentOrder = null;
                    return;
                }
            }
        }
    }

    private void addSandwich() {
        System.out.println("\n--- Add Sandwich ---");
        System.out.println("  1) Custom Sandwich");
        System.out.println("  2) BLT (Signature)");
        System.out.println("  3) Philly Cheese Steak (Signature)");

        Integer sandwichChoice = InputValidator.getValidIntOrBack(kb, 1, 3);

        if (sandwichChoice == null) {
            return;
        }

        if (sandwichChoice == 2) {
            Sandwich blt = new BLT();
            customizeSignatureSandwich(blt);
            currentOrder.addItem(blt);
            System.out.println("  BLT added to order.");
            return;
        }

        if (sandwichChoice == 3) {
            Sandwich philly = new PhillyCheeseSteak();
            customizeSignatureSandwich(philly);
            currentOrder.addItem(philly);
            System.out.println("  Philly Cheese Steak added to order.");
            return;
        }

        System.out.println("\n  Select bread:");
        System.out.println("  1) White");
        System.out.println("  2) Wheat");
        System.out.println("  3) Rye");
        System.out.println("  4) Wrap");

        Integer breadChoice = InputValidator.getValidIntOrBack(kb, 1, 4);

        if (breadChoice == null) {
            return;
        }

        BreadType bread = switch (breadChoice) {
            case 1 -> BreadType.WHITE;
            case 2 -> BreadType.WHEAT;
            case 3 -> BreadType.RYE;
            default -> BreadType.WRAP;
        };

        System.out.println("\n  Select size:");
        System.out.println("  1) 4\" - $5.50");
        System.out.println("  2) 8\" - $7.00");
        System.out.println("  3) 12\" - $8.50");

        Integer sizeChoice = InputValidator.getValidIntOrBack(kb, 1, 3);

        if (sizeChoice == null) {
            return;
        }

        Size size = switch (sizeChoice) {
            case 1 -> Size.FOUR_INCH;
            case 2 -> Size.EIGHT_INCH;
            default -> Size.TWELVE_INCH;
        };

        Boolean toasted = InputValidator.getYesNoOrBack(
                kb,
                "\n  Would you like it toasted? (yes/no): "
        );

        if (toasted == null) {
            return;
        }

        Sandwich sandwich = new Sandwich(size, bread, toasted);

        addMeatsToSandwich(sandwich);
        addCheesesToSandwich(sandwich);
        addRegularToppingsToSandwich(sandwich);
        addSaucesToSandwich(sandwich);

        currentOrder.addItem(sandwich);

        System.out.printf("%n  Sandwich added! ($%.2f)%n", sandwich.getPrice());
    }

    private void customizeSignatureSandwich(Sandwich sandwich) {
        System.out.println("\nCurrent signature sandwich:");
        System.out.println(sandwich.getDetails());

        Boolean customize = InputValidator.getYesNoOrBack(
                kb,
                "\nWould you like to customize this sandwich? (yes/no): "
        );

        if (customize == null || !customize) {
            return;
        }

        while (true) {
            System.out.println("\n--- Customize Signature Sandwich ---");
            System.out.println("  1) Remove topping");
            System.out.println("  2) Add meat");
            System.out.println("  3) Add cheese");
            System.out.println("  4) Add regular topping");
            System.out.println("  5) Add sauce");
            System.out.println("  0) Done");

            int choice = InputValidator.getValidInt(kb, 0, 5);

            switch (choice) {
                case 1 -> removeToppingFromSandwich(sandwich);
                case 2 -> addMeatsToSandwich(sandwich);
                case 3 -> addCheesesToSandwich(sandwich);
                case 4 -> addRegularToppingsToSandwich(sandwich);
                case 5 -> addSaucesToSandwich(sandwich);
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void removeToppingFromSandwich(Sandwich sandwich) {
        List<Topping> toppings = sandwich.getToppings();

        if (toppings.isEmpty()) {
            System.out.println("  No toppings to remove.");
            return;
        }

        System.out.println("\n  Select topping to remove:");

        for (int i = 0; i < toppings.size(); i++) {
            Topping topping = toppings.get(i);
            String extraText = topping.isExtra() ? " (extra)" : "";

            System.out.printf(
                    "  %d) %s%s%n",
                    i + 1,
                    topping.getName(),
                    extraText
            );
        }

        System.out.println("  0) Cancel");

        int choice = InputValidator.getValidInt(kb, 0, toppings.size());

        if (choice == 0) {
            return;
        }

        Topping removed = toppings.get(choice - 1);
        sandwich.removeTopping(removed);

        System.out.println("  Removed " + removed.getName() + ".");
    }

    private void addMeatsToSandwich(Sandwich sandwich) {
        List<String> meats = List.of(
                "Steak",
                "Ham",
                "Salami",
                "Roast Beef",
                "Chicken",
                "Bacon"
        );

        System.out.println("\n  Select meats (enter each number, 0 when done):");

        for (int i = 0; i < meats.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, meats.get(i));
        }

        System.out.println("  0) Done");

        while (true) {
            int meatChoice = InputValidator.getValidInt(kb, 0, meats.size());

            if (meatChoice == 0) {
                break;
            }

            String meatName = meats.get(meatChoice - 1);
            Boolean extra = InputValidator.getYesNoOrBack( kb,
                    "  Add extra " + meatName + "? (yes/no): "
            );

            if (extra == null) {
                System.out.println("  Meat selection cancelled.");
                continue;
            }

            sandwich.addTopping(new Meat(meatName, false));

            if (extra) {
                sandwich.addTopping(new Meat(meatName, true));
            }

            System.out.println("  Added. Select another meat or 0 to finish:");
        }
    }

    private void addCheesesToSandwich(Sandwich sandwich) {
        List<String> cheeses = List.of(
                "American",
                "Provolone",
                "Cheddar",
                "Swiss"
        );

        System.out.println("\n  Select cheeses (enter each number, 0 when done):");

        for (int i = 0; i < cheeses.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, cheeses.get(i));
        }

        System.out.println("  0) Done");

        while (true) {
            int cheeseChoice = InputValidator.getValidInt(kb, 0, cheeses.size());

            if (cheeseChoice == 0) {
                break;
            }
            String cheeseName = cheeses.get(cheeseChoice - 1);
            Boolean extra = InputValidator.getYesNoOrBack(
                    kb,
                    "  Add extra " + cheeseName + "? (yes/no): "
            );
            if (extra == null) {
                System.out.println("  Cheese selection cancelled.");
                continue;
            }
            sandwich.addTopping(new Cheese(cheeseName, false));

            if (extra) {
                sandwich.addTopping(new Cheese(cheeseName, true));
            }
            System.out.println("  Added. Select another cheese or 0 to finish:");
        }
    }
    private void addRegularToppingsToSandwich(Sandwich sandwich) {
        List<String> toppings = List.of(
                "Lettuce",
                "Peppers",
                "Onions",
                "Tomatoes",
                "Jalapeños",
                "Cucumbers",
                "Pickles",
                "Guacamole",
                "Mushrooms"
        );

        System.out.println("\n  Select toppings (enter each number, 0 when done):");

        for (int i = 0; i < toppings.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, toppings.get(i));
        }

        System.out.println("  0) Done");

        while (true) {
            int toppingChoice = InputValidator.getValidInt(kb, 0, toppings.size());

            if (toppingChoice == 0) {
                break;
            }

            sandwich.addTopping(
                    new RegularTopping(toppings.get(toppingChoice - 1), false)
            );

            System.out.println("  Added. Select another topping or 0 to finish:");
        }
    }

    private void addSaucesToSandwich(Sandwich sandwich) {
        List<String> sauces = List.of(
                "Mayo",
                "Mustard",
                "Ketchup",
                "Ranch",
                "Thousand Islands",
                "Vinaigrette"
        );

        System.out.println("\n  Select sauces (enter each number, 0 when done):");

        for (int i = 0; i < sauces.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, sauces.get(i));
        }

        System.out.println("  0) Done");

        while (true) {
            int sauceChoice = InputValidator.getValidInt(kb, 0, sauces.size());

            if (sauceChoice == 0) {
                break;
            }

            sandwich.addTopping(
                    new Sauce(sauces.get(sauceChoice - 1), false)
            );

            System.out.println("  Added. Select another sauce or 0 to finish:");
        }
    }

    private void addDrink() {
        System.out.println("\n--- Add Drink ---");

        System.out.println("  Select size:");
        System.out.println("  1) Small  - $2.00");
        System.out.println("  2) Medium - $2.50");
        System.out.println("  3) Large  - $3.00");

        Integer sizeChoice = InputValidator.getValidIntOrBack(kb, 1, 3);

        if (sizeChoice == null) {
            return;
        }

        DrinkSize size = switch (sizeChoice) {
            case 1 -> DrinkSize.SMALL;
            case 2 -> DrinkSize.MEDIUM;
            default -> DrinkSize.LARGE;
        };

        System.out.println("\n  Select flavor:");
        System.out.println("  1) Coke");
        System.out.println("  2) Sprite");
        System.out.println("  3) Fanta");
        System.out.println("  4) Dr Pepper");
        System.out.println("  5) Lemonade");
        System.out.println("  6) Water");

        Integer flavorChoice = InputValidator.getValidIntOrBack(kb, 1, 6);

        if (flavorChoice == null) {
            return;
        }

        DrinkFlavor flavor = switch (flavorChoice) {
            case 1 -> DrinkFlavor.COKE;
            case 2 -> DrinkFlavor.SPRITE;
            case 3 -> DrinkFlavor.FANTA;
            case 4 -> DrinkFlavor.DR_PEPPER;
            case 5 -> DrinkFlavor.LEMONADE;
            default -> DrinkFlavor.WATER;
        };

        currentOrder.addItem(new Drink(size, flavor));

        System.out.println("  Drink added.");
    }

    private void addChips() {
        System.out.println("\n--- Add Chips ---");

        System.out.println("  1) Classic");
        System.out.println("  2) BBQ");
        System.out.println("  3) Sour Cream & Onion");
        System.out.println("  4) Salt & Vinegar");
        System.out.println("  5) Jalapeno");

        Integer choice = InputValidator.getValidIntOrBack(kb, 1, 5);

        if (choice == null) {
            return;
        }

        String type = switch (choice) {
            case 1 -> "Classic";
            case 2 -> "BBQ";
            case 3 -> "Sour Cream & Onion";
            case 4 -> "Salt & Vinegar";
            default -> "Jalapeno";
        };

        currentOrder.addItem(new Chips(type));

        System.out.println("  Chips added.");
    }

    private void checkout() {
        if (currentOrder.isEmpty()) {
            System.out.println("\n  You cannot checkout with an empty order.");
            return;
        }

        System.out.println("\n================================");
        System.out.println("           Checkout             ");
        System.out.println("================================");
        System.out.println(currentOrder.getOrderDetails());
        System.out.println("================================");

        Boolean confirm = InputValidator.getYesNoOrBack(
                kb,
                "\n  Confirm order? (yes/no): "
        );

        if (confirm == null) {
            return;
        }

        if (confirm) {
            ReceiptFileManager.saveReceipt(currentOrder);
            System.out.println("  Thank you for your order!");
        } else {
            System.out.println("  Order cancelled.");
        }
        currentOrder = null;
    }
}
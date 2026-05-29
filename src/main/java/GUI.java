import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {

    private Order currentOrder;
    private JTextArea orderArea;

    private final Color publixGreen = new Color(0, 120, 40);
    private final Color lightGreen = new Color(220, 245, 220);

    public GUI() {
        currentOrder = new Order();

        setTitle("StackHouse");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createLayout();

        setVisible(true);
    }

    private void createLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("StackHouse", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(publixGreen);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JButton addSandwichButton = createButton("Add Sandwich");
        JButton addDrinkButton = createButton("Add Drink");
        JButton addChipsButton = createButton("Add Chips");
        JButton checkoutButton = createButton("Checkout");
        JButton clearButton = createButton("Cancel Order");

        buttonPanel.add(addSandwichButton);
        buttonPanel.add(addDrinkButton);
        buttonPanel.add(addChipsButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(clearButton);

        orderArea = new JTextArea();
        orderArea.setEditable(false);
        orderArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        orderArea.setBackground(lightGreen);
        orderArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(orderArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Order"));

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        addSandwichButton.addActionListener(e -> addSandwich());
        addDrinkButton.addActionListener(e -> addDrink());
        addChipsButton.addActionListener(e -> addChips());
        checkoutButton.addActionListener(e -> checkout());
        clearButton.addActionListener(e -> clearOrder());

        updateOrderArea();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setBackground(publixGreen);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);

        return button;
    }

    private void addSandwich() {
        String[] options = {
                "Custom Sandwich",
                "BLT",
                "Philly Cheese Steak"
        };

        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Choose sandwich type:",
                "Add Sandwich",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == null) {
            return;
        }

        Sandwich sandwich;

        if (choice.equals("BLT")) {
            sandwich = new BLT();
            customizeSignatureSandwich(sandwich);
        } else if (choice.equals("Philly Cheese Steak")) {
            sandwich = new PhillyCheeseSteak();
            customizeSignatureSandwich(sandwich);
        } else {
            sandwich = buildCustomSandwich();
        }

        if (sandwich != null) {
            currentOrder.addItem(sandwich);
            updateOrderArea();
        }
    }

    private void customizeSignatureSandwich(Sandwich sandwich) {
        int customize = JOptionPane.showConfirmDialog(
                this,
                sandwich.getDetails() + "\n\nWould you like to customize this sandwich?",
                "Customize Signature Sandwich",
                JOptionPane.YES_NO_OPTION
        );

        if (customize != JOptionPane.YES_OPTION) {
            return;
        }

        String[] options = {
                "Remove Topping",
                "Add Meat",
                "Add Cheese",
                "Add Toppings",
                "Add Sauces",
                "Done"
        };

        while (true) {
            String choice = (String) JOptionPane.showInputDialog(
                    this,
                    sandwich.getDetails() + "\n\nChoose customization option:",
                    "Customize Sandwich",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == null || choice.equals("Done")) {
                return;
            }

            switch (choice) {
                case "Remove Topping" -> removeToppingFromSandwich(sandwich);
                case "Add Meat" -> addMeat(sandwich);
                case "Add Cheese" -> addCheese(sandwich);
                case "Add Toppings" -> addRegularToppings(sandwich);
                case "Add Sauces" -> addSauces(sandwich);
            }
        }
    }

    private void removeToppingFromSandwich(Sandwich sandwich) {
        List<Topping> toppings = sandwich.getToppings();

        if (toppings.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "There are no toppings to remove."
            );
            return;
        }

        String[] toppingOptions = new String[toppings.size()];

        for (int i = 0; i < toppings.size(); i++) {
            Topping topping = toppings.get(i);
            String extraText = topping.isExtra() ? " (extra)" : "";
            toppingOptions[i] = topping.getName() + extraText;
        }

        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Choose topping to remove:",
                "Remove Topping",
                JOptionPane.PLAIN_MESSAGE,
                null,
                toppingOptions,
                toppingOptions[0]
        );

        if (selected == null) {
            return;
        }

        for (int i = 0; i < toppingOptions.length; i++) {
            if (toppingOptions[i].equals(selected)) {
                Topping removed = toppings.get(i);
                sandwich.removeTopping(removed);

                JOptionPane.showMessageDialog(
                        this,
                        removed.getName() + " removed."
                );

                return;
            }
        }
    }

    private Sandwich buildCustomSandwich() {
        BreadType bread = (BreadType) JOptionPane.showInputDialog(
                this,
                "Choose bread:",
                "Bread",
                JOptionPane.PLAIN_MESSAGE,
                null,
                BreadType.values(),
                BreadType.WHITE
        );

        if (bread == null) {
            return null;
        }

        Size size = (Size) JOptionPane.showInputDialog(
                this,
                "Choose size:",
                "Size",
                JOptionPane.PLAIN_MESSAGE,
                null,
                Size.values(),
                Size.EIGHT_INCH
        );

        if (size == null) {
            return null;
        }

        int toastedChoice = JOptionPane.showConfirmDialog(
                this,
                "Would you like it toasted?",
                "Toasted",
                JOptionPane.YES_NO_OPTION
        );

        boolean toasted = toastedChoice == JOptionPane.YES_OPTION;

        Sandwich sandwich = new Sandwich(size, bread, toasted);

        addMeat(sandwich);
        addCheese(sandwich);
        addRegularToppings(sandwich);
        addSauces(sandwich);

        return sandwich;
    }

    private void addMeat(Sandwich sandwich) {
        String[] meats = {
                "None",
                "Steak",
                "Ham",
                "Salami",
                "Roast Beef",
                "Chicken",
                "Bacon"
        };

        String meat = (String) JOptionPane.showInputDialog(
                this,
                "Choose meat:",
                "Meat",
                JOptionPane.PLAIN_MESSAGE,
                null,
                meats,
                meats[0]
        );

        if (meat != null && !meat.equals("None")) {
            sandwich.addTopping(new Meat(meat, false));

            int extra = JOptionPane.showConfirmDialog(
                    this,
                    "Add extra " + meat + "?",
                    "Extra Meat",
                    JOptionPane.YES_NO_OPTION
            );

            if (extra == JOptionPane.YES_OPTION) {
                sandwich.addTopping(new Meat(meat, true));
            }
        }
    }

    private void addCheese(Sandwich sandwich) {
        String[] cheeses = {
                "None",
                "American",
                "Provolone",
                "Cheddar",
                "Swiss"
        };

        String cheese = (String) JOptionPane.showInputDialog(
                this,
                "Choose cheese:",
                "Cheese",
                JOptionPane.PLAIN_MESSAGE,
                null,
                cheeses,
                cheeses[0]
        );

        if (cheese != null && !cheese.equals("None")) {
            sandwich.addTopping(new Cheese(cheese, false));

            int extra = JOptionPane.showConfirmDialog(
                    this,
                    "Add extra " + cheese + "?",
                    "Extra Cheese",
                    JOptionPane.YES_NO_OPTION
            );

            if (extra == JOptionPane.YES_OPTION) {
                sandwich.addTopping(new Cheese(cheese, true));
            }
        }
    }

    private void addRegularToppings(Sandwich sandwich) {
        JCheckBox lettuce = new JCheckBox("Lettuce");
        JCheckBox peppers = new JCheckBox("Peppers");
        JCheckBox onions = new JCheckBox("Onions");
        JCheckBox tomatoes = new JCheckBox("Tomatoes");
        JCheckBox jalapenos = new JCheckBox("Jalapeños");
        JCheckBox cucumbers = new JCheckBox("Cucumbers");
        JCheckBox pickles = new JCheckBox("Pickles");
        JCheckBox guacamole = new JCheckBox("Guacamole");
        JCheckBox mushrooms = new JCheckBox("Mushrooms");

        JCheckBox[] boxes = {
                lettuce,
                peppers,
                onions,
                tomatoes,
                jalapenos,
                cucumbers,
                pickles,
                guacamole,
                mushrooms
        };

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);

        for (JCheckBox box : boxes) {
            panel.add(box);
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Choose Regular Toppings",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            for (JCheckBox box : boxes) {
                if (box.isSelected()) {
                    sandwich.addTopping(new RegularTopping(box.getText(), false));
                }
            }
        }
    }

    private void addSauces(Sandwich sandwich) {
        JCheckBox mayo = new JCheckBox("Mayo");
        JCheckBox mustard = new JCheckBox("Mustard");
        JCheckBox ketchup = new JCheckBox("Ketchup");
        JCheckBox ranch = new JCheckBox("Ranch");
        JCheckBox thousand = new JCheckBox("Thousand Islands");
        JCheckBox vinaigrette = new JCheckBox("Vinaigrette");

        JCheckBox[] boxes = {
                mayo,
                mustard,
                ketchup,
                ranch,
                thousand,
                vinaigrette
        };

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);

        for (JCheckBox box : boxes) {
            panel.add(box);
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Choose Sauces",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            for (JCheckBox box : boxes) {
                if (box.isSelected()) {
                    sandwich.addTopping(new Sauce(box.getText(), false));
                }
            }
        }
    }

    private void addDrink() {
        DrinkSize size = (DrinkSize) JOptionPane.showInputDialog(
                this,
                "Choose drink size:",
                "Drink Size",
                JOptionPane.PLAIN_MESSAGE,
                null,
                DrinkSize.values(),
                DrinkSize.MEDIUM
        );

        if (size == null) {
            return;
        }

        DrinkFlavor flavor = (DrinkFlavor) JOptionPane.showInputDialog(
                this,
                "Choose drink flavor:",
                "Drink Flavor",
                JOptionPane.PLAIN_MESSAGE,
                null,
                DrinkFlavor.values(),
                DrinkFlavor.COKE
        );

        if (flavor == null) {
            return;
        }

        currentOrder.addItem(new Drink(size, flavor));
        updateOrderArea();
    }

    private void addChips() {
        String[] chips = {
                "Classic",
                "BBQ",
                "Sour Cream & Onion",
                "Salt & Vinegar",
                "Jalapeno"
        };

        String type = (String) JOptionPane.showInputDialog(
                this,
                "Choose chip type:",
                "Chips",
                JOptionPane.PLAIN_MESSAGE,
                null,
                chips,
                chips[0]
        );

        if (type == null) {
            return;
        }

        currentOrder.addItem(new Chips(type));
        updateOrderArea();
    }

    private void checkout() {
        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot checkout with an empty order."
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                currentOrder.getOrderDetails(),
                "Confirm Order",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            ReceiptFileManager.saveReceipt(currentOrder);

            JOptionPane.showMessageDialog(
                    this,
                    "Order complete. Receipt saved."
            );

            currentOrder = new Order();
            updateOrderArea();
        }
    }

    private void clearOrder() {
        currentOrder = new Order();
        updateOrderArea();
    }

    private void updateOrderArea() {
        if (currentOrder.isEmpty()) {
            orderArea.setText("No items yet.");
        } else {
            orderArea.setText(currentOrder.getOrderDetails());
        }
    }
}
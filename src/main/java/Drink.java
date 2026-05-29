public class Drink implements OrderItem{
    private DrinkSize size;
    private DrinkFlavor flavor;

    public Drink(DrinkSize size, DrinkFlavor flavor) {
        this.size = size;
        this.flavor = flavor;
    }

    @Override
    public String getName() {
        return formatFlavorName() + " (" + size.name() + ")";
    }

    private String formatFlavorName() {
        return switch (flavor) {
            case COKE -> "Coke";
            case SPRITE -> "Sprite";
            case FANTA -> "Fanta";
            case DR_PEPPER -> "Dr Pepper";
            case LEMONADE -> "Lemonade";
            case WATER -> "Water";
        };
    }

    @Override
    public double getPrice() {
        return switch (size) {
            case SMALL -> 2.00;
            case MEDIUM -> 2.50;
            case LARGE -> 3.00;
        };
    }

    @Override
    public String getDetails() {
        return String.format("Drink: %s - $%.2f", getName(), getPrice());
    }
}
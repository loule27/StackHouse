import java.util.ArrayList;
import java.util.List;

public class Sandwich implements OrderItem {
    private Size size;
    private BreadType bread;
    private boolean toasted;
    private List<Topping> toppings;

    public Sandwich(Size size, BreadType bread, boolean toasted) {
        this.size = size;
        this.bread = bread;
        this.toasted = toasted;
        this.toppings = new ArrayList<>();
    }

    public Size getSize() {
        return size;
    }
    public BreadType getBread() {
        return bread;
    }
    public boolean isToasted() {
        return toasted;
    }
    public List<Topping> getToppings() {
        return toppings;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    public void setBread(BreadType bread) {
        this.bread = bread;
    }
    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    @Override
    public String getName() {
        return size.name().replace("_", " ") + " on " +
                bread.name().charAt(0) +
                bread.name().substring(1).toLowerCase();
    }

    @Override
    public double getPrice() {

        double base = switch (size) {
            case FOUR_INCH   -> 5.50;
            case EIGHT_INCH  -> 7.00;
            case TWELVE_INCH -> 8.50;
        };

        for (Topping t : toppings) {
            base += t.getPrice(size);
        }

        return base;
    }

    @Override
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        if (toasted) sb.append(" [Toasted]");
        sb.append("\n");

        for (Topping t : toppings) {
            sb.append("    - ").append(t.getName());
            if (t.isExtra()) sb.append(" (extra)");
            sb.append("\n");
        }

        sb.append(String.format("    Sandwich Total: $%.2f", getPrice()));
        return sb.toString();
    }
}
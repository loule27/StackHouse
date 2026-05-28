public class Chips implements OrderItem {
    private String type;

    public Chips(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type + " Chips";
    }

    @Override
    public double getPrice() {
        return 1.50;
    }

    @Override
    public String getDetails() {
        return String.format("Chips: %s - $%.2f", type, getPrice());
    }
}

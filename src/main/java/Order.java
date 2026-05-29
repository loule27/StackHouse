import java.util.ArrayList;
import java.util.List;

public class Order {
    private ArrayList<OrderItem> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item){
        items.add(0, item);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public String getOrderDetails() {
        if (items.isEmpty()) {
            return "  No items in order.";
        }

        StringBuilder sb = new StringBuilder();
        for (OrderItem item : items) {
            sb.append(item.getDetails()).append("\n");
        }
        sb.append(String.format("\nOrder Total: $%.2f", getTotalPrice()));
        return sb.toString();
    }
}

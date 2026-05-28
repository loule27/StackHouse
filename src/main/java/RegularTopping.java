public class RegularTopping extends Topping {

    public RegularTopping(String name, boolean extra) {
        super(name, extra);
    }

    @Override
    public double getPrice(Size size) {
        return 0.00; // Regular toppings are always free
    }
}

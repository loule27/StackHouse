public class Sauce extends Topping{

    public Sauce(String name, boolean extra) {
        super(name, extra);
    }

    @Override
    public double getPrice(Size size) {
        return 0.00;
    }
}

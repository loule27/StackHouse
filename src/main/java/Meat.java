public class Meat extends Topping {

    public Meat(String name, boolean extra) {
        super(name, extra);
    }

    @Override
    public double getPrice(Size size) {
        if (isExtra()) {
            return switch (size) {
                case FOUR_INCH   -> 0.50;
                case EIGHT_INCH  -> 1.00;
                case TWELVE_INCH -> 1.50;
            };
        }
        return switch (size) {
            case FOUR_INCH   -> 1.00;
            case EIGHT_INCH  -> 2.00;
            case TWELVE_INCH -> 3.00;
        };
    }
}

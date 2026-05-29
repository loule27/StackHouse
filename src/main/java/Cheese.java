public class Cheese extends Topping {

    public Cheese(String name, boolean extra) {
        super(name, extra);
    }

    @Override
    public double getPrice(Size size){
        if (isExtra()) {
            return switch (size) {
                case FOUR_INCH    -> 0.30;
                case EIGHT_INCH   -> 0.60;
                case TWELVE_INCH  -> 0.90;
            };
        }
        return switch (size) {
            case FOUR_INCH    -> 0.75;
            case EIGHT_INCH   -> 1.50;
            case TWELVE_INCH  -> 2.25;
        };
    }
}

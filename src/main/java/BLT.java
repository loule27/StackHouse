public class BLT extends SignatureSandwich{

    public BLT() {
        super(Size.EIGHT_INCH, BreadType.WHITE, true);
        customize();
    }

    @Override
    public void customize() {
        addTopping(new Meat("Bacon", false));
        addTopping(new Cheese("Cheddar", false));
        addTopping(new RegularTopping("Lettuce", false));
        addTopping(new RegularTopping("Tomato", false));
        addTopping(new Sauce("Ranch", false));
    }

    @Override
    public String getName() {
        return "BLT (Signature)";
    }
}

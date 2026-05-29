public class PhillyCheeseSteak extends SignatureSandwich{

    public PhillyCheeseSteak() {
        super(Size.EIGHT_INCH, BreadType.WHITE, true);
        customize();
    }

    @Override
    public void customize() {
        addTopping(new Meat("Steak", false));
        addTopping(new Cheese("American", false));
        addTopping(new RegularTopping("Peppers", false));
        addTopping(new Sauce("Mayo", false));
    }

    @Override
    public String getName() {
        return "Philly Cheese Steak (Signature)";
    }
}

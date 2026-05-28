public abstract class SignatureSandwich extends Sandwich {

    public SignatureSandwich(Size size, BreadType bread, boolean toasted) {
        super(size, bread, toasted);
    }

    // Child classes define their default toppings
    public abstract void customize();
}

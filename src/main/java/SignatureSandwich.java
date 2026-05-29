public abstract class SignatureSandwich extends Sandwich{

    public SignatureSandwich(Size size, BreadType bread, boolean toasted) {
        super(size, bread, toasted);
    }

    public abstract void customize();
}

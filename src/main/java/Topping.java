public abstract class Topping{
    private String name;
    private boolean extra;

    public Topping(String name, boolean extra) {
        this.name = name;
        this.extra = extra;
    }

    public String getName() {
        return name;
    }
    public boolean isExtra() {
        return extra;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    // Each child defines its own pricing per size
    public abstract double getPrice(Size size);
}
package Models;

public class State {
    private  State parent;
    private Game value;
    private int cost ;
    private int h = 0;

    public boolean hasPrevious()
    {
        return this.parent != null;
    }

    public State getParent() {
        return parent;
    }

    public Game getValue() {
        return value;
    }
}

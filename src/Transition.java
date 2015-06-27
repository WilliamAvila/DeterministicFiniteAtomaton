import java.io.Serializable;

/**
 * Created by william on 5/1/15.
 */
public class Transition implements Serializable {
    State source;
    State destination;
    char symbol;
    String symbols;

    public Transition() {

    }

    public Transition(State source, State destination, char symbol) {
        this.source = source;
        this.destination = destination;
        this.symbol = symbol;
    }

    public Transition(State source, State destination, String symbols) {
        this.source = source;
        this.destination = destination;
        this.symbols = symbols;
        this.symbol = symbols.charAt(0);
    }



}

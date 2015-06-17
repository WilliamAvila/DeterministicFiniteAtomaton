/**
 * Created by william on 6/16/15.
 */
public class TMTransition extends Transition {
    String symbols;
    public TMTransition(State source, State destination, String symbols) {
        this.source =source;
        this.destination = destination;
        this.symbols =symbols;
    }
}

import java.util.HashSet;
import java.util.Set;

/**
 * Created by william on 4/29/15.
 */
public  class Automaton {
    public Set<State> states;                 //1.A finite set of states Q
    public Set<Character>alphabet;            //2.A finite set of symbols alphabet
    public State startState;                  //3.A start state
    public Set<State> finalStates;            //4.A set of final or accepting states

    public Set<Transition> transitions;

    public Automaton(Set<State> states, Set<Character> alphabet, State startState, Set<State> finalStates, Set<Transition> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.finalStates = finalStates;
        this.transitions = transitions;
    }

    public Automaton() {


        this.states =new HashSet<>();
        this.alphabet =new HashSet<>();
        this.transitions=new HashSet<>();
        this.startState=null;
        this.finalStates =new HashSet<>();
    }

    public  void addState(State state){
        this.states.add(state);
        System.out.println("Added State: "+state.name);
    }

    public  void addTransition(Transition transition){
        this.transitions.add(transition);
        System.out.println("Added Transition: "+ transition.source.name+"-"+transition.symbol+"-"+transition.destination.name);
    }


    public  void addFinalState(State state){
        this.finalStates.add(state);

        System.out.println("Added Final State: " + state.name);
    }

    public  void addSymbol(char symbol){
        this.alphabet.add(symbol);

        System.out.println("Added Symbol: " + symbol);
    }


    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<Character> alphabet) {
        this.alphabet = alphabet;
    }

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
        System.out.println(startState.name);
    }

    public Set<State> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<State> finalStates) {
        this.finalStates = finalStates;
    }
}

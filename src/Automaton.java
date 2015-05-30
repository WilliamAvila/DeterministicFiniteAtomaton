import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by william on 4/29/15.
 */
public  class Automaton  implements java.io.Serializable {
    public Set<State> states;                 //1.A finite set of states Q
    public Set<Character>alphabet;            //2.A finite set of symbols alphabet
    public State startState;                  //3.A start state
    public Set<State> finalStates;            //4.A set of final or accepting states

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Set<Transition> transitions) {
        this.transitions = transitions;
    }

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

    public Boolean addTransition(Transition transition){


        for(Transition trans:transitions){
            if(trans.source.name.equals(transition.source.name)&& trans.symbol ==transition.symbol)
                return false;
        }

        this.transitions.add(transition);
        System.out.println("Added Transition: " + transition.source.name + "-" + transition.symbol + "-" + transition.destination.name);
        alphabet.add(transition.symbol);


        return  true;



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

    public void printAutomaton(){
        for(Transition trans:transitions){
            System.out.println("Transition: " + trans.source.name + "--" + trans.symbol + "--" + trans.destination.name);
        }


    }

    public Set<Transition> getNextTransitions(State start){

        Set<Transition>nextTransitions =new HashSet<>();

        if(start!=null) {
            for (Transition t : transitions) {
                if (t.source.name.equals(start.name)) {
                    nextTransitions.add(t);
                }
            }
        }
        return  nextTransitions;
    }

    public boolean existState(String s){
        for(State st:states){
            if(s.equals(st.name))
                return true;
        }
        return false;
    }

}

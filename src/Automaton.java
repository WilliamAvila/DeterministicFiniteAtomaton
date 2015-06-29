import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by william on 4/29/15.
 */
public  class Automaton  implements java.io.Serializable {
    public ArrayList<State> states;                 //1.A finite set of states Q
    public ArrayList<Character> alphabet;            //2.A finite set of symbols alphabet
    public State startState;                  //3.A start state
    public ArrayList<State> finalStates;            //4.A set of final or accepting states


    public ArrayList<Transition> transitions;

    public Automaton(ArrayList<State> states, ArrayList<Character> alphabet, State startState, ArrayList<State> finalStates, ArrayList<Transition> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.finalStates = finalStates;
        this.transitions = transitions;

    }

    public Automaton() {


        this.states =new ArrayList<>();
        this.alphabet =new ArrayList<>();
        this.transitions=new ArrayList<>();
        this.startState=null;
        this.finalStates =new ArrayList<>();
    }

    public void updateTransitions(){
        for(State s: states){
            for(Transition t:transitions){
                if(s.name.equals(t.source.name)){
                    t.source.PointX = s.PointX;
                    t.source.PointY = s.PointY;
                }
                else if(s.name.equals(t.destination.name)){

                    t.destination.PointX = s.PointX;
                    t.destination.PointY = s.PointY;
                }


            }
        }

    }

    public  void addState(State state){
        this.states.add(state);
        System.out.println("Added State: "+state.name);
    }

    public Boolean addTransition(Transition transition){


        for(Transition trans:transitions){
            if(trans.source.name.equals(transition.source.name)&& trans.symbol ==transition.symbol && this instanceof DFA)
                return false;
        }

        this.transitions.add(transition);
        System.out.println("Added Transition: " + transition.source.name + "-" + transition.symbol + "-" + transition.destination.name);
        alphabet.add(transition.symbol);

        return  true;



    }

    public State getStateWithName(String name){
        State state = null;
        for(State s:states){
            if(s.name.equals(name))
                state=s;
        }
        return state;
    }

    public  void printStatePositions(){
        for(State s:states){
            System.out.println(s.name +" "+s.PointX+" "+s.PointY);
        }
    }

    public void setStateWithAttributes(String name,double posX,double posY){
        State state = null;
        for(State s:states){
            if(s.name.equals(name)) {
                state = s;
                s.setPoint(posX,posY);
            }
        }
    }

    public  void addFinalState(State state){
        this.finalStates.add(state);

        System.out.println("Added Final State: " + state.name);
    }

    public  void addSymbol(char symbol){
        this.alphabet.add(symbol);

        System.out.println("Added Symbol: " + symbol);
    }

    public Set<Transition> getTransitionsFromStateSet(State state){
        List nextTransitions = new ArrayList();
        Set<Transition> trans=new HashSet<>();

        if(state!=null) {
            for(Transition t:transitions){
                if(state.name.equals(t.source.name))
                    trans.add(t);

            }
        }

        return  trans;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    public ArrayList<Character> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String input) {
        for(int i=0;i<input.length();i++){
            if(!alphabet.contains(input.charAt(i)))
                alphabet.add(input.charAt(i));

        }
    }

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
        System.out.println(startState.name);
    }

    public ArrayList<State> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(ArrayList<State> finalStates) {
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


    public boolean isFinal(State s) {
        for(State f:finalStates) {
            if (f.name.equals(s.name))
                return true;
        }
        return false;
    }

    public State getNextState(char symbol, State start){
        State next =null;

        if(start!=null) {
            for (Transition t : transitions) {
                if (t.source.name.equals(start.name) && t.symbol == symbol) {
                    next = t.destination;
                    return next;
                }
            }
        }
        return  next;
    }

}

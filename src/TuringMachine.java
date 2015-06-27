import java.util.HashSet;
import java.util.Set;

/**
 * Created by william on 6/15/15.
 */
public class TuringMachine extends Automaton {
    Character alphabet[];
    State start;
    TMTape tape;

    Set<TMTransition> TMTransitions;
    char Blank;

    public TuringMachine(Set<State> states, State startState, Set<State> finalStates, Set<TMTransition> transitions) {
        this.states = states;
        this.startState = startState;
        this.TMTransitions = transitions;
        this.finalStates = finalStates;
    }

    public TuringMachine(){
        TMTransitions=new HashSet<TMTransition>();
    }

    public boolean addTMTransition(TMTransition transition){

        for(TMTransition trans:TMTransitions){
            if(trans.source.name.equals(transition.source.name)&& trans.symbols.equals(transition.symbols))
                return false;
        }

        this.TMTransitions.add(transition);
        System.out.println("Added Transition: " + transition.source.name + "-" + transition.symbols + "-" + transition.destination.name);

        return  true;
    }

    public boolean evaluateTuringMachine(String input){
        State lastState =startState;
        Boolean accepted=false;
        setAlphabet(input);
        tape = new TMTape(input);

        TMTransition t =getNextTransition(lastState,input.charAt(0));

            while (t!=null) {
                tape.MoveNext(t.symbols);
                State next = t.destination;
                t = getNextTransition(next, tape.getCurrentTapeValue());
                lastState = next;
            }

        if(lastState!=null) {

            for (State s : finalStates) {
                if (lastState.name.equals(s.name)) {
                    return true;
                }
            }
        }

        return accepted;
    }

    public TMTransition getNextTransition(State start,char symbol){
        TMTransition transition = null;
        for(TMTransition t: TMTransitions){
            if(t.source.name.equals(start.name)){
                String tokens[] = t.symbols.split(",");
                if(tokens[0].charAt(0) == symbol)
                    return t;
            }
        }
        return transition;
    }



}

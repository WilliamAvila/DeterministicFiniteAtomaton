import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by william on 6/15/15.
 */
public class TuringMachine extends Automaton {
    Character alphabet[];
    State start;
    TMTape tape;


    char Blank;

    public TuringMachine(ArrayList<State> states, State startState, ArrayList<State> finalStates, ArrayList<Transition> transitions) {
        this.states = states;
        this.startState = startState;
        this.transitions = transitions;
        this.finalStates = finalStates;
    }

    public TuringMachine(){
        transitions=new ArrayList<>();
    }

    public boolean addTMTransition(TMTransition transition){

        for(Transition trans:transitions){
            if(trans.source.name.equals(transition.source.name)&& trans.symbols.equals(transition.symbols))
                return false;
        }

        this.transitions.add(transition);
        System.out.println("Added Transition: " + transition.source.name + "-" + transition.symbols + "-" + transition.destination.name);

        return  true;
    }

    public boolean evaluateTuringMachine(String input){
        State lastState =startState;
        Boolean accepted=false;
        setAlphabet(input);
        tape = new TMTape(input);

        Transition t =getNextTransition(lastState,input.charAt(0));

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

    public Transition getNextTransition(State start,char symbol){
        Transition transition = null;
        for(Transition t: transitions){
            if(t.source.name.equals(start.name)){
                String tokens[] = t.symbols.split(",");
                if(tokens[0].charAt(0) == symbol)
                    return t;
            }
        }
        return transition;
    }



}

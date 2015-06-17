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


    public boolean evaluateTuringMachine(String input){
        State lastState =startState;
        Boolean accepted=false;
        tape = new TMTape(input);

        for(int i =0;i<input.length();i++){

            TMTransition t =getNextTransition(lastState,input.charAt(i));
            if(t!=null){

                State next = t.destination;
                tape.MoveNext(t.symbols);
                lastState =next;

            }else{
                break;
            }

        }

        if(tape.getTapeContent().contains(input))
            return false;



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

import java.util.Set;

/**
 * Created by william on 5/1/15.
 */
public class DFA extends Automaton {

    public DFA(Set<State> states, Set<Character> alphabet, State startState, Set<State> finalStates, Set<Transition> transitions) {
        super(states, alphabet, startState, finalStates, transitions);
    }

    public DFA() {
        super();
    }



    public Boolean evaluateDFA(String input){
        State lastState =startState;
        Boolean accepted=false;

        for(int i =0;i<input.length();i++){
            State next =getNextState(input.charAt(i),lastState);
            lastState =next;

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


}

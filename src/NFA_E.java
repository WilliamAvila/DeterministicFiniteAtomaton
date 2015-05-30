import java.util.HashSet;
import java.util.Set;

/**
 * Created by william on 5/17/15.
 */
public class NFA_E extends Automaton {


    public boolean evaluateNFA(String input, State currentState) {

        Set<Transition> trans;
        trans = getNextTransitions(currentState);

        if(!input.isEmpty()){
            char first = input.charAt(0);
            Set<Boolean> results = new HashSet<>();

            for (Transition t : trans){
                if(t.symbol ==first ){
                    currentState = t.destination;
                    results.add(evaluateNFA(input.substring(1), currentState)) ;
                } else if (t.symbol == 'E') {
                    currentState = t.destination;
                    results.add(evaluateNFA(input, currentState));
                }
            }

            for(boolean value: results){
                if(value){ return true;}
            }

        }else{
            for (State s : finalStates) {
                if (currentState.name.equals(s.name)) {
                    return true;
                }
            }
        }
        return false;
    }




}

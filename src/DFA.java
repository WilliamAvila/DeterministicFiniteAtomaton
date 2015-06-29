import javax.swing.text.html.MinimalHTMLWriter;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by william on 5/1/15.
 */
public class DFA extends Automaton {

    public DFA(ArrayList<State> states, ArrayList<Character> alphabet, State startState, ArrayList<State> finalStates, ArrayList<Transition> transitions) {
        super(states, alphabet, startState, finalStates, transitions);
    }

    public DFA() {
        super();
    }



    public Boolean evaluateDFA(String input){
        State lastState =startState;
        Boolean accepted=false;

        for(int i =0;i<input.length();i++){
            State next =getNextState(input.charAt(i), lastState);
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


    public DFA minimizeDFA(){
        DFA minimize = this;
        removeLonelyStates(minimize);


        return minimize;
    }


    public void removeLonelyStates(DFA dfa){

        for(State s:states){
            if(getNextTransitions(s).size()==0)
                states.remove(s);
        }



    }


}

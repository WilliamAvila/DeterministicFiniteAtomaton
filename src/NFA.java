import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by william on 5/6/15.
 */
public class NFA extends Automaton{
    public NFA() {
        super();
    }

    public NFA(ArrayList<State> states, ArrayList<Character> alphabet, State startState, ArrayList<State> finalStates, ArrayList<Transition> transitions) {
        super(states, alphabet, startState, finalStates, transitions);
    }

    public Boolean addNFATransition(Transition transition){
        for(Transition trans:transitions){
            if(trans.source.name.equals(transition.source.name)&& trans.symbol ==transition.symbol &&
                    trans.destination.name.equals(transition.destination.name))
                return false;
        }

        this.transitions.add(transition);
        System.out.println("Added Transition: " + transition.source.name + "-" + transition.symbol + "-" + transition.destination.name);
        for(char alp: alphabet){
            if(alp!=transition.symbol)
                alphabet.add(transition.symbol);;
        }


        return  true;

    }


    public boolean evaluateNFA(String input, State currentState){

        ArrayList<Transition> trans;
        trans = getNextTransitions(currentState);

        if(!input.isEmpty()){
            char first = input.charAt(0);
            Set<Boolean> results = new HashSet<>();

            for (Transition t : trans){
                if(t.symbol ==first ){
                    currentState = t.destination;
                    results.add(evaluateNFA(input.substring(1), currentState)) ;
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

    public DFA convertToDFA(){

            DFA dfa = new DFA();
            dfa.startState = this.startState;
            List<State> currents = new ArrayList<>();
            currents.add(startState);
            for(State s:finalStates){
                if(s.name.equals(startState.name)){
                    dfa.finalStates.add(s);
                }
            }
            dfa.states.add(startState);
            //getStatesAndTransitions(dfa, currents, 0);
            return dfa;

    }






}

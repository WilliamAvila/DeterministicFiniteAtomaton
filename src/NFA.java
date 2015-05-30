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

    public NFA(Set<State> states, Set<Character> alphabet, State startState, Set<State> finalStates, Set<Transition> transitions) {
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

        Set<Transition> trans;
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
        DFA dfa =new DFA();

        List<State> list = new ArrayList<State>(states);

        for(State s:states){

        }

        return dfa;
    }



}

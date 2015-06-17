import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by william on 5/17/15.
 */
public class NFA_E extends Automaton {

    Set<Transition>EmptyTransitions;
    Set<State>EmptyStates;
    Transition transitionsArray[];
    public NFA_E(Set<State> states, Set<Character> alphabet, State startState, Set<State> finalStates, Set<Transition> transitions) {
        super(states, alphabet, startState, finalStates, transitions);
        this.EmptyTransitions = new HashSet<>();
        this.EmptyStates = new HashSet<>();
        transitionsArray = transitions.toArray(new Transition[transitions.size()]);

    }
    public NFA_E(){

    }

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
    public Transition[] getTransitionsFromState(State start){
        List nextTransitions = new ArrayList();


        if(start!=null) {
            for (int i = 0; i < transitions.size(); i++) {

                if (transitionsArray[i].source.name.equals(start.name)) {
                    nextTransitions.add(transitionsArray[i]);
                }
            }
        }

        return (Transition[]) nextTransitions.toArray(new Transition[0]);
    }


    public  State[] Closure (State state){
        List list = new ArrayList();
        list.add(state);

        for (int i=0; i<list.size(); i++) {
            state = (State) list.get(i);
            Transition transitions[] =
                    this.getTransitionsFromState(state);
            for(int k = 0; k < transitions.length; k++) {
                Transition transition = transitions[k];
                if(transition.symbol == ' ') {
                    State toState = transition.destination;
                    if(!list.contains(toState)) {
                        list.add(toState);
                    }
                }
            }
        }

        return (State[]) list.toArray(new State[0]);
    }




    public void getAllStates(State start, char symbol){
//
//        Set<State> firstStates = this.Closure(start);
//
//        for (State s : firstStates) {
//            for(Transition t: transitions) {
//                if(s.name.equals(t.source.name)){
//                    if(t.symbol == symbol ) {
//                        EmptyTransitions.add(t);
//                        EmptyStates.add(s);
//
//                    }
//
//
//                }
//
//            }
//        }

    }



    public NFA ConvertToNFA(){

        return null;
    }




}

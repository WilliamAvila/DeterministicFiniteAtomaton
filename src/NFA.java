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

            DFA dfa = new DFA();
            dfa.startState = this.startState;
            List<State> actuales = new ArrayList<>();
            actuales.add(startState);
            for(State s:finalStates){
                if(s.name.equals(startState.name)){
                    dfa.finalStates.add(s);
                }
            }
            dfa.states.add(startState);
            //getStatesAndTransitions(dfa, actuales, 0);
            return dfa;

    }


//    private void getStatesAndTransitions(DFA dfa,List<State> actuales,int pos) {
//        List <State> nuevosActuales;
//        nuevosActuales = new ArrayList<>();
//        if(pos == alphabet.size())
//            return;
//
//        for(Transition transition:transitions){
//            for(State state:actuales)
//                if(transition.source.name.equals(state.name)&&
//                        transition.symbol == (alphabet.get(pos))){
//                    nuevosActuales.add(transition.destination);
//                    break;
//                }
//        }
//        getStatesAndTransitions(dfa, actuales, pos + 1);
//        if(!nuevosActuales.isEmpty()){
//            if(siEsIgualAlgunEstadoDFA(nuevosActuales, dfa)){
//                crearTransicionDFA(nuevosActuales, dfa, actuales, pos);
//                return;
//            }
//
//            State nuevoEstado = new State();
//            nuevoEstado.name=nuevosActuales.stream().map((estado) -> estado.name).reduce(
//                    nuevoEstado.name, String::concat);
//            dfa.states.add(nuevoEstado);
//            agregarEstadosFinalesDFA(nuevosActuales, dfa);
//            crearTransicionDFA(nuevosActuales, dfa, actuales, pos);
//            getStatesAndTransitions(dfa, nuevosActuales, 0);
//        }
//
//    }



}

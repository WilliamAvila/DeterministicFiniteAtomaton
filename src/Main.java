import javax.swing.undo.AbstractUndoableEdit;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        GraphicDFA gdfa = new GraphicDFA();
        gdfa.setVisible(true);


        DFA dfa;
        Set<State> states =new HashSet<State>();
        Set<Transition> transitions=new HashSet<>();
        Set<State>finalStates =new HashSet<>();


        State state1 =new State("I-0");
        State state2 =new State("I-1");
        State state3 = new State("I-2");
        states.add(state1);
        states.add(state2);
        states.add(state3);

        finalStates.add(state2);
        finalStates.add(state3);

        Transition t1 = new Transition(state1,state1,'1');
        Transition t2 = new Transition(state1,state2,'0');
        Transition t3 = new Transition(state2,state2,'1');
        Transition t4 = new Transition(state2,state3,'0');

        transitions.add(t1);
        transitions.add(t2);
        transitions.add(t3);
        transitions.add(t4);

        /*dfa  = new DFA(states,null,state1,finalStates,transitions);

        Boolean ans=dfa.evaluateDFA("111110111110");
        System.out.printf(String.valueOf(ans));*/




    }
}
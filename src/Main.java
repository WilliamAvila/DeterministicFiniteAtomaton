import javax.swing.undo.AbstractUndoableEdit;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        GraphicDFA gdfa = new GraphicDFA();
        gdfa.setVisible(true);


        NFA nfa;
        Set<State> states =new HashSet<State>();
        Set<Transition> transitions=new HashSet<>();
        Set<State>finalStates =new HashSet<>();


        State state1 =new State("p");
        State state2 =new State("q");
        State state3 = new State("r");
        State state4 = new State("s");

        states.add(state1);
        states.add(state2);
        states.add(state3);
        states.add(state4);

        finalStates.add(state2);
        finalStates.add(state3);

        Transition t1 = new Transition(state1,state1,'1');
        Transition t2 = new Transition(state1,state2,'1');
        Transition t3 = new Transition(state2,state2,'0');
        Transition t4 = new Transition(state2,state3,'0');

        transitions.add(t1);
        transitions.add(t2);
        transitions.add(t3);
        transitions.add(t4);

        nfa  = new NFA(states,null,state1,finalStates,transitions);

        Boolean ans=nfa.evaluateNFA("11100",nfa.startState);
        System.out.println(String.valueOf(ans));
        // nfa.printAutomaton();
        DFA dfa =nfa.convertToDFA();
        dfa.printAutomaton();










    }
}
/**
 * Created by william on 6/25/15.
 */
import org.unitec.regularexpresion.RegularExpressionParser;
import org.unitec.regularexpresion.tree.*;

import java.util.ArrayList;

public class ConvertRegExToNFA_E {

    static NFA_E  nfa_e;
    String RegEx;
    static State lastState;
    static String genericName;
    static int count;
    static ArrayList<State> kleeneStates;
    ArrayList<Transition> transitions;
    public ConvertRegExToNFA_E(){
        nfa_e =new NFA_E();
        count =0;
        genericName="q";
        kleeneStates = new ArrayList<>();
    }
    public static void main(String[] args) {
        try {
            String str = "1.(0+1)";
            Node rootNode = new RegularExpressionParser().Parse(str);

            nfa_e =new NFA_E();
            count =0;
            genericName="q";
            kleeneStates = new ArrayList<>();

           // String tree = getTree(rootNode);
            //System.out.println(tree);
            String inversedExpression =  obtainInverse(rootNode);
            //nfa_e.printAutomaton();

//            System.out.println(inversedExpression);
//            System.out.println(rootNode.inverseExpression());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void buildNFA_E(){




    }

    public static NFA_E  getTree(Node rootNode){
        if(rootNode instanceof CharNode) {

            NFA_E tempNFA_E = new NFA_E();
            buildNFA_EFromSymbol(tempNFA_E, ((CharNode) rootNode).getValue());

            return tempNFA_E;


        }
        else if (rootNode instanceof ORNode) {
            ORNode orNode = (ORNode) rootNode;
            return buildOrNFA_E(getTree(orNode.getLeftNode()), getTree(orNode.getRightNode()));

        }

        else if (rootNode instanceof ANDNode)
        {
            ANDNode andNode = (ANDNode)rootNode;
            return buildAndNFA_E(getTree(andNode.getLeftNode()), getTree(andNode.getRightNode()));
        }
        else
        {
            RepeatNode repeatNode = (RepeatNode)rootNode;

            if(repeatNode.getNode() instanceof CharNode)
                return buildKleeneNFA_E(getTree((CharNode)repeatNode.getNode()));
            else if(repeatNode.getNode() instanceof ORNode)
                return buildKleeneNFA_E(getTree((ORNode)repeatNode.getNode()));
            else if(repeatNode.getNode() instanceof ANDNode)
                return buildKleeneNFA_E(getTree((ANDNode)repeatNode.getNode()));
            return null;

        }

    }



    public static void buildNFA_EFromSymbol(NFA_E nfa_e , String symbol){
        State state1 = new State(getGenericName());
        State state2 = new State(getGenericName());
        Transition t = new Transition(state1, state2, symbol);
        nfa_e.addTransition(t);
        lastState = state2;
        nfa_e.addState(state1);
        nfa_e.addState(state2);
        nfa_e.addFinalState(lastState);
        nfa_e.setStartState(state1);
        nfa_e.setFinalState(lastState);
    }



    private static String obtainInverse(Node rootNode) {
        if(rootNode instanceof CharNode)
            return ((CharNode)rootNode).getValue();
        else if (rootNode instanceof ORNode)
        {
            ORNode orNode = (ORNode)rootNode;
            return "("+
                    obtainInverse(orNode.getLeftNode()) +"+"+
                    obtainInverse(orNode.getRightNode())+
                    ")";
        }
        else if (rootNode instanceof ANDNode)
        {
            ANDNode andNode = (ANDNode)rootNode;
            return "("+
                    obtainInverse(andNode.getRightNode()) +"."+
                    obtainInverse(andNode.getLeftNode())+
                    ")";
        }
        else
        {
            return "("+
                    obtainInverse(((RepeatNode) rootNode).getNode())+
                    ")*";
        }

    }


    public static NFA_E buildOrNFA_E(NFA_E nfa_e1, NFA_E nfa_e2){
        NFA_E combination = getCombinationNFA_E(nfa_e1,nfa_e2);


        State start = new State(getGenericName());
        combination.setStartState(start);
        combination.addState(start);

        combination.addTransition(new Transition(start, nfa_e1.startState, 'E'));
        combination.addTransition(new Transition(start, nfa_e2.startState, 'E'));


        State lastState = new State(getGenericName());
        combination.addState(lastState);
        combination.addFinalState(lastState);
        combination.setFinalState(lastState);


        combination.addTransition(new Transition(nfa_e1.getFinalState(),lastState, 'E'));
        combination.addTransition(new Transition(nfa_e2.getFinalState(),lastState,'E'));

        return combination;

    }

    public static NFA_E buildAndNFA_E(NFA_E nfa_e1, NFA_E nfa_e2){
        NFA_E combination = getCombinationNFA_E(nfa_e1,nfa_e2);


        State start = new State(getGenericName());
        combination.setStartState(start);
        combination.addState(start);

        combination.addTransition(new Transition(start, nfa_e1.startState, 'E'));


        State lastState = new State(getGenericName());
        combination.addState(lastState);
        combination.addFinalState(lastState);
        combination.setFinalState(lastState);

        combination.addTransition(new Transition(nfa_e1.getFinalState(),nfa_e2.startState, 'E'));
        combination.addTransition(new Transition(nfa_e2.getFinalState(),lastState,'E'));

        return combination;

    }

    public static NFA_E buildKleeneNFA_E(NFA_E nfa_e){
        NFA_E applyStar = new NFA_E();
        applyStar = getCombinationNFA_E(applyStar,nfa_e);
        State startState = new State(getGenericName());
        applyStar.setStartState(startState);
        applyStar.addState(startState);

        State lastState = new State(getGenericName());
        applyStar.addState(lastState);
        applyStar.addFinalState(lastState);
        applyStar.setFinalState(lastState);

        applyStar.addTransition(new Transition(startState, lastState, "E"));
        applyStar.addTransition(new Transition(startState, nfa_e.startState, "E"));

        applyStar.addTransition(new Transition(nfa_e.finalState,lastState , "E"));
        applyStar.addTransition(new Transition(nfa_e.startState,nfa_e.finalState,"E"));


        return applyStar;
    }

    public static String getGenericName(){
        String name = genericName + count;
        count ++;
        return name;
    }

    public static NFA_E getCombinationNFA_E(NFA_E nfa_e1, NFA_E nfa_e2){
        NFA_E combination= new NFA_E();


        nfa_e2.finalStates.clear();

        for(State s: nfa_e1.states)
            combination.addState(s);

        for(Transition t: nfa_e1.transitions)
            combination.addTransition(t);

        for(State s: nfa_e2.states)
            combination.addState(s);

        for(Transition t: nfa_e2.transitions)
            combination.addTransition(t);


        return combination;

    }

}

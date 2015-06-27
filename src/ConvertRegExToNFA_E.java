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

            String tree = getTree(rootNode);
            System.out.println(tree);
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

    public static String  getTree(Node rootNode){
        if(rootNode instanceof CharNode) {

//            State state1 = new State(getGenericName());
//            State state2 = new State(getGenericName());
//            nfa_e.addTransition(new Transition(state1, state2, ((CharNode) rootNode).getValue()));
//            lastState = state2;
//            nfa_e.addFinalState(lastState);

            return ((CharNode) rootNode).getValue();


        }
        else if (rootNode instanceof ORNode)
        {
            ORNode orNode = (ORNode)rootNode;
              buildOrNFA_E(rootNode);

            return "("+
                    getTree(orNode.getLeftNode()) +"+"+
                    getTree(orNode.getRightNode())+
                    ")";
        }
        else if (rootNode instanceof ANDNode)
        {
            ANDNode andNode = (ANDNode)rootNode;
            buildAndNFA_E(rootNode);

            return "("+
                    getTree(andNode.getRightNode()) +"."+
                    getTree(andNode.getLeftNode())+
                    ")";
        }
        else
        {
            return "("+
                    getTree(((RepeatNode) rootNode).getNode())+
                    ")*";
        }
    }

    private static void saveKleenStates(State state1,State state2,State state3,State state4){
        kleeneStates.add(state1);
        kleeneStates.add(state2);
        kleeneStates.add(state3);
        kleeneStates.add(state4);
    }




    private static void findLastState(){

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
    public static void buildAndNFA_E(Node rootNode){

        ANDNode andNode = (ANDNode)rootNode;
        if(andNode.getLeftNode() instanceof CharNode && andNode.getRightNode() instanceof CharNode) {

            State state1;
            if (lastState != null) {
                state1 = new State(getGenericName());

                nfa_e.addTransition(new Transition(nfa_e.getStateWithName(lastState.name),state1,'E'));
                nfa_e.finalStates.remove(lastState);
            } else {
                state1 = new State(getGenericName());

                nfa_e.setStartState(state1);
            }

            State state2 = new State(getGenericName());
            State state3 = new State(getGenericName());
            State state4 = new State(getGenericName());
            State state5 = new State(getGenericName());
            State state6 = new State(getGenericName());
            nfa_e.addState(state1);
            nfa_e.addState(state2);
            nfa_e.addState(state3);
            nfa_e.addState(state4);
            nfa_e.addState(state5);
            nfa_e.addState(state6);
            nfa_e.addTransition(new Transition(state1, state2, 'E'));
            nfa_e.addTransition(new Transition(state2, state3, getTree(andNode.getLeftNode())));
            nfa_e.addTransition(new Transition(state3, state4, 'E'));
            nfa_e.addTransition(new Transition(state4, state5, getTree(andNode.getRightNode())));
            nfa_e.addTransition(new Transition(state5, state6, 'E'));
            lastState = state6;
            nfa_e.addFinalState(lastState);

        }else if(andNode.getLeftNode() instanceof CharNode ){

            State state1 = new State(getGenericName());
            State state2 = new State(getGenericName());
            nfa_e.addTransition(new Transition(state1, state2, ((CharNode)andNode.getLeftNode()).getValue()));
            lastState = state2;
            nfa_e.addFinalState(lastState);
            nfa_e.addState(state1);
            nfa_e.addState(state2);
            nfa_e.setStartState(state1);
        }else if( andNode.getRightNode() instanceof CharNode){
            State state1 = new State(getGenericName());
            State state2 = new State(getGenericName());
            nfa_e.addTransition(new Transition(lastState, state1, ((CharNode) andNode.getRightNode()).getValue()));
            nfa_e.addTransition(new Transition(state1, state2,'E'));
            nfa_e.finalStates.clear();
            lastState = state2;
            nfa_e.addState(state1);
            nfa_e.addState(state2);

            nfa_e.addFinalState(lastState);

        }
    }

    public static void buildOrNFA_E(Node rootNode){
        ORNode orNode = (ORNode)rootNode;
        if(orNode.getLeftNode() instanceof CharNode && orNode.getRightNode() instanceof CharNode){


        if(orNode.getLeftNode() instanceof CharNode && orNode.getRightNode() instanceof CharNode) {
            State state1;
            if (lastState != null) {
                state1 = new State(getGenericName());

                nfa_e.addTransition(new Transition(lastState,state1,'E'));
                nfa_e.finalStates.remove(lastState);
            } else {
                state1 = new State(getGenericName());
                nfa_e.setStartState(state1);
            }

            State state2 = new State(getGenericName());
            State state3 = new State(getGenericName());
            State state4 = new State(getGenericName());
            State state5 = new State(getGenericName());
            State state6 = new State(getGenericName());
            nfa_e.addState(state1);
            nfa_e.addState(state2);
            nfa_e.addState(state3);
            nfa_e.addState(state4);
            nfa_e.addState(state5);
            nfa_e.addState(state6);
            nfa_e.addTransition(new Transition(state1, state2, 'E'));
            nfa_e.addTransition(new Transition(state1, state3, 'E'));
            nfa_e.addTransition(new Transition(state2, state4, getTree(orNode.getLeftNode())));
            nfa_e.addTransition(new Transition(state3, state5, getTree(orNode.getRightNode())));
            nfa_e.addTransition(new Transition(state4, state6, 'E'));
            nfa_e.addTransition(new Transition(state5, state6, 'E'));
            lastState = state6;
            nfa_e.addFinalState(lastState);
        }else if(orNode.getLeftNode() instanceof CharNode ){
            State state2 = new State(getGenericName());
            nfa_e.addTransition(new Transition(lastState, state2, ((CharNode) rootNode).getValue()));
            lastState = state2;
            nfa_e.addFinalState(lastState);
            nfa_e.addState(state2);
        }else if( orNode.getRightNode() instanceof CharNode){
            State state1 = new State(getGenericName());
            State state2 = new State(getGenericName());
            nfa_e.addTransition(new Transition(state1, state2, ((CharNode) rootNode).getValue()));
            lastState = state2;
            nfa_e.addFinalState(lastState);

            nfa_e.addState(state1);
            nfa_e.addState(state2);
        }
        }
    }
    public static void buildKleeneNFA_E(){

    }

    public static String getGenericName(){
        String name = genericName + count;
        count ++;
        return name;
    }

}

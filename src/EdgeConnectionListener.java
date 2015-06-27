import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.sun.org.apache.xpath.internal.SourceTree;

import javax.swing.*;

/**
 * Created by william on 5/2/15.
 */
public class EdgeConnectionListener implements mxEventSource.mxIEventListener {


    mxGraphComponent graphComponent;

    Automaton automaton;

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    public EdgeConnectionListener(mxGraphComponent graphComponent, Automaton automaton) {
        this.graphComponent=graphComponent;
        this.automaton=automaton;

    }

    @Override
    public void invoke(Object o, mxEventObject event) {
        mxCell edge = (mxCell) event.getProperty("cell");
        String symbol = showInputDialog();

        if (symbol!=null ) {
            System.out.println("Value: " +symbol);
            if (setCorrectSymbol(symbol,edge)) {
                edge.setValue(symbol);
                graphComponent.refresh();
            } else {

                JOptionPane.showMessageDialog(null, "Transition already Exist");
                edge.removeFromParent();
            }
        }else{
            Object[] edges = graphComponent.getGraph().getEdgesBetween( edge.getTarget(), edge.getSource());
            for( Object ed: edges) {
                if(((mxCell)ed).getValue()!=null);
                else
                graphComponent.getGraph().getModel().remove( ed);
            }
        }


    }


    public boolean setCorrectSymbol(String symbol , mxCell edge){

        if(automaton instanceof DFA ){
            return automaton.addTransition(new Transition(new State(edge.getSource().getValue().toString()),
                    new State(edge.getTarget().getValue().toString()), symbol.charAt(0)));

        }
        else if(automaton instanceof  NFA || automaton instanceof NFA_E){
            return ((NFA)automaton).addNFATransition(new Transition(new State(edge.getSource().getValue().toString()),
                    new State(edge.getTarget().getValue().toString()), symbol.charAt(0)));

        }
        else if(automaton instanceof PDA ) {

            return ((PDA)automaton).addTransition(new Transition(new State(edge.getSource().getValue().toString()),
                    new State(edge.getTarget().getValue().toString()), symbol));


        }else if(automaton instanceof TuringMachine){

            return ((TuringMachine)automaton).addTMTransition(new TMTransition(new State(edge.getSource().getValue().toString()),
                    new State(edge.getTarget().getValue().toString()), symbol));



        }

        return false;
    }
    private String showInputDialog() {
        String inputValue;
        if(automaton instanceof TuringMachine) {
           inputValue = JOptionPane.showInputDialog("Name of the Transition:\na,x,y");

            if (inputValue == null || inputValue.isEmpty() || !inputValue.matches("[0-9a-zA-Z#],[0-9a-zA-Z#],[rl]")) {
                inputValue = showInputDialog();
            }


        }else if(automaton instanceof PDA){
             inputValue = JOptionPane.showInputDialog("Name of the Transition:\na,x,y");

            if (inputValue == null || inputValue.isEmpty() || !inputValue.matches("[0-9a-zA-Z],[0-9a-zA-Z],[0-9a-zA-Z]*")) {
                inputValue = showInputDialog();
            }
        }else{
           inputValue = JOptionPane.showInputDialog("Name of the Transition:");

            if (inputValue == null || inputValue.isEmpty() || !inputValue.matches("[0-9a-zA-Z]")) {
                inputValue = showInputDialog();
            }
        }

        return inputValue;
    }
}


import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;

import javax.swing.*;

/**
 * Created by william on 5/2/15.
 */
public class EdgeConnectListener implements mxEventSource.mxIEventListener {

    mxGraphComponent graphComponent;
    DFA dfa;

    public EdgeConnectListener(mxGraphComponent graphComponent, DFA dfa) {
        this.graphComponent=graphComponent;
        this.dfa=dfa;

    }

    @Override
    public void invoke(Object o, mxEventObject event) {
        mxCell edge = (mxCell) event.getProperty("cell");
        String symbol =JOptionPane.showInputDialog( "Name of the transition");
        /*if(edge.getSource()!=null) {
            System.out.println(edge.getSource().getValue().toString());*/
            dfa.addTransition(new Transition(new State(edge.getSource().getValue().toString()),
                    new State(edge.getTarget().getValue().toString()),symbol.charAt(0)));
        /*}
        else {
            System.out.println(edge.getTarget().getValue().toString());
            dfa.addTransition(new Transition(new State(edge.getSource().getValue().toString()),
                    new State(edge.getTarget().getValue().toString()), symbol.charAt(0)));
        }*/
        edge.setValue(symbol);
        graphComponent.refresh();

    }
}

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
    NFA dfa;

    public EdgeConnectionListener(mxGraphComponent graphComponent, NFA dfa) {
        this.graphComponent=graphComponent;
        this.dfa=dfa;

    }


    @Override
    public void invoke(Object o, mxEventObject event) {
        mxCell edge = (mxCell) event.getProperty("cell");
        String symbol = JOptionPane.showInputDialog("Name of the transition");

        if (symbol!=null ) {
            System.out.println("Value: " +symbol);
            if (dfa.addNFATransition(new Transition(new State(edge.getSource().getValue().toString()),
                    new State(edge.getTarget().getValue().toString()), symbol.charAt(0)))) {
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
}

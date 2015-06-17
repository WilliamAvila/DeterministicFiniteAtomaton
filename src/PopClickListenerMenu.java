import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxCellState;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by william on 5/31/15.
 */
public class PopClickListenerMenu {
    private mxGraphComponent graphComponent;
    private mxCellState state;
    Automaton automata;
    mxCell cell;


    public PopClickListenerMenu(mxGraphComponent graphComponent, Automaton automata) {
        this.graphComponent = graphComponent;
        this.automata = automata;
    }

    public void mousePressed(MouseEvent e) {
        doPop(e);
    }



    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUpMenu menu = new PopUpMenu(graphComponent);
        menu.show(e.getComponent(), e.getX(), e.getY());

        menu.setFinal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    super.mousePressed(e);
                    cell.setStyle("shape=doubleEllipse;fillColor=white;fontColor=red");
                    automata.addFinalState(new State(cell.getValue().toString()));
                    graphComponent.refresh();
                }
            }
        });


        menu.setInitial.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    super.mousePressed(e);
                    cell.setStyle("fontColor=blue");
                    automata.setStartState(new State(cell.getValue().toString()));
                    graphComponent.refresh();
                }
            }
        });


    }
}

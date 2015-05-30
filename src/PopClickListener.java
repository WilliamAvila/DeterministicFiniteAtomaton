import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxCellState;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by william on 5/1/15.
 */
class PopClickListener extends MouseAdapter {
    private mxGraphComponent graphComponent;
    private mxCellState state;
    Automaton automata;
    mxCell cell;


    public PopClickListener(mxGraphComponent graphComponent, Automaton automata) {
        this.graphComponent = graphComponent;
        this.automata = automata;
    }

    public void mousePressed(MouseEvent e) {

        cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
        /*if (SwingUtilities.isLeftMouseButton(e)) {

            if (cell != null  && cell.isVertex()) {
                //System.out.println(cell.getValue().toString());
                if(cell.getEdgeCount()!=0)
                    System.out.println(cell.getEdgeAt(0).getValue().toString());
               // System.out.println(cell.getStyle().toString());



            }
        }*/
        if (SwingUtilities.isRightMouseButton(e)){
            mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
            if (cell != null  && cell.isVertex()) {
                doPop(e);
            }
        }
    }



    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUpDemo menu = new PopUpDemo(graphComponent);
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

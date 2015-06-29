import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import org.unitec.regularexpresion.RegularExpressionParser;
import org.unitec.regularexpresion.tree.Node;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by william on 5/31/15.
 */
public class PopClickListenerMenu extends MouseAdapter {
    private mxGraphComponent graphComponent;
    private mxCellState state;
    Automaton automaton;
    mxGraph graph;
    mxCell cell;


    public PopClickListenerMenu(mxGraphComponent graphComponent, Automaton automata) {
        this.graphComponent = graphComponent;
        this.automaton = automata;
        this.graph = graphComponent.getGraph();
    }


    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    public void mousePressed(MouseEvent e) {


        if (SwingUtilities.isRightMouseButton(e)) {
            mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
            if (cell == null) {
                doPop(e);
            }
        }

    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUpMenuMainWindow menu = new PopUpMenuMainWindow(graphComponent,automaton);
        menu.show(e.getComponent(), e.getX(), e.getY());

        menu.regExToNFA_E.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    super.mousePressed(e);
                    String inputValue = JOptionPane.showInputDialog("Regular Expression:");

                    ConvertRegExToNFA_E cvr = new ConvertRegExToNFA_E();


                    try {
                        Node rootNode = new RegularExpressionParser().Parse(inputValue);

                        NFA_E nfa_e = cvr.getTree(rootNode);
                        CreateVisualAutomaton(nfa_e);

                        automaton = nfa_e;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        menu.clearScreen.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    clearGraph(graph);

                }
            }
        });

       if(automaton instanceof NFA){
           menu.convertToDFA.addMouseListener(new MouseAdapter() {
               @Override
               public void mousePressed(MouseEvent e) {
                   if (SwingUtilities.isLeftMouseButton(e)) {
                       super.mousePressed(e);
                   }
               }
           });
       }


    }

    public void CreateVisualAutomaton(Automaton automata){

        Object parent = graph.getDefaultParent();
        int x=80,y=80;
        int xStart = 10,yStart =10;

        int xFinal = 380,yFinal =300;

        for(State s:automata.states){
            graph.getModel().beginUpdate();
            if(automata.startState.name.equals(s.name)) {
                Object v = graph.insertVertex(parent, null, s.name, xStart, yStart, 50, 50,"shape=ellipse;fillColor=cyan");


            }
            else if(automata.isFinal(s)) {
                Object v = graph.insertVertex(parent, null, s.name, xFinal, yFinal, 50, 50, "shape=doubleEllipse;fillColor=white;fontColor=red");

            }
            else {
                Object v = graph.insertVertex(parent, null, s.name, x, y, 50, 50, "shape=ellipse;fillColor=white");
                x+=80;
            }

            if(x>400) {
                y += 100;
                x=80;
            }
            graph.getModel().endUpdate();
        }

        for (Transition t : automata.transitions) {

            Object vertex =getVertexInGraph(t.source.name);

            Object vertex2 =getVertexInGraph(t.destination.name);

            graph.getModel().beginUpdate();
            graph.insertEdge(parent, null, t.symbol, vertex, vertex2);
            graph.getModel().endUpdate();
        }
    }

    public void clearGraph(mxGraph graph){
        graph.getModel().beginUpdate();
        graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
        graph.getModel().endUpdate();
    }




    public Object getVertexInGraph(String name) {
        Object vertex = new Object();
        Object parent = graph.getDefaultParent();
        for (int i = 0; i <graph.getModel().getChildCount(parent); i++) {

            vertex = graph.getModel().getChildAt(parent, i);
            if ((((mxCell) vertex).getValue().toString()).equals(name))
                return vertex;
        }
        return vertex;
    }
}

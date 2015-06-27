import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxCellState;
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
    mxCell cell;


    public PopClickListenerMenu(mxGraphComponent graphComponent, Automaton automata) {
        this.graphComponent = graphComponent;
        this.automaton = automata;
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

                    String str = "0.(0+1)";
                    try {
                        Node rootNode = new RegularExpressionParser().Parse(str);

                        cvr.getTree(rootNode);
                        //CreateVisualAutomaton(cvr.nfa_e);
                        automaton =cvr.nfa_e;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        menu.setNFA.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    super.mousePressed(e);
                }
            }
        });


    }
}

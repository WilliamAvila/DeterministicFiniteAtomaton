import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by william on 5/1/15.
 */
class PopUpMenu extends JPopupMenu {
    JMenuItem setInitial;
    JMenuItem setFinal;
    JMenuItem setInitialSymbol;
    mxGraphComponent graphComponent;

    public PopUpMenu(mxGraphComponent graphComponent,Automaton automaton) {
        this.graphComponent = graphComponent;
        setInitial = new JMenuItem("Set Initial");
        setFinal = new JMenuItem("Set Final");
        if(automaton instanceof PDA){
            setInitialSymbol = new JMenuItem("Set Initial Symbol");
            add(setInitialSymbol);
        }

        add(setInitial);
        add(setFinal);
    }

}
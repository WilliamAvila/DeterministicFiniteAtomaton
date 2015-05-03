import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by william on 5/1/15.
 */
class PopUpDemo extends JPopupMenu {
    JMenuItem setInitial;
    JMenuItem setFinal;
    mxGraphComponent graphComponent;

    public PopUpDemo(mxGraphComponent graphComponent) {
        this.graphComponent = graphComponent;
        setInitial = new JMenuItem("Set Initial");
        setFinal = new JMenuItem("Set Final");

        add(setInitial);
        add(setFinal);
    }

}
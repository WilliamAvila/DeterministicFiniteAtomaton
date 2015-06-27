import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;

/**
 * Created by william on 5/31/15.
 */
public class PopUpMenuMainWindow extends JPopupMenu {
    JMenuItem setDFA;
    JMenuItem setNFA;
    JMenuItem setNFAe;
    JMenuItem regExToNFA_E;
    mxGraphComponent graphComponent;

    public PopUpMenuMainWindow(mxGraphComponent graphComponent,Automaton automaton) {
        this.graphComponent = graphComponent;
        setDFA = new JMenuItem("Convert DFA");
        setNFA = new JMenuItem("Convert NFA");
        setNFAe = new JMenuItem("Convert NFA-E");
        regExToNFA_E = new JMenuItem("Convert Regular Expression to NFA-E");

        add(setDFA);
        add(setNFA);
        add(setNFAe);
        add(regExToNFA_E);
    }
}

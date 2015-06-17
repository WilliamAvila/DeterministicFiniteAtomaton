import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;

/**
 * Created by william on 5/31/15.
 */
public class PopUpMenuMainWIndow extends JPopupMenu {
    JMenuItem setDFA;
    JMenuItem setNFA;
    JMenuItem setNFAe;

    public PopUpMenuMainWIndow() {

        setDFA = new JMenuItem("Create DFA");
        setNFA = new JMenuItem("Create NFA");
        setNFAe = new JMenuItem("Create NFA-E");

        add(setDFA);
        add(setNFA);
        add(setNFAe);
    }
}

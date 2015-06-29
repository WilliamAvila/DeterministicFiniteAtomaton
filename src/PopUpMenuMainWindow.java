import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;

/**
 * Created by william on 5/31/15.
 */
public class PopUpMenuMainWindow extends JPopupMenu {

    JMenuItem minimizeDFA;
    JMenuItem convertToDFA;
    JMenuItem convertToNFA;
    JMenuItem regExToNFA_E;
    JMenuItem clearScreen;
    mxGraphComponent graphComponent;

    public PopUpMenuMainWindow(mxGraphComponent graphComponent,Automaton automaton) {
        this.graphComponent = graphComponent;
        if(automaton instanceof NFA) {
            convertToDFA = new JMenuItem("Convert NFA to DFA");
            convertToNFA = new JMenuItem("Convert NFA-E to NFA");
            add(convertToDFA);
            add(convertToNFA);

        }
        if(automaton instanceof DFA) {
            minimizeDFA = new JMenuItem("Minimize DFA");
            add(minimizeDFA);
        }
        regExToNFA_E = new JMenuItem("Convert Regular Expression to NFA-E");
        clearScreen = new JMenuItem("Clear");

        add(regExToNFA_E);
        add(clearScreen);
    }
}

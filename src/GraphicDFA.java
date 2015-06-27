import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxGraph;
import org.unitec.regularexpresion.RegularExpressionParser;
import org.unitec.regularexpresion.tree.Node;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Map;


/**
 * Created by william on 4/28/15.
 */
public class GraphicDFA extends JFrame {
    private mxGraph graph;
    private mxGraphComponent graphComponent;
    private  JTextField text;
    private  JTextField evalText;
    private JButton addNodeButton;
    private JButton evalButton;
    private JLabel transLabel;
    private JLabel moveLabel;
    private JLabel deleteLabel;
    private JLabel labelA;
    private NFA nfa;
    private TuringMachine tm;
    private Automaton automaton;
    private  JFileChooser fc;
    private JButton openButton, saveButton;
    private  JTextArea log;
    private JRadioButton tmButton;
    private PopClickListener pcl;
    private PopClickListenerMenu pclm;
    private EdgeConnectionListener ecl;


    public  GraphicDFA(){
        super("NFA");
        initGUI();
    }

    private void initGUI(){
        setSize(520, 650);
        setLocationRelativeTo(null);
        graph = new mxGraph();
        nfa = new NFA();
        tm = new TuringMachine();
//        mxKeyboardHandler mk =new mxKeyboardHandler( graphComponent);
        //Init Log


        graph.setCellsResizable(false);
        graph.setAllowLoops(true);
        graph.setAllowDanglingEdges(false);
        graph.setVertexLabelsMovable(false);


        Map<String, Object> style = graph.getStylesheet().getDefaultEdgeStyle();
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ENTITY_RELATION);


        setSaveAndOpenButton();
        addMenuBar();

        automaton = new TuringMachine();
        tmButton.setSelected(true);

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(520, 400));
        getContentPane().add(graphComponent);

        graph.getChildVertices(graph.getDefaultParent());
        pclm= new PopClickListenerMenu(graphComponent, automaton);
        pcl =new PopClickListener(graphComponent, automaton);
        ecl =new EdgeConnectionListener(graphComponent, automaton);
        graphComponent.getGraphControl().addMouseListener(pcl);
        graphComponent.getGraphControl().addMouseListener(pclm);
        graphComponent.getConnectionHandler().addListener(mxEvent.CONNECT, ecl);

        /*moveLabel = new JLabel();
        addIcon(moveLabel, 25, 25, "/Images/move.png");
        transLabel = new JLabel();
        addIcon(transLabel, 25, 25, "/Images/transition.png");
        deleteLabel = new JLabel();
        addIcon(deleteLabel, 25, 25, "/Images/delete.png");*/

        setAddNodeAndEvalButton();

    }

    public void setAddNodeAndEvalButton(){
        JScrollPane logScrollPane = new JScrollPane(log);

        text = new JTextField();
        getContentPane().add(text);

        text.setPreferredSize(new Dimension(150, 21));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        addNodeButton = new JButton("Add Node");
        getContentPane().add(addNodeButton);

        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String checkExistence = "";
                for (State st : automaton.getStates()) {
                    if (st.name.equals(text.getText().toString()))
                        checkExistence = st.name;
                }
                automaton.existState(text.getText().toString());

                if (!text.getText().toString().equals(checkExistence)) {
                    State state = new State(text.getText().toString());
                    graph.getModel().beginUpdate();
                    Object parent = graph.getDefaultParent();
                    Object v1 = graph.insertVertex(parent, null, text.getText(), 10, 30, 50, 50, "shape=ellipse;fillColor=white");

                    //dfa.addState(state);
                    automaton.addState(state);
                    graph.getModel().endUpdate();
                    text.setText("");

                } else if (text.getText().toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "Name Cannot be Empty");
                } else {

                    JOptionPane.showMessageDialog(null, "State already Exist");
                }
            }
        });

        evalText = new JTextField();
        getContentPane().add(evalText);
        evalText.setPreferredSize(new Dimension(300, 21));

        evalButton = new JButton("Evaluate");
        evalButton.setPreferredSize(new Dimension(120, 20));

        evalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                System.out.println("Value: " + evalText.getText().toString());

                if(automaton instanceof TuringMachine && automaton.getFinalStates() != null && automaton.getStartState() != null) {
                    if (((TuringMachine)automaton).evaluateTuringMachine(evalText.getText().toString()))
                        labelA.setText("Accepted");
                    else
                        labelA.setText("Not Accepted");
                } else if(automaton instanceof PDA && automaton.getStartState() != null  && ((PDA) automaton).initialSymbol !='\u0000') {
                    if (((PDA)automaton).evaluatePDA(evalText.getText().toString()))
                        labelA.setText("Accepted");
                    else
                        labelA.setText("Not Accepted");

                }else if(automaton instanceof NFA && automaton.getStartState() != null) {
                    if (((NFA)automaton).evaluateNFA(evalText.getText().toString(),((NFA) automaton).startState))
                        labelA.setText("Accepted");
                    else
                        labelA.setText("Not Accepted");

                }else if(automaton instanceof NFA_E && automaton.getStartState() != null) {
                    if (((NFA_E)automaton).evaluateNFA_E(evalText.getText().toString(), ((NFA_E) automaton).startState))
                        labelA.setText("Accepted");
                    else
                        labelA.setText("Not Accepted");

                }else if(automaton instanceof DFA && automaton.getStartState() != null) {
                    if (((DFA)automaton).evaluateDFA(evalText.getText().toString()))
                        labelA.setText("Accepted");
                    else
                        labelA.setText("Not Accepted");

                }else{
                    if(automaton instanceof PDA)
                        JOptionPane.showMessageDialog(null, "Set a Final and an Initial State and an Initial Symbol");
                    else
                        JOptionPane.showMessageDialog(null, "Set a Final and an Initial State");
                }
            }
        });
        getContentPane().add(evalButton);

        labelA = new JLabel();
        labelA.setPreferredSize(new Dimension(100, 20));
        labelA.setText("Result");
        getContentPane().add(labelA);

        //Add the buttons and the log to this panel.
        add(logScrollPane, BorderLayout.CENTER);
    }


    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = GraphicDFA.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


    public void CreateVisualAutomaton(Automaton automata){

        Object parent = graph.getDefaultParent();

        for(State s:automata.states){
            graph.getModel().beginUpdate();
            if(automata.startState.name.equals(s.name)) {
                Object v = graph.insertVertex(parent, null, s.name, s.PointX, s.PointY, 50, 50,"shape=ellipse;fillColor=cyan");
            }
            else if(automata.isFinal(s)) {
                Object v = graph.insertVertex(parent, null, s.name, s.PointX, s.PointY, 50, 50, "shape=doubleEllipse;fillColor=white;fontColor=red");
            }
            else {
                Object v = graph.insertVertex(parent, null, s.name, s.PointX, s.PointY, 50, 50, "shape=ellipse;fillColor=white");
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


    public void addMenuBar(){
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem menuItemDFA;
        JRadioButtonMenuItem menuItemNFA;
        JRadioButtonMenuItem menuItemNFA_E;
        JRadioButtonMenuItem menuItemPDA;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Select Automaton");
        menu.setMnemonic(KeyEvent.VK_S);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        menuItemDFA = new JRadioButtonMenuItem("Deterministic Automaton(DFA)");
        menuItemDFA.setSelected(true);
        menuItemDFA.setMnemonic(KeyEvent.VK_D);
        group.add(menuItemDFA);
        menu.add(menuItemDFA);

        menuItemDFA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                automaton = new DFA();
                clearGraph();
                setListeners();
                System.out.println("DFA Selected");
            }
        });

        menuItemNFA = new JRadioButtonMenuItem("Non Deterministic Automaton(NFA)");
        menuItemNFA.setMnemonic(KeyEvent.VK_N);
        group.add(menuItemNFA);
        menu.add(menuItemNFA);

        menuItemNFA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                automaton = new NFA();
                clearGraph();
                setListeners();
                System.out.println("NFASelected");
            }
        });


        menuItemNFA_E = new JRadioButtonMenuItem("Non Deterministic Automaton Epsilon(NFA-E)");
        menuItemNFA_E.setMnemonic(KeyEvent.VK_E);
        group.add(menuItemNFA_E);
        menu.add(menuItemNFA_E);
        menuItemNFA_E.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                automaton = new NFA_E();
                clearGraph();
                setListeners();
                System.out.println("NFA_E Selected");
            }
        });

        menuItemPDA = new JRadioButtonMenuItem("Pushdown Automaton(PDA)");
        menuItemPDA.setMnemonic(KeyEvent.VK_P);
        group.add(menuItemPDA);
        menu.add(menuItemPDA);
        menuItemPDA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                automaton = new PDA();
                clearGraph();
                setListeners();
                System.out.println("PDA Selected");
            }
        });

        menuItemPDA.isSelected();

        tmButton= new JRadioButton("Turing Machine");
        tmButton.setMnemonic(KeyEvent.VK_T);
        tmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                automaton = new TuringMachine();
                clearGraph();
                setListeners();
                System.out.println("Turing Machine Selected");
            }
        });

        group.add(tmButton);
        getContentPane().add(menuBar);
        getContentPane().add(tmButton);


    }
    public void setListeners(){
        if(automaton != null) {
            ecl.setAutomaton(automaton);
            pcl.setAutomaton(automaton);
            pclm.setAutomaton(automaton);
        }
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


    public void setSaveAndOpenButton(){
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        fc = new JFileChooser();
        FileNameExtensionFilter fileFilter =new FileNameExtensionFilter("ser files (*.ser)", "ser");

        fc.addChoosableFileFilter(fileFilter);
        fc.setFileFilter(fileFilter);
        //Create Open File Button
        openButton = new JButton("Open",
                createImageIcon("Images/Open16.gif"));
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == openButton) {
                    int returnVal = fc.showOpenDialog(GraphicDFA.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        //This is where a real application would open the file.
                        try{
                            System.out.println(file.getAbsolutePath());
                            FileInputStream door = new FileInputStream(file.getAbsolutePath());
                            ObjectInputStream reader = new ObjectInputStream(door);
                            Automaton x ;
                            x = (Automaton) reader.readObject();
                            CreateVisualAutomaton(x);
                            automaton = x;
                            System.out.println(x.states.iterator().next().name);


                        }catch (Exception ex) {
                            System.out.println("Exception thrown during Opening: " + ex.toString());

                        }
                        log.append("Opening: " + file.getName() + "." + "\n");
                    } else {
                        log.append("Open command cancelled" + "\n");
                    }
                }
            }
        });

        //Create Save File Button
        saveButton = new JButton("Save",
                createImageIcon("Images/Save16.png"));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object parent = graph.getDefaultParent();
                Object []cells =graphComponent.getCells(new Rectangle(520,650),parent );
                for(Object obj: cells) {
                    mxCell cell = ((mxCell) obj);
                    if(automaton.getStateWithName(cell.getValue().toString())!=null) {
                        if (cell.getValue().toString().equals(automaton.getStateWithName(cell.getValue().toString()).name)) {

                            automaton.setStateWithAttributes(cell.getValue().toString(), cell.getGeometry().getX(), cell.getGeometry().getY());
                        }
                    }
                }
                automaton.printStatePositions();
                //Handle save button action.
                if (e.getSource() == saveButton) {


                    int returnVal = fc.showSaveDialog(GraphicDFA.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        //This is where a real application would save the file.
                        try
                        {
                            automaton.updateTransitions();
                            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath()+".ser");
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(automaton);
                            oos.close();
                        }
                        catch (Exception ex)
                        {
                            System.out.println("Exception thrown during Saving: " + ex.toString());
                        }
                        log.append("Saving: " + file.getName() + "." + "\n");
                    } else {
                        log.append("Save command cancelled" + "\n");
                    }
                    log.setCaretPosition(log.getDocument().getLength());
                }
            }
        });

        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(saveButton, BorderLayout.PAGE_START);
    }

    public void clearGraph(){
        graph.getModel().beginUpdate();
        graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
        graph.getModel().endUpdate();
    }


}

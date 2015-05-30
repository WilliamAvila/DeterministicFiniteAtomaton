import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;


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
    private   mxCell cell;
    private JLabel labelA;
    private DFA dfa;
    private NFA nfa;
    private  JFileChooser fc;
    private JButton openButton, saveButton;
    private  JTextArea log;


    public  GraphicDFA(){
        super("NFA");
        initGUI();
    }

    private void initGUI(){
        setSize(520, 650);
        setLocationRelativeTo(null);
        graph = new mxGraph();
        dfa = new DFA();
        nfa = new NFA();

        //Init Log
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser();


        FileNameExtensionFilter fileFilter =new FileNameExtensionFilter("ser files (*.ser)", "ser");

        fc.addChoosableFileFilter(fileFilter);
        fc.setFileFilter(fileFilter);
        graph.setCellsResizable(false);
        graph.setAllowLoops(true);
        graph.setAllowDanglingEdges(false);
        graph.setVertexLabelsMovable(false);

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
                            NFA x = new NFA();
                            x = (NFA) reader.readObject();

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
                //Handle save button action.
              if (e.getSource() == saveButton) {
                int returnVal = fc.showSaveDialog(GraphicDFA.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would save the file.
                    try
                    {
                        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath()+".ser");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(nfa);
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

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(520, 400));
        getContentPane().add(graphComponent);


        graph.getChildVertices(graph.getDefaultParent());


       /* graphComponent.getGraphControl().addMouseListener(new PopClickListener(graphComponent, dfa));


        graphComponent.getConnectionHandler().addListener(mxEvent.CONNECT, new EdgeConnectionListener(graphComponent, dfa));
*/
        graphComponent.getGraphControl().addMouseListener(new PopClickListener(graphComponent, nfa));


        graphComponent.getConnectionHandler().addListener(mxEvent.CONNECT, new EdgeConnectionListener(graphComponent, nfa));


        moveLabel = new JLabel();
        addIcon(moveLabel, 25, 25, "/Images/move.png");
        transLabel = new JLabel();
        addIcon(transLabel, 25, 25, "/Images/transition.png");
        deleteLabel = new JLabel();
        addIcon(deleteLabel, 25, 25, "/Images/delete.png");


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
                //for(State st:dfa.getStates()){
                for (State st : nfa.getStates()) {
                    if (st.name.equals(text.getText().toString()))
                        checkExistence = st.name;
                }

                if (!text.getText().toString().equals(checkExistence)) {

                    graph.getModel().beginUpdate();
                    Object parent = graph.getDefaultParent();
                    Object v1 = graph.insertVertex(parent, null, text.getText(), 330, 30, 50, 50, "shape=ellipse;fillColor=white");
                    State state = new State(text.getText().toString());
                    //dfa.addState(state);
                    nfa.addState(state);
                    graph.getModel().endUpdate();


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

                //if (!evalText.getText().toString().equals("") && dfa.getFinalStates()!=null&&dfa.getStartState()!=null ) {
                    /*if(dfa.evaluateDFA(evalText.getText().toString()))*/
                if (/*!evalText.getText().toString().equals("") &&*/ nfa.getFinalStates() != null && nfa.getStartState() != null) {
                    if (nfa.evaluateNFA(evalText.getText().toString(), nfa.startState))
                        labelA.setText("Accepted");
                    else
                        labelA.setText("Not Accepted");
                } else {

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

    public void addIcon(final JLabel label , int width,int height, String path){

        label.setPreferredSize(new Dimension(width, height));
        getContentPane().add(label);
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        label.setIcon(icon);
        getContentPane().add(label);
        label.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && label.getBorder() ==null ) {
                    label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.blue));
                }
                else if(SwingUtilities.isLeftMouseButton(e) && label.getBorder() !=null){
                    label.setBorder(null);
                }
            }
        });
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


}

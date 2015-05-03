import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Created by william on 4/28/15.
 */
public class GraphicDFA extends JFrame {
    private mxGraph graph;
    private mxGraphComponent graphComponent;
    private  JTextField text;
    private  JTextField evalText;
    private JButton button;
    private JButton evalButton;
    private JLabel transLabel;
    private JLabel moveLabel;
    private JLabel deleteLabel;
    private   mxCell cell;
    private JLabel labelA;
    private DFA dfa;


    public  GraphicDFA(){
        super("JGraph");
        initGUI();
    }

    private void initGUI(){
        setSize(520, 600);
        setLocationRelativeTo(null);
        graph = new mxGraph();
        dfa = new DFA();

        graph.setCellsResizable(false);
        graph.setAllowLoops(true);
        graph.setAllowDanglingEdges(false);
        graph.setVertexLabelsMovable(false);

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(520, 400));
        getContentPane().add(graphComponent);



        graph.getChildVertices(graph.getDefaultParent());


        graphComponent.getGraphControl().addMouseListener(new PopClickListener(graphComponent, dfa));


        graphComponent.getConnectionHandler().addListener(mxEvent.CONNECT, new EdgeConnectListener(graphComponent, dfa));


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

        button = new JButton("Add Node");
        getContentPane().add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //System.out.println("Value: " + text.getText().toString());

                if (!text.getText().toString().equals("")) {
                    graph.getModel().beginUpdate();
                    Object parent = graph.getDefaultParent();


                    Object v1 = graph.insertVertex(parent, null, text.getText(), 330, 30, 50, 50, "shape=ellipse;fillColor=white");
                    State state = new State(text.getText().toString());
                    dfa.addState(state);
                    graph.getModel().endUpdate();


                } else {

                    JOptionPane.showMessageDialog(null, "Name Cannot be Empty");
                }
            }
        });



        evalText = new JTextField();
        getContentPane().add(evalText);
        evalText.setPreferredSize(new Dimension(300, 21));

        evalButton = new JButton("Evaluate");
        evalButton.setPreferredSize(new Dimension(120,20));

        evalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                System.out.println("Value: " + evalText.getText().toString());

                if (!evalText.getText().toString().equals("") && dfa.getFinalStates()!=null&&dfa.getStartState()!=null) {
                    if(dfa.evaluateDFA(evalText.getText().toString()))
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
        labelA.setPreferredSize(new Dimension(100,20));
        labelA.setText("Test");
        getContentPane().add(labelA);

        graph.getModel().beginUpdate();
        Object parent = graph.getDefaultParent();
        graph.insertVertex(parent,null,"test",30,80,50,50,"shape=doubleEllipse;fillColor=green;fontColor=red");


        graph.getModel().endUpdate();
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


}

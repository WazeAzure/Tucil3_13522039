package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.xml.crypto.Data;

import datahandling.Datahandling;
import algorithm.*;

class MyWindowListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        System.err.println("closing called");
        System.exit(0);
    }
}

class MyRadioListener implements ItemListener {
    JRadioButton radioUCS;
    JRadioButton radioGBFS;
    JRadioButton radioAStar;
    JTextField hiddenField;

    public MyRadioListener(JRadioButton radioUCS, JRadioButton radioGBFS, JRadioButton radioAStar, JTextField hiddenField){
        this.radioUCS = radioUCS;
        this.radioGBFS = radioGBFS;
        this.radioAStar = radioAStar;
        this.hiddenField = hiddenField;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == this.radioUCS) { 
            if (e.getStateChange() == 1) { 
                this.hiddenField.setText("1");
            } 
        } 
        else if (e.getSource() == this.radioGBFS) { 
            if (e.getStateChange() == 1) { 
                this.hiddenField.setText("2");
            } 
        } 
        else if (e.getSource() == this.radioAStar) { 
            if (e.getStateChange() == 1) { 
                this.hiddenField.setText("3");
            } 
        } 
        else { 
            this.hiddenField.setText("0");
        }
    }
}

class MyButtonListener implements ActionListener {
    JTextField textField1;
    JTextField textField2;
    JTextField hField;
    JTextArea textArea;
    JLabel timeLabel;
    JLabel nodeVisitedLabel;
    JLabel memoryLabel;
    Datahandling datahandling;

    public MyButtonListener(JTextField t1, JTextField t2, JTextArea t3, Datahandling dh, JTextField hiddenField, JLabel time, JLabel mem, JLabel node){
        this.textField1 = t1;
        this.textField2 = t2;
        this.textArea = t3;
        this.datahandling = dh;
        this.hField = hiddenField;

        this.timeLabel = time;
        this.memoryLabel = mem;
        this.nodeVisitedLabel =  node;
    }

    public boolean verifyWord(String s){
        return datahandling.checkWord(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // clear textArea
        this.textArea.setText(null);

        String val1 = this.textField1.getText();
        String val2 = this.textField2.getText();
        String val3 = this.hField.getText();
        System.err.println(val1);
        System.err.println(val2);
        System.err.println(val3);

        // JOptionPane.showMessageDialog(null, "Value: " + val1 + "\nValue2: " + val2); // Display the value
        if(!verifyWord(val1)){
            this.textArea.setText("INVALID INPUT 1");
            this.textArea.setForeground(Color.red);
            return;
        }

        if(!verifyWord(val2)){
            this.textArea.setText("INVALID INPUT 2");
            this.textArea.setForeground(Color.red);
            return;
        }

        if(val3.equals("0")){
            this.textArea.setText("INVALID ALGORITHM");
            this.textArea.setForeground(Color.red);
            return;
        }

        if(val1.length() != val2.length()){
            this.textArea.setText("DIFFERENT INPUT LENGTH");
            this.textArea.setForeground(Color.red);
            return;
        }
        
        // call algorithm handler
        Algohandler algo = new Algohandler();
        ArrayList<String> pathResult = new ArrayList<String>();
        pathResult = algo.solve(val1, val2, val3, this.datahandling);

        if(pathResult.size() == 0){
            this.textArea.setText("NO PATH FOUND");
            this.textArea.setForeground(Color.red);
        } else {
            String ans = "";
            for(int i=0; i<pathResult.size(); i++){
                ans = pathResult.get(i) + "\n" + ans;
            }

            this.textArea.setText(ans);
            this.textArea.setForeground(Color.black);
        }
        this.memoryLabel.setText("memory used: " + (algo.memoryUsed/1000) + "kb");
        this.nodeVisitedLabel.setText("node visited: " + algo.nodeVisited + " | ");
        this.timeLabel.setText("time executed: " + algo.timeExecuted + "ms" + " | ");
    }
}

/**
 * Class to handle all GUI for the application
 * @author WazeAzure
 */
public class Gui {
    /** frame width in px */
    private int width;
    /** frame height in px*/
    private int height;
    /** default frame width in px {@code 500px} */
    public static int DEFAULT_WIDTH = 500;
    /** default frame width in px {@code 1000px} */
    public static int DEFAULT_HEIGHT = 750;
    /** default column width for text field input {@code 20} */
    public static int DEFAULT_COLUMN_TEXT_FIELD = 20;
    /** program title */
    private String title;
    /** main frame */
    JFrame frame;
    
    private Datahandling dh;

    MyButtonListener buttonListener;

    /**
     * initialize a GUI with default value width {@code 500px} and height {@code 1000px}
     */
    public Gui(Datahandling f){
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.title = "World Ladder Solver - WazeAzure";
        this.dh = f;

        // main frame
        this.frame = new JFrame();

        
    }
    /**
     * initialize a GUI.
     * @param width - frame width in px
     * @param height - frame height in px
     */
    public Gui(int width, int height){
        this.width = width;
        this.height = height;
        this.title = "World Ladder Solver - WazeAzure";

        // main frame
        this.frame = new JFrame();
    }
    /**
     * Function to build the main frame for the gui
     * @param args
     */
    public void main(){
        addListener();

        // set title
        frame.setTitle(this.title);
        // set frame size
        frame.setSize(this.width, this.height);

        // create top part
        createTopRegion();
        // create middle content 
        createMiddleRegion();
        // create bottom part
        createBottomRegion();

        // show frame
        frame.setVisible(true);

        System.out.println("FROM gui");
    }

    public void createTopRegion(){
        // create panel
        JPanel top = createPanel();
        top.setBackground(new Color(18, 84, 136));

        // create text & insert to panel
        JLabel t = createLabel("Let's Solve Your Game!", Color.white, top, JLabel.CENTER);
        top.add(t, BorderLayout.CENTER);

        // add panel to frame
        this.frame.getContentPane().add(top, BorderLayout.PAGE_START);
    }

    public void addListener(){
        MyWindowListener s = new MyWindowListener();
        this.frame.addWindowListener(s);
    }

    public void createMiddleRegion(){
        // create panel
        JPanel middle = createPanel();
        middle.setBackground(new Color(173, 217, 216));
        // middle.setLayout(new BoxLayout(middle, BoxLayout.Y_AXIS));

        // create first text field
        JTextField t1 = createTextField(20, new Font("Arial", Font.PLAIN, 20));
        t1.setHorizontalAlignment(JTextField.CENTER);
        t1.setSize(50, 30);
        middle.add(t1);

        // create scrolling region
        JTextArea textArea = new JTextArea(20, 20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        middle.add(scrollPane);
        
        // create second text field
        JTextField t2 = createTextField(20, new Font("Arial", Font.PLAIN, 20));
        t2.setHorizontalAlignment(JTextField.CENTER);
        t2.setSize(50, 30);
        middle.add(t2);

        
        // create algorithm option
        JPanel radios = createPanel();
        JTextField hiddenField = new JTextField("0");
        ButtonGroup radioButtonGroup = new ButtonGroup();
        JRadioButton radioUCS = new JRadioButton("UCS");
        JRadioButton radioGBFS = new JRadioButton("GBFS");
        JRadioButton radioAStar = new JRadioButton("A*");
        
        radioUCS.addItemListener(new MyRadioListener(radioUCS, radioGBFS, radioAStar, hiddenField));
        radioGBFS.addItemListener(new MyRadioListener(radioUCS, radioGBFS, radioAStar, hiddenField));
        radioAStar.addItemListener(new MyRadioListener(radioUCS, radioGBFS, radioAStar, hiddenField));
        
        radioButtonGroup.add(radioUCS);
        radioButtonGroup.add(radioGBFS);
        radioButtonGroup.add(radioAStar);
        
        radios.add(radioUCS);
        radios.add(radioGBFS);
        radios.add(radioAStar);
        
        middle.add(radios);

        
        
        // add time exection, visited node, and memory used
        JPanel information = createPanel();
        JLabel timeLabel = new JLabel();
        JLabel memoryLabel = new JLabel();
        JLabel nodeVisitedLabel = new JLabel();

        information.add(timeLabel);
        information.add(nodeVisitedLabel);
        information.add(memoryLabel);

        
        // set button listener
        buttonListener = new MyButtonListener(t1, t2, textArea, this.dh, hiddenField, timeLabel, memoryLabel, nodeVisitedLabel);
        
        // create submit button
        JButton submit = new JButton("SOLVE");
        submit.setFont(new Font("Arial", Font.PLAIN, 20));
        submit.setHorizontalAlignment(JButton.CENTER);
        submit.setPreferredSize(new Dimension(200, 30));
        
        submit.addActionListener(buttonListener);
        middle.add(submit);
        
        // add button listener
        this.frame.getRootPane().setDefaultButton(submit);
        
        // add info to panel
        middle.add(information);
        
        // add panel to frame
        this.frame.add(middle, BorderLayout.CENTER);
    }

    public void createSubmitButton(){
        JPanel submitRegion = createPanel();
        

        this.frame.add(submitRegion);
    }

    public void createBottomRegion(){
        // create panel
        JPanel bottom = createPanel();
        bottom.setBackground(new Color(18, 84, 136));

        // create text & insert to panel
        JLabel t = createLabel("COPYRIGHT @ 2024 | WazeAzure", Color.white, bottom, JLabel.CENTER);
        bottom.add(t, BorderLayout.CENTER);

        // add panel to frame
        this.frame.add(bottom, BorderLayout.SOUTH);
    }

    public JPanel createPanel(){
        JPanel p = new JPanel();
        return p;
    }

    public JLabel createLabel(String content, Color color, JPanel panel, int textHorizontalAlignment){
        JLabel text = new JLabel(content);
        text.setHorizontalAlignment(textHorizontalAlignment);
        text.setForeground(color);
        return text;
    }

    public JTextField createTextField(int column, Font font){
        JTextField textField = new JTextField();
        textField.setColumns(column);
        textField.setText("example");
        textField.setBorder(BorderFactory.createCompoundBorder(
            textField.getBorder(), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // if font not null
        if(font != null){
            textField.setFont(font);
        }

        return textField;
    }
}

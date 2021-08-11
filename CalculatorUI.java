package calculator;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;



/***************************************************************************
 *  CalculatorUI
 *
 * User interface for Simple Calculator
 *
 * Layout:
 *
 *                0   1   2   3
 *              -----------------
 *           0  |  DISPLAY      |
 *              -----------------
 *           1  | Clear     | + |
 *              -----------------
 *           2  | 1 | 2 | 3 | - |
 *              -----------------
 *           3  | 4 | 5 | 6 | X |
 *              -----------------
 *           4  | 7 | 8 | 9 | / |
 *              -----------------
 *           5  |     0     | = |
 *              -----------------
 ***************************************************************************/
public class CalculatorUI {

    private Calculator calculator = new Calculator();  // backing class (where the work is done)
    private JLabel displayField = null; // where results are displayed

    // internal action listener class to process button presses
    private static class CalculatorActionListener implements ActionListener {
        private Calculator calculator = null;
        private JLabel displayField = null;
   
        // constructor
        public CalculatorActionListener(Calculator calc, JLabel field) {
            this.calculator = calc;
            this.displayField = field;
        }

        // process the pressed button
        private void processButton(String b) {
            //System.out.println("[" + b + "]");
            this.calculator.enter(b);
            this.displayField.setText(this.calculator.getCurrentValueAsString());
        }

        //  method to override actionPerformed 
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton)e.getSource();
            String buttonText = button.getText();
            processButton(buttonText);
        }
        
    }

    /**
     *  constructor
     */
    public CalculatorUI() {
    }

    /**
     *  Launch the Window
     */
    public void display() {
        this.calculator.clear();
        initializeFrame();
    }

    // create a button and add its action listener
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(new CalculatorActionListener(this.calculator, this.displayField));
        return button;
    }

    // add number buttons
    private void addNumberButtons(Container pane, GridBagConstraints constraints) {
        constraints.gridwidth = 1;
        for (int i = 0; i < 9; i++) {
            constraints.gridy = 2 + i / 3;
            constraints.gridx = i % 3;
            JButton button = createButton(Integer.toString(i + 1));
            pane.add(button, constraints);
        }
        JButton button = createButton("0");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 3;
        pane.add(button, constraints);
    }

    // add number display
    private void addNumberDisplay(Container pane, GridBagConstraints constraints) {
        this.displayField = new JLabel("0", SwingConstants.RIGHT);
        this.displayField.setFont(new Font("Courier New",Font.BOLD, 32));
        this.displayField.setForeground(Color.RED);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        pane.add(this.displayField, constraints);
    }

    // add operation buttons
    private void addOperationButtons(Container pane, GridBagConstraints constraints) {
        JButton button = createButton(Calculator.STR_CLEAR);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        pane.add(button, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        button = createButton(Calculator.STR_PLUS);
        pane.add(button, constraints);

        constraints.gridy = 2;
        button = createButton(Calculator.STR_MINUS);
        pane.add(button, constraints);

        constraints.gridy = 3;
        button = createButton(Calculator.STR_MULTIPLY);
        pane.add(button, constraints);

        constraints.gridy = 4;
        button = createButton(Calculator.STR_DIVIDE);
        pane.add(button, constraints);

        constraints.gridy = 5;
        button = createButton(Calculator.STR_EQUALS);
        pane.add(button, constraints);
    }

    // initialize the window, set up the frame
    // initialize the window, set up the frame
    private JFrame initializeFrame() {
        JFrame frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.green);
        frame.setSize(350,250);

        // set up the grid
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        //constraints.weightx = 0.5;
        //constraints.weighty = 0.5;
        frame.setLayout(layout);

        Container pane = frame.getContentPane();
        pane.setBackground(Color.darkGray);
        addNumberDisplay(pane, constraints);
        addNumberButtons(pane, constraints);
        addOperationButtons(pane, constraints);
        frame.setVisible(true);
        
        return frame;
    }

    public static void main(String args[]){
        CalculatorUI ui = new CalculatorUI();
        ui.display();
    }
}



package StuGraManag;

import DataBase.Backend;
import Toaster.Toaster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateGrades extends JFrame {
    private final JTextField gradeATextField;
    private final JTextField gradeBTextField;
    private final JTextField gradeCTextField;
    private final JTextField gradeDTextField;
    private final JTextField gradeETextField;
    private final JTextField gradeFTextField;
    private final JTable gradesTable;
    private final JButton updateButton;

    public UpdateGrades(JFrame parent, int gradeId,JPanel panel,
                        DefaultTableModel modelTable) {
        super("Update Grades");
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Toaster t=new Toaster(panel);

        gradeATextField = new JTextField();
        gradeBTextField = new JTextField();
        gradeCTextField = new JTextField();
        gradeDTextField = new JTextField();
        gradeETextField = new JTextField();
        gradeFTextField = new JTextField();

        addKeyListener(gradeATextField);
        addKeyListener(gradeBTextField);
        addKeyListener(gradeCTextField);
        addKeyListener(gradeDTextField);
        addKeyListener(gradeETextField);
        addKeyListener(gradeFTextField);

        updateButton = new JButton("Update");

        updateButton.setBackground(Color.green);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkGrades()) {

                    float updatedGradeA = Float.parseFloat(gradeATextField.getText());
                    float updatedGradeB = Float.parseFloat(gradeBTextField.getText());
                    float updatedGradeC = Float.parseFloat(gradeCTextField.getText());
                    float updatedGradeD = Float.parseFloat(gradeDTextField.getText());
                    float updatedGradeE = Float.parseFloat(gradeETextField.getText());
                    float updatedGradeF = Float.parseFloat(gradeFTextField.getText());

                    DefaultTableModel model = (DefaultTableModel) gradesTable.getModel();

                        Object[] rowData = new Object[]{
                                "Grades " + (model.getRowCount() + 1),
                                updatedGradeA, updatedGradeB, updatedGradeC,
                                updatedGradeD, updatedGradeE, updatedGradeF
                        };

                    float[] grades ={
                            updatedGradeA, updatedGradeB, updatedGradeC,
                            updatedGradeD, updatedGradeE, updatedGradeF
                    };



                    Backend backend=new Backend();
                    boolean test = backend.updateGrades(grades,gradeId);
                    if (test) {
                        model.addRow(rowData);
                        Object[] row = new Object[0];
                        for (int i = modelTable.getRowCount() - 1; i >= 0; i--) {
                            String id = (String) modelTable.getValueAt(i, 0); // Assuming the label is in the first column
                            if (id != null && id.equals(gradeId + "")) {
                                row = new Object[]{
                                        modelTable.getValueAt(i, 0),
                                        modelTable.getValueAt(i, 1),
                                        modelTable.getValueAt(i,2),
                                        updatedGradeA, updatedGradeB, updatedGradeC,
                                        updatedGradeD, updatedGradeE, updatedGradeF
                                };
                                modelTable.removeRow(i);
                            }
                        }
                        modelTable.addRow(row);
                        t.info("Success: Grades updated successfully");
                    } else {
                        t.error("Error: error while updating the grades");
                    }
                }else{
                    t.warn("Warning: Please provide all grades before updating.");
                }

            }
        });

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Num");
        tableModel.addColumn("Grade A");
        tableModel.addColumn("Grade B");
        tableModel.addColumn("Grade C");
        tableModel.addColumn("Grade D");
        tableModel.addColumn("Grade E");
        tableModel.addColumn("Grade F");

        gradesTable = new JTable(tableModel);
        gradesTable.setDefaultEditor(Object.class, null);
        gradesTable.setPreferredScrollableViewportSize(new Dimension(400, 100));
        JScrollPane scrollPane = new JScrollPane(gradesTable);

        add(createLabeledTextField("Grade A:", gradeATextField));
        add(createLabeledTextField("Grade B:", gradeBTextField));
        add(createLabeledTextField("Grade C:", gradeCTextField));
        add(createLabeledTextField("Grade D:", gradeDTextField));
        add(createLabeledTextField("Grade E:", gradeETextField));
        add(createLabeledTextField("Grade F:", gradeFTextField));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(updateButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(scrollPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);


    }

    private JPanel createLabeledTextField(String label, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(label));
        panel.add(textField);
        return panel;
    }

    private static void addKeyListener(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE) || (c == '.'))) {
                    e.consume();
                }
                if (c == '.' && textField.getText().contains(".")) {
                    e.consume();
                }
                try {
                    float value = Float.parseFloat(textField.getText() + c);
                    if (value < 0 || value > 20) {
                        e.consume();
                    }
                } catch (NumberFormatException ex) {
                    if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                        e.consume();
                    }
                }
            }
        });
    }

    public boolean checkGrades(){
        return !gradeATextField.getText().isEmpty() && !gradeBTextField.getText().isEmpty()
                && !gradeCTextField.getText().isEmpty() && !gradeDTextField.getText().isEmpty()
                && !gradeETextField.getText().isEmpty() && !gradeFTextField.getText().isEmpty();
    }
}


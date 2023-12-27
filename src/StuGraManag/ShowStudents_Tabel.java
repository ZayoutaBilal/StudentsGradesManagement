package StuGraManag;

import DataBase.Backend;
import Toaster.Toaster;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ShowStudents_Tabel {
	private final JPanel panel;
	private final JPanel panelbtn;
    private final JPanel paneltxt;
	private final JButton clear;
	private final JTextField search;
	private final DefaultTableModel model;
	private final JTable table;
	private final JButton delete;
	private int id=-1;
	public JFrame frame;
	private Toaster t;
	public ShowStudents_Tabel(JFrame frame , String className) {
			
		
			this.frame=frame;
			this.panel =new JPanel();
			this.panel.setLayout(new BorderLayout());
			this.panelbtn =new JPanel();
			this.paneltxt =new JPanel();
			BoxLayout layout = new BoxLayout(this.paneltxt, BoxLayout.Y_AXIS);
			this.paneltxt.setLayout(layout);
			
			this.t=new Toaster(panel);
			t.info("Click twice in left for row for more student's details");

			this.clear=new JButton("Clear");
			this.clear.setBackground(Color.LIGHT_GRAY);
			
			this.search=new JTextField();
			
			String[] column= {"ID","Number","First Name","Last Name","Gender","Birthday","Class","Email","Phone"};
			Backend back =new Backend();
			String[][] data=back.Students_tab(back.searchClassId(className));
			
			this.model = new DefaultTableModel(column,0);
			for(int i=0;i<data.length;i++) {
				this.model.addRow(
		                   new Object[]{
		                        data[i][0],
		                        data[i][1],
		                        data[i][2],
		                        data[i][3],
		                        data[i][4],
		                        data[i][5],
		                        data[i][6],
		                        data[i][7],
		                        data[i][8]
		                        
		                   }
		              );
			}
			
			
			this.table=new JTable(this.model);
			final TableRowSorter<TableModel> sort = new TableRowSorter<>(table.getModel());
			JScrollPane scrol=new JScrollPane(this.table);
			this.table.setRowSorter(sort);

			this.search.getDocument().addDocumentListener(new DocumentListener()
	        { 
	            public void insertUpdate(DocumentEvent e) {
	                String str = search.getText();
	                if (str.trim().isEmpty()) {
	                    sort.setRowFilter(null);
	                } else {
	                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
	                }
	            }
	            
	            public void removeUpdate(DocumentEvent e) {
	                String str = search.getText();
	                if (str.trim().isEmpty()) {
	                    sort.setRowFilter(null);
	                } else {
	                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
	                }
	            }
	            
	            public void changedUpdate(DocumentEvent e) {}
	        });
			
			
		 this.table.setDefaultRenderer(Object.class, new CustomCellRenderer());
		
			 
			this.delete=new JButton("Delete");
			delete.setBackground(Color.red);

			this.delete.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a) {

					int selectedRow = table.getSelectedRow();
	                if (selectedRow != -1) {
	                int choice = JOptionPane.showConfirmDialog(null,
	                        "Are you sure you want to delete this student ?",
	                        "Delete Student", JOptionPane.YES_NO_OPTION);
	                
	                if (choice == JOptionPane.YES_OPTION) {
	                    String value = table.getModel().getValueAt(selectedRow, 0).toString();
	                    id = Integer.parseInt(value);
	                    Backend back = new Backend();
	                    boolean check = back.deleteStudent(id);
	                    if (check) {
	                        model.removeRow(selectedRow);
	                        t.success("Student successfully deleted.");
	                    } else {
	                       t.error("ERROR! Please try again later.");
	                    }
	                }
	            } else {
	               t.warn("Please select a row to delete.");
	            }
				}
			});
			
			this.clear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					table.clearSelection();
				}
			});

			table.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        int selectedRow = table.getSelectedRow();
			        if (selectedRow != -1) {
			            if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 2) {
			                ArrayList<String> std = new ArrayList<>();
			                for (int i = 0; i < 9; i++) {
			                    std.add(i, table.getValueAt(selectedRow, i).toString());
			                }
			                new StudentDetailsFrame(std);
			            }
			        }
			    }
			});

			// Disable cell editing
			table.setDefaultEditor(Object.class, null);

			this.paneltxt.add(new JLabel("search:"));
			this.paneltxt.add(this.search);
			this.panelbtn.add(this.delete);
			this.panelbtn.add(this.clear);
			this.panel.add(this.paneltxt,BorderLayout.NORTH);
			this.panel.add(scrol,BorderLayout.CENTER);
			this.panel.add(this.panelbtn,BorderLayout.SOUTH);
			this.frame.add(this.panel);
			this.frame.setVisible(true);

	}

	

}

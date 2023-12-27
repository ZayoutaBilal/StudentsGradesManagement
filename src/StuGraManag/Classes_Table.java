package StuGraManag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import DataBase.Backend;
import Toaster.Toaster;

public class Classes_Table {
	
	private final JPanel panel;
	private final JPanel paneltxt;
	private final JPanel panelbtn;
	private final JTextField txtid;
	private final JTextField txtname;
	private final JTextField txtdescription;
	private final JTextField search;
	private final DefaultTableModel model;
	private final JTable table;
	private final JButton delete;
	private final JButton clear;
	private final JButton update;
	private int id=-1;
	public JFrame frame;
	
	public Classes_Table(JFrame frame) {
		this.frame=frame;
		this.panel =new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panelbtn =new JPanel();
		this.paneltxt =new JPanel();
		BoxLayout layout = new BoxLayout(this.paneltxt, BoxLayout.Y_AXIS);
		this.paneltxt.setLayout(layout);
		this.txtid=new JTextField("ID");
		this.txtname=new JTextField("Name");
		this.txtdescription=new JTextField("Description");
		this.search=new JTextField();
		Toaster t=new Toaster(panel);
		String[] column= {"ID","NAME","DESCRIPTION"};
		Backend back =new Backend();
		String[][] data=back.Classes_tab();
		
		this.model = new DefaultTableModel(column,0);
		for(int i=0;i<data.length;i++) {
			this.model.addRow(
	                   new Object[]{
	                        data[i][0],
	                        data[i][1],
	                        data[i][2]
	                   }
	              );
		}
		this.table=new JTable(this.model);
		final TableRowSorter<TableModel> sort = new TableRowSorter<>(table.getModel());
		JScrollPane scrol=new JScrollPane(this.table);
		this.table.setRowSorter(sort);
		this.txtid.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				if(((c<'0')||(c>'9'))&&(c!=KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}	
		});
		this.search.getDocument().addDocumentListener(new DocumentListener()
        { 
            public void insertUpdate(DocumentEvent e) {
                String str = search.getText();
                if (str.trim().length() == 0) {
                    sort.setRowFilter(null);
                } else {
                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
            }
            
            public void removeUpdate(DocumentEvent e) {
                String str = search.getText();
                if (str.trim().length() == 0) {
                    sort.setRowFilter(null);
                } else {
                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
            }
            
            public void changedUpdate(DocumentEvent e) {}
        });
		this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
				txtid.setText(table.getValueAt(table.getSelectedRow(),0).toString());
				txtname.setText(table.getValueAt(table.getSelectedRow(),1).toString());
				txtdescription.setText(table.getValueAt(table.getSelectedRow(),2).toString());
				}catch(Exception ex) {ex.getMessage();}
			}
		});
		
	 this.table.setDefaultRenderer(Object.class, new CustomCellRenderer());
	// Disable cell editing
	table.setDefaultEditor(Object.class, null);
		 
		this.delete=new JButton("Delete");
		delete.setBackground(Color.red);
		this.clear=new JButton("Clear");
		this.clear.setBackground(Color.LIGHT_GRAY);
		this.update=new JButton("Update");
		update.setBackground(Color.green);
		

		this.clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtid.setText("ID");
				txtname.setText("Name");
				txtdescription.setText("Description");
				table.clearSelection();
			}
		});
		this.delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {

				int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this class?",
                        "Delete Class", JOptionPane.YES_NO_OPTION);
                
                if (choice == JOptionPane.YES_OPTION) {
                    String value = table.getModel().getValueAt(selectedRow, 0).toString();
                    id = Integer.parseInt(value);
                    Backend back = new Backend();
                    boolean check = back.deleteClass(id);
                    if (check) {
                        model.removeRow(selectedRow);
                        t.success("Class successfully deleted.");
                       
                    } else {
                    	t.error("ERROR! Please try again later.");
                    }
                }
            } else {
            	t.warn("Please select a row to delete.");
            }
			}
		});
		this.update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1) {
					int choice = JOptionPane.showConfirmDialog(null,
	                        "Are you sure you want to update this class?",
	                        "Update Class", JOptionPane.YES_NO_OPTION);
					if(choice == JOptionPane.YES_OPTION) {
						int newid=Integer.parseInt(txtid.getText());
						String newname=txtname.getText();
						String newdescription=txtdescription.getText();
						String value=table.getModel().getValueAt(table.getSelectedRow(),0).toString();
						int oldid=Integer.parseInt(value);
						Backend back=new Backend();
						boolean check=back.updateClass( oldid, newid, newname, newdescription);
						if(check) {
							int i=table.getSelectedRow();
							   model.setValueAt(txtid.getText(), i, 0);
				               model.setValueAt(txtname.getText(), i, 1);
				               model.setValueAt(txtdescription.getText(), i, 2);
							t.success("UPDATE SUCCESSFULLY ...");
						}else {
							t.error("ERROR...");
							t.info("Please check that the id is unique and every field is not empty");
							
						}
					}
				
				
				}else {
					t.warn("Please select a row to update.");
	            }
			}
		});
		this.paneltxt.add(this.txtid);
		this.paneltxt.add(this.txtname);
		this.paneltxt.add(this.txtdescription);
		this.paneltxt.add(new JLabel("search:"));
		this.paneltxt.add(this.search);
		this.panelbtn.add(this.delete);
		this.panelbtn.add(this.update);
		this.panelbtn.add(this.clear);
		this.panel.add(this.paneltxt,BorderLayout.NORTH);
		this.panel.add(scrol,BorderLayout.CENTER);
		this.panel.add(this.panelbtn,BorderLayout.SOUTH);
		this.frame.add(this.panel);
		this.frame.setVisible(true);
	}
	
	
	public JPanel showClasses() {
		
		
		return this.panel;
	}

}

package StuGraManag;

import DataBase.Backend;
import DataBase.ConfigFrame;
import DataBase.Creat_DB;
import Utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Interface extends JFrame {

    public Interface(JPanel imagePanel) {
    	ImageIcon im = new ImageIcon(Objects.requireNonNull(Interface.class.getResource(Constants.iconImg)));
        Image image = im.getImage();
       setIconImage(image);
		setTitle("StuGraManage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);


        JMenuBar menuBar = new JMenuBar();
        JMenu classeMenu = new JMenu("Classes Management");
        JMenu etudiantsMenu = new JMenu("Students Management");
        JMenu notesMenu = new JMenu("Grades Management");
        JMenu plus = new JMenu("Plus");
        

        
        menuBar.add(classeMenu);
        menuBar.add(etudiantsMenu);
        menuBar.add(notesMenu);
        menuBar.add(plus);
        
       
        
        JMenuItem Restart = new JMenuItem("Restart The App");
        plus.add(Restart);
        Restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Interface.this.dispose();
                new Interface(backPanel());
                
            }
        });

        JMenuItem export = new JMenuItem("Export");
        plus.add(export);
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Interface.removeFirstPanelFromFrame(Interface.this);
                new ExcelExporter(Interface.this);
            }
        });

        JMenuItem UpdateConfig = new JMenuItem("Update Config");
        plus.add(UpdateConfig);
        UpdateConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfigFrame();
            }
        });

        JMenuItem createDB = new JMenuItem("Create DataBase");
        plus.add(createDB);
        createDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Creat_DB();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        JMenuItem showClassesItem = new JMenuItem("Show Classes");
        JMenuItem addClassItem = new JMenuItem("Add Class");


        showClassesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Interface.removeFirstPanelFromFrame(Interface.this);
                new Classes_Table(Interface.this);
                
            }
        });

        addClassItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            		
            		String description = null ;
                    String name = JOptionPane.showInputDialog(Interface.this, "Enter the name of class:", "Add Class", JOptionPane.QUESTION_MESSAGE);
                    	if(name!=null) {
                    	while(name.isEmpty()) {
                		JOptionPane.showMessageDialog(Interface.this, "you need to insert the name of class", "Add Class",JOptionPane.INFORMATION_MESSAGE);
                    	name = JOptionPane.showInputDialog(Interface.this, "Enter the name of class:", "Add Class", JOptionPane.QUESTION_MESSAGE);	
                    	if(name==null) break;
                    	}
                    	if(name!=null) {
                    		 description = JOptionPane.showInputDialog(Interface.this, "Enter the description of class:", "Add Class", JOptionPane.QUESTION_MESSAGE);	
                    		
                    	}
                    	if(name != null && description != null && description.isEmpty()) {
                			description=name;
                			
                    	}
                    	if(name != null && description != null ) {
                			
                			Backend back=new Backend();
                			boolean check=back.addClass(name,description);
                			if(check) {
                				JOptionPane.showMessageDialog(Interface.this, "Adding Class successful, Please refresh the App", "Add Class",JOptionPane.INFORMATION_MESSAGE);
                				Interface.removeFirstPanelFromFrame(Interface.this);
                                new Classes_Table(Interface.this);
                			}else {
                				JOptionPane.showMessageDialog(Interface.this, "Error. Please try again.", "Add Class", JOptionPane.ERROR_MESSAGE);
            					
                			}
                			
                    	}
                    	}
            }
        });


        classeMenu.add(showClassesItem);
        classeMenu.add(addClassItem);

        JMenu showStudentsItem = new JMenu("Show Students");
        Backend back=new Backend();
        String[] classes=back.selectClass();

        for (final String c : classes) {
            JMenuItem cls = new JMenuItem(c);
            showStudentsItem.add(cls);

            cls.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Interface.removeFirstPanelFromFrame(Interface.this);
                    new ShowStudents_Tabel(Interface.this, c);
                }
            });

        }
        JMenuItem addStudentItem = new JMenuItem("Add Student");
        JMenuItem updateStudentItem = new JMenuItem("Update Student");



        addStudentItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Interface.removeFirstPanelFromFrame(Interface.this);
                new AddStudent(Interface.this);
            }
        });

        updateStudentItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Interface.removeFirstPanelFromFrame(Interface.this);
                new UpdateStudent(Interface.this);
            }
        });


        etudiantsMenu.add(showStudentsItem);
        etudiantsMenu.add(addStudentItem);
        etudiantsMenu.add(updateStudentItem);
        
        
        JMenu showNotesItem = new JMenu("Grades");

        for (final String cls : classes) {
            JMenuItem clsname = new JMenuItem(cls);
            showNotesItem.add(clsname);
            clsname.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Interface.removeFirstPanelFromFrame(Interface.this);
                    new ShowGrades_Tabel(Interface.this, cls);
                }
            });

        }

        notesMenu.add(showNotesItem);
        setJMenuBar(menuBar);
        add(imagePanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    private static void removeFirstPanelFromFrame(JFrame frame) {
        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                frame.remove(component);
                frame.revalidate();
                frame.repaint();
                return;
            }
        }
    }
    
    public static JPanel backPanel() {
    		JPanel imagePanel = new JPanel() {
            
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(Interface.class.getResource(Constants.iterfacImge));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
       };
       return imagePanel;
    }

}

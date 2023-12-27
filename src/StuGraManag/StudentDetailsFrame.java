package StuGraManag;

import DataBase.Backend;
import Utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class StudentDetailsFrame extends JFrame {

	    private int std_id;
	    private final String number;
	    private final String first_name;
	    private final String last_name;
	    private final String gender;
	    private final String birthday;
	    private final String className;
	    private final String email;
	    private final String phone;
	    JLabel pictureLabel ;

    public StudentDetailsFrame(ArrayList<String> std) {
      	number=std.get(1);
    	first_name=std.get(2);last_name=std.get(3);
    	gender=std.get(4);
    	birthday=std.get(5);
    	try {
    		std_id=Integer.parseInt(std.get(0));
    	}catch(Exception ignored) {}
    	className=std.get(6);
    	email=std.get(7);
    	phone=std.get(8);
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setTitle(first_name + " " + last_name);
        ImageIcon im = new ImageIcon(Objects.requireNonNull(StudentDetailsFrame.class.getResource(Constants.profileImg)));
        Image image = im.getImage();
        setIconImage(image);

        Backend back = new Backend();

        JLabel idLabel = new JLabel("Student ID: " + std_id);
        idLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel numberLabel = new JLabel("Number: " + std_id);
        numberLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel nameLabel = new JLabel("Name: " + first_name + " " + last_name);
        nameLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel genderLabel = new JLabel("Gender: " + gender);
        genderLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel birthdayLabel = new JLabel("Birthday: " + birthday);
        birthdayLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel classLabel = new JLabel("Class: " + className);
        classLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel emailLabel = new JLabel("Email: " + email);
        emailLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel phoneLabel = new JLabel("Phone: " + phone);
        phoneLabel.setHorizontalAlignment(JLabel.LEFT);

        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        pictureLabel.setSize(100, 100);

        JPanel panelPicture = new JPanel(new BorderLayout());
        panelPicture.add(pictureLabel, BorderLayout.NORTH); 

        JPanel panel = new JPanel(new GridLayout(8, 1, 3, 1)); 
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        displayStudentImage(this.std_id,this.gender);

        panel.add(idLabel);
        panel.add(numberLabel);
        panel.add(nameLabel);
        panel.add(genderLabel);
        panel.add(birthdayLabel);
        panel.add(classLabel);
        panel.add(emailLabel);
        panel.add(phoneLabel);

       
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(10, 100));
       


        JPanel gradesPanel = new JPanel();
        String[] columnNames = {"Grade X", "Value"};
        
        int[] grades=back.studentGrades(std_id);
        
     Object[][] data = {
         {"Grade A", grades[0]},
         {"Grade B", grades[1]},
         {"Grade C", grades[2]},
         {"Grade D", grades[3]},
         {"Grade E", grades[4]},
         {"Grade F", grades[5]},

     };

     JTable gradesTable = new JTable(data, columnNames);
     gradesTable.setEnabled(false);
        
     JScrollPane scrollPane = new JScrollPane(gradesTable);
     scrollPane.setPreferredSize(new Dimension(150, 120));
     gradesPanel.add(scrollPane);
     
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.WEST);
        mainPanel.add(separator, BorderLayout.CENTER);
        mainPanel.add(gradesPanel, BorderLayout.EAST);

    
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(panelPicture, BorderLayout.NORTH);
        containerPanel.add(mainPanel, BorderLayout.CENTER);

        add(containerPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    private void displayStudentImage(int studentId, String Gender) {
        Backend back = new Backend();
        byte[] imageData = back.retrieveStudentImage(studentId);

        if (imageData != null) {
            ImageIcon imageIcon = new ImageIcon(imageData);
            Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            pictureLabel.setIcon(imageIcon);
        } else {
        	ImageIcon defaultImageIcon;
        	if(Gender.equals("Male")) {
        	 defaultImageIcon = new ImageIcon(StudentDetailsFrame.class.getResource(Constants.DIMImg));
        	}
        	else {
        	defaultImageIcon = new ImageIcon(StudentDetailsFrame.class.getResource(Constants.DIFImg));
        	}
             Image defaultImage = defaultImageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
             defaultImageIcon = new ImageIcon(defaultImage);
             pictureLabel.setIcon(defaultImageIcon);
           
        }
    }

}


package StuGraManag;

import java.awt.Color;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileFilter;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import DataBase.Backend;
import Toaster.Toaster;

public class AddStudent {
	
	 	JLabel message;
	    JLabel fnameLabel,lnameLabel, dobLabel, genderLabel,BmonthLabel,BdayLabel,ByearLabel,numberLabel;
	    JTextField fnameField,lnameField,BmonthField,BdayField,ByearField;
	    JRadioButton genderMale, genderFemale;
	    ButtonGroup genderGroup;
	    JLabel mailLabel, mobileNoLabel;
	    JTextField mailField, mobileNoField;
	    JLabel numbererrorLabel = new JLabel("");
	    JLabel mailerrorLabel = new JLabel("");
	    JPanel panel=new JPanel();
	    
	    
	    JLabel branchLabel;
	    JComboBox<String> branchList;
	    
	    JButton registerButton,clearButton; 
	    
	public AddStudent(JFrame frame) {

        message = new JLabel("Register a new Student");
        message.setFont(new Font("Courier", Font.BOLD, 20));
        fnameLabel = new JLabel("First Name");
        lnameLabel = new JLabel("Last Name");
        ByearLabel = new JLabel("Year :");
        BmonthLabel = new JLabel("Month :");
        BdayLabel = new JLabel("Day :");
        numberLabel=new JLabel("Number");
        BmonthField = new JTextField();
        BdayField = new JTextField();
        ByearField = new JTextField();
        fnameField = new JTextField();
        lnameField = new JTextField();
        dobLabel = new JLabel("Birthday");
        genderLabel = new JLabel("Gender");
        genderMale = new JRadioButton("Male", true);
        genderFemale = new JRadioButton("Female",false);
        genderGroup = new ButtonGroup();
        genderGroup.add(genderMale);
        genderGroup.add(genderFemale);
        mailLabel = new JLabel("Mail");
        mailField = new JTextField();
        mobileNoLabel = new JLabel("Mobile");
        mobileNoField = new JTextField();
       
        
        branchLabel = new JLabel("Class");
        branchList = new JComboBox<String>();
        
        
        Backend back =new Backend();
        String[] data=back.selectClass(); 
        for(int i=0;i<data.length;i++) {
        branchList.addItem(data[i]);
        }
        
        final SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 50, 1);
        final JSpinner numberSpinner = new JSpinner(spinnerModel);
        
        registerButton = new JButton("Register");
        registerButton.setBackground(Color.green);
        clearButton=new JButton("Clear");
        clearButton.setBackground(Color.LIGHT_GRAY);
        
        
        panel.setLayout(null);
        message.setBounds(50, 10, 600, 30);
        fnameLabel.setBounds(50, 60, 100, 30);
        fnameField.setBounds(130, 60, 200, 30);
        lnameField.setBounds(130, 110, 200, 30);
        lnameLabel.setBounds(50, 110, 100, 30);
        genderLabel.setBounds(50, 160, 100, 30);
        genderMale.setBounds(130, 160, 100, 30);
        genderFemale.setBounds(240, 160, 100, 30);
        dobLabel.setBounds(50, 210, 100, 30);
        ByearLabel.setBounds(130,210,40,30);
        ByearField.setBounds(170, 217, 50, 20);
        BmonthLabel.setBounds(240,210,45,30);
        BmonthField.setBounds(290,217,50,20);
        BdayLabel.setBounds(360,210,35,30);
        BdayField.setBounds(400,217,50,20);
       

        mobileNoLabel.setBounds(50, 260, 100, 30);
        mobileNoField.setBounds(130, 260, 200, 30);
        numbererrorLabel.setBounds(340,260,500,30);
        mailLabel.setBounds(50, 310, 100, 30);
        mailField.setBounds(130, 310, 200, 30);
        mailerrorLabel.setBounds(340,310,500,30);
        branchLabel.setBounds(50, 360, 100, 30);
        branchList.setBounds(130, 360, 200, 30);
        numberLabel.setBounds(50, 410, 100, 30);
        numberSpinner.setBounds(130, 410, 200, 30);
        registerButton.setBounds(450, 360, 200, 30);
        clearButton.setBounds(450, 410, 200, 30);
        
        panel.add(message);
        panel.add(fnameLabel);
        panel.add(fnameField);
        panel.add(lnameLabel);
        panel.add(lnameField);
        panel.add(dobLabel);
        panel.add(ByearLabel);
        panel.add(ByearField);
        panel.add(BmonthLabel);
        panel.add(BmonthField);
        panel.add(BdayLabel);
        panel.add(BdayField);
        panel.add(genderLabel);
        panel.add(genderMale);
        panel.add(genderFemale);
        panel.add(mailLabel);
        panel.add(mailField);
        panel.add(mailerrorLabel);
        panel.add(mobileNoLabel);
        panel.add(mobileNoField);
        panel.add(numbererrorLabel);
        panel.add(branchLabel);
        panel.add(branchList);
        panel.add(numberLabel);
        panel.add(numberSpinner);
        panel.add(registerButton);
        panel.add(clearButton);

        
        
        
        
        
        ByearField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                     e.consume();  // ignorer l'événement 
                }
            }
         });
        
        BmonthField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                     e.consume();  // ignorer l'événement 
                }
            }
         });
        
        BdayField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                     e.consume();  // ignorer l'événement 
                }
            }
         });
        
        mobileNoField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                     e.consume();  // ignorer l'événement 
                }
            }
         });
        
        lnameField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != KeyEvent.VK_BACK_SPACE && c != ' ') {
                    e.consume();  // Ignore the event
                }
            }
        });
        
        fnameField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != KeyEvent.VK_BACK_SPACE && c != ' ') {
                    e.consume();  // Ignore the event
                }
            }
        });
        
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String[] student =new String[8];
            	student[0]=spinnerModel.getValue().toString();
            	student[1]=fnameField.getText();
            	student[2]=lnameField.getText();
            	student[3]=getSelectedGender();
            	student[4]=BdayField.getText()+"-"+BmonthField.getText()+"-"+ByearField.getText();
            	student[5]=branchList.getSelectedItem().toString();
            	student[6]=mailField.getText();
            	student[7]=mobileNoField.getText();

            	Toaster t=new Toaster(panel);
            	
            	if(mailerrorLabel.getText().equals("") && !BdayField.getText().equals("") && !BmonthField.getText().equals("") && !ByearField.getText().equals("")
                		&& numbererrorLabel.getText().equals("") && !fnameField.getText().equals("") && !lnameField.getText().equals("")) {
	            	String error=checkBirthday(ByearField.getText(),BmonthField.getText(),BdayField.getText());
	                messagesBirthday(error);
	                
	                if(error=="") {
	                	File image=null;
	                	 int choice = JOptionPane.showConfirmDialog(null,
	 	                        "do you  want to select an image ?",
	 	                        "Select Image", JOptionPane.YES_NO_OPTION);
	                	if( choice==JOptionPane.YES_OPTION) {
	                		image=handleImageSelection();
	                		
	                	}
		            	Backend back=new Backend();
		            	boolean check;
						try {
							check = back.registreStudent(student,image);
							if(check) {
								
								t.success("Adding Student successful!");
			            		
			            	}else {
			            		t.error("Error. Please try again");
			            	}
						} catch (ParseException e1) {
							t.error(e1.getMessage());
						} catch (SQLException e1) {
							t.error(e1.getMessage());
						}
	            	
	                }
            	}else {
                	t.warn("Error: Please fill in all the fields");
                }
            }
        });
        


        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	fnameField.setText("");
            	lnameField.setText("");
            	BdayField.setText("");
            	BmonthField.setText("");
            	ByearField.setText("");
            	mailField.setText("");
            	mobileNoField.setText("");
                genderMale.setSelected(true);
                genderFemale.setSelected(false);
                branchList.setSelectedIndex(0);
                numberSpinner.setValue(((SpinnerNumberModel) spinnerModel).getMinimum());
                
            }
        });
        
        
        mobileNoField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkPhoneNumber();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkPhoneNumber();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkPhoneNumber();
            }
            
            private void checkPhoneNumber() {
                String phoneNumber = mobileNoField.getText();
                if (phoneNumber.length() != 10) {
                    numbererrorLabel.setText("Phone number must have exactly 10 digits.");
                    numbererrorLabel.setForeground(Color.RED);
                } else {
                    numbererrorLabel.setText("");
                }
            }
        });
        
        mailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            	checkEmail();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	checkEmail();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	checkEmail();
            }
            
            private void checkEmail() {
                String email = mailField.getText();
                if (!isValidEmail(email)) {
                    mailerrorLabel.setText("Invalid email address");
                    mailerrorLabel.setForeground(Color.RED);
                } else {
                    mailerrorLabel.setText("");
                }
            }
        });


        
        frame.add(this.panel);
        frame.setVisible(true);
	}
	
	
	
	public static boolean isValidEmail(String email) {
        // A simple regular expression for basic email validation
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    private String getSelectedGender() {
        if (genderMale.isSelected()) {
            return "Male";
        } else if (genderFemale.isSelected()) {
            return "Female";
        } else {
            return "Unknown";
        }
    }
    
    public String checkBirthday(String year,String month,String day) {
    	String check="";
    	int yyyy,mm,dd;
    	yyyy=Integer.parseInt(year);
    	mm=Integer.parseInt(month);
    	dd=Integer.parseInt(day);
    	Year currentYear = Year.now();
        int yearValue = currentYear.getValue();
    	if(yyyy<1990 || yyyy>=yearValue) {
    		check+="year";
    	}
    	if(mm<1 || mm>12) {
    		check+="month";
    	}
    	if(dd<1 || dd>31) {
    		check+="day";
    	}
    	return check;
    }
    
    
    public File handleImageSelection() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image");
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(imageFilter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;

    }
    


    
    public void messagesBirthday(String check) {
    	Toaster t=new Toaster(panel);
    	String strError;
    	if(check!="") {
    		switch(check) { 
    		
    		case "year":		strError="Error: The provided year is not logical";
    		t.warn(strError);
    		break;
    			
    		case "day":	strError= "Error: The provided day is not logical."; 
    		t.warn(strError);
    		break;
    			
    		case "month":	strError="Error: The provided month is not logical.";
    		t.warn(strError);
    		break;
    			
    		case "yearmonthday" : strError= "Error: The provided year, month, and day are not logical.";
    		t.warn(strError);
    		break;
    			
    		case "yearmonth": strError= "Error: The provided year, and month are not logical.";
    		t.warn(strError);
    		break;
    		
    		case "yearday":strError= "Error: The provided year, and day are not logical.";
    		t.warn(strError);
    		break;
    			
    		case "monthday" :strError= "Error: The provided month, and day are not logical.";
    		t.warn(strError);
    		break;
    			
			default:
			break;
    		
    		}
    	}
    }

}

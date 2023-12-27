package StuGraManag;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.Year;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import DataBase.Backend;
import Toaster.Toaster;
import Utils.Constants;

public class UpdateStudent {
	JLabel message,searchLabel;
    JLabel fnameLabel,lnameLabel, dobLabel, genderLabel,BmonthLabel,BdayLabel,ByearLabel,numberLabel;
    JTextField IdFiield,fnameField,lnameField,BmonthField,BdayField,ByearField;
    JRadioButton genderMale, genderFemale;
    ButtonGroup genderGroup;
    JLabel mailLabel, mobileNoLabel;
    JTextField mailField, mobileNoField;
    JLabel numbererrorLabel = new JLabel("");
    JLabel mailerrorLabel = new JLabel("");
    JPanel panel=new JPanel();
    JSpinner numberSpinner;
    JLabel branchLabel;
    JComboBox<String> branchList;
    JButton updateButton,clearButton,searchNewStudent,searchButton; 
    JLabel imageLabel;
    JButton modifyImageButton;

    private int studentId;
	public UpdateStudent(JFrame frame) {
		
        imageLabel = new JLabel();
		message = new JLabel();
		searchLabel=new JLabel("Please enter the Student ID for which you want to update the information:");
        message.setFont(new Font("Courier", Font.BOLD, 20));
        fnameLabel = new JLabel("First Name");
        lnameLabel = new JLabel("Last Name");
        ByearLabel = new JLabel("Year :");
        BmonthLabel = new JLabel("Month :");
        BdayLabel = new JLabel("Day :");
        numberLabel=new JLabel("Number");
        IdFiield= new JTextField();
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
        numberSpinner = new JSpinner(spinnerModel);
        
        updateButton = new JButton("Update");
        updateButton.setBackground(Color.green);
        clearButton=new JButton("Clear");
        clearButton.setBackground(Color.LIGHT_GRAY);
        searchButton = new JButton("Search");
        searchButton.setBackground(Color.CYAN);
        searchNewStudent=new JButton("Other Student");
        searchNewStudent.setBackground(Color.CYAN);
        modifyImageButton = new JButton("Modify Photo");
        modifyImageButton.setBackground(Color.RED);
        
        panel.setLayout(null);
        IdFiield.setBounds(130, 60, 200, 30);
        searchButton.setBounds(130, 110, 200, 30);
        searchLabel.setBounds(50, 10, 600, 30);
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
        searchNewStudent.setBounds(300, 500, 200, 30);
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
        updateButton.setBounds(450, 360, 200, 30);
        clearButton.setBounds(450, 410, 200, 30);
        imageLabel.setBounds(500, 20, 200, 200);
        modifyImageButton.setBounds(500, 225, 200, 30);
       
        this.setVisible(false);
        
        panel.add(message);
        panel.add(searchLabel);
        panel.add(IdFiield);
        panel.add(searchButton);
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
        panel.add(updateButton);
        panel.add(clearButton);
        panel.add(searchNewStudent);
        panel.add(imageLabel);
        panel.add(modifyImageButton);
        
        
        Toaster t=new Toaster(panel);
        
        IdFiield.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                     e.consume();  // ignorer l'événement 
                }
            }
         });
        
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
        
       
        
        updateButton.addActionListener(new ActionListener() {
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
      	
            	if(mailerrorLabel.getText().equals("") && !BdayField.getText().equals("") && !BmonthField.getText().equals("") && !ByearField.getText().equals("")
                		&& numbererrorLabel.getText().equals("") && !fnameField.getText().equals("") && !lnameField.getText().equals("")) { 
	            	String error=checkBirthday(ByearField.getText(),BmonthField.getText(),BdayField.getText());
	                messagesBirthday(error);
	                if(error=="") {
	                	int choice = JOptionPane.showConfirmDialog(null,
		                        "Are you sure you want to delete this student ?",
		                        "Delete Student", JOptionPane.YES_NO_OPTION);
		                
		                if (choice == JOptionPane.YES_OPTION) {
		            	Backend back=new Backend();
		            	boolean check;
						try {
							check = back.updateStudent(student,studentId);
							if(check) {
			            		t.success( "Updating Student successful");
			            	}else {
			            		t.error("Error. Please try again.");
			            	}
						} catch (ParseException e1) {
							t.warn("Error. Please try again.");
						}
	            	
	                }
	                }
            	}else {
                	t.warn("Error: Please fill in all the fields");
                }
            }
        });
        
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! IdFiield.getText().equals("")) {
	                studentId = Integer.parseInt(IdFiield.getText());
	                Backend back=new Backend();
	        		String[] student =back.importstudent(studentId);
	        		if(student!=null) {
	        		fillingFields(student);
	                searchButton.setVisible(false);
	                IdFiield.setVisible(false);
	                searchLabel.setVisible(false);
	                setVisible(true);
	                displayStudentImage(studentId,getSelectedGender());
	        		}else {
	        		t.info("The student you are looking for does not exist !");
	        		}
                }else {
                t.warn("Please fill the field.");
                }
            }
        });
        
        
        searchNewStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                setVisible(false);
                searchButton.setVisible(true);
                IdFiield.setVisible(true);
                searchLabel.setVisible(true);
                IdFiield.setText(null);
                
            }
        });
        
        modifyImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	Backend back=new Backend();
            	File photo=handleImageSelection();
            	if(photo != null) {
            		boolean b=back.modifyPhoto(studentId,photo);
            		if(b) {
            			try {
            			    Image originalImage = ImageIO.read(photo);
            			    Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            			    ImageIcon imageIcon = new ImageIcon(scaledImage);
            			    imageLabel.setIcon(imageIcon);

            			} catch (IOException e1) {
            			    e1.printStackTrace();
            			}
            			t.success("your photo has been changed");
            		}
            		else t.error("Error while changing your photo!");
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
                branchList.setSelectedIndex(-1);
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
    
    
    

    public void messagesBirthday(String check) {
    	Toaster t=new Toaster(panel);
    	String strError;
    	if(!Objects.equals(check, "")) {
    		switch(check) { 
    		
    		case "year":	strError="Error: The provided year is not logical";
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
    	public void setVisible(boolean statu) {
    		
    		searchNewStudent.setVisible(statu);
    		message.setVisible(statu);
            fnameLabel.setVisible(statu);
            fnameField.setVisible(statu);
            lnameLabel.setVisible(statu);
            lnameField.setVisible(statu);
            dobLabel.setVisible(statu);
            ByearLabel.setVisible(statu);
            ByearField.setVisible(statu);
            BmonthLabel.setVisible(statu);
            BmonthField.setVisible(statu);
            BdayLabel.setVisible(statu);
            BdayField.setVisible(statu);
            genderLabel.setVisible(statu);
            genderMale.setVisible(statu);
            genderFemale.setVisible(statu);
            mailLabel.setVisible(statu);
            mailField.setVisible(statu);
            mailerrorLabel.setVisible(statu);
            mobileNoLabel.setVisible(statu);
            mobileNoField.setVisible(statu);
            numbererrorLabel.setVisible(statu);
            branchLabel.setVisible(statu);
            branchList.setVisible(statu);
            numberLabel.setVisible(statu);
            numberSpinner.setVisible(statu);
            updateButton.setVisible(statu);
            clearButton.setVisible(statu);
            imageLabel.setVisible(statu);
            modifyImageButton.setVisible(statu);
    	}
    
    	public void fillingFields(String[] student) {
    		message.setText("Update the Student who has ID="+student[0]);
    		fnameField.setText(student[2]);
    		lnameField.setText(student[3]);
    		if(student[4].equals("Male")) {
    			genderMale.setSelected(true);
                genderFemale.setSelected(false);
    		}else {
    			genderMale.setSelected(false);
                genderFemale.setSelected(true);
    		}
    		mailField.setText(student[7]);
    		mobileNoField.setText(student[8]);
    		int value=Integer.parseInt(student[1]);
    		numberSpinner.setValue(value);
    		branchList.setSelectedItem(student[6]);
    		try {
    			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(student[5]);

                
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

                ByearField.setText(yearFormat.format(date));
                BmonthField.setText(monthFormat.format(date));
                BdayField.setText(dayFormat.format(date));
                
            } catch (ParseException ex) {
               ex.getMessage();
            }
    	}

        private void displayStudentImage(int studentId, String Gender) {
            Backend back = new Backend();
            byte[] imageData = back.retrieveStudentImage(studentId);

            if (imageData != null) {
                ImageIcon imageIcon = new ImageIcon(imageData);
                Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(image);
                imageLabel.setIcon(imageIcon);
            } else {
            	ImageIcon defaultImageIcon;
            	if(Gender.equals("Male")) {
            	 defaultImageIcon = new ImageIcon(StudentDetailsFrame.class.getResource(Constants.DIMImg));
            	}
            	else {
            	defaultImageIcon = new ImageIcon(StudentDetailsFrame.class.getResource(Constants.DIFImg));
            	}
                 Image defaultImage = defaultImageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                 defaultImageIcon = new ImageIcon(defaultImage);
                 imageLabel.setIcon(defaultImageIcon);
               
            }
        }
    	 
    	    public File handleImageSelection() {
    	        JFileChooser fileChooser = new JFileChooser();
    	        fileChooser.setDialogTitle("Select Image");
    	        FileFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
    	        fileChooser.setFileFilter(imageFilter);

    	        int result = fileChooser.showOpenDialog(null);
    	        if (result == JFileChooser.APPROVE_OPTION) {
    	            File selectedFile = fileChooser.getSelectedFile();
    	            return selectedFile;
    	        }

    	        return null;

    	    }

}

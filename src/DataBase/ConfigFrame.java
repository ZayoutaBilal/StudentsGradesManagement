package DataBase;

import Utils.Constants;
import Utils.SecureAppConfiguration;
import Utils.SecureDatabaseConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class ConfigFrame extends JFrame{

    private JTextField usernameField, ipField, portField, dbUsernameField,dbNameField;
    private JPasswordField passwordField, dbPasswordField;
    private final SecureDatabaseConfiguration SecureDatabaseConfiguration=new SecureDatabaseConfiguration();
    private final SecureAppConfiguration SecureAppConfiguration=new SecureAppConfiguration();

	public ConfigFrame(){
		 
	        setSize(450, 350);
	        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        ImageIcon icon = new ImageIcon(Objects.requireNonNull(ConfigFrame.class.getResource(Constants.registerInfosImg)));
	        Image image = icon.getImage();
	        setIconImage(image);

	        JPanel panel = new JPanel();

            String[] config=new String[7];
            config[0]=SecureAppConfiguration.getAppUsername();
            config[1]=SecureAppConfiguration.getAppPassword();
            config[2]=SecureDatabaseConfiguration.getDatabaseUsername();
            config[3]=SecureDatabaseConfiguration.getDatabasePassword();
            config[4]=SecureDatabaseConfiguration.getDatabaseIP();
            config[5]=SecureDatabaseConfiguration.getDatabasePort();
            config[6]=SecureDatabaseConfiguration.getDatabaseName();

	        placeComponents(panel,config);
	        add(panel);

	        setLocationRelativeTo(null);
	        setUndecorated(true);
	        setVisible(true);
	}
    private void placeComponents(JPanel panel,String[] config){
        panel.setLayout(null);

        // Application Information
        JLabel appInfoLabel = new JLabel("Application Informations",JLabel.CENTER);
        appInfoLabel.setBounds(10, 10, 200, 25);
        panel.add(appInfoLabel);

        JLabel userLabel = new JLabel("App Username:");
        userLabel.setBounds(10, 40, 95, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(130, 40, 165, 25);
        usernameField.setText(config[0]);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("App Password:");
        passwordLabel.setBounds(10, 70, 90, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(130, 70, 165, 25);
        passwordField.setText(config[1]);
        panel.add(passwordField);

        // Database Information
        JLabel dbInfoLabel = new JLabel("Database Informations",JLabel.CENTER);
        dbInfoLabel.setBounds(10, 100, 200, 25);
        panel.add(dbInfoLabel);

        JLabel dbUserLabel = new JLabel("DB Username:");
        dbUserLabel.setBounds(10, 130, 90, 25);
        panel.add(dbUserLabel);

        dbUsernameField = new JTextField(20);
        dbUsernameField.setBounds(130, 130, 165, 25);
        dbUsernameField.setText(config[2]);
        panel.add(dbUsernameField);

        JLabel dbPasswordLabel = new JLabel("DB Password:");
        dbPasswordLabel.setBounds(10, 160, 90, 25);
        panel.add(dbPasswordLabel);

        dbPasswordField = new JPasswordField(20);
        dbPasswordField.setBounds(130, 160, 165, 25);
        dbPasswordField.setText(config[3]);
        panel.add(dbPasswordField);

        JLabel dbIpLabel = new JLabel("DB IP Address:");
        dbIpLabel.setBounds(10, 190, 100, 25);
        panel.add(dbIpLabel);

        ipField = new JTextField(20);
        ipField.setBounds(130, 190, 165, 25);
        ipField.setText(config[4]);
        panel.add(ipField);

        JLabel dbPortLabel = new JLabel("DB Port:");
        dbPortLabel.setBounds(10, 220, 80, 25);
        panel.add(dbPortLabel);

        portField = new JTextField(20);
        portField.setBounds(130, 220, 165, 25);
        portField.setText(config[5]);
        portField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
        panel.add(portField);
        
        JLabel dbNametLabel = new JLabel("DB Name");
        dbNametLabel.setBounds(10, 250, 80, 25);
        panel.add(dbNametLabel);
        
        dbNameField = new JTextField(20);
        dbNameField.setBounds(130, 250, 165, 25);
        dbNameField.setText(config[6]);
        panel.add(dbNameField);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.GREEN);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBounds(100, 290, 85, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	boolean test1=SecureAppConfiguration.saveAppCredentials(usernameField.getText(),passwordField.getText());
                boolean test2=SecureDatabaseConfiguration.saveDatabaseConfiguration(dbNameField.getText(),ipField.getText(), portField.getText(), dbUsernameField.getText(), dbPasswordField.getText());

                if(test2 && test1) {
                	JOptionPane.showMessageDialog(null, "Configuration saved successfully .", "INFORMATION_MESSAGE" ,
                			JOptionPane.INFORMATION_MESSAGE);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                    dispose();

                }else {
                	JOptionPane.showMessageDialog(null, "Configuration isn't saved successfully !", "ERROR_MESSAGE" ,
                			JOptionPane.ERROR_MESSAGE);
                }
            }


        });

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(250, 290, 85, 25);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        closeButton.setBackground(Color.BLUE);
        closeButton.setForeground(Color.WHITE); 
        
        panel.add(closeButton);
        panel.add(registerButton);
    }
}

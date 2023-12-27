package StuGraManag;
import Toaster.Toaster;
import Utils.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class LoginUI extends JFrame {


	private final Toaster toaster;
    private TextFieldPassword passwordField ;
    private TextFieldUsername usernameField;
    private final JPanel mainJPanel;
    

    public LoginUI() {
    	
        this.mainJPanel = getMainJPanel();
        
        ImageIcon icon = new ImageIcon(LoginUI.class.getResource(Constants.iconImg));
        Image image = icon.getImage();
        setIconImage(image);
        
        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addUsernameTextField(mainJPanel);

        addPasswordTextField(mainJPanel);

        addLoginButton(mainJPanel);

        addCloseButton(mainJPanel);

        this.add(mainJPanel);
        this.pack();
        this.setVisible(true);
        this.toFront();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);

        toaster = new Toaster(mainJPanel);
    }

    private JPanel getMainJPanel() {
        this.setUndecorated(true);

        Dimension size = new Dimension(800, 400);

        JPanel panel1 = new JPanel();
        panel1.setSize(size);
        panel1.setPreferredSize(size);
        panel1.setBackground(UIUtils.COLOR_BACKGROUND);
        panel1.setLayout(null);

        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;

            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(getLocationOnScreen().x + x - lastX, getLocationOnScreen().y + y - lastY);
                lastX = x;
                lastY = y;
            }
        };

        panel1.addMouseListener(ma);
        panel1.addMouseMotionListener(ma);



        return panel1;
    }

    private void addSeparator(JPanel panel1) {
        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setForeground(UIUtils.COLOR_OUTLINE);
        panel1.add(separator1);
        separator1.setBounds(310, 80, 1, 240);
    }

    private void addLogo(JPanel panel1) {
        JLabel label1 = new JLabel();
        label1.setFocusable(false);
        label1.setIcon(new ImageIcon(LoginUI.class.getResource(Constants.iconImg)));
        panel1.add(label1);
        label1.setBounds(55, 146, 200, 170);
    }

    private void addUsernameTextField(JPanel panel1) {
       usernameField = new TextFieldUsername();
       

        usernameField.setBounds(423, 109, 250, 44);
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(UIUtils.PLACEHOLDER_TEXT_USERNAME)) {
                    usernameField.setText("");
                }
                usernameField.setForeground(new  Color(0, 0, 0));
                usernameField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(UIUtils.PLACEHOLDER_TEXT_USERNAME);
                }
                usernameField.setForeground(UIUtils.COLOR_OUTLINE);
                usernameField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });

        panel1.add(usernameField);
    }

    private void addPasswordTextField(JPanel panel1) {
        passwordField = new TextFieldPassword();

        passwordField.setBounds(423, 168, 250, 44);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	 if (passwordField.getText().equals(UIUtils.PLACEHOLDER_TEXT_PASSWORD)) {
            		 passwordField.setText("");
                 }
                passwordField.setForeground(new  Color(0, 0, 0));
                passwordField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if (passwordField.getText().isEmpty()) {
            		passwordField.setText(UIUtils.PLACEHOLDER_TEXT_PASSWORD);
                }
                passwordField.setForeground(UIUtils.COLOR_OUTLINE);
                passwordField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                	loginEventHandler();
            }
        
        });

        panel1.add(passwordField);
    }

    private void addLoginButton(JPanel panel1) {
        final Color[] loginButtonColors = {UIUtils.COLOR_INTERACTIVE, Color.white};

        JLabel loginButton = new JLabel() {
            private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = UIUtils.get2dGraphics(g);
                super.paintComponent(g2);

                Insets insets = getInsets();
                int w = getWidth() - insets.left - insets.right;
                int h = getHeight() - insets.top - insets.bottom;
                g2.setColor(loginButtonColors[0]);
                g2.fillRoundRect(insets.left, insets.top, w, h, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);

                FontMetrics metrics = g2.getFontMetrics(UIUtils.FONT_GENERAL_UI);
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_TEXT_LOGIN)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(loginButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_TEXT_LOGIN, x2, y2);
            }
        };

        loginButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                loginEventHandler();
			            
            }
            @Override
            
            
            public void mouseEntered(MouseEvent e) {
                loginButtonColors[0] = UIUtils.COLOR_INTERACTIVE_DARKER;
                loginButtonColors[1] = UIUtils.OFFWHITE;
                loginButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButtonColors[0] = UIUtils.COLOR_INTERACTIVE;
                loginButtonColors[1] = Color.white;
                loginButton.repaint();
            }
        });

        loginButton.setBackground(UIUtils.COLOR_BACKGROUND);
        loginButton.setBounds(423, 247, 250, 44);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel1.add(loginButton);
    }

    private void addForgotPasswordButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_FORGOT_PASS, 423, 300, () -> {
            toaster.error("Forgot password event");
        }));
    }

    private void addCloseButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_CLOSE, 631, 300, () -> {
        	 toaster.info("Shutting down");
            
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	dispose(); 
                //OR System.exit(0); 
                }
            });
            
            timer.setRepeats(false);
            timer.start();
        }));
    }

    private void loginEventHandler() {
    	
    	String pswd=passwordField.getText();
		String us=usernameField.getText();
		if(pswd.equals("") || us.equals("")) {
			toaster.warn("Please insert Username and Password");
		}else {
		boolean check= checkUser(us,pswd);
		if(check){
			
			
			JPanel imagePanel = new JPanel() {
	           
	            private static final long serialVersionUID = 1L;

				protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                
	                ImageIcon im = new ImageIcon(LoginUI.class.getResource(Constants.iterfacImge));
	                Image image = im.getImage();
	                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	                
	            }
	        };
	        
	        
	        LoginUI.this.dispose();
			new Interface(imagePanel);
			JOptionPane.showMessageDialog(null, "Welcome to STUGRAMANAG");
			
			
			
		}
		else{
			toaster.error("Invalid credentials. Please try again.");
			passwordField.setText("");
			usernameField.setText("");
		}
	}		
    }

    
    
    public static boolean checkUser(String us,String pswd) {
		SecureAppConfiguration config_file = new SecureAppConfiguration();
        return us.equals(config_file.getAppUsername()) && pswd.equals(config_file.getAppPassword());
	
	}


}
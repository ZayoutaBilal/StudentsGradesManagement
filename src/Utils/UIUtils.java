package Utils;


import java.awt.*;
import java.util.HashMap;

public class UIUtils {
    public static final Font FONT_GENERAL_UI = new Font("Segoe UI", Font.PLAIN, 20);
    public static final Font FONT_FORGOT_PASSWORD = new Font("Segoe UI", Font.PLAIN, 12);

    public static final Color COLOR_OUTLINE = new Color(10, 10, 10);
    public static final Color COLOR_BACKGROUND = new Color(200, 200, 200);
    public static final Color COLOR_INTERACTIVE = new Color(20, 168, 225);
    public static final Color COLOR_INTERACTIVE_DARKER = new Color(13, 110, 148);
    public static final Color OFFWHITE = new Color(255, 255, 255);

    public static final String BUTTON_TEXT_LOGIN = "Login";
    public static final String BUTTON_TEXT_FORGOT_PASS = "Forgot your password?";
    public static final String BUTTON_TEXT_CLOSE = "Close";

    public static final String PLACEHOLDER_TEXT_USERNAME = "Username";
    public static final String PLACEHOLDER_TEXT_PASSWORD = "Password";

    public static final int ROUNDNESS = 8;

    public static Graphics2D get2dGraphics(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.addRenderingHints(new HashMap<RenderingHints.Key, Object>() {{
            put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        }});
        return g2;
    }
}
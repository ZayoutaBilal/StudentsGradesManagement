package Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class SecureAppConfiguration {

    private final Properties properties;

    public SecureAppConfiguration() {
        properties = new Properties();
        try {
            File configFile = new File(Constants.CONFIG_FILE_APP);
            if (configFile.exists()){
                properties.load(new FileReader(Constants.CONFIG_FILE_APP));
            }else{
                System.out.println("The config file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getAppUsername() {
        return properties.getProperty("appUsername", "");
    }

    public String getAppPassword() {
        String encryptedPassword = properties.getProperty("appPassword", "");
        return decrypt(encryptedPassword);
    }

    public boolean saveAppCredentials(String appUsername, String appPassword) {
        properties.setProperty("appUsername", appUsername);
        String encryptedPassword = encrypt(appPassword);
        properties.setProperty("appPassword", encryptedPassword);

        try {
            properties.store(new FileWriter(Constants.CONFIG_FILE_APP), null);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String encrypt(String value) {
        try {
            Cipher cipher = Cipher.getInstance(Constants.ENCRYPTION_ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(Constants.ENCRYPTION_KEY.getBytes(), Constants.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedValue = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String decrypt(String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance(Constants.ENCRYPTION_ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(Constants.ENCRYPTION_KEY.getBytes(), Constants.ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedValue = cipher.doFinal(decodedValue);
            return new String(decryptedValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

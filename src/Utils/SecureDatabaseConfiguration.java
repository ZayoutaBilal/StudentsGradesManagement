package Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class SecureDatabaseConfiguration {

    private final Properties properties;

    public SecureDatabaseConfiguration() {
        properties = new Properties();
        try {
            properties.load(new FileReader(Constants.CONFIG_FILE_DB));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public String getDatabaseName() {
        return properties.getProperty("dbName", "");
    }
    
    public String getDatabaseIP() {
        return properties.getProperty("dbIP", "");
    }

    public String getDatabaseUsername() {
        return properties.getProperty("dbUsername", "");
    }

    public String getDatabasePassword() {
        String encryptedPassword = properties.getProperty("dbPassword", "");
        return decrypt(encryptedPassword);
    }
    
    public String getDatabasePort() {
        return properties.getProperty("dbPort", "");
    }

    public boolean saveDatabaseConfiguration(String dbName,String dbIP,String dbPort, String dbUsername, String dbPassword) {
    	properties.setProperty("dbName", dbName);
        properties.setProperty("dbIP", dbIP);
        properties.setProperty("dbPort",dbPort);
        properties.setProperty("dbUsername", dbUsername);
        String encryptedPassword = encrypt(dbPassword);
        properties.setProperty("dbPassword", encryptedPassword);

        try {
            properties.store(new FileWriter(Constants.CONFIG_FILE_DB), null);
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

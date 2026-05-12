package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ConfigReader {
    private static ConfigReader instance;
    private static final Properties properties = new Properties();

    private ConfigReader() {
        // Step 1: Load TestData.properties first (non-sensitive data)
        try (FileInputStream fileInputStream = new FileInputStream(
                "src/test/resources/TestData.properties")) {
            properties.load(fileInputStream);
            System.out.println("✅ TestData.properties loaded successfully");
        } catch (IOException e) {
            System.out.println("❌ Failed to load TestData.properties: " + e.getMessage());
        }

        // Step 2: Load secret.properties on top (credentials)
        // This overrides any duplicate keys from TestData.properties
        try (FileInputStream secretInputStream = new FileInputStream(
                "src/test/resources/secret.properties")) {
            properties.load(secretInputStream);
            System.out.println("✅ secret.properties loaded successfully");
        } catch (IOException e) {
            // Warn but don't crash — teammate may not have set it up yet
            System.out.println("⚠️ secret.properties not found! " +
                    "Please copy secret.properties.template, " +
                    "rename it to secret.properties and fill in your credentials");
        }

    } catch (IOException e) {
        throw new RuntimeException(
                "Failed to load testdata.json. " +
                        "Ensure the file exists at src/test/resources/testdata.json", e);
       }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.out.println("⚠️ Property not found for key: " + key);
        }
        return value;
    }

    public static void main(String[] args) {
        ConfigReader configReader = ConfigReader.getInstance();
        String baseUrl = configReader.getProperty("orangeHrm.url");
        System.out.println("URL: " + baseUrl);
    }
}
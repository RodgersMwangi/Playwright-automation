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
    private final Map<String, String> configMap = new HashMap<>();


    private static final Set<String> ENCODED_KEYS = Set.of(
            "admin.password",
           " admin.newPassword",
            "invalid.current.password",
            "mismatch.new.password",
            "mismatch.confirm.password",
            "weak.password",
            "adduser.password"
            );

    private ConfigReader() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> rawMap = mapper.readValue(
                    new File("src/test/resources/testdata.json"),
                    new TypeReference<Map<String, String>>() {}
            );

            for (Map.Entry<String, String> entry : rawMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (ENCODED_KEYS.contains(key)  && value != null && !value.isEmpty() ){
                    configMap.put(key, new String(Base64.getDecoder().decode(value)));
                } else {
                    configMap.put(key, value != null ? value : "");
            }
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
        if (!configMap.containsKey(key)) {
            throw new IllegalArgumentException("Key not found in testdata.json: '" + key + "'");
        }
        return configMap.get(key);
    }

    public static void main(String[] args) {
    ConfigReader config = ConfigReader.getInstance();
    System.out.println ("URL   : " + config.getProperty("orangeHrm.url"));
    System.out.println ("Password : " + config.getProperty("admin.Password"));
        System.out.println("Empty  : '" + config.getProperty("empty.value") + "'");
      }

    }


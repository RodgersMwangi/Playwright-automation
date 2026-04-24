package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static ConfigReader instance;
    private static final Properties properties=new Properties();
    private static final String testdataFilePath="C:\\Users\\Rowl\\Documents\\Playwright\\PlaywrightJavaProject\\src\\test\\resources\\TestData.properties";

    private ConfigReader(){
        try(FileInputStream fileInputStream=new FileInputStream(testdataFilePath)){
            properties.load(fileInputStream);
        }catch(IOException e){
            System.out.println("Failed to load properties file");
        }
    }

    public static ConfigReader getInstance(){
        if(instance==null){
            synchronized (ConfigReader.class){
                if(instance==null){
                    instance=new ConfigReader();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        ConfigReader configReader=ConfigReader.getInstance();
        String baseurl=configReader.getProperty("orangeHrm.url");
        System.out.println(baseurl);
    }
}

package src.env;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    public static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/env/.env");
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }

}

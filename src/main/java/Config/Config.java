package Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Config config;
    private final String PATH = "settings.txt";
    private int port;
    private String ip;

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    private Config() {
        try {
            FileReader fr = new FileReader(PATH);
            Properties properties = new Properties();
            properties.load(fr);
            port = Integer.parseInt((String) properties.get("port"));
            ip = properties.getProperty("host");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

}



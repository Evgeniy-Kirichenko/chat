package Server;

import Config.Config;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void setLogger(String FILE_LOG) {
        try {
            FileHandler fileHandler = new FileHandler(FILE_LOG, true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String FILE_LOG = "log.txt";
        setLogger(FILE_LOG);
        Config config = Config.getInstance();
        Server server = new Server();
        server.startServer(config.getPort());

    }

}


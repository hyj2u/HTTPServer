package server1.server;

import server1.config.Configuration;
import server1.config.ConfigurationManager;
import server1.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// Driver class for the Http Server
public class HttpServer {
    private final  static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {
        LOGGER.info("server starting ...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfigutation();

        LOGGER.info("Using Port: "+ conf.getPort());
        LOGGER.info("Using WebRoot: "+conf.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            //exceptio handle
        }


    }
}

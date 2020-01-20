package server;

import config.Configuration;
import config.ConfigurationManager;

// Driver class for the Http Server
public class HttpServer {
    public static void main(String[] args) {
        System.out.println("server starting ...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfigutation();

        System.out.println("Using Port: "+ conf.getPort());
        System.out.println("Using WebRoot: "+conf.getWebroot());


    }
}

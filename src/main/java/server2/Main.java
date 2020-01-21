package server2;

import org.slf4j.LoggerFactory;
import server2.config.ServerConfiguration;
import server2.response.RequestRouter;
import server2.server.HttpServer;
import server2.util.RequestLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        ServerConfiguration serverConfiguration = new ServerConfiguration(); //default 8080, ""
        RequestLogger requestLogger = new RequestLogger();
        //RequestRouter
        RequestRouter requestRouter = new RequestRouter();
        try {
            ServerSocket serverSocket = new ServerSocket(serverConfiguration.getPort());
            HttpServer httpServer = new HttpServer(serverSocket,true, Executors.newFixedThreadPool(20),requestRouter,requestLogger );
            httpServer.start();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }
}

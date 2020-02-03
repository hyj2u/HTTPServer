package server2;

import org.slf4j.LoggerFactory;
import server2.config.ServerConfiguration;

import server2.handler.RequestRouter;
import server2.server.HttpServer;
import server2.server.ServerStatus;
import server2.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        ServerConfiguration serverConfiguration = new ServerConfiguration(); //default 8080, ""
        Logger requestLogger = new Logger();
        Logger responseLogger = new Logger();
        RequestRouter requestRouter = new RequestRouter("src/main/resources", requestLogger);
        try {
            ServerSocket serverSocket = new ServerSocket(serverConfiguration.getPort());
            HttpServer httpServer = new HttpServer(System.out, serverSocket,new ServerStatus(), Executors.newFixedThreadPool(200),requestRouter);
            httpServer.start();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }
}

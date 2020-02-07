import config.ServerConfiguration;
import handler.RequestRouter;
import org.slf4j.LoggerFactory;
import server.HttpServer;
import server.ServerStatus;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        ServerConfiguration serverConfiguration = new ServerConfiguration(8080, "src/main/resources");
        RequestRouter requestRouter = new RequestRouter(serverConfiguration.getPath());
        try {
            ServerSocket serverSocket = new ServerSocket(serverConfiguration.getPort());
            HttpServer httpServer = new HttpServer(System.out, serverSocket,new ServerStatus(), Executors.newFixedThreadPool(200),requestRouter);
            httpServer.start();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }
}

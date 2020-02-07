import config.ServerConfiguration;
import handler.RequestRouter;
import org.slf4j.LoggerFactory;
import server.HttpServer;
import server.ServerStatus;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

/**
 * Main class for run http server.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class Main {
    /**
     * Main method that runs a http server, and generate thread for connection.
     * Also configures server, and initialize RequestRouter.
     *
     * @throws IOException
     * @see ServerConfiguration
     * @see ServerConfiguration#getPath()
     * @see RequestRouter
     * @see ServerSocket
     * @see HttpServer
     * @see ServerStatus
     * @see Executors#newFixedThreadPool(int)
     * @see HttpServer#start()
     * @since 1.0
     */
    public static void main(String[] args) {

        ServerConfiguration serverConfiguration = new ServerConfiguration(8080, "src/main/resources");
        RequestRouter requestRouter = new RequestRouter(serverConfiguration.getPath());
        try {
            ServerSocket serverSocket = new ServerSocket(serverConfiguration.getPort());
            HttpServer httpServer = new HttpServer(System.out, serverSocket, new ServerStatus(), Executors.newFixedThreadPool(200), requestRouter);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

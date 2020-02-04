package server;

import handler.RequestRouter;
import org.slf4j.LoggerFactory;
import util.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;


public class HttpServer {

    private ServerSocket serverSocket;
    private ServerStatus serverStatus;
    private Executor executor;
    private RequestRouter requestRouter;

    private PrintStream printStream;

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public HttpServer(PrintStream printStream, ServerSocket serverSocket, ServerStatus serverStatus, Executor executor, RequestRouter requestRouter) {
        this.serverSocket = serverSocket;
        this.serverStatus = serverStatus;
        this.executor = executor;
        this.requestRouter = requestRouter;

        this.printStream= printStream;
    }

    public void start() {
        while (serverStatus.isRunning()) {
            try {
                Socket socket = serverSocket.accept();
                printStream.println("Request made");
                Logger requestLogger = new Logger();
                Logger responseLogger = new Logger();
                executor.execute(new ServerRunner(socket, new ConnectionManager(requestRouter, requestLogger, responseLogger)));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }

        }
    }

}

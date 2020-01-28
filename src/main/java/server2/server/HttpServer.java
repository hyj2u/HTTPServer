package server2.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server2.handler.RequestRouter;
import server2.util.RequestLogger;

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
    private RequestLogger requestLogger;
    private PrintStream printStream;

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public HttpServer(PrintStream printStream, ServerSocket serverSocket, ServerStatus serverStatus, Executor executor, RequestRouter requestRouter, RequestLogger requestLogger) {
        this.serverSocket = serverSocket;
        this.serverStatus = serverStatus;
        this.executor = executor;
        this.requestRouter = requestRouter;
        this.requestLogger = requestLogger;
        this.printStream= printStream;
    }

    public void start() {
        while (serverStatus.isRunning()) {
            try {
                Socket socket = serverSocket.accept();
                LOGGER.info("Connection made");
                executor.execute(new ServerRunner(socket, new ConnectionManager(requestRouter, requestLogger)));
            } catch (IOException e) {
                requestLogger.addLog("Socket error Occurred");
                LOGGER.error(e.getMessage());
            }

        }
    }

}

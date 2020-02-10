package server;

import handler.RequestRouter;
import org.slf4j.LoggerFactory;
import request.RequestParser;
import util.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

/**
 * This class sets Http Server setting.
 * Starts socket connection by executor.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class HttpServer {
    /**
     * Socket for server.
     */
    private ServerSocket serverSocket;
    /**
     * Socket status for server.
     */
    private ServerStatus serverStatus;
    /**
     * Executor that will run serverRunner.
     */
    private Executor executor;
    /**
     * Finds right handler for each request.
     */
    private RequestRouter requestRouter;
    /**
     * A way to print.
     */
    private PrintStream printStream;
    /**
     * This Constructor set printStream, serverSocket, serverStatus, executor and requestRouter.
     *
     * @param printStream a way to print
     * @param serverSocket socket for server
     * @param serverStatus socket status for server
     * @param executor executor that will run serverRunner
     * @param requestRouter finds right handler for each request
     * @see ServerStatus
     * @see RequestRouter
     * @since 1.0
     */
    public HttpServer(PrintStream printStream, ServerSocket serverSocket, ServerStatus serverStatus, Executor executor, RequestRouter requestRouter) {
        this.serverSocket = serverSocket;
        this.serverStatus = serverStatus;
        this.executor = executor;
        this.requestRouter = requestRouter;
        this.printStream = printStream;
    }
    /**
     * Starts socket while status is true.
     * Initialize request and response logger.
     * execute serverRunner at one connection.
     *
     * @see ServerStatus
     * @see Logger
     * @see ServerRunner
     * @see ConnectionManager
     * @see RequestRouter
     * @since 1.0
     */
    public void start() {
        while (serverStatus.isRunning()) {
            try {
                Socket socket = serverSocket.accept();
                printStream.println("Request made");
                Logger requestLogger = new Logger();
                Logger responseLogger = new Logger();
                executor.execute(new ServerRunner(socket, new ConnectionManager(requestRouter, requestLogger, responseLogger)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

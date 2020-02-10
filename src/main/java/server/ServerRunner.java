package server;

import handler.RequestRouter;
import util.Logger;

import java.io.IOException;
import java.net.Socket;
/**
 * This class runs connectionManager's response method.
 * It implements Runnable.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class ServerRunner implements Runnable {
    /**
     * Socket for server.
     */
    private Socket socket;
    /**
     * Bridge between request and response.
     */
    private ConnectionManager connectionManager;
    /**
     * This Constructor set socket and connectionManager.
     *
     * @param socket socket for server
     * @param connectionManager bridge between request and response.
     * @see ConnectionManager
     * @since 1.0
     */
    public ServerRunner(Socket socket, ConnectionManager connectionManager) {
        this.socket = socket;
        this.connectionManager = connectionManager;
    }
    /**
     * Runs connectionManager's respond to method.
     *
     * @throws IOException
     * @see ConnectionManager#respondTo(Socket)
     * @since 1.0
     */
    @Override
    public void run() {
        try {
            connectionManager.respondTo(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

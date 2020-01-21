package server2.server;

import java.net.Socket;

public class ServerRunner implements Runnable {
    private Socket socket;
    private ConnectionManager connectionManager;

    public ServerRunner(Socket socket, ConnectionManager connectionManager) {
        this.socket = socket;
        this.connectionManager = connectionManager;
    }

    @Override
    public void run() {
        //respondTo
    }
}

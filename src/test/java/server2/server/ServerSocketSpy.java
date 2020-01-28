package server2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ServerSocketSpy extends ServerSocket {
    private List<Socket> sockets;
    private int currentClient;
    private int timesAcceptCalled;
    private SocketException socketException;
    private boolean exceptionSet;

    public ServerSocketSpy(List<Socket> sockets) throws IOException {
        this.sockets =sockets;
        currentClient=0;
        timesAcceptCalled =0;
        socketException = new SocketException();
        exceptionSet = false;
    }

    @Override
    public Socket accept() throws IOException {
        Socket socket = sockets.get(currentClient);
        currentClient++;
        timesAcceptCalled++;
        if(exceptionSet){
            throw socketException;
        }
        return socket;
    }

    public int getTimesAcceptCalled() {
        return timesAcceptCalled;
    }

    public void setExceptionSetTrue() {
        exceptionSet = true;
    }
}

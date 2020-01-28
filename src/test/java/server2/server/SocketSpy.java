package server2.server;

import server2.util.IOHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketSpy extends Socket {
    private IOHelper ioHelper;
    private boolean closeWasCalled;

    public SocketSpy(String input){
        ioHelper = new IOHelper(input);
        closeWasCalled = false;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return ioHelper.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return ioHelper.getOutputStream();
    }
    public String getOutputAsString(){
        return ioHelper.getStringOutput();
    }

    @Override
    public synchronized void close() throws IOException {
       closeWasCalled = true;
       super.close();
    }

    public boolean isCloseWasCalled() {
        return closeWasCalled;
    }
}

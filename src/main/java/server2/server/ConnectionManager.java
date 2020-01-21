package server2.server;

import server2.request.Request;
import server2.request.RequestParser;
import server2.request.RequestReader;
import server2.response.RequestRouter;
import server2.util.RequestLogger;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ConnectionManager {
    private RequestParser requestParser;
    //responseWriter

    private RequestRouter requestRouter;
    private RequestLogger requestLogger;

    public ConnectionManager(RequestRouter requestRouter, RequestLogger requestLogger) {
        this.requestRouter = requestRouter;
        this.requestLogger = requestLogger;
        this.requestParser = new RequestParser();
        //responseWriter initialize
    }

    private Request getRequest(Socket socket) throws IOException {
        return requestParser.parse(socket.getInputStream());
    }
    private void writeToLog(Request request){
        requestLogger.addLog(request.getRequestLine());
    }

    //respondTo
    //getResponse
    //getRequest

}

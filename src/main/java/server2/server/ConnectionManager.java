package server2.server;

import server2.handler.RequestRouter;
import server2.request.Request;
import server2.request.RequestParser;
import server2.response.Response;
import server2.util.RequestLogger;
import java.io.IOException;
import java.net.Socket;


public class ConnectionManager {
    private RequestParser requestParser;
    private ResponseWriter responseWriter;
    private RequestRouter requestRouter;
    private RequestLogger requestLogger;

    public ConnectionManager(RequestRouter requestRouter, RequestLogger requestLogger) {
        this.requestRouter = requestRouter;
        this.requestLogger = requestLogger;
        this.requestParser = new RequestParser();
        responseWriter = new ResponseWriter();
    }


    private void writeToLog(Request request) {
        requestLogger.addLog(request.getRequestLine());
    }


    private Request getRequest(Socket socket) throws IOException {
        return requestParser.parse(socket.getInputStream());
    }
    private Response getResponse(Socket socket) throws IOException {
        Request request = getRequest(socket);
        writeToLog(request);
        return requestRouter.handle(request);
    }
    public void respondTo(Socket socket) throws IOException {
        responseWriter.write(getResponse(socket), socket.getOutputStream());
        socket.close();
    }


}

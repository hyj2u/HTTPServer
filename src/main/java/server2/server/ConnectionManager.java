package server2.server;

import server2.handler.RequestRouter;
import server2.request.Request;
import server2.request.RequestParser;
import server2.response.Response;
import server2.util.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class ConnectionManager {
    private RequestParser requestParser;
    private ResponseWriter responseWriter;
    private RequestRouter requestRouter;
    private Logger requestLogger;
    private Logger responseLogger;

    public ConnectionManager(RequestRouter requestRouter, Logger requestLogger, Logger responseLogger) {
        this.requestRouter = requestRouter;
        this.requestLogger = requestLogger;
        this.requestParser = new RequestParser();
        this.responseLogger = responseLogger;
        responseWriter = new ResponseWriter();
    }


    private void writeToLog(Request request) {
        requestLogger.addLog(request.getRequestLine());
    }
    private void writeToLog(Response response){
        responseLogger.addLog(response.getResponseLine());
    }



    private Request getRequest(Socket socket) throws IOException {
        InputStream clientIn = socket.getInputStream();
        return requestParser.parse(clientIn);
    }
    private Response getResponse(Socket socket) throws IOException {
        Request request = getRequest(socket);
        writeToLog(request);
        System.out.println(requestLogger.getLogsBody());
        requestLogger.clearLog();
        return requestRouter.handle(request);
    }
    public void respondTo(Socket socket) throws IOException {
        Response response =getResponse(socket);
        writeToLog(response);
        System.out.println(responseLogger.getLogsBody());
        responseLogger.clearLog();
        responseWriter.write(response, socket.getOutputStream());
        System.out.println("socket close");
        socket.close();
    }


}

package server;

import handler.RequestRouter;
import request.Request;
import request.RequestParser;
import response.Response;
import util.FileContentConverter;
import util.Logger;
import util.ResourceTypeIdentifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This class is a bridge between request and response.
 * Write request and response log to print at console.
 * Writes response to socket.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class ConnectionManager {
    /**
     * Makes object request with client's input.
     */
    private RequestParser requestParser;
    /**
     * Makes object response for client output.
     */
    private ResponseWriter responseWriter;
    /**
     * Finds right handler for each request.
     */
    private RequestRouter requestRouter;
    /**
     * Save request message as List to print at console.
     */
    private Logger requestLogger;
    /**
     * Save response message as List to print at console.
     */
    private Logger responseLogger;
    /**
     * This Constructor set request router, request logger and response logger.
     * Initialize requestParser and responseWriter.
     *
     * @param requestRouter finds right handler
     * @param requestLogger saves request message
     * @param responseLogger saves response message
     * @see RequestRouter
     * @see Logger
     * @see RequestParser
     * @see ResponseWriter
     * @since 1.0
     */
    public ConnectionManager(RequestRouter requestRouter, Logger requestLogger, Logger responseLogger) {
        this.requestRouter = requestRouter;
        this.requestLogger = requestLogger;
        this.requestParser = new RequestParser();
        this.responseLogger = responseLogger;
        responseWriter = new ResponseWriter();
    }
    /**
     * Adds request message to request logger list.
     *
     * @param request the object that made with client input at socket
     * @see Request
     * @see Request#getRequestLine()
     * @see Logger#addLog(String)
     * @since 1.0
     */
    private void writeToLog(Request request) {
        requestLogger.addLog(request.getRequestLine());
    }
    /**
     * Adds response message to response logger list.
     *
     * @param response the object that will be wrote to socket
     * @see Response
     * @see Response#getResponseLine() 
     * @see Logger#addLog(String)
     * @since 1.0
     */
    private void writeToLog(Response response) {
        responseLogger.addLog(response.getResponseLine());
    }
    /**
     * Returns request object after refine client input.
     *
     * @param socket client input
     * @return Request
     * @see RequestParser#parse(InputStream)
     * @since 1.0
     */
    private Request getRequest(Socket socket) throws IOException {
        InputStream clientIn = socket.getInputStream();
        return requestParser.parse(clientIn);
    }
    /**
     * Returns response object which is result of handled request.
     *
     * @param socket client input
     * @return Response
     * @see Request
     * @see #getRequest(Socket) 
     * @see #writeToLog(Request) 
     * @see RequestRouter#handle(Request) 
     * @since 1.0
     */
    private Response getResponse(Socket socket) throws IOException {
        Request request = getRequest(socket);
        writeToLog(request);
        System.out.println(requestLogger.getLogsBody());
        return requestRouter.handle(request);
    }
    /**
     * Writes response to client socket.
     *
     * @param socket client input/output
     * @see Response
     * @see #getResponse(Socket) 
     * @see ResponseWriter#write(Response, OutputStream)
     * @since 1.0
     */
    public void respondTo(Socket socket) throws IOException {
        Response response = getResponse(socket);
        writeToLog(response);
        System.out.println(responseLogger.getLogsBody());
        responseWriter.write(response, socket.getOutputStream());
        System.out.println("socket close");
        socket.close();
    }


}

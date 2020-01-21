package server2.server;

import server2.response.RequestRouter;
import server2.util.RequestLogger;

import java.util.logging.Logger;

public class ConnectionManager {
    //requestParser
    //responseWriter

    private RequestRouter requestRouter;
    private RequestLogger requestLogger;

    public ConnectionManager(RequestRouter requestRouter, RequestLogger requestLogger) {
        this.requestRouter = requestRouter;
        this.requestLogger = requestLogger;
        //requestParser 생성
        //responseWriter 생성
    }
    //respondTo
    //getRespinse
    //getRequest
    //writeToRequestLog
}

package server2.server;

import org.junit.Test;
import server2.handler.RequestRouter;
import server2.util.RequestLogger;

import java.io.IOException;

import static org.junit.Assert.*;

public class ConnectionManagerTest {
    private final String requestInput ="GET /testFile.txt HTTP/1.1";
    private final String rootPath ="src/test/resources";
    private final RequestLogger requestLogger = new RequestLogger();
    private final RequestRouter requestRouter = new RequestRouter(rootPath, requestLogger);
    private final ConnectionManager connectionManager = new ConnectionManager(requestRouter, requestLogger);
    private final SocketSpy socketSpy = new SocketSpy(requestInput);

    @Test
    public void respondTo() throws IOException {
        connectionManager.respondTo(socketSpy);
        assertFalse(socketSpy.getOutputAsString().isEmpty());
        assertFalse(requestLogger.getLogsBody().isEmpty());
        assertTrue(socketSpy.isCloseWasCalled());
    }
}
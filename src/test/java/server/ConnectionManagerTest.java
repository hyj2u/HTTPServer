package server;

import org.junit.Test;
import handler.RequestRouter;
import util.Logger;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConnectionManagerTest {
    private final String requestInput ="GET /testFile.txt HTTP/1.1";
    private final String rootPath ="src/test/resources";
    private final Logger requestLogger = new Logger();
    private final Logger responseLogger = new Logger();
    private final RequestRouter requestRouter = new RequestRouter(rootPath);
    private final ConnectionManager connectionManager = new ConnectionManager(requestRouter, requestLogger,responseLogger);
    private final SocketSpy socketSpy = new SocketSpy(requestInput);

    @Test
    public void respondTo() throws IOException {
        connectionManager.respondTo(socketSpy);
        assertFalse(socketSpy.getOutputAsString().isEmpty());
        assertFalse(requestLogger.getLogsBody().isEmpty());
        assertTrue(socketSpy.isCloseWasCalled());
    }
}
package server2.server;

import org.junit.Test;
import server2.handler.RequestRouter;
import server2.util.IOHelper;
import server2.util.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HttpServerTest {
    private final IOHelper ioHelper = new IOHelper("");
    private final PrintStream printStream = ioHelper.getPrintStream();
    private final Logger requestLogger = new Logger();
    private final Logger responseLogger = new Logger();
    private final RequestRouter requestRouter = new RequestRouter("src/test/resources", requestLogger);
    private final ExecutorWithNoThreads executorWithNoThreads = new ExecutorWithNoThreads();

    @Test
    public void twoRequestRespondTo() throws IOException {
        SocketSpy client1 = new SocketSpy("GET /testFile.txt");
        SocketSpy client2 = new SocketSpy("GET /testFile.txt");
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(Arrays.asList(client1, client2));
        ServerStatusSpy serverStatusSpy = new ServerStatusSpy(2);
        HttpServer httpServer = new HttpServer(printStream, serverSocketSpy, serverStatusSpy, executorWithNoThreads, requestRouter, requestLogger,responseLogger );
        httpServer.start();
        assertEquals(2, serverSocketSpy.getTimesAcceptCalled());
        assertEquals(2, executorWithNoThreads.getAmountExecutedWasCalled());
    }
    @Test
    public void nullSocketConnection() throws IOException {
        ServerStatusSpy serverStatusSpy = new ServerStatusSpy(1);
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(Arrays.asList(new SocketSpy("")));
        serverSocketSpy.setExceptionSetTrue();
        HttpServer httpServer = new HttpServer(printStream, serverSocketSpy, serverStatusSpy,executorWithNoThreads, requestRouter, requestLogger, responseLogger);
        httpServer.start();
        assertEquals("Socket error Occurred\n", requestLogger.getLogsBody());
    }

}
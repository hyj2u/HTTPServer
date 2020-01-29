package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.RequestLogger;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestRouterTest {
    private final String testRootPath ="src/test/resources";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody="";
    private final String testFilePath ="/testFile.txt";

    @Test
    public void requestCanHandles() throws IOException {
        Request request = new Request(HttpVerb.GET, testFilePath,emptyHeaders, emptyBody);
        RequestRouter requestRouter = new RequestRouter(testRootPath, new RequestLogger());
        Response response = requestRouter.handle(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
    }
    @Test
    public void requestForNoHandler() throws IOException {
        Request request = new Request(HttpVerb.NOTRECOGNIZED, testFilePath, emptyHeaders, emptyBody);
        RequestRouter requestRouter = new RequestRouter(testRootPath, new RequestLogger());
        Response response = requestRouter.handle(request);
        assertEquals(ResponseStatus.METHODNOTALLOWED, response.getResponseStatus());
    }

}
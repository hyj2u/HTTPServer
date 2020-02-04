package handler;

import org.junit.Test;
import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;
import util.Logger;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RequestRouterTest {
    private final String testRootPath ="src/test/resources";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody="";
    private final String testFilePath ="/testFile.txt";

    @Test
    public void requestCanHandles() throws IOException {
        Request request = new Request(HttpVerb.GET, testFilePath,emptyHeaders, emptyBody);
        RequestRouter requestRouter = new RequestRouter(testRootPath);
        Response response = requestRouter.handle(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
    }
    @Test
    public void requestForNoHandler() throws IOException {
        Request request = new Request(HttpVerb.NOTRECOGNIZED, testFilePath, emptyHeaders, emptyBody);
        RequestRouter requestRouter = new RequestRouter(testRootPath);
        Response response = requestRouter.handle(request);
        assertEquals(ResponseStatus.METHODNOTALLOWED, response.getResponseStatus());
    }

}
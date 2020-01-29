package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseHeader;
import server2.response.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class OptionsHandlerTest {
    private final HashMap<String, String > emptyHeaders = new HashMap<>();
    private final String emptyBody ="";
    private final OptionsHandler optionsHandler = new OptionsHandler();

    @Test
    public void requestForNonLogFile() throws IOException {
        String filePath ="/testFile.txt";
        Request request = new Request(HttpVerb.OPTIONS, filePath, emptyHeaders, emptyBody);
        Response response = optionsHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(HttpVerb.getAllowedMethods().getBytes(), response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
    @Test
    public void requestForLogFile() throws IOException {
        String logsPath ="/logs";
        Request request = new Request(HttpVerb.OPTIONS, logsPath, emptyHeaders, emptyBody);
        Response response = optionsHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(HttpVerb.getAllowedForLogMethods().getBytes(), response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }

}
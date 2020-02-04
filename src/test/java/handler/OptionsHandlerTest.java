package handler;

import org.junit.Test;
import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseHeader;
import response.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OptionsHandlerTest {
    private final HashMap<String, String > emptyHeaders = new HashMap<>();
    private final String emptyBody ="";
    private final OptionsHandler optionsHandler = new OptionsHandler();

    @Test
    public void requestForNonLogFile()  {
        String filePath ="/testFile.txt";
        Request request = new Request(HttpVerb.OPTIONS, filePath, emptyHeaders, emptyBody);
        Response response = optionsHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(HttpVerb.getAllowedMethods().getBytes(), response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
    @Test
    public void requestForLogFile() {
        String logsPath ="/logs";
        Request request = new Request(HttpVerb.OPTIONS, logsPath, emptyHeaders, emptyBody);
        Response response = optionsHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(HttpVerb.getAllowedForLogMethods().getBytes(), response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }

}
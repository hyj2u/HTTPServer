package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseHeader;
import server2.response.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CookieHandlerTest {
    private final String eatCookiePath ="/eat_cookie";
    private final Map<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody ="";
    private final CookieHandler cookieHandler = new CookieHandler();

    @Test
    public void reqeustWithSetCookiePath() throws IOException {
        String cookieParameterPath = "/cookie?type=mint";
        Request request = new Request(HttpVerb.GET, cookieParameterPath, emptyHeaders, emptyBody);
        Response response = cookieHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals("mint".getBytes(), response.getHeaders().get(ResponseHeader.COOKIE));
        assertArrayEquals("Eat".getBytes(), response.getBodyContent());
    }
    @Test
    public void requestWithoutCookieHeader() throws IOException {
        Request request = new Request(HttpVerb.GET, eatCookiePath, emptyHeaders, emptyBody);
        Response response =cookieHandler.getResponse(request);
        assertEquals(ResponseStatus.NOTFOUND, response.getResponseStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBodyAsByte(), response.getBodyContent());
    }
    @Test
    public void requestForCookieSetCookieResponse() throws IOException {
        HashMap<String, String> cookieLemonHeader = new HashMap<>();
        String  cookieFlavour ="lemon";
        cookieLemonHeader.put("Cookie", cookieFlavour);
        Request request = new Request(HttpVerb.GET, eatCookiePath, cookieLemonHeader, emptyBody);
        Response response =cookieHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(("mmmm"+cookieFlavour).getBytes(), response.getBodyContent());
    }

}
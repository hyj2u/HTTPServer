package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;

import java.io.IOException;

public class CookieHandler extends Handler {

    public CookieHandler() {
        addHandledVerb(HttpVerb.GET);
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        return null;
    }
}

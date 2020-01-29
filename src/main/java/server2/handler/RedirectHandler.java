package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.IOException;

public class RedirectHandler extends Handler {

    public RedirectHandler() {
        addHandledVerb(HttpVerb.GET);
        addHandledPathSegment("redirect");
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        Response response = new Response();
        response.setLocationHeader("/");
        response.setBodyContent(ResponseStatus.NOTMODIFIED.getStatusBodyAsByte());
        response.setResponseStatus(ResponseStatus.NOTMODIFIED);
        return response;
    }
}

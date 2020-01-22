package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.util.ResourceTypeIdentifier;

public class GetHandler extends Handler{
    private String roothPath;
    private ResourceTypeIdentifier resourceTypeIdentifier;


    @Override
    public Response getResponse(Request request) {
        return null;
    }

    @Override
    public void addHandledVerb(HttpVerb httpVerb) {
        super.addHandledVerb(httpVerb);
    }

    @Override
    public void addHandledPathSegment(String pathSegment) {
        super.addHandledPathSegment(pathSegment);
    }

    @Override
    public boolean isHandledVerb(Request request) {
        return super.isHandledVerb(request);
    }

    @Override
    public boolean canHandles(Request request) {
        return super.canHandles(request);
    }
}

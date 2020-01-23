package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Handler {
    private final ArrayList<HttpVerb> handledVerbs = new ArrayList<>();
    private final ArrayList<String> handledPathSegments = new ArrayList<>();

    public abstract Response getResponse(Request request) throws IOException;

    public void addHandledVerb(HttpVerb httpVerb){
        handledVerbs.add(httpVerb);
    }
    public void addHandledPathSegment(String pathSegment){
        handledPathSegments.add(pathSegment);
    }
    public boolean isHandledVerb(Request request){
        return handledVerbs.contains(request.getHttpVerb());
    }
    private boolean isHandledPathSegment(Request request){
        for(String pathSegment : handledPathSegments){
            if(containsPathSegment(request, pathSegment)){
                return true;
            }
        }
        return false;
    }
    public boolean canHandles(Request request){
        return isHandledVerb(request) && isHandledPathSegment(request);
    }
    private boolean containsPathSegment(Request request, String pathSegment){
        return request.getResourcePath().contains(pathSegment);
    }

}

package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;

public class HeadHandler extends Handler {

    private String rootPath;
    private ResourceTypeIdentifier resourceTypeIdentifier;

    public HeadHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.HEAD);
        resourceTypeIdentifier = new ResourceTypeIdentifier();
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }
    private Response setResourceNotFoundResponse(){
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.NOTFOUND);
        return response;
    }
    private Response getHeadResponse(File resource){
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.OK);
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        return response;
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        Response response;
        File resource = new File(rootPath+request.getResourcePath());
        if(!resource.exists()){
            response = setResourceNotFoundResponse();
        }else{
            response =getHeadResponse(resource);
        }
        response.clearBody();
        return response;
    }
}

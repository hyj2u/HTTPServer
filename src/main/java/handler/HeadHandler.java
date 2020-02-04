package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;
import util.ResourceTypeIdentifier;

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
    public Response getResponse(Request request)  {
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

package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;
import util.ContentType;
import util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
/**
 * HeadHandler class generates response for request that has content-type header and head method.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class HeadHandler extends Handler {
    /**
     * Resource path that client requests.
     */
    private String rootPath;
    /**
     * Class that defines the request's content-type.
     */
    private ResourceTypeIdentifier resourceTypeIdentifier;
    /**
     * This Constructor sets resource path and adds HttpVerb.HEAD to handledVerbs.
     * Initialize resourceTypeIdentifier to find content-type.
     *
     * @param rootPath resource path that client requests
     * @see HttpVerb
     * @see Handler#addHandledVerb(HttpVerb)
     * @see ResourceTypeIdentifier
     * @since 1.0
     */
    public HeadHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.HEAD);
        resourceTypeIdentifier = new ResourceTypeIdentifier();
    }
    /**
     * Override method that checks handledPathSegments to set true.
     *
     * @param request the object that made with client input at socket
     * @return <code>true</code>
     * @see Request
     * @since 1.0
     */
    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }
    /**
     * Makes response which is 404 Not Found.
     *
     * @see ResponseStatus
     * @see Response#setResponseStatus(ResponseStatus)
     * @since 1.0
     */
    private Response setResourceNotFoundResponse(){
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.NOTFOUND);
        return response;
    }
    /**
     * Returns response that has content-type header with resource's type of request.
     *
     * @param resource
     * @return Response
     * @see Response
     * @see File
     * @see ResponseStatus
     * @see Response#setResponseStatus(ResponseStatus)
     * @see Response#setContentTypeHeader(ContentType)
     * @see ResourceTypeIdentifier#getType(File)
     * @since 1.0
     */
    private Response getHeadResponse(File resource){
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.OK);
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        return response;
    }
    /**
     * If resource does not exist, send 404 response.
     * Otherwise, make head response.
     *
     * @param request the object that made with client input at socket
     * @return if resource file exists, make head response, else send 404 response.
     * @see Request
     * @see Response
     * @see File
     * @see Request#getResourcePath()
     * @see File#exists()
     * @see #setResourceNotFoundResponse()
     * @see #getHeadResponse(File)
     * @see Response#clearBody()
     * @since 1.0
     */
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

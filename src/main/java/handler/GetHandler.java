package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;
import util.ContentType;
import util.FileContentConverter;
import util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * GetHandler class generates response for request that has get method.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class GetHandler extends Handler {
    /**
     * Resource path that client requests.
     */
    private String rootPath;
    /**
     * Class that defines the request's content-type.
     */
    private ResourceTypeIdentifier resourceTypeIdentifier;
    /**
     * Class that reads resource file.
     */
    private FileContentConverter fileContentConverter;
    /**
     * Class that check request's range.
     */
    private RangeResponder rangeResponder;

    /**
     * This Constructor sets resource path and adds HttpVerb.GET to handledVerbs.
     * Initialize resourceTypeIdentifier to find content-type.
     * Initialize fileContentConverter to read file content.
     * Initialize rangeResponder to check range header.
     *
     * @param rootPath resource path that client requests
     * @see HttpVerb
     * @see Handler#addHandledVerb(HttpVerb)
     * @see ResourceTypeIdentifier
     * @see FileContentConverter
     * @see RangeResponder
     * @since 1.0
     */
    public GetHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.GET);
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
        rangeResponder = new RangeResponder(rootPath, fileContentConverter, resourceTypeIdentifier);
    }

    /**
     * Checks the file that client request exists.
     *
     * @param request the object that made with client input at socket
     * @return <code>true</code> if file does not exists
     * <code>false</code> otherwise;
     * @see Request
     * @see Request#getResourcePath()
     * @see Paths#get(URI)
     * @see Files#exists(Path, LinkOption...)
     * @since 1.0
     */
    private boolean resourceDoesNotExist(Request request) {
        return !(Files.exists(Paths.get(rootPath + request.getResourcePath())));
    }

    /**
     * Makes response which is 404 Not Found.
     *
     * @see Response
     * @see ResponseStatus
     * @see Response#setResponseStatus(ResponseStatus)
     * @see ResponseStatus#getStatusBodyAsByte()
     * @since 1.0
     */
    private Response notFoundResponse() {
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBodyAsByte());
        return response;
    }

    /**
     * Check request header has "Range".
     *
     * @param request the object that made with client input at socket
     * @return <code>true</code> if request has Range header;
     * <code>false</code> otherwise;
     * @see Request
     * @see Request#getHeaders()
     * @see java.util.ArrayList#contains(Object)
     * @since 1.0
     */
    private boolean checkRange(Request request) {
        return request.getHeaders().containsKey("Range");
    }

    /**
     * Returns response for get request.
     *
     * @param request the object that made with client input at socket
     * @return object response for client output
     * @throws IOException
     * @see Request
     * @see Response
     * @see File
     * @see Request#getResourcePath()
     * @see ResourceTypeIdentifier#getType(File)
     * @see FileContentConverter#getFullContents(File)
     * @see Response#setContentTypeHeader(ContentType)
     * @see Response#setResponseStatus(ResponseStatus)
     * @see Response#setContentLengthHeader(String)
     * @since 1.0
     */
    private Response fullGet(Request request) throws IOException {
        Response response = new Response();
        File resource = new File(rootPath + request.getResourcePath());
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        response.setBodyContent(fileContentConverter.getFullContents(resource));
        response.setResponseStatus(ResponseStatus.OK);
        response.setContentLengthHeader(fileContentConverter.getFullContents(resource).length + "");
        return response;
    }

    /**
     * If request has Range header, this method calls rangeResponder to check range.
     * Otherwise, call full get to generate response.
     *
     * @param request the object that made with client input at socket
     * @return if request has range header, use rangeResponder class to check range, else make get response.
     * @throws IOException
     * @see Request
     * @see Response
     * @see RangeResponder#doRange(Request)
     * @since 1.0
     */
    private Response doGet(Request request) throws IOException {
        if (checkRange(request)) {
            return rangeResponder.doRange(request);
        } else {
            return fullGet(request);
        }
    }
    /**
     * Returns response for get request.
     * If resource that client requests do not exists, send 404 respond.
     * Other wise, make get response.
     *
     * @param request the object that made with client input at socket
     * @return object response for client output
     * @see Request
     * @see Response
     * @see #resourceDoesNotExist(Request)
     * @see #notFoundResponse()
     * @see #doGet(Request)
     * @since 1.0
     */
    @Override
    public Response getResponse(Request request) throws IOException {
        if (resourceDoesNotExist(request)) {
            return notFoundResponse();
        } else {
            return doGet(request);
        }
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
}

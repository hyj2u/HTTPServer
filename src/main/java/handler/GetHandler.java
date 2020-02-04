package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;
import util.FileContentConverter;
import util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetHandler extends Handler {
    private String rootPath;
    private ResourceTypeIdentifier resourceTypeIdentifier;
    private FileContentConverter fileContentConverter;
    private RangeResponder rangeResponder;

    public GetHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.GET);
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
        rangeResponder = new RangeResponder(rootPath, fileContentConverter, resourceTypeIdentifier);
    }

    private boolean resourceDoesNotExist(Request request) {
        return !(Files.exists(Paths.get(rootPath + request.getResourcePath()))); 
    }

    private Response notFoundResponse() {
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBodyAsByte());
        return response;
    }

    private boolean checkRange(Request request) {
        return request.getHeaders().containsKey("Range");
    }

    private Response fullGet(Request request) throws IOException {
        Response response = new Response();
        File resource = new File(rootPath + request.getResourcePath());
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        response.setBodyContent(fileContentConverter.getFullContents(resource));
        response.setResponseStatus(ResponseStatus.OK);
        return response;
    }


    private Response doGet(Request request) throws IOException {
        if (checkRange(request)) {
            return rangeResponder.doRange(request);
        } else {
            return fullGet(request);
        }
    }


    @Override
    public Response getResponse(Request request) throws IOException {
        if (resourceDoesNotExist(request)) {
            return notFoundResponse();
        } else {
           return doGet(request);
        }
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }
}

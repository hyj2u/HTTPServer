package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.FileContentConverter;
import server2.util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetHandler extends Handler {
    private String roothPath;
    private ResourceTypeIdentifier resourceTypeIdentifier;
    private FileContentConverter fileContentConverter;
    private RangeResponder rangeResponder;

    public GetHandler(String roothPath) {
        this.roothPath = roothPath;
        addHandledVerb(HttpVerb.GET);
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
        rangeResponder = new RangeResponder(roothPath, fileContentConverter, resourceTypeIdentifier);
    }

    private boolean resourceDoesNotExist(Request request) {
        if (Files.exists(Paths.get(roothPath + request.getResourcePath()))) {
            return false;
        } else {
            return true;
        }
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
        File resource = new File(roothPath + request.getResourcePath());
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
    public boolean canHandles(Request request) {
        return true;
    }
}

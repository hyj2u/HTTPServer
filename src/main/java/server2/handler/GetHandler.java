package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.FileContentConverter;
import server2.util.ResourceTypeIdentifier;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GetHandler extends Handler{
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
    private boolean resourceDoesNotExist(Request request){
        if(Files.exists(Paths.get(roothPath+request.getResourcePath()))){
            return false;
        }else{
            return true;
        }
    }
    private Response notFoundResponse(){
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBodyAsByte());
        return response;
    }


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
        return true;
    }
}

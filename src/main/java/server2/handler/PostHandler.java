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

public class PostHandler extends Handler {

    private Request request;
    private Response response;
    private String rootPath;

    public PostHandler() {
        addHandledVerb(HttpVerb.POST);
    }

   @Override
   public boolean isHandledPathSegment(Request request) {
       return true;
   }


    private void doPost() {
        response.setBodyContent(request.getBodyContent().getBytes());
        response.setResponseStatus(ResponseStatus.OK);
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        this.request = request;
        response = new Response();
        doPost();
        return response;
    }




}

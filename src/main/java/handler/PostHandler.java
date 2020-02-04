package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;

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
    public Response getResponse(Request request)  {
        this.request = request;
        response = new Response();
        doPost();
        return response;
    }




}

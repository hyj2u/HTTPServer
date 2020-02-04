package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;

import java.io.File;
import java.io.IOException;

public class OptionsHandler extends Handler {

    public OptionsHandler() {
        addHandledVerb(HttpVerb.OPTIONS);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        File resource  = new File(request.getResourcePath());
        String allowedMethods;
        if(resource.getName().toLowerCase().contains("logs")){
            allowedMethods = HttpVerb.getAllowedForLogMethods();
        }else{
            allowedMethods = HttpVerb.getAllowedMethods();
        }
        response.setAllowHeader(allowedMethods);
        response.setResponseStatus(ResponseStatus.OK);
        return response;
    }
}

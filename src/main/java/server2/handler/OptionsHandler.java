package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.File;
import java.io.IOException;

public class OptionsHandler extends Handler{

    public OptionsHandler() {
        addHandledVerb(HttpVerb.OPTIONS);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    @Override
    public Response getResponse(Request request) throws IOException {
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

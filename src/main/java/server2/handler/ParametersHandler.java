package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ParametersHandler extends Handler {
    public ParametersHandler() {
        addHandledVerb(HttpVerb.GET);
        addHandledPathSegment("parameter");
    }

    private String removeFirstPart(String fullURI){
        int paramStart = fullURI.indexOf("?");
        return fullURI.substring(paramStart+1);
    }
    private String decode(String fullParams){
        String decoded =null;
        try {
            decoded = URLDecoder.decode(fullParams, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decoded;
    }
    private String getAllParams(String fullURI){
        String paramNoFirstPart= removeFirstPart(fullURI);
        String[] parameters = paramNoFirstPart.split("&");
        StringBuilder bodyContent = new StringBuilder();
        for(String param : parameters){
            String[] splitVariable = param.split("=");
            bodyContent.append(decode(splitVariable[0]));
            bodyContent.append(" = ");
            bodyContent.append(decode(splitVariable[1]));
            bodyContent.append("\n");
        }
        return new String(bodyContent);
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        Response response = new Response();
        String body =getAllParams(request.getResourcePath());
        response.setBodyContent(body.getBytes());
        response.setResponseStatus(ResponseStatus.OK);
        return response;
    }
}

package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.IOException;

public class CookieHandler extends Handler {

    public CookieHandler() {
        addHandledVerb(HttpVerb.GET);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return (request.getResourcePath().toLowerCase().contains("cookie"));
    }
    private String getCookieType(String fullURI){
        int paramStart = fullURI.indexOf("?");
        String fullCookie = fullURI.substring(paramStart+1);
        return fullCookie.split("=")[1];
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        Response response = new Response();
        if(request.getResourcePath().contains("eat")&& request.getHeaders().get("Cookie")!=null){
            response.setResponseStatus(ResponseStatus.OK);
            response.setBodyContent(("mmmm"+request.getHeaders().get("Cookie")).getBytes());
        }else if(request.getResourcePath().contains("eat")){
            response.setResponseStatus(ResponseStatus.NOTFOUND);
            response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBodyAsByte());
        }else {
            String cookieType = getCookieType(request.getResourcePath());
            response.setResponseStatus(ResponseStatus.OK);
            response.setCookieHeader(cookieType);
            response.setBodyContent("Eat".getBytes());
        }
        return response;
    }
}

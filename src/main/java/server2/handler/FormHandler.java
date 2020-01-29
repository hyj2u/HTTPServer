package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormHandler extends Handler {
    private Response response;
    private Request request;
    private Map<String, String> formFileds;
    private String rootPath;

    public FormHandler(String rootPath) {
        this.rootPath = rootPath;
        formFileds = new HashMap<>();
        addHandledVerb(HttpVerb.GET);
        addHandledVerb(HttpVerb.POST);
        addHandledPathSegment("form");
    }
    private Response getResponseToGet(){
        File resource = new File(rootPath+request.getResourcePath());
        String keyRequest = resource.getName();
        if(!formFileds.containsKey(keyRequest)){
            response.setResponseStatus(ResponseStatus.NOTFOUND);
            response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBodyAsByte());
        }else{
            response.setResponseStatus(ResponseStatus.OK);
            response.setBodyContent((keyRequest+"="+formFileds.get(keyRequest)).getBytes());
        }
        return response;
    }
    private Response getResponseToPost(){
        String[] keyValuePair = request.getBodyContent().split("=");
        String keyOfData = keyValuePair[0];
        String valueOfData = keyValuePair[1];
        formFileds.put(keyOfData, valueOfData);
        String formName = request.getResourcePath().split("/")[1];
        response.setLocationHeader("/"+formName+"/"+keyOfData);
        response.setResponseStatus(ResponseStatus.CREATED);
        return response;
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        response = new Response();
        this.request  =request;
        HttpVerb httpVerb = request.getHttpVerb();
        if(httpVerb ==HttpVerb.GET){
            response = getResponseToGet();
        }else if(httpVerb ==HttpVerb.POST){
            response= getResponseToPost();
        }
        return response;
    }
}

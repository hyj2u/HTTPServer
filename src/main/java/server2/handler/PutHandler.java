package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PutHandler extends Handler{

    private String rootPath;
    private Request request;
    private Response response;

    public PutHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.PUT);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    private void writeFile(){
        try {
            Files.write(Paths.get(rootPath+request.getResourcePath()), request.getBodyContent().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void doCreateResponse(){
        writeFile();
        response.setResponseStatus(ResponseStatus.CREATED);
    }
    private void doUpdateResponse(){
        writeFile();
        response.setResponseStatus(ResponseStatus.OK);
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        this.request = request;
        response = new Response();
        if(Files.exists(Paths.get(rootPath+request.getResourcePath()))){
            doUpdateResponse();
        }else{
            doCreateResponse();
        }
        return response;
    }
}

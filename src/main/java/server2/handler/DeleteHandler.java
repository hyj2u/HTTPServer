package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteHandler extends Handler {

    private String rootPath;
    private Request request;
    private Response response;

    public DeleteHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.DELETE);
    }


    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    private void deleteResource(){
        try {
            Files.delete(Paths.get(rootPath+request.getResourcePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setResponseStatus(ResponseStatus.OK);
    }
    private void setNotFound(){
        response.setResponseStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBodyAsByte());
    }

    @Override
    public Response getResponse(Request request) throws IOException {
       this.request = request;
       response = new Response();
       if(Files.exists(Paths.get(rootPath+request.getResourcePath()))){
           deleteResource();
       }else{
           setNotFound();
       }
       return response;
    }
}

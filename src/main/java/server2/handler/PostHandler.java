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

    public PostHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.POST);
    }


    private void doPost() {
        try {
            //post handle later
            Files.write(Paths.get(Paths.get(rootPath) + request.getResourcePath()), request.getBodyContent().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

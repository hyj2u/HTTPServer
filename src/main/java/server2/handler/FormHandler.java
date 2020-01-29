package server2.handler;

import server2.request.Request;
import server2.response.Response;

import java.io.IOException;
import java.util.Map;

public class FormHandler extends Handler {
    private Response response;
    private Request request;
    private Map<String, String> formFileds;
    private String rootPath;

    @Override
    public Response getResponse(Request request) throws IOException {
        return null;
    }
}

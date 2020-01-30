package server2.handler;

import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestRouter {

    private final ArrayList<Handler> handlers = new ArrayList<>();

    private void addHandler(Handler handler) {
        handlers.add(handler);
    }

    private void addHandlers(List<Handler> handlers) {
        for (Handler handler : handlers) {
            addHandler(handler);
        }
    }

    public RequestRouter(String rootPath, Logger requestLogger) {
        addHandlers(Arrays.asList(
               new CookieHandler(),
                new FormHandler(rootPath),
                new RedirectHandler(),
                new ParametersHandler(),
                new DeleteHandler(rootPath),
                new GetHandler(rootPath),
                new PostHandler(),
                new HeadHandler(rootPath),
                new OptionsHandler(),
                new PatchHandler(rootPath),
                new PutHandler(rootPath)
        ));
    }

    private Response methodNotAllowedResponse() {
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.METHODNOTALLOWED);
        return response;
    }

    public Response handle(Request request) throws IOException {
        for (Handler handler : handlers) {
            if (handler.canHandles(request)) {
                return handler.getResponse(request);
            }
        }
        return methodNotAllowedResponse();
    }


}

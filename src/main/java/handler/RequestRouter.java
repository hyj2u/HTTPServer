package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * RequestRouter class find right handler for each request.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class RequestRouter {
    /**
     * List that contain handler's child classes.
     *
     * @see #addHandler(Handler)
     */
    private final ArrayList<Handler> handlers = new ArrayList<>();
    /**
     * Add handler class to handlers.
     *
     * @param handler
     * @see Handler
     * @since 1.0
     */
    private void addHandler(Handler handler) {
        handlers.add(handler);
    }

    /**
     * Add list which have handlers to handlers field.
     *
     * @param handlers list that contain handler's child classes
     * @see Handler
     * @see #addHandler(Handler)
     * @since 1.0
     */
    private void addHandlers(List<Handler> handlers) {
        for (Handler handler : handlers) {
            addHandler(handler);
        }
    }
    /**
     * This Constructor sets resource path and add handlers to handlers list.
     *
     * @param rootPath resource path that client request
     * @see #addHandlers(List)
     * @since 1.0
     */
    public RequestRouter(String rootPath) {
        addHandlers(Arrays.asList(
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
    /**
     * Sends Not Allowed Response
     *
     * @return Response response which status is Method not Allowed
     * @see Response
     * @see Response#setResponseStatus(ResponseStatus)
     * @see ResponseStatus
     * @since 1.0
     */
    private Response methodNotAllowedResponse() {
        Response response = new Response();
        response.setResponseStatus(ResponseStatus.METHODNOTALLOWED);
        return response;
    }
    /**
     * Find right handler and send request to it.
     *
     * @param request
     * @return Response
     * @throws IOException when request has null
     * @see Response
     * @see Handler
     * @see Handler#canHandles(Request)
     * @see Handler#getResponse(Request)
     * @since 1.0
     */
    public Response handle(Request request) throws IOException {
        for (Handler handler : handlers) {
            if (handler.canHandles(request)) {
                return handler.getResponse(request);
            }
        }
        return methodNotAllowedResponse();
    }


}

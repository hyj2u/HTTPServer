package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * PatchHandler class generates response for request that has post method.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class PostHandler extends Handler {
    /**
     * This Constructor adds HttpVerb.POST to handledVerbs.
     *
     * @see HttpVerb
     * @see Handler#addHandledVerb(HttpVerb)
     * @since 1.0
     */
    public PostHandler() {
        addHandledVerb(HttpVerb.POST);
    }
    /**
     * Override method that checks handledPathSegments to set true.
     *
     * @param request the object that made with client input at socket
     * @return <code>true</code>
     * @see Request
     * @since 1.0
     */
    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    /**
     * Returns response for post request.
     * Set response body content as a request body content.
     *
     * @param request the object that made with client input at socket
     * @return object response for client output
     * @see Request
     * @see Response
     * @see Response#setBodyContent(byte[])
     * @see Request#getBodyContent()
     * @see String#getBytes()
     * @see Response#setResponseStatus(ResponseStatus)
     * @see ResponseStatus
     * @since 1.0
     */
    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        response.setBodyContent(request.getBodyContent().getBytes());
        response.setResponseStatus(ResponseStatus.OK);
        return response;
    }


}

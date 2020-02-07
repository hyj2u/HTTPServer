package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * DeleteHandler class generates response for request that has delete method.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class DeleteHandler extends Handler {
    /**
     * Resource path that client requests.
     */
    private String rootPath;
    /**
     * The object that made with client input at socket.
     */
    private Request request;
    /**
     * The object response for client output.
     */
    private Response response;

    /**
     * This Constructor sets resource path and adds HttpVerb.Delete to handledVerbs.
     *
     * @param rootPath resource path that client requests
     * @see HttpVerb
     * @see Handler#addHandledVerb(HttpVerb)
     * @since 1.0
     */
    public DeleteHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.DELETE);
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
     * Deletes the file that client requests.
     * If delete process succeed, set response status 200 OK.
     * Otherwise, print error message.
     *
     * @exception IOException
     * @see Request#getResourcePath()
     * @see Paths#get(URI)
     * @see Files#delete(Path)
     * @see ResponseStatus
     * @see Response#setResponseStatus(ResponseStatus)
     * @since 1.0
     */
    private void deleteResource() {
        try {
            Files.delete(Paths.get(rootPath + request.getResourcePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setResponseStatus(ResponseStatus.OK);
    }
    /**
     * Makes response which is 404 Not Found.
     *
     * @see ResponseStatus
     * @see Response#setResponseStatus(ResponseStatus)
     * @see ResponseStatus#getStatusBodyAsByte()
     * @since 1.0
     */
    private void setNotFound() {
        response.setResponseStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBodyAsByte());
    }
    /**
     * Returns response for delete request.
     *
     * @param request the object that made with client input at socket
     * @return object response for client output
     * @see Request
     * @see Response
     * @see #deleteResource()
     * @see #setNotFound()
     * @since 1.0
     */
    @Override
    public Response getResponse(Request request) {
        this.request = request;
        response = new Response();
        if (Files.exists(Paths.get(rootPath + request.getResourcePath()))) {
            deleteResource();
        } else {
            setNotFound();
        }
        return response;
    }
}

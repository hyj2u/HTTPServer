package handler;

import request.HttpVerb;
import request.Request;
import response.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Handler is abstract base class for all request handlers which help to generate appropriate response for each request.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public abstract class Handler {
    /**
     * List for HttpVerbs that can be handled
     *
     * @see #addHandledVerb(HttpVerb) 
     * @see #isHandledVerb(Request) 
     */
    private final ArrayList<HttpVerb> handledVerbs = new ArrayList<>();
    /**
     * List for path segments that can be handled
     *
     * @see #addHandledPathSegment(String) 
     * @see #isHandledPathSegment(Request)
     */
    private final ArrayList<String> handledPathSegments = new ArrayList<>();

    /**
     * Returns response for request.
     *
     * @param request the object that made with client input at socket
     * @return object response for client output
     * @throws IOException
     * @see Request
     * @see Response
     * @since 1.0
     */
    public abstract Response getResponse(Request request) throws IOException;

    /**
     * Adds httpVerb to ArrayList handledVerbs.
     *
     * @param httpVerb the object that made with client input at socket
     * @see HttpVerb
     * @since 1.0
     */
    public void addHandledVerb(HttpVerb httpVerb) {
        handledVerbs.add(httpVerb);
    }

    /**
     * Adds pathSegment to ArrayList handledPathSegments.
     *
     * @param pathSegment URL path for find right handler
     * @since 1.0
     */
    public void addHandledPathSegment(String pathSegment) {
        handledPathSegments.add(pathSegment);
    }

    /**
     * Checks handledVerbs if there is httpVerb for request.
     *
     * @param request the object that made with client input at socket
     * @return <code>true</code> if handledVerbs contains httpVerb of request;
     * <code>false</code> otherwise
     * @see Request#getHttpVerb()
     * @since 1.0
     */
    public boolean isHandledVerb(Request request) {
        return handledVerbs.contains(request.getHttpVerb());
    }

    /**
     * Checks URL of request that has path segment.
     *
     * @param request     the object that made with client input at socket
     * @param pathSegment URL path for find right handler
     * @return <code>true</code> if resource path contains pathSegment of request;
     * <code>false</code> otherwise
     * @see Request
     * @see Request#getResourcePath()
     * @since 1.0
     */
    private boolean containsPathSegment(Request request, String pathSegment) {
        return request.getResourcePath().contains(pathSegment);
    }

    /**
     * Checks handledPathSegments if there is pathSegment for request.
     *
     * @param request the object that made with client input at socket
     * @return <code>true</code> if handledPathSegment contains pathSegment of request;
     * <code>false</code> otherwise
     * @see Request
     * @see #containsPathSegment(Request, String)
     * @since 1.0
     */
    public boolean isHandledPathSegment(Request request) {
        for (String pathSegment : handledPathSegments) {
            if (containsPathSegment(request, pathSegment)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check both isHandledVerb and isHandledPathSegment are true.
     *
     * @param request the object that made with client input at socket
     * @return <code>true</code> if isHandledVerb return value and isHandledPathSegment return value are both true;
     * <code>false</code> otherwise
     * @see Request
     * @see #isHandledVerb(Request) 
     * @see #isHandledPathSegment(Request) 
     * @since 1.0
     */
    public boolean canHandles(Request request) {
        return isHandledVerb(request) && isHandledPathSegment(request);
    }
    
}


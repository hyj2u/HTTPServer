package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;
import util.ContentType;
import util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
/**
 * OptionsHandler class generates response for request that has allowed method header and options method.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class OptionsHandler extends Handler {
    /**
     * This Constructor sd adds HttpVerb.OPTIONS to handledVerbs.
     *
     * @see HttpVerb
     * @see Handler#addHandledVerb(HttpVerb)
     * @since 1.0
     */
    public OptionsHandler() {
        addHandledVerb(HttpVerb.OPTIONS);
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
     * Returns response that contains header allowed methods.
     * This header informs what methods do we provide.
     *
     * @param request the object that made with client input at socket
     * @return Response
     * @see Response
     * @see File
     * @see Request#getResourcePath()
     * @see File#getName()
     * @see String#toLowerCase()
     * @see String#contains(CharSequence)
     * @see ResponseStatus
     * @see Response#setResponseStatus(ResponseStatus)
     * @see Response#setAllowHeader(String)
     * @since 1.0
     */
    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        File resource  = new File(request.getResourcePath());
        String allowedMethods;
        if(resource.getName().toLowerCase().contains("logs")){
            allowedMethods = HttpVerb.getAllowedForLogMethods();
        }else{
            allowedMethods = HttpVerb.getAllowedMethods();
        }
        response.setAllowHeader(allowedMethods);
        response.setResponseStatus(ResponseStatus.OK);
        return response;
    }
}

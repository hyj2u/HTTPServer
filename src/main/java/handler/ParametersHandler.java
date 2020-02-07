package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ParametersHandler class generates response for request that has parameters by get method.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class ParametersHandler extends Handler {
    /**
     * This Constructor add HttpVerb.GET to handledVerbs and ? to handledPathSegments.
     *
     * @see HttpVerb
     * @see Handler#addHandledVerb(HttpVerb)
     * @see Handler#addHandledPathSegment(String) 
     * @since 1.0
     */
    public ParametersHandler() {
        addHandledVerb(HttpVerb.GET);
        addHandledPathSegment("?");
    }
    /**
     * Returns parameters part from URI.
     *
     * @param fullURI
     * @return parameter part
     * @see String#indexOf(String) 
     * @see String#substring(int)
     * @since 1.0
     */
    private String removeFirstPart(String fullURI){
        int paramStart = fullURI.indexOf("?");
        return fullURI.substring(paramStart+1);
    }
    /**
     * Returns parameters encoded by UTF-8.
     *
     * @param fullParams parameter part of URL
     * @return encoded parameters
     * @exception UnsupportedEncodingException when String cannot be encoded as UTF-8
     * @see URLDecoder#decode(String, String)
     * @since 1.0
     */
    private String decode(String fullParams){
        String decoded =null;
        try {
            decoded = URLDecoder.decode(fullParams, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decoded;
    }
    /**
     *Parse params to key=value form, and return it.
     *
     * @param fullURI
     * @return params as a body content
     * @see #removeFirstPart(String) 
     * @see String#split(String) 
     * @see StringBuilder
     * @see StringBuilder#append(String)
     * @since 1.0
     */
    private String getAllParams(String fullURI){
        String paramNoFirstPart= removeFirstPart(fullURI);
        String[] parameters = paramNoFirstPart.split("&");
        StringBuilder bodyContent = new StringBuilder();
        for(String param : parameters){
            String[] splitVariable = param.split("=");
            bodyContent.append(decode(splitVariable[0]));
            bodyContent.append(" = ");
            bodyContent.append(decode(splitVariable[1]));
            bodyContent.append("\n");
        }
        return new String(bodyContent);
    }
    /**
     * Set params to response's body content, and returns it.
     *
     * @param request the object that made with client input at socket
     * @return Response
     * @see Request
     * @see Response
     * @see #getAllParams(String) 
     * @see Request#getResourcePath()
     * @see String#getBytes() 
     * @see Response#setResponseStatus(ResponseStatus) 
     * @see ResponseStatus
     * @see Response#setContentLengthHeader(String) 
     * @since 1.0
     */
    @Override
    public Response getResponse(Request request)  {
        Response response = new Response();
        String body =getAllParams(request.getResourcePath());
        response.setBodyContent(body.getBytes());
        response.setResponseStatus(ResponseStatus.OK);
        response.setContentLengthHeader(body.length()+"");
        return response;
    }
}

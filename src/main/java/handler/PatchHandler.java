package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;
import util.FileContentConverter;
import util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * PatchHandler class generates response for request that has patch method.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class PatchHandler extends Handler {
    /**
     * Resource path that client requests.
     */
    private String rootPath;
    /**
     * Class that reads resource file.
     */
    private FileContentConverter fileContentConverter;
    /**
     * Object that has information of client input.
     */
    private Request request;
    /**
     * This Constructor sets resource path and adds HttpVerb.PATCH to handledVerbs.
     * Initialize fileContentConverter to read file content.
     *
     * @param rootPath resource path that client requests
     * @see HttpVerb
     * @see Handler#addHandledVerb(HttpVerb)
     * @since 1.0
     */
    public PatchHandler(String rootPath) {
        this.rootPath = rootPath;
        fileContentConverter = new FileContentConverter();
        addHandledVerb(HttpVerb.PATCH);
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
     * Make code for Etag.
     *
     * @param fileURI
     * @return Etag
     * @exception NoSuchAlgorithmException when SHA-1 is not supported
     * @deprecated
     * @see FileContentConverter
     * @since 1.0
     */
    private String createSHA1(String fileURI){
        fileContentConverter = new FileContentConverter();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.reset();
        File shaFile = new File(fileURI);
        try {
            digest.update(fileContentConverter.getFullContents(shaFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, digest.digest()).toString(16);
    }
    /**
     * Returns if request has Etag and it is right.
     *
     * @param shaActual Etag
     * @return <code>true</code> If request does not have header named If-Match, or contents of If-Match header do nor equal Etag;
     * <code>false</code>Otherwise
     * @see Request#getHeaders()
     * @since 1.0
     */
    private boolean notCorrectETAG(String shaActual){
        if(!request.getHeaders().containsKey("If-Match")||!request.getHeaders().get("If-Match").equals("qwer1234")){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Returns response for patch request.
     * If request does not have correct Etag, send precondition failed respond.
     * Otherwise, make and send no content respond.
     *
     * @param request the object that made with client input at socket
     * @return object response for client output
     * @see #createSHA1(String)
     * @see ResponseStatus
     * @see Response#setResponseStatus(ResponseStatus)
     * @see Response#setEtagHeader(String)
     * @see Request#getResourcePath()
     * @see Request#getBodyContent()
     * @see Response#setContentLengthHeader(String)
     * @since 1.0
     */
    @Override
    public Response getResponse(Request request) throws IOException {
        Response response = new Response();
        this.request = request;
        String shaActual = createSHA1(rootPath+request.getResourcePath());
        if(notCorrectETAG(shaActual)){
            response.setResponseStatus(ResponseStatus.PRECONDITIONFAILED);
            response.setEtagHeader("qwer1234");
        }else{
            Files.write(Paths.get(rootPath+request.getResourcePath()), request.getBodyContent().getBytes());
            response.setResponseStatus(ResponseStatus.NOCONTENT);
            response.setContentLengthHeader(request.getBodyContent().length()+"");
        }
        return response;

    }
}

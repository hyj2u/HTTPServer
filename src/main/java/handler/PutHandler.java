package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

/**
 * PutHandler class generates response for request that has put method.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class PutHandler extends Handler {
    /**
     * Resource path that client requests.
     */
    private String rootPath;
    /**
     * Object that has information of client input.
     */
    private Request request;
    /**
     * This Constructor sets resource path and adds HttpVerb.PUT to handledVerbs.
     *
     * @param rootPath resource path that client requests
     * @see HttpVerb
     * @see Handler#addHandledVerb(HttpVerb)
     * @since 1.0
     */
    public PutHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HttpVerb.PUT);
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
     * makes file with request's contents at resource path.
     *
     * @exception IOException when path is not correct
     * @see Files#write(Path, byte[], OpenOption...) 
     * @see Request#getResourcePath() 
     * @see Request#getBodyContent() 
     * @see String#getBytes() 
     * @since 1.0
     */
    private void writeFile(){
        try {
            Files.write(Paths.get(rootPath+request.getResourcePath()), request.getBodyContent().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * If making file succeeds, send 201 response.
     *
     * @exception IOException when path is not correct
     * @see Files#write(Path, byte[], OpenOption...)
     * @see Request#getResourcePath()
     * @see Request#getBodyContent()
     * @see String#getBytes()
     * @since 1.0
     */
    private void doCreateResponse(Response response){
        writeFile();
        response.setResponseStatus(ResponseStatus.CREATED);
    }
    /**
     * If file exists, updates file and sends 200 response.
     *
     * @see #writeFile() 
     * @see Response#setResponseStatus(ResponseStatus) 
     * @see ResponseStatus
     * @since 1.0
     */
    private void doUpdateResponse(Response response){
        writeFile();
        response.setResponseStatus(ResponseStatus.OK);
    }
    /**
     * Returns response for put request.
     * If resource file exists, do update procedure, else do create procedure.
     *
     * @param request the object that made with client input at socket
     * @return object response for client output
     * @see Response
     * @see Response#setContentLengthHeader(String) 
     * @see Request#getBodyContent() 
     * @see String#length() 
     * @see Files#exists(Path, LinkOption...) 
     * @see Paths#get(URI) 
     * @see Request#getResourcePath() 
     * @see #doCreateResponse(Response) 
     * @see #doUpdateResponse(Response) 
     * @since 1.0
     */
    @Override
    public Response getResponse(Request request) {
        this.request = request;
        Response response = new Response();
        response.setContentLengthHeader(request.getBodyContent().length()+"");
        if(Files.exists(Paths.get(rootPath+request.getResourcePath()))){
            doUpdateResponse(response);
        }else{
            doCreateResponse(response);
        }
        return response;
    }
}

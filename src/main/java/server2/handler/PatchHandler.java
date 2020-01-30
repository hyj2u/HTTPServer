package server2.handler;

import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.FileContentConverter;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PatchHandler extends Handler {
    private String rootPath;
    private FileContentConverter fileContentConverter;
    private Request request;

    public PatchHandler(String rootPath) {
        this.rootPath = rootPath;
        fileContentConverter = new FileContentConverter();
        addHandledVerb(HttpVerb.PATCH);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    private String createSHA1(String fileURI){
        fileContentConverter = new FileContentConverter();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.reset();;
        File shaFile = new File(fileURI);
        try {
            digest.update(fileContentConverter.getFullContents(shaFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, digest.digest()).toString(16);
    }
    private boolean notCorrectETAG(String shaActual){
        if(!request.getHeaders().containsKey("If-Match")||!request.getHeaders().get("If-Match").equals("qwer1234")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        Response response = new Response();
        this.request = request;
        String shaActual = createSHA1(rootPath+request.getResourcePath());
        if(notCorrectETAG(shaActual)){
            response.setResponseStatus(ResponseStatus.PRECONDITIONFAILED);
        }else{
            Files.write(Paths.get(rootPath+request.getResourcePath()), request.getBodyContent().getBytes());
            response.setResponseStatus(ResponseStatus.NOCONTENT);
        }
        return response;

    }
}

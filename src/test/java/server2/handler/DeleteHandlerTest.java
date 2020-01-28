package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class DeleteHandlerTest {
    private final String rootPath ="src/test/resources";
    private final String emptyBody="";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final DeleteHandler deleteHandler = new DeleteHandler(rootPath);

    private void createFileAt(String path) throws IOException {
        if(!Files.exists(Paths.get(path))){
            File newFile = new File(path);
            newFile.createNewFile();
        }
    }
    @Test
    public void deleteRequestForExistingFile() throws IOException {
        String fileToDeletePath ="fileToDelete.html";
        createFileAt(rootPath+fileToDeletePath);
        Request request = new Request(HttpVerb.DELETE, fileToDeletePath, emptyHeaders, emptyBody);
        Response response= deleteHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertFalse(Files.exists(Paths.get(rootPath+fileToDeletePath)));
    }
    @Test
    public void  deleteRequestForNonExistentFile() throws IOException {
        String noFilePath ="/noFile.html";
        Request request = new Request(HttpVerb.DELETE, noFilePath, emptyHeaders, emptyBody);
        Response response = deleteHandler.getResponse(request);
        assertEquals(ResponseStatus.NOTFOUND, response.getResponseStatus());
    }
}
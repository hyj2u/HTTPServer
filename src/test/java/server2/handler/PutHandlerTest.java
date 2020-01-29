package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PutHandlerTest {
    private final String testRootPath ="src/test/resources";
    private final String resourcePath ="/newFile.txt";
    private final String fullTestPath = testRootPath+resourcePath;
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final PutHandler putHandler = new PutHandler(testRootPath);

    private void deleteTestFileIfExists() throws IOException {
        if(Files.exists(Paths.get(fullTestPath))){
            Files.delete(Paths.get(fullTestPath));
        }
    }
    @Test
    public void putRequestForNonExistentFile() throws IOException {
        deleteTestFileIfExists();
        String bodyContent ="first text";
        Request request = new Request(HttpVerb.PUT, resourcePath, emptyHeaders, bodyContent);
        Response response = putHandler.getResponse(request);
        assertEquals(ResponseStatus.CREATED, response.getResponseStatus());
        assertTrue(Files.exists(Paths.get(fullTestPath)));
        assertArrayEquals(bodyContent.getBytes(), Files.readAllBytes(Paths.get(fullTestPath)));
    }
    private void overwriteDataToFile(byte[] content, String path) throws IOException {
        Files.write(Paths.get(path), content);
    }

    @Test
    public void putRequestForUpdateFile() throws IOException {
        overwriteDataToFile("put put".getBytes(), fullTestPath);
        String updatedContents ="updated text";
        Request request = new Request(HttpVerb.PUT, resourcePath, emptyHeaders, updatedContents);
        Response response = putHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(updatedContents.getBytes(), Files.readAllBytes(Paths.get(fullTestPath)));
    }

}
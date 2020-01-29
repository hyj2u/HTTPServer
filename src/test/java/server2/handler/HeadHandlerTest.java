package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseHeader;
import server2.response.ResponseStatus;
import server2.util.ContentType;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class HeadHandlerTest {
    private final static String testRootPath ="src/test/resources";
    private final static HashMap<String , String> emptyHeaders = new HashMap<>();
    private final static String emptyBody ="";

    @Test
    public void getResponseExistingFile() throws IOException {
        String resourcePath = "/testFile.txt";
        Request request = new Request(HttpVerb.HEAD, resourcePath, emptyHeaders, emptyBody);
        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(ContentType.TXT.getValueAsByte(), response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
    @Test
    public void getResponseExistingDir() throws IOException {
        String resourcePath ="/testDir/";
        Request request = new Request(HttpVerb.HEAD, resourcePath, emptyHeaders, emptyBody);
        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(ContentType.HTML.getValueAsByte(), response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
    @Test
    public void  getResponseNotFoundFile() throws IOException {
        String resourcePath ="/noFile.txt";
        Request request = new Request(HttpVerb.HEAD, resourcePath, emptyHeaders, emptyBody);
        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);
        assertEquals(ResponseStatus.NOTFOUND, response.getResponseStatus());
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }

}
package handler;

import org.junit.Test;
import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseHeader;
import response.ResponseStatus;
import util.ContentType;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HeadHandlerTest {
    private final static String testRootPath ="src/test/resources";
    private final static HashMap<String , String> emptyHeaders = new HashMap<>();
    private final static String emptyBody ="";

    @Test
    public void getResponseExistingFile()  {
        String resourcePath = "/testFile.txt";
        Request request = new Request(HttpVerb.HEAD, resourcePath, emptyHeaders, emptyBody);
        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(ContentType.TXT.getValueAsByte(), response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
    @Test
    public void getResponseExistingDir() {
        String resourcePath ="/testDir/";
        Request request = new Request(HttpVerb.HEAD, resourcePath, emptyHeaders, emptyBody);
        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(ContentType.HTML.getValueAsByte(), response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
    @Test
    public void  getResponseNotFoundFile() {
        String resourcePath ="/noFile.txt";
        Request request = new Request(HttpVerb.HEAD, resourcePath, emptyHeaders, emptyBody);
        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);
        assertEquals(ResponseStatus.NOTFOUND, response.getResponseStatus());
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }

}
package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseHeader;
import server2.response.ResponseStatus;
import server2.util.ContentType;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GetHandlerTest {
    private final String testFileRoot = "src/test/resources";
    private final HashMap<String , String> emptyHeaders = new HashMap<>();
    private final String emptyBody ="";
    private final GetHandler getHandler = new GetHandler(testFileRoot);

    @Test
    public void getRequestForOKResponse() throws IOException {
        String filePath="/testFile.txt";
        Request request = new Request(HttpVerb.GET, filePath, emptyHeaders, emptyBody);
        Response response = getHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(ContentType.TXT.getValueAsByte(), response.getHeaders().get(ResponseHeader.CONTENTTYPE));
       assertArrayEquals("file contents\r\n".getBytes(), response.getBodyContent());
    }
    @Test
    public void getRequestForNotFoundResponse() throws IOException {
        String nonExixtingFilePath = "/no-file.txt";
        Request request = new Request(HttpVerb.GET, nonExixtingFilePath, emptyHeaders, emptyBody);
        Response response =getHandler.getResponse(request);
        assertEquals(ResponseStatus.NOTFOUND, response.getResponseStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBodyAsByte(), response.getBodyContent());
    }

}
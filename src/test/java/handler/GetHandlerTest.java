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
        String nonExistingFilePath = "/no-file.txt";
        Request request = new Request(HttpVerb.GET, nonExistingFilePath, emptyHeaders, emptyBody);
        Response response =getHandler.getResponse(request);
        assertEquals(ResponseStatus.NOTFOUND, response.getResponseStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBodyAsByte(), response.getBodyContent());
    }

}
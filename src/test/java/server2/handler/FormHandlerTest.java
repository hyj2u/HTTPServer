package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseHeader;
import server2.response.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class FormHandlerTest {
    private final String testRootPath ="src/test/resources";
    private final String formPath = "/www-form/data";
    private final HashMap<String , String> emptyHeaders = new HashMap<>();
    private final String emptyBody="";
    private final String blackCatData = "data=blackcat";
    private final String yellowCatData ="data=yellowcat";
    private final FormHandler formHandler = new FormHandler(testRootPath);

    @Test
    public void getRequestFromWithoutData() throws IOException {
        Request getRequest = new Request(HttpVerb.GET, formPath, emptyHeaders, emptyBody);
        Response getResponse = formHandler.getResponse(getRequest);
        assertEquals(ResponseStatus.NOTFOUND, getResponse.getResponseStatus());
    }
    @Test
    public void requestFormSetOK() throws IOException {
       Request postRequest = new Request(HttpVerb.POST, formPath, emptyHeaders, blackCatData);
        Response postResponse = formHandler.getResponse(postRequest);
        assertEquals(ResponseStatus.CREATED, postResponse.getResponseStatus());
        assertArrayEquals(formPath.getBytes(), postResponse.getHeaders().get(ResponseHeader.LOCATION));

        Request getRequest = new Request(HttpVerb.GET, formPath, emptyHeaders, emptyBody);
        Response getResponse = formHandler.getResponse(getRequest);
        assertEquals(ResponseStatus.OK, getResponse.getResponseStatus());
        assertArrayEquals(blackCatData.getBytes(), getResponse.getBodyContent());
    }

}
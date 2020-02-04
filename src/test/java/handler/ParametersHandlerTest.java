package handler;

import org.junit.Test;
import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParametersHandlerTest {
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody ="";
    private final ParametersHandler parametersHandler = new ParametersHandler();

    @Test
    public void getResponse() throws IOException {
        String parametersPath = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20"
                + "that%20all%22%3F&variable_2=stuff";
        Request request = new Request(HttpVerb.GET, parametersPath, emptyHeaders, emptyBody);
        Response response =parametersHandler.getResponse(request);
        assertEquals(ResponseStatus.OK, response.getResponseStatus());
        assertArrayEquals(("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?\nvariable_2 = stuff\n").getBytes(), response.getBodyContent());
    }
}
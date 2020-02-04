package server;

import org.junit.Test;
import response.Response;
import response.ResponseStatus;
import util.ContentType;
import util.IOHelper;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ResponseWriterTest {
    private Response makeResponse(ResponseStatus responseStatus, ContentType contentType, byte[] bodyContents){
        Response response = new Response();
        response.setResponseStatus(responseStatus);
        response.setContentTypeHeader(contentType);
        response.setBodyContent(bodyContents);
        return response;
    }
    private String makeResponseExpected(ResponseStatus responseStatus, ContentType contentType, String bodyContent){
       return ("HTTP/1.1"+" "+new String(responseStatus.getPhraseAsByte())+"\r\n"+"Content-Type: "+contentType.getValue()+"\r\n\r\n"+bodyContent);
    }

    @Test
    public void buildResponse_NoHeaders() throws IOException {
        ResponseStatus responseStatus = ResponseStatus.OK;
        ContentType contentType = ContentType.HTML;
        String content ="processGoogleToken({\"newToken\":\"ChAIgN-a8QUQusLYzou1zucJEj" + "sAW_L70Kqa2Ww9OsS2NAcyAl96sjMrtq04FBZ2Mp0qPt8YEwbaiTfOBvYL73cLOMy2aHzFeO01l1M3Jw\",\"validLifetimeSecs\"" +
                ":300,\"freshLifetimeSecs\":300,\"1p_jar\":\"2020-01-22-04\",\"pucrd\":\"\"});";
        Response response = makeResponse(responseStatus, contentType, content.getBytes());
        IOHelper clientIO = new IOHelper("");
        String expectedResponse = makeResponseExpected(responseStatus, contentType, content);
        ResponseWriter responseWriter = new ResponseWriter();
        responseWriter.write(response, clientIO.getOutputStream());
        assertEquals(expectedResponse, clientIO.getStringOutput());
    }

}
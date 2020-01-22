package server2.server;

import org.junit.Test;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.ContentType;
import server2.util.IOHelper;

import static org.junit.Assert.*;

public class ResponseWriterTest {
    private Response makeResponse(ResponseStatus responseStatus, ContentType contentType, byte[] bodyContents){
        Response response = new Response();
        response.setResponseStatus(responseStatus);
        response.setContentTypeHeader(contentType);
        response.setBodyContent(bodyContents);
        return response;
    }
    private String makeResponseExpected(ResponseStatus responseStatus, ContentType contentType, byte[] bodyContents){
       return ("HTTP/1.1"+" "+responseStatus.getPhrase()+"\r\n"+"Content-Type: "+contentType.getValue()+"\r\n\r\n"+bodyContents);
    }

    @Test
    public void buildResponse_NoHeaders{
        ResponseStatus responseStatus = ResponseStatus.OK;
        ContentType contentType = ContentType.HTML;
        String content ="processGoogleToken({\"newToken\":\"ChAIgN-a8QUQusLYzou1zucJEj" + "sAW_L70Kqa2Ww9OsS2NAcyAl96sjMrtq04FBZ2Mp0qPt8YEwbaiTfOBvYL73cLOMy2aHzFeO01l1M3Jw\",\"validLifetimeSecs\"" +
                ":300,\"freshLifetimeSecs\":300,\"1p_jar\":\"2020-01-22-04\",\"pucrd\":\"\"});";
        Response response = makeResponse(responseStatus, contentType, content.getBytes());
        IOHelper clientIO = new IOHelper("");
        String expectedResponse = makeResponseExpected(responseStatus, contentType, content.getBytes());
        ResponseWriter responseWriter = new ResponseWriter();
        //responseWriter.write(response, clientIO.getOutputStream());
    }

}
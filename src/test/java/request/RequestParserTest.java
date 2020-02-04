package request;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOHelper;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestParserTest {
    private final String filePath="/connect-boostcamp/simpleHttpd/blob/master/doc/simplehttpd/Readme.md";
    private final String hostData ="github.com";
    private final String hostHeader="Host: "+hostData;
    private final String contentLengthData= "3293";
    private final String contentLengthHeader ="Content-Length: "+contentLengthData;
    private final String bodyContent ="v=1&_v=j48&a=1050134340&t=pageview&_s=1&dl=https%3A%2F%2Fgithub.com";
    private final String emptyBody ="";
    private final RequestParser requestParser = new RequestParser();

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestParser.class);
    @Test
    public void givenRequest_buildRequest() throws IOException {

        String requestInput ="GET " +filePath+" HTTP/1.1\n" +hostHeader+"\n"+contentLengthHeader+"\n\n"+bodyContent;
        IOHelper clientIO = new IOHelper(requestInput);
        InputStream requestInputStream =clientIO.getInputStream();
        Request request =requestParser.parse(requestInputStream);
        assertEquals(HttpVerb.GET, request.getHttpVerb());
        assertEquals(filePath, request.getResourcePath());
        assertEquals(hostData, request.getHeaders().get("Host"));
        assertEquals(contentLengthData, request.getHeaders().get("Content-Length"));
        assertEquals(bodyContent, request.getBodyContent());
    }
    @Test
    public void givenRequestWithNoBody_buildRequest() throws IOException {

        String requestInput ="GET " +filePath+" HTTP/1.1\n" +hostHeader+"\n";
        IOHelper clientIO = new IOHelper(requestInput);
        InputStream requestInputStream = clientIO.getInputStream();
        Request request = requestParser.parse(requestInputStream);
        assertEquals(HttpVerb.GET, request.getHttpVerb());
        assertEquals(filePath, request.getResourcePath());
        assertEquals(hostData, request.getHeaders().get("Host"));
        assertNull(contentLengthData, request.getHeaders().get("Content-Length"));
        assertEquals(emptyBody, request.getBodyContent());
    }
}
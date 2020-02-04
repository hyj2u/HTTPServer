package request;

import org.junit.Test;
import util.IOHelper;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RequestReaderTest {

    private final String requestLineInput ="GET /14 HTTP/1.1";
    private final String headersInput ="Host: tistory4.daumcdn.net\n" +
            "Connection: keep-alive\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36\n";
    private final String bodyInput = "body Content";
    private final String fullRequestInput = requestLineInput+"\n"+headersInput+"\n"+bodyInput;

    @Test
    public void canExtractAllRequestElements() throws IOException {
        IOHelper clientIO = new IOHelper(fullRequestInput);
        RequestReader requestReader = new RequestReader(clientIO.getInputStream());
        assertEquals(requestLineInput, requestReader.extractRequestLine());
        assertEquals(headersInput, requestReader.extractHeaders());
        assertEquals(bodyInput,requestReader.extractBodyContent(bodyInput.length()));
    }

}
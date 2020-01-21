package server2.request;

import org.junit.Test;
import server2.util.IOHelper;

import java.io.IOException;

import static org.junit.Assert.*;

public class RequestReaderTest {

    private final String requestLineInput ="GET /14 HTTP/1.1";
    private final String headersInput ="Host: tistory4.daumcdn.net\n" +
            "Connection: keep-alive\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36\n" +
            "Accept: */*\n" +
            "Sec-Fetch-Site: cross-site\n" +
            "Sec-Fetch-Mode: no-cors\n" +
            "Referer: https://androidyongyong.tistory.com/14\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n";
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
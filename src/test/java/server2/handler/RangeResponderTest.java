package server2.handler;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseHeader;
import server2.response.ResponseStatus;
import server2.util.FileContentConverter;
import server2.util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RangeResponderTest {


    private final String testRootPath ="src/test/resources";
    private final String filePath ="/photo.jpg";
    private final String testFullPath = testRootPath+filePath;
    private final FileContentConverter fileContentConverter = new FileContentConverter();
    private final ResourceTypeIdentifier resourceTypeIdentifier = new ResourceTypeIdentifier();
    private final RangeResponder rangeResponder = new RangeResponder(testRootPath, fileContentConverter, resourceTypeIdentifier);
    private final String emptyBody ="";
    byte[] fullTestFileContents;


    private static final Logger LOGGER = LoggerFactory.getLogger(RangeResponderTest.class);

    @Before
    public void setUp() throws IOException {
        fullTestFileContents = fileContentConverter.getFullContents(new File(testFullPath));
    }
    private byte[] getFileOfRange(int start, int end){
        return Arrays.copyOfRange(fullTestFileContents, start, end);
    }
    @Test
    public void rangeRequestWithFullRange() throws IOException {
        String byteRange = "bytes=0-4";
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HttpVerb.GET, filePath, rangeHeader, emptyBody);

        Response response = rangeResponder.doRange(request);

        int rangeStart = 0;
        int rangeEnd = 5;
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getResponseStatus());
        assertArrayEquals("bytes 0-4/440".getBytes(), response.getHeaders().get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }
    @Test
    public void rangeRequestWithStartByte() throws IOException {
        String byteRange ="11-";
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HttpVerb.GET, filePath, rangeHeader, emptyBody);
        Response response =rangeResponder.doRange(request);
        int rangeStart =11;
        int rangeEnd = fullTestFileContents.length;
        System.out.println(rangeEnd);
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);
        assertEquals(ResponseStatus.PARTIALCONTENT, response.getResponseStatus());
        assertArrayEquals("bytes 11-439/440".getBytes(), response.getHeaders().get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }
    @Test
    public void rangeRequestWithEndByte() throws IOException {
        String byteRange ="bytes=-6";
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HttpVerb.GET, filePath, rangeHeader, emptyBody);
        Response response= rangeResponder.doRange(request);
        int rangeStart =9;
        int rangeEnd = fullTestFileContents.length;
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);
        assertEquals(ResponseStatus.PARTIALCONTENT, response.getResponseStatus());
        assertArrayEquals("bytes 9-14/440".getBytes(), response.getHeaders().get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }
    @Test
    public void rangeRequestIsNotSatisfiable() throws IOException {
        String byteRange = "bytes=10-20";
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HttpVerb.GET, filePath, rangeHeader, emptyBody);
        Response response= rangeResponder.doRange(request);
        assertEquals(ResponseStatus.RANGENOTSATISFIABLE, response.getResponseStatus());
        assertArrayEquals("bytes */440".getBytes(), response.getHeaders().get(ResponseHeader.CONTENTRANGE));
    }

}
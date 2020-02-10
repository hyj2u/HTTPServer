package request;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * RequestParser makes object request with client's input.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class RequestParser {
    /**
     * Returns matching httpVerb.
     *
     * @param requestedVerb http method from client input
     * @return httpVerb
     * @see HttpVerb#find(String)
     * @since 1.0
     */
    private HttpVerb matchHttpverb(String requestedVerb) {
        return HttpVerb.find(requestedVerb);
    }
    /**
     * Returns headers as a hashmap from String array.
     *
     * @param headersCollection headers as a String array format as key : value
     * @return headers as hashmap
     * @since 1.0
     */
    private HashMap<String, String> mapHeaders(String[] headersCollection) {
        HashMap<String, String> newHeaders = new HashMap<>();
        for (String header : headersCollection) {
            String[] headerElements = header.split(":", 2);
            newHeaders.put(headerElements[0].trim(), headerElements[1].trim());
        }
        return newHeaders;
    }
    /**
     * Returns headers as String array.
     *
     * @param allHeaders
     * @return headers as hashmap
     * @since 1.0
     */
    private HashMap<String, String> assembleHeaders(String allHeaders) {
        String[] headersCollection = allHeaders.split("[\\r\\n]");
        return mapHeaders(headersCollection);
    }

    /**
     * Return headers as a hashMap.
     *
     * @param potentialHeaders header information as a String
     * @return HashMap headers
     * @see #assembleHeaders(String)
     * @since 1.0
     */
    private HashMap<String, String> setRequestHeaders(String potentialHeaders) {
        if (potentialHeaders.isEmpty()) {
            return new HashMap<>();
        } else {
            return assembleHeaders(potentialHeaders);
        }
    }
    /**
     * Returns if there is header named Content-Length.
     *
     * @param headers request headers
     * @return <code>true</code> if headers hashmap contains key content-length
     * <code>false</code> otherwise;
     * @since 1.0
     */
    private boolean contentLengthExists(HashMap<String, String> headers) {
        return headers.get("Content-Length") != null;
    }
    /**
     * Returns body part of request.
     *
     * @param headers request headers
     * @param requestReader object that reads client input
     * @return body contents
     * @see RequestReader
     * @see #contentLengthExists(HashMap)
     * @see RequestReader#extractBodyContent(int)
     * @since 1.0
     */
    private String setBodyContent(HashMap<String, String> headers, RequestReader requestReader) throws IOException {
        if (contentLengthExists(headers)) {
            int length = Integer.parseInt(headers.get("Content-Length").trim());
            return requestReader.extractBodyContent(length);
        } else {
            return "";
        }
    }
    /**
     * Returns request object with full information
     *
     * @param clientIn inputstream form client
     * @return Request
     * @see RequestReader
     * @see RequestReader#extractRequestLine()
     * @see HttpVerb
     * @see #matchHttpverb(String)
     * @see #setRequestHeaders(String)
     * @see Request
     * @since 1.0
     */
    public Request parse(InputStream clientIn) throws IOException {
        RequestReader requestReader = new RequestReader(clientIn);
        String line = requestReader.extractRequestLine();
        String[] requestLine = line.split(" ");
        HttpVerb httpVerb = matchHttpverb(requestLine[0]);
        String resourcePath = requestLine[1];
        if (resourcePath.equals("/")) {
            resourcePath = "/index.html";
        }
        if (resourcePath.equals("/index.html/style.css")) {
            resourcePath = "/style.css";
        }
        HashMap<String, String> headers = setRequestHeaders(requestReader.extractHeaders());
        String bodyContent = setBodyContent(headers, requestReader);
        return new Request(httpVerb, resourcePath, headers, bodyContent);
    }


}

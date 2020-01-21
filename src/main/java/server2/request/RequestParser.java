package server2.request;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class RequestParser {
    private HttpVerb matchHttpverb(String requestedVerb) {
        return HttpVerb.find(requestedVerb);
    }

    private HashMap<String, String> setRequestHeaders(String potentialHeaders) {
        if (potentialHeaders.isEmpty()) {
            return new HashMap<>();
        } else {
            return assembleHeaders(potentialHeaders);
        }
    }

    private HashMap<String, String> assembleHeaders(String allHeaders) {
        String[] headersCollection = allHeaders.split("[\\r\\n]");
        return mapHeaders(headersCollection);
    }

    private HashMap<String, String> mapHeaders(String[] headersCollection) {
        HashMap<String, String> newHeaders = new HashMap<>();
        for (String header : headersCollection) {
            String[] headerElements = header.split(":", 2);
            newHeaders.put(headerElements[0].trim(), headerElements[1].trim());
        }
        return newHeaders;
    }
    private boolean contentLengthExists(HashMap<String, String> headers){
        return headers.get("Content-Length") !=null? true :false;
    }
    private String setBodyContent(HashMap<String, String> headers, RequestReader requestReader) throws IOException {
        if(contentLengthExists(headers)==true){
            int length = Integer.parseInt(headers.get("Content-Length").trim());
            return requestReader.extractBodyContent(length);
        }else{
            return "";
        }
    }

    public Request parse(InputStream clientIn) throws IOException {
        RequestReader requestReader = new RequestReader(clientIn);
        String[] requestLine = requestReader.extractRequestLine().split(" ");
        HttpVerb httpVerb = matchHttpverb(requestLine[0]);
        String resourcePath = requestLine[1];
        HashMap<String,String> headers = setRequestHeaders(requestReader.extractHeaders());
        String bodyContent =setBodyContent(headers,requestReader);
        return new Request(httpVerb, resourcePath, headers,bodyContent);
    }


}
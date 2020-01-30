package server2.request;

import java.util.Map;

public class Request {
    private String httpVersion;
    private HttpVerb httpVerb;
    private String resourcePath;
    private Map<String, String> headers;
    private String bodyContent;

    public Request( HttpVerb httpVerb, String resourcePath, Map<String, String> headers, String bodyContent) {
        this.httpVersion = "HTTP/1.1";
        this.httpVerb = httpVerb;
        this.resourcePath = resourcePath;
        this.headers = headers;
        this.bodyContent = bodyContent;
    }

    public HttpVerb getHttpVerb() {
        return httpVerb;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBodyContent() {
        return bodyContent;
    }
    public String getRequestLine(){
        String request="Request\r\n"+httpVersion+"\r\n"+httpVerb +" "+resourcePath+"\r\n";
        for(Map.Entry<String, String> entry : headers.entrySet()){
            request += entry.getKey()+": "+entry.getValue()+"\r\n";
        }

        return request;
    }

}

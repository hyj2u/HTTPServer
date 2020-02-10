package request;

import java.util.Map;

/**
 * Request class has information of client input such as, request method, http version, request header, and body contents.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class Request {
    /**
     * http version of request.
     *
     */
    private String httpVersion;
    /**
     * http method of request.
     *
     * @see #getHttpVerb()
     */
    private HttpVerb httpVerb;
    /**
     * Location for resource that client requests.
     *
     * @see #getResourcePath()
     */
    private String resourcePath;
    /**
     * Map that has request header for key and header contents for value.
     *
     * @see #getHeaders()
     */
    private Map<String, String> headers;
    /**
     * body content of http request.
     *
     * @see #getBodyContent()
     */
    private String bodyContent;
    /**
     * This Constructor set httpVerb, resourcePath, headers, and body Contents.
     * Http Version is HTTP/1.1 for default.
     *
     * @param httpVerb method of request
     * @param resourcePath location of resource
     * @param headers http request headers
     * @param bodyContent body content of http request
     * @since 1.0
     */
    public Request(HttpVerb httpVerb, String resourcePath, Map<String, String> headers, String bodyContent) {
        this.httpVersion = "HTTP/1.1";
        this.httpVerb = httpVerb;
        this.resourcePath = resourcePath;
        this.headers = headers;
        this.bodyContent = bodyContent;
    }
    /**
     * Returns http method of request.
     *
     * @return httpVerb
     * @since 1.0
     */
    public HttpVerb getHttpVerb() {
        return httpVerb;
    }
    /**
     * Returns resource location of request.
     *
     * @return resourcePath
     * @since 1.0
     */
    public String getResourcePath() {
        return resourcePath;
    }
    /**
     * Returns http request headers as a map.
     *
     * @return headers
     * @since 1.0
     */
    public Map<String, String> getHeaders() {
        return headers;
    }
    /**
     * Returns http request body content.
     *
     * @return httpVerb
     * @since 1.0
     */
    public String getBodyContent() {
        return bodyContent;
    }
    /**
     * Returns information of request as a String with CRLF.
     *
     * @return request as a String
     * @since 1.0
     */
    public String getRequestLine(){
        String request="Request\r\n"+httpVersion+"\r\n"+httpVerb +" "+resourcePath+"\r\n";
        for(Map.Entry<String, String> entry : headers.entrySet()){
            request += entry.getKey()+": "+entry.getValue()+"\r\n";
        }

        return request;
    }

}

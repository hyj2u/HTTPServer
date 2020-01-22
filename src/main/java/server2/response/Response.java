package server2.response;

import server2.util.ContentType;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private byte[] httpVersion;
    private ResponseStatus responseStatus;
    private Map<ResponseHeader, byte[]> headers;
    private byte[] bodyContent;

    public Response() {
        httpVersion ="HTTP/1.1".getBytes();
        this.responseStatus = ResponseStatus.INTENRNALSERVERERROR;
        headers = new HashMap<>();
        bodyContent = new byte[0];
    }

    public byte[] getHttpVersion() {
        return httpVersion;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public Map<ResponseHeader, byte[]> getHeaders() {
        return headers;
    }

    public byte[] getBodyContent() {
        return bodyContent;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;
    }
    public void clearBody(){
        bodyContent = new byte[0];
    }
    public void setContentTypeHeader(ContentType contentType){
        headers.put(ResponseHeader.CONTENTTYPE, contentType.getValueAsByte());
    }
    public void setAllowHeader(String allowedMethods){
        headers.put(ResponseHeader.ALLOW, allowedMethods.getBytes());
    }
    public void setContentRangeHeader(String contentRangeValue){
        headers.put(ResponseHeader.CONTENTRANGE, contentRangeValue.getBytes());
    }
    public void setUnauthorizedHeader(String authenticateMessage){
        headers.put(ResponseHeader.AUTHENTICATION, authenticateMessage.getBytes());
    }
    public void setLocationHeader(String redirectURI){
        headers.put(ResponseHeader.LOCATION, redirectURI.getBytes());
    }
    public void setCookieHeader(String cookieType){
        headers.put(ResponseHeader.COOKIE, cookieType.getBytes());
    }
}

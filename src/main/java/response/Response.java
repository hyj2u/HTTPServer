package response;

import util.ContentType;

import java.util.HashMap;
import java.util.Map;

/**
 * Response class has information of client output such as, response status, headers and body contents.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class Response {
    /**
     * http version of response.
     */
    private byte[] httpVersion;
    /**
     * http response status.
     *
     * @see #getHttpVersion()
     */
    private ResponseStatus responseStatus;
    /**
     * http headers of response
     *
     * @see #getResponseStatus()
     * @see #setResponseStatus(ResponseStatus)
     */
    private Map<ResponseHeader, byte[]> headers;
    /**
     * http body content of response
     *
     * @see #getHeaders()
     */
    private byte[] bodyContent;

    /**
     * This Constructor set httpVerb, response status, headers, and body Contents.
     * Http Version is HTTP/1.1 for default.
     * Response status is default for Internal Server error.
     * Initialize headers and body content.
     *
     * @see ResponseStatus
     * @see ResponseStatus#INTENRNALSERVERERROR
     * @see #getBodyContent()
     * @see #setBodyContent(byte[])
     * @since 1.0
     */
    public Response() {
        httpVersion = "HTTP/1.1".getBytes();
        this.responseStatus = ResponseStatus.INTENRNALSERVERERROR;
        headers = new HashMap<>();
        bodyContent = new byte[0];
    }

    /**
     * Returns http method of response.
     *
     * @return httpVerb
     * @since 1.0
     */
    public byte[] getHttpVersion() {
        return httpVersion;
    }

    /**
     * Returns response status of response.
     *
     * @return response status
     * @since 1.0
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Returns headers of response.
     *
     * @return headers as a map
     * @since 1.0
     */
    public Map<ResponseHeader, byte[]> getHeaders() {
        return headers;
    }

    /**
     * Returns body content of response.
     *
     * @return body content
     * @since 1.0
     */
    public byte[] getBodyContent() {
        return bodyContent;
    }

    /**
     * Sets response status of response.
     *
     * @param responseStatus
     * @since 1.0
     */
    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * Sets body content of response.
     *
     * @param bodyContent
     * @since 1.0
     */
    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;
    }

    /**
     * Sets body content to ="".
     *
     * @since 1.0
     */
    public void clearBody() {
        bodyContent = new byte[0];
    }

    /**
     * Sets header named content type.
     *
     * @param contentType
     * @see ResponseHeader#CONTENTTYPE
     * @see ContentType
     * @see ContentType#getValueAsByte()
     * @since 1.0
     */
    public void setContentTypeHeader(ContentType contentType) {
        headers.put(ResponseHeader.CONTENTTYPE, contentType.getValueAsByte());
    }

    /**
     * Sets header named allow.
     *
     * @param allowedMethods
     * @see ResponseHeader#ALLOW
     * @since 1.0
     */
    public void setAllowHeader(String allowedMethods) {
        headers.put(ResponseHeader.ALLOW, allowedMethods.getBytes());
    }

    /**
     * Sets header named content-range.
     *
     * @param contentRangeValue
     * @see ResponseHeader#CONTENTRANGE
     * @since 1.0
     */
    public void setContentRangeHeader(String contentRangeValue) {
        headers.put(ResponseHeader.CONTENTRANGE, contentRangeValue.getBytes());
    }

    /**
     * Sets header named etag.
     *
     * @param etag
     * @see ResponseHeader#ETag
     * @since 1.0
     */
    public void setEtagHeader(String etag) {
        headers.put(ResponseHeader.ETag, etag.getBytes());
    }

    /**
     * Sets header named content-length.
     *
     * @param contentLength
     * @see ResponseHeader#CONTENTLENGTH
     * @since 1.0
     */
    public void setContentLengthHeader(String contentLength) {
        headers.put(ResponseHeader.CONTENTLENGTH, contentLength.getBytes());
    }

    /**
     * Sets header named location.
     *
     * @param redirectURI
     * @see ResponseHeader#LOCATION
     * @since 1.0
     */
    public void setLocationHeader(String redirectURI) {
        headers.put(ResponseHeader.LOCATION, redirectURI.getBytes());
    }

    /**
     * Returns response as a String.
     *
     * @return response
     * @see ResponseHeader
     * @since 1.0
     */
    public String getResponseLine() {
        String response = "Response\r\n" + new String(httpVersion) + " " + responseStatus + "\r\n";
        for (Map.Entry<ResponseHeader, byte[]> entry : headers.entrySet()) {
            response += entry.getKey() + ": " + new String(entry.getValue()) + "\r\n";
        }
        return response;
    }

}

package response;
/**
 * ResponseStatus Enum contain response statues that this server provides.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public enum ResponseStatus {
    OK("200 OK", ""),
    CREATED("201 Created", ""),
    NOCONTENT("204 No Content", ""),
    METHODNOTALLOWED("405 Method Not Alloewd", "The request method cannot be used"),
    NOTFOUND("404 Not Found", "404 Error - The server can not find requested resource"),
    PARTIALCONTENT("206 partial Content", ""),
    RANGENOTSATISFIABLE("416 RangeNotSatisfiable",""),
    INTENRNALSERVERERROR("500 Internal Server Error", "500 Error - The server has encountered a situation it doesn't know how to handle"),
    PRECONDITIONFAILED("412 Precondition Failed", "Access to the target resource has been denied");
    /**
     * Status code and name
     *
     * @see #getPhraseAsByte()
     */
    private String phrase;
    /**
     * body content of response status
     *
     * @see #getStatusBodyAsByte()
     */
    private String statusBody;
    /**
     * This Constructor set status name, code and body content.
     *
     * @param phrase status name and code
     * @param statusBody body content of response status
     * @since 1.0
     */
    ResponseStatus(String phrase, String statusBody) {
        this.phrase = phrase;
        this.statusBody = statusBody;
    }
    /**
     * Return status code and name as byte.
     *
     * @return status code and name
     * @since 1.0
     */
    public byte[] getPhraseAsByte(){
        return phrase.getBytes();
    }
    /**
     * Return status body content as byte.
     *
     * @return status body content
     * @since 1.0
     */
    public byte[] getStatusBodyAsByte(){
        return statusBody.getBytes();
    }
}

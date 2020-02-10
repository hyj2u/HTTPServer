package response;
/**
 * ResponseHeader Enum contains response header that this sever supports.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public enum  ResponseHeader {
    ALLOW("Allow"),
    CONTENTRANGE("Content-Range"),
    CONTENTTYPE("Content-Type"),
    ETag("Etag"),
    LOCATION("Location"),
    CONTENTLENGTH("Content-Length");

    /**
     * Header name.
     *
     * @see #getLabelAsByte()
     */
    private String label;
    /**
     * This Constructor set header name.
     *
     * @param label header name
     * @since 1.0
     */
    ResponseHeader(String label){
        this.label =label;
    }
    /**
     * Returns header name as byte.
     *
     * @return label header name
     * @since 1.0
     */
    public byte[] getLabelAsByte(){
        return label.getBytes();
    }

}

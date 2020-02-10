package util;
/**
 * ContentType enum contain content types that http server knows.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public enum ContentType {
    TXT("text/plain"),
    HTML("text/html"),
    GIF("image/gif"),
    JPG("image/jpg"),
    PNG("image/png");

    /**
     * Content type name.
     *
     * @see #getValueAsByte()
     * @see #getValue()
     */
    private String contentTypeString;
    /**
     * This Constructor sets content type name.
     *
     * @param contentTypeString content type name
     * @since 1.0
     */
    ContentType(String contentTypeString) {
        this.contentTypeString = contentTypeString;
    }
    /**
     * Returns content type name as a byte.
     *
     * @return content type name as byte
     * @since 1.0
     */
    public byte[] getValueAsByte(){
        return contentTypeString.getBytes();
    }
    /**
     * Returns content type name.
     *
     * @return content type name
     * @since 1.0
     */
    public String getValue(){
        return contentTypeString;
    }
}

package request;


/**
 * HttpVerb Enum contains method that this server provides.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public enum HttpVerb {

    DELETE("DELETE", true, false),
    GET("GET", true, true),
    POST("POST", true, true),
    PUT("PUT", true, false),
    PATCH("PATCH", true, true),
    HEAD("HEAD", true, true),
    OPTIONS("OPTIONS", true, true),
    NOTRECOGNIZED("", false, false);
    /**
     * Method name
     */
    private String label;
    /**
     * If its method is allowed for this server.
     */
    private boolean allowed;
    /**
     * If its method is allowed to print log for this server.
     */
    private boolean allowedforLogs;

    /**
     * This Constructor sets label,and allowance for server and log.
     *
     * @param label          method name
     * @param allowed        allow for server request
     * @param allowedforLogs allow for printing logs
     * @since 1.0
     */
    HttpVerb(String label, boolean allowed, boolean allowedforLogs) {
        this.label = label;
        this.allowed = allowed;
        this.allowedforLogs = allowedforLogs;
    }

    /**
     * Find right httpVerb for client's request
     *
     * @param requestedHttpVerb parsed String request httpVerb part
     * @see HttpVerb
     * @see HttpVerb#values()
     * @see String#equals(Object)
     * @since 1.0
     */
    public static HttpVerb find(String requestedHttpVerb) {
        for (HttpVerb verb : HttpVerb.values()) {
            if (verb.label.equals(requestedHttpVerb)) {
                return verb;
            }
        }
        return NOTRECOGNIZED;
    }
    /**
     * Returns allowed methods as a String with seperator ',';
     *
     * @return alloewd methods
     * @see StringBuilder
     * @see HttpVerb#values()
     * @see HttpVerb#allowed
     * @see StringBuilder#append(String)
     * @since 1.0
     */
    public static String getAllowedMethods() {
        StringBuilder allowedMethods = new StringBuilder();
        String previousSeperator = "";
        for (HttpVerb verb : HttpVerb.values()) {
            if (verb.allowed) {
                allowedMethods.append(previousSeperator).append(verb.label);
                previousSeperator = ", ";
            }
        }
        return new String(allowedMethods);
    }
    /**
     * Returns allowed methods to print log as a String with seperator ',';
     *
     * @return alloewd methods for logs
     * @see StringBuilder
     * @see HttpVerb#values()
     * @see HttpVerb#allowedforLogs
     * @see StringBuilder#append(String)
     * @since 1.0
     */
    public static String getAllowedForLogMethods() {
        StringBuilder allowedMethods = new StringBuilder();
        String previousSeperator = "";
        for (HttpVerb verb : HttpVerb.values()) {
            if (verb.allowedforLogs) {
                allowedMethods.append(previousSeperator).append(verb.label);
                previousSeperator = ", ";
            }
        }
        return new String(allowedMethods);
    }


}

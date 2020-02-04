package request;

public enum HttpVerb {

    DELETE("DELETE", true, false),
    GET("GET",true,true),
    POST("POST", true,true),
    PUT("PUT", true,false),
    PATCH("PATCH", true,true),
    HEAD("HEAD", true, true),
    OPTIONS("OPTIONS",true,true),
    NOTRECOGNIZED("", false,false);

    private String label;
    private boolean allowed;
    private boolean allowedforLogs;

    HttpVerb(String label, boolean allowed, boolean allowedforLogs) {
        this.label = label;
        this.allowed = allowed;
        this.allowedforLogs = allowedforLogs;
    }
    public static HttpVerb find(String requestedHttpVerb){
        for(HttpVerb verb : HttpVerb.values()){
            if(verb.label.equals(requestedHttpVerb)){
                return verb;
            }
        }
        return NOTRECOGNIZED;
    }
    public static String getAllowedMethods(){
        StringBuilder allowedMethods = new StringBuilder();
        String previousSeperator="";
        for(HttpVerb verb: HttpVerb.values()){
            if(verb.allowed){
                allowedMethods.append(previousSeperator).append(verb.label);
                previousSeperator=", ";
            }
        }
        return new String(allowedMethods);
    }
    public static String getAllowedForLogMethods(){
        StringBuilder allowedMethods = new StringBuilder();
        String previousSeperator="";
        for(HttpVerb verb: HttpVerb.values()){
            if(verb.allowed){
                allowedMethods.append(previousSeperator).append(verb.label);
                previousSeperator=", ";
            }
        }
        return new String(allowedMethods);
    }


}

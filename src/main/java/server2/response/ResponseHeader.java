package server2.response;

public enum  ResponseHeader {
    ALLOW("Allow"),
    AUTHENTICATION("WWW-Authenticate"),
    CONTENTRANGE("Content-Range"),
    CONTENTTYPE("Content-Type"),
    COOKIE("Set-Cookie"),
    LOCATION("Location");

    private String label;
    ResponseHeader(String label){
        this.label =label;
    }
    public byte[] getLabelAsByte(){
        return label.getBytes();
    }

}

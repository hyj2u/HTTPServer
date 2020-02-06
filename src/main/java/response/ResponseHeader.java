package response;

public enum  ResponseHeader {
    ALLOW("Allow"),
    CONTENTRANGE("Content-Range"),
    CONTENTTYPE("Content-Type"),
    ETag("Etag"),
    LOCATION("Location"),
    CONTENTLENGTH("Content-Length");

    private String label;
    ResponseHeader(String label){
        this.label =label;
    }
    public byte[] getLabelAsByte(){
        return label.getBytes();
    }

}

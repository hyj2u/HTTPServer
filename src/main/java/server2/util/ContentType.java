package server2.util;

public enum ContentType {
    TXT("text/plain"),
    HTML("text/html"),
    GIF("image/gif"),
    JPG("img/jpg"),
    PNG("image/png");

    private String contentTypeString;

    ContentType(String contentTypeString) {
        this.contentTypeString = contentTypeString;
    }
    public byte[] getValueAsByte(){
        return contentTypeString.getBytes();
    }
    public String getValue(){
        return contentTypeString;
    }
}

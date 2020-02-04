package util;

public enum ContentType {
    TXT("text/plain"),
    HTML("text/html"),
    GIF("image/gif"),
    JPG("image/jpg"),
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

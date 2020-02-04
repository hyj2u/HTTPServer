package response;

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

    private String phrase;
    private String statusBody;

    ResponseStatus(String phrase, String statusBody) {
        this.phrase = phrase;
        this.statusBody = statusBody;
    }

    public byte[] getPhraseAsByte(){
        return phrase.getBytes();
    }

    public byte[] getStatusBodyAsByte(){
        return statusBody.getBytes();
    }
}

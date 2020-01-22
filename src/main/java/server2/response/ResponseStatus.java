package server2.response;

public enum ResponseStatus {
    OK("200 OK", ""),
    CREATED("201 Created", ""),
    NOCONTENT("204 No Content", ""),
    ACCEPTED("202 Accepted", ""),
    NOTMODIFIED("304 Not Modified", "Redirection message"),
    UNAUTHORIZED("401 Unauthorized","Client must authenticate itself to get the requested response"),
    NOTFOUND("404 Not Found", "404 Error - The server can not find requested resource"),
    BADREQUEST("400 Bad Request", "The server could not understand the request due to invalid systax"),
    FORBIDDEN("403 Forbidden","The client does not have access rights to content"),
    TOOMANYREQUESTS("429 Too Many Requests", "The user has sent too many requests in a given amount of time"),
    INTENRNALSERVERERROR("500 Internal Server Error", "500 Error - The server has encountered a situation it doesn't know how to handle");

    private String phrase;
    private String statusBody;

    ResponseStatus(String phrase, String statusBody) {
        this.phrase = phrase;
        this.statusBody = statusBody;
    }

    public byte[] getPhraseAsByte(){
        return phrase.getBytes();
    }
    public String getPhrase(){
        return phrase;
    }
    public byte[] getStatusBodyAsByte(){
        return statusBody.getBytes();
    }
}

package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestReader {
    /**
     * Reader that reads client input.
     */
    private BufferedReader reader;
    /**
     * This Constructor set reader with client input.
     * Initializes BufferedReader and translate client input to InputStreamReader to BufferedReader.
     *
     * @param clientIn client inputstream
     * @since 1.0
     */
    public RequestReader(InputStream clientIn){
        reader = new BufferedReader(new InputStreamReader(clientIn));
    }
    /**
     * Returns request as a String
     *
     * @return requestline use BufferedReader to read
     * @since 1.0
     */
    public String extractRequestLine() throws IOException {
        return reader.readLine();
    }
    /**
     * Returns headers from request.
     * Read request while it's null.
     *
     * @return headers
     * @since 1.0
     */
    public String extractHeaders() throws IOException {
        StringBuilder headers = new StringBuilder();
        String line;
        while((line=reader.readLine())!=null){
            if(line.isEmpty()){
                break;
            }else{
                headers.append(line).append("\n");
            }
        }
        return  new String(headers);
    }
    /**
     * Returns body content from request.
     * Read request while it's null.
     *
     * @return body content
     * @since 1.0
     */
    public String extractBodyContent(int contentLength) throws IOException {
        StringBuilder body =new StringBuilder();
        char[] buffer = new char[contentLength];
        reader.read(buffer);
        for(char c : buffer){
            body.append(c);
        }
        return body.toString().trim();
    }

}

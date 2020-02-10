package server;

import handler.RequestRouter;
import response.Response;
import response.ResponseHeader;
import util.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
/**
 * This class writes response to socket.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class ResponseWriter {
    /**
     * Writes response to socket.
     *
     * @param response response as byte array
     * @param outputStream socket output
     * @throws IOException
     * @since 1.0
     */
    private void writeToSocket(byte[] response, OutputStream outputStream) throws IOException {
        outputStream.write(response);
        outputStream.flush();
    }
    /**
     * makes response as byte array and sends it.
     *
     * @param response 
     * @param outputStream socket output
     * @throws IOException
     * @see #writeToSocket(byte[], OutputStream) 
     * @since 1.0
     */
    public void write(Response response, OutputStream outputStream) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        responseStream.write(response.getHttpVersion());
        responseStream.write(" ".getBytes());
        responseStream.write(response.getResponseStatus().getPhraseAsByte());
        responseStream.write("\r\n".getBytes());
        for(Map.Entry<ResponseHeader, byte[]> entry : response.getHeaders().entrySet()){
            responseStream.write(entry.getKey().getLabelAsByte());
            responseStream.write(": ".getBytes());
            responseStream.write(entry.getValue());
            responseStream.write("\r\n".getBytes());
        }
        responseStream.write("\r\n".getBytes());
        responseStream.write(response.getBodyContent());
        writeToSocket(responseStream.toByteArray(), outputStream);
        responseStream.flush();
    }
}

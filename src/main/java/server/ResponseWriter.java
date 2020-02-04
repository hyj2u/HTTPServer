package server;

import response.Response;
import response.ResponseHeader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResponseWriter {
    private void writeToSocket(byte[] response, OutputStream outputStream) throws IOException {
        outputStream.write(response);
        outputStream.flush();
    }
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

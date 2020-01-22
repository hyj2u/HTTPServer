package server2.util;

import java.io.*;

public class IOHelper {
    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintStream printStream;

    public IOHelper(String input){
        inputStream = new ByteArrayInputStream(input.getBytes());
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}

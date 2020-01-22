package server2.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileContentConverter {
    public byte[] getFullContents(File resource) throws IOException {
        byte[] encoded = new byte[0];
        encoded = Files.readAllBytes(Paths.get(resource.toURI()));
        return encoded;
    }
    public byte[] getPartialContent(byte[] content, int startingIndex, int endIndex){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for(int i=startingIndex; i<=endIndex; i++){
            outputStream.write(content[i]);
        }
        return outputStream.toByteArray();
    }
}

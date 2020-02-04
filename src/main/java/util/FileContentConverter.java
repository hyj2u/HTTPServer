package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileContentConverter {
    public byte[] getFullContents(File resource) throws IOException {

        return Files.readAllBytes(Paths.get(resource.toURI()));
    }
    public byte[] getPartialContent(byte[] content, int startingIndex, int endIndex){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for(int i=startingIndex; i<=endIndex; i++){
            outputStream.write(content[i]);
        }
        return outputStream.toByteArray();
    }
}

package server2.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class FileContentConverterTest {
    @Test
    public void getFileContents() throws IOException {
        String filePath = "src/test/resources/photo.jpg";
        byte[] fileContents = ("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\"><path fill=\"none\" d=\"M0 0h24v24H0z\"/><path fill=\"#34A853\" " +
                "d=\"M10 2v2a6 6 0 0 1 6 6h2a8 8 0 0 0-8-8z\"/><path fill=\"#EA4335\" d=\"M10 4V2a8 8 0 0 0-8 8h2c0-3.3 2.7-6 6-6z\"/><path fill=\"#FBBC04\" " +
                "d=\"M4 10H2a8 8 0 0 0 8 8v-2c-3.3 0-6-2.69-6-6z\"/><path fill=\"#4285F4\"" +
                " d=\"M22 20.59l-5.69-5.69A7.96 7.96 0 0 0 18 10h-2a6 6 0 0 1-6 6v2c1.85 0 3.52-.64 4.88-1.68l5.69 5.69L22 20.59z\"/></svg>\n").getBytes();
        Files.write(Paths.get(filePath), fileContents);
        FileContentConverter fileContentConverter = new FileContentConverter();
        byte[] actualContents =  fileContentConverter.getFullContents(new File(filePath));

        assertArrayEquals(actualContents, fileContents);

    }

}
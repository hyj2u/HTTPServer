package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class reads file.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class FileContentConverter {
    /**
     * Returns all contents of file.
     *
     * @param resource target file
     * @return file contents
     * @throws IOException
     * @since 1.0
     */
    public byte[] getFullContents(File resource) throws IOException {
        return Files.readAllBytes(Paths.get(resource.toURI()));
    }
    /**
     * Returns some part of file.
     *
     * @param content
     * @param startingIndex
     * @param endIndex
     * @return partial contents of file
     * @since 1.0
     */
    public byte[] getPartialContent(byte[] content, int startingIndex, int endIndex) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int i = startingIndex; i <= endIndex; i++) {
            outputStream.write(content[i]);
        }
        return outputStream.toByteArray();
    }
}

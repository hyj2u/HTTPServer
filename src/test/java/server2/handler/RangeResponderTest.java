package server2.handler;

import org.junit.Before;
import org.junit.Test;
import server2.util.FileContentConverter;
import server2.util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RangeResponderTest {


    private final String testRootPath ="src/test/resources";
    private final String filePath ="/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rdZ8uDfBFrhbNfXBwY6pdKv598SYw.CMID/s32-c/photo.jpg";
    private final String testFullPath = testRootPath+filePath;
    private final FileContentConverter fileContentConverter = new FileContentConverter();
    private final ResourceTypeIdentifier resourceTypeIdentifier = new ResourceTypeIdentifier();
    private final RangeResponder rangeResponder = new RangeResponder(testRootPath, fileContentConverter, resourceTypeIdentifier);
    private final String emptyBody ="";
    byte[] fullTestFileContents;

    @Before
    public void setUp() throws IOException {
        fullTestFileContents = fileContentConverter.getFullContents(new File(testFullPath));
    }
    @Test
    public void rangeRequestWithFullRange(){
        String byteRange ="bytes=0-1023";

    }

}
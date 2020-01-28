package server2.handler;

import org.junit.Test;
import server2.request.HttpVerb;
import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.FileContentConverter;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PatchHandlerTest {
    private final String testRootPath = "src/test/resources";
    private final String patchFilePath = "/testPatchFile.txt";
    private final String fullTestPath = testRootPath + patchFilePath;
    private final File testPatchFile = new File(fullTestPath);
    private final PatchHandler patchHandler = new PatchHandler(testRootPath);
    private final FileContentConverter fileContentConverter = new FileContentConverter();

    @Test
    public void patchRequestWithNoEtag() throws IOException {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        String emptyBody ="";
        Request request = new Request(HttpVerb.PATCH, patchFilePath, emptyHeaders, emptyBody);
        Response response = patchHandler.getResponse(request);
        assertEquals(ResponseStatus.PRECONDITIONFAILED, response.getResponseStatus());
    }
    private void overwriteDatatoFile(byte[] content, String path) throws IOException {
        Files.write(Paths.get(path), content);
    }
    private String createSHA1() throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(fileContentConverter.getFullContents(testPatchFile));
        return new BigInteger(1, digest.digest()).toString(16);
    }
    @Test
    public void patchRequestWithCorrectEtag() throws IOException, NoSuchAlgorithmException {
        overwriteDatatoFile("patch stuff".getBytes(), fullTestPath);
        String SHAData = createSHA1();
        HashMap<String, String > EtagHeader = new HashMap<>();
        EtagHeader.put("If-Match", SHAData);
        String contentToPatch ="patched content";
        Request request = new Request(HttpVerb.PATCH, patchFilePath, EtagHeader, contentToPatch);
        Response response =patchHandler.getResponse(request);
        assertEquals(ResponseStatus.NOCONTENT, response.getResponseStatus());
        assertArrayEquals("patched content".getBytes(), fileContentConverter.getFullContents(testPatchFile));
    }

}
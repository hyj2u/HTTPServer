package util;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ResourceTypeIdentifierTest {

    @Test
    public void getType() {
        String filepath = "/src/test/resoucres/photo.png";
        File resource = new File(filepath);
        ResourceTypeIdentifier resourceTypeIdentifier = new ResourceTypeIdentifier();
        assertEquals(ContentType.PNG, resourceTypeIdentifier.getType(resource));
    }
}
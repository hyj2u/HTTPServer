package util;

import java.io.File;
/**
 * This class returns type of resource.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class ResourceTypeIdentifier {
    /**
     * Returns contentType of resource
     *
     * @param resource
     * @return ContentType
     * @see ContentType
     * @since 1.0
     */
    public ContentType getType(File resource){
        String resourceName = resource.getName();
        if(resource.isDirectory() || resourceName.contains(".html")){
            return ContentType.HTML;
        }else if(resourceName.contains(".jpg")){
            return ContentType.JPG;
        }else if(resourceName.contains(".gif")){
            return ContentType.GIF;
        }else if(resourceName.contains(".png")){
            return ContentType.PNG;
        }else{
            return ContentType.TXT;
        }
    }
}

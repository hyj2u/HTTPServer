package response;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ResponseTest {

    @Test
    public void clearBody() {
        Response response = new Response();
        response.setBodyContent(("{\"success\":true,\"code\":200,\"message\":\"\\uac00\\uc838\\uc624\\uae30 \\uc131\\uacf5\",\"reactionCounter\":" +
                "{\"sum\":5,\"like\":5,\"sad\":0},\"reactionActivated\":\"\"}").getBytes());
        response.clearBody();
        assertArrayEquals("".getBytes(), response.getBodyContent());
    }
}
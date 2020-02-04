package request;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class HttpVerbTest {
   @Test
    public void givenVerb_getMatchingVerb(){
       assertEquals(HttpVerb.GET,HttpVerb.find("GET"));
   }
   @Test
    public void givenVerv_matchesNotRecognized(){
       assertEquals(HttpVerb.NOTRECOGNIZED, HttpVerb.find("get"));
   }

}

package server2.request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


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

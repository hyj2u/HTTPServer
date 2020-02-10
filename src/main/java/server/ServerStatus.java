package server;

import java.io.IOException;
import java.net.Socket;

/**
 * This class manages status of server.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class ServerStatus {
    /**
     * Status of server.
     * It always returns true.
     *
     * @since 1.0
     */
    public boolean isRunning(){
        return true;
    }

}

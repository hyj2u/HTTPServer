package config;

/**
 * Configure port and path for socket connection.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class ServerConfiguration {
    /**
     * Port number for connection
     *
     * @see #setPort(int)
     * @see #getPort()
     */
    private int port;
    /**
     * Location for base resource package
     *
     * @see #setPath(String)
     * @see #getPath()
     */
    private String path;

    /**
     * This Constructor set port and path for socket connection.
     *
     * @param port port number for connection
     * @param path location for base resource package
     * @since 1.0
     */
    public ServerConfiguration(int port, String path) {
        this.port = port;
        this.path = path;
    }

    /**
     * This Constructor set default port as 8080 and path as "" for socket connection.
     *
     * @since 1.0
     */
    public ServerConfiguration() {
        this.port = 8080;
        this.path = "";
    }

    /**
     * Returns port number for server.
     *
     * @return port number
     * @since 1.0
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets port number for server.
     *
     * @param port port number for connection
     * @since 1.0
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns path for server's basic resource package
     *
     * @return location for base resource package
     * @since 1.0
     */
    public String getPath() {
        return path;
    }
    /**
     * Sets path for server.
     *
     * @param path location for base resource package
     * @since 1.0
     */
    public void setPath(String path) {
        this.path = path;
    }
}

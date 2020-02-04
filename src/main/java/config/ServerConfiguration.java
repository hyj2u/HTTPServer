package config;

public class ServerConfiguration {
    private int port;
    private String path;

    public ServerConfiguration(int port, String path) {
        this.port = port;
        this.path = path;
    }

    public ServerConfiguration() {
        this.port= 8080;
        this.path ="";
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

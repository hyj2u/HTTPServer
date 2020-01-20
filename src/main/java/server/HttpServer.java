package server;

import config.Configuration;
import config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// Driver class for the Http Server
public class HttpServer {
    public static void main(String[] args) {
        System.out.println("server starting ...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfigutation();

        System.out.println("Using Port: "+ conf.getPort());
        System.out.println("Using WebRoot: "+conf.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            //read part
            //write part
            String html="<html><head><title>HTTPServer</title></head><body><h1>This is Simple HTTP Server served by Java</h1></body></html>";
            final String CRLF="\n\r";
            String response =
                            "HTTP/1.1 200 OK"+CRLF+ //Status Line
                            "Content-Length: "+html.getBytes().length+CRLF+ //header
                            CRLF+
                            html+
                            CRLF+CRLF;
            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

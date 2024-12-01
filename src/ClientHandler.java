package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    // Required members
    private final Socket serverConn;
    private Cookie cookie;
    private List<String> cookieList;

    // Constructor
    public ClientHandler(Socket serverConn, Cookie cookie, List<String> cookieList) {
        this.serverConn = serverConn;
        this.cookie = cookie;
        this.cookieList = cookieList;
    }


    @Override
    public void run() {
        
        try {
            // Open the input stream to read client requests
            InputStream is = serverConn.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);


            // Open the output stream to write to clients
            OutputStream os = serverConn.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);


            // Get the client request and respond accordingly
            String clientRequest = "";
            while ((clientRequest = br.readLine()) != null) {

                if (clientRequest.equals("get-cookie")) {
                    // Use method to get a cookie to send to the client
                    String fortuneToSend = cookie.getFortune(cookieList);
                    // Send fortune to client
                    bw.write("cookie-text: " + fortuneToSend + "\n");
                    bw.flush();
                    System.out.println("Cookie successfully sent to client");

                } else if (clientRequest.equals("close")) {
                    System.out.println("Closing connection with client");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
}

package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//  java -cp fortunecookie.jar fc.Client localhost:12345
public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {

        // Check that file was run correctly
        if (args.length < 1) {
            System.out.println("Provide <host>:<port number>");
        }

        // Define localhost and port number
        String[] terms = args[0].split(":");
        String host = terms[0];
        int port = Integer.parseInt(terms[1]);


        // Open client's socket connection to server
        Socket clientConn = new Socket(host, port);

        // Open output stream to send (write) requests to server
        OutputStream os = clientConn.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        // Open console to get client's requests in the terminal
        Console console = System.console();

        while (true) {
            
            // Print instructions and read client's request
            System.out.println("Type [get-cookie] to recieve your fortune, or [close] to end connection");
            String clientRequest = console.readLine().toLowerCase();

            if (clientRequest.equals("get-cookie")) {

                // Send request to server
                bw.write(clientRequest + "\n");
                bw.flush();

                // Open input stream to recieve cookie from server
                InputStream is = clientConn.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);

                // Read the cookie from server, remove prefix
                String recievedCookie = br.readLine();
                System.out.println(recievedCookie.substring(13));


            } else if (clientRequest.equals("close")) {

                // Close connection with server
                bw.write(clientRequest + "\n");
                bw.flush();
                System.out.println("Ending connection with server");
                break;


            } else {

                System.out.println("Invalid request - try again");
            }
        }

        clientConn.close();

    }
    
    
}

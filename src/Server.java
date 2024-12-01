package src;

// import java.io.BufferedReader;
// import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.io.OutputStream;
// import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// java -cp fortunecookie.jar fc.Server 12345 cookie_file.txt
public class Server {

    public static void main(String[] args) throws IOException {
        
        // Check if args were entered
        if (args.length < 2) {
            System.out.println("Provide port and file");
        }


        // Define file
        String filePath = args[1];
        File cookieFile = new File(filePath);


        // Open cookie_file.txt and read the file to an arrayList
        // Initialize the cookie class and use method to read the file into cookieList ArrayList
        Cookie cookie = new Cookie();
        List<String> cookieList = cookie.getCookieList(cookieFile);
        


        // Define port and open ServerSocket
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server open, listening on port " + port);
        

        // Initialize a threadPool to handle client connections and requests
        ExecutorService threadPool = Executors.newFixedThreadPool(3);


        // Keep the server running
        while (true) {

            // Open the server's socket and accept connections
            Socket serverConn = serverSocket.accept();
            System.out.println("A client has connected");

            // Create instance of ClientHandler, pass in socket, cookie object, and cookieList
            ClientHandler clientHandler = new ClientHandler(serverConn, cookie, cookieList);
            // Give it to the threadPool
            threadPool.submit(clientHandler);

            
            /*
             *  Everything that used to be here is now done by 
             *  the clientHandler and an available thread
             *      - Opening input stream to read client requests
             *      - Opening output stream to write to client
             *      - Getting the client requests and responding accordingly
             */

        }
        

    }
}
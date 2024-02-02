package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // initialize socket and input stream
    private Socket socket;
    private ServerSocket server;
  private BufferedReader br;

    // creates a server and connects to the given port
    public Server(int port) {
        // start server and wait for a connection
        try {
            // start our server
            server = new ServerSocket(port);
            System.out.println("waiting for a client...");
            socket = server.accept();
            System.out.println("main.Client accepted");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = "";

            // reads message from client until "Stop" received
            while (!line.equals("Stop"))
            {
                try {
                    line = br.readLine();
                    System.out.println(line);
                }
                catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            socket.close();
            br.close();

        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(6666);
    }
}

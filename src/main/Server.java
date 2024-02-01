package main;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // initialize socket and input stream
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;

    // creates a server and connects to the given port
    public Server(int port) {
        // start server and wait for a connection
        try {
            // start our server
            server = new ServerSocket(port);
            System.out.println("main.Server started");

            System.out.println("waiting for a client...");

            // we accept the line in the given port
            // and create a socket
            // we now have an established connection between our client and server on the given socket
            socket = server.accept();
            System.out.println("main.Client accepted");

            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                String line = "";

                // reads message from client until "Stop" received
                while (!line.equals("Stop"))
                {
                    try {
                        line = in.readUTF();
                        System.out.println(line);
                    }
                    catch (IOException i) {
                        System.out.println(i);
                    }
                }
            System.out.println("Closing connection");

            socket.close();
            in.close();

        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(6666);
    }
}

package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * simulates printer behavior
 */

public class Server {

    // initialize socket and input stream
    private Socket socket;
    private ServerSocket server;
    private BufferedReader br;
    private PrintWriter pw;

    // creates a server and connects to the given port
    public Server(int port) {
        // start server and wait for a connection
        try {
            // start our server
            server = new ServerSocket(port);
            System.out.println("waiting for a client...");
            socket = server.accept();
            System.out.println("main.Client accepted");

            // reads input from client
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // to return string to client is a query is made
            pw = new PrintWriter(socket.getOutputStream(), true);

            String line = "";

            // reads message from client until "Stop" received
            while (!line.equals("Stop"))
            {
                // if message is a query
                try {
                    line = br.readLine();
                    if (line.equals("^0?HS")) {
                        line = hydraulicState();
                        pw.println(line);
                    }
                    if(line.equals("^0?SH")) {
                        line = serviceHistory();
                        pw.println(line);
                    }
                    if (line.equals("^0?VS")) {
                        line = serialNumber();
                        pw.println(line);
                    }
                    if (line.equals("^0?CC")) {
                        line = currentCounter();
                        pw.println(line);
                    }
                    if (line.equals("^0?SL")) {
                        line = tankLevel();
                        pw.println(line);
                    }
                    if (line.equals("^0?RS")) {
                        line = activeErrors();
                        pw.println(line);
                    }
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

    // called if command is HS and returns required PAR
    public String hydraulicState() {
        return "^0=HS7305\t2292\t20\t967\t133\t3";
    }

    // called if sent command is SH
    public String serviceHistory() {
        return "^0=SH4641\t538\t1083375067";
    }

    // called if command is VS
    public String serialNumber() {
        return "^0=VSFPDATV120\t0.0.28.148\t0.0.76.0\t28.28.127.9\tV2.0.0.20\tTyp 1 Release\tEnglish\t0\tS60.28.28.09/T2\tLJ3\t307856\t1.1 (399)";
    }

    // called if command is CC
    public String currentCounter() {
        return "^0=CC133\t0\t93164";
    }

    // called if SL is received
    public String tankLevel() {
        return "^0=SL19\t12\t0\t0";
    }

    // called if RS received
    public String activeErrors() {
        return "^0=RS4\t2\t0\t0\t0\t0";
    }

    public static void main(String[] args) {
        Server server = new Server(6666);
    }
}

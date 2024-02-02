package main;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * THIS IS THE APP WE WILL USE FOR TESTS
 * Sends new visco value to printer only if temperature changes
 * Needs to use strings to work with Leibinger
 */

public class Client {

    // we initialize our socket (tunnel)
    // and our input reader and output stream
    // we will take the input from the user
    // and send it to the socket using output stream
    private Socket socket;
    private ConnectArduino connectArduino;

    // try using string
    // reads from printer (Server)
    private BufferedReader br;
    // writes to printer (Server)
    private PrintWriter pw;


    String currentSetpoint = "SV 500";

    // constructor that takes the ip address and the port
    // also receives an instance of the arduino connector
    public Client(String address, int port, ConnectArduino connectArduino) throws IOException {
        this.connectArduino = connectArduino;
        // try to establish a connection
        try {
            // create a socket
            socket = new Socket(address, port);
            // to use STRINGS to read from printer
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // to use string to write to printer
            pw = new PrintWriter(socket.getOutputStream(), true);

        }
        catch (UnknownHostException u) {
            System.out.println(u);
        }
        catch (IOException i) {
            System.out.println(i);
        }

        // string to be used to add temp from Arduino
        String viscoSetPoint = "";
        String test = "test";

        // reads temp from arduino and sends corresponding visco correction to server (printer)
        while (!viscoSetPoint.equals("Stop")) {
            viscoSetPoint = connectArduino.getTemp();
            // check to see if a new visco setpoint needs to be sent to the server (printer)

            if(!viscoSetPoint.equals(currentSetpoint)) {
                pw.println(viscoSetPoint);  // try when connected to printer
                currentSetpoint = viscoSetPoint;
            }
        }

        // close the connection
        try
        {
           // out.close();
            socket.close();
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws IOException {
        ConnectArduino connectArduino = new ConnectArduino();
        Client client = new Client("127.0.0.1", 6666, connectArduino);
    }
}

package main;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Prod app check
 */

public class ClientSendTempsDynamically {

    private Socket socket;
    private ConnectArduino connectArduino;
    // reads from printer (Server)
    private BufferedReader br;
    // writes to printer (Server)
    private PrintWriter pw;

    String viscoSetPoint = "";
    String currentSetpoint = "SV6000";

    // constructor that takes the ip address and the port
    // also receives an instance of the arduino connector
    public ClientSendTempsDynamically(String address, int port, ConnectArduino connectArduino) throws IOException {
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

        // reads temp from arduino and sends corresponding visco correction to server (printer)
        //   while (!viscoSetPoint.equals("Stop")) {
            while (true) {
            viscoSetPoint = connectArduino.getTemp();
            // check to see if a new visco setpoint needs to be sent to the server (printer)
            if(!viscoSetPoint.equals(currentSetpoint)) {
                pw.println(viscoSetPoint);
                currentSetpoint = viscoSetPoint;
            }

        }
    }

    public static void main(String[] args) throws IOException {
        ConnectArduino connectArduino = new ConnectArduino();
        ClientSendTempsDynamically client = new ClientSendTempsDynamically("127.0.0.1", 6666, connectArduino);
    }
}

package main;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

    // get a list of queries
    List<String> queries = getQueries();

    String reply = "";

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

            // for monitoring with ? commands
            for (String item : queries) {
                pw.println(item);
                reply = br.readLine();
                System.out.println("reply received is: "+reply);
            }

            }
    }

    public List<String> getCommands() {

        List<String> commands = new ArrayList<String>();
        commands.add("Hello");
        commands.add("World");
        System.out.println(commands);

        return commands;

    }

    public List<String> getQueries() {

        List<String> queries = new ArrayList<String>();
        queries.add("^0?RS");
        queries.add("^0?SH");  // last service
        queries.add("^0?VS");  // serial number
        queries.add("^0?HS");  // pressure diaphragm position actual visco
        queries.add("^0?CC");  // current counter
        queries.add("^0?SL");  // tank level
        System.out.println(queries);

        return queries;

    }

    public static void main(String[] args) throws IOException {
        ConnectArduino connectArduino = new ConnectArduino();
        ClientSendTempsDynamically client = new ClientSendTempsDynamically("127.0.0.1", 6666, connectArduino);
    }
}

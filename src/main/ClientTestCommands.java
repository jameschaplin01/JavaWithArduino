package main;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

/**
 * Just use to test sending an receiving parameters to printer
 * Uses strings to write to printer - works on printer but needs to connect to arduino and send viso values
 */

public class ClientTestCommands {

    public static void main(String args[]) throws Exception {

        Socket socket;
        String command = "";

        // socket = new Socket("127.0.0.1", 6666);
         socket = new Socket("192.168.1.11", 3000);

        // reads from printer (Server)
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // writes to printer (Server)
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        // to enter from command prompt
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try{
                // waits for 10 seconds between loops
                Thread.sleep(1000);
            }catch(InterruptedException ex){
                //do stuff
            }

            System.out.println("Enter command");
            command = scanner.nextLine();
            // writes a string to server
            pw.println(command);

            if (command.equals("Close"))
                break;

            // If a query is sent this runs
                if (command.substring(2,3).equals("?")) {
                    String input = br.readLine();
                    System.out.println(input);
            }
        }
        br.close();
        pw.close();
        socket.close();
    }

}

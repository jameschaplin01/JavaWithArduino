package main;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

/**
 * Just use to test sending an receiving parameters to printer
 * Uses strings to write to printer - works on printer but needs to connect to arduino and send viso values
 *
 */

public class ClientTestCommands {

    public static void main(String args[]) throws Exception {

        Properties p;
        InputStream is;
        Socket socket1;
        int portNumber = 1777;
        String command = "";

        // add properties file to instance
        // add reader to get data from file
        p = new Properties();
      //  is = new FileInputStream("C:\\workspace SICPA\\CommandSendApp\\src\\main\\resources\\inks.properties");
        // loads data into properties object
      //  p.load(is);

        socket1 = new Socket("127.0.0.1", 6666);
        // socket1 = new Socket("192.168.1.11", 3000);

        // reads from printer (Server)
        BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

        // writes to printer (Server)
        PrintWriter pw = new PrintWriter(socket1.getOutputStream(), true);

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

            // Assuming socket is connected and not null

            if(socket1 != null){
                // only works when there is a reply!!!! check that the message sent is expecting a response or don't send
                String input = br.readLine();
                System.out.println(input);
                if(socket1.getInputStream().available() > 0){
                    byte[] buffer;
                    buffer = new byte[socket1.getInputStream().available()];
                    socket1.getInputStream().read(buffer);

                    // Your code here to deal with buffer.

                }
            }
        }

        br.close();
        pw.close();
        socket1.close();
    }

}

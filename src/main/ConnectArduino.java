package main;

import com.fazecast.jSerialComm.SerialPort;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectArduino {
    SerialPort sp;
    String viscoSetpoint = "";
    String setPoint = "";
    Double tempFloat = 20.00;
    Properties p;
    InputStream is;


    // constructor makes initial connection to com port
    public ConnectArduino() throws IOException {

        // add properties file to instance
        // add reader to get data from file
        p = new Properties();
        is = new FileInputStream("C:\\workspace\\JavaWithArduino\\resources\\inks.properties");
        // loads data into properties object
        p.load(is);


        sp = SerialPort.getCommPort("COM4");
        sp.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING,0,0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);

        var hasOpened = sp.openPort();
        if (!hasOpened) {
            throw new IllegalStateException("Failed to open serial port");
        }
    }

    // gets temperature from temp sensor in ink
    // checks with properties file for corresponding visco setpoint and returns string
    public String getTemp() {
        try
        {
                byte[] readBuffer = new byte[100];
                int numRead = sp.readBytes(readBuffer, readBuffer.length);
                //Convert bytes to String
                String S = new String(readBuffer, "UTF-8");
                tempFloat = Double.parseDouble(S);
                setPoint = checkSetPoint(tempFloat);
            System.out.println("setpoint is: "+ setPoint);
                System.out.println("Temperature: " +tempFloat);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return setPoint;
    }

    // query inks.properties and return asocicated viscosity setpoint
    public String checkSetPoint(Double inkTemp) {
        if(inkTemp <= 5) {
            viscoSetpoint = p.getProperty("5");
        }
        else if(inkTemp <= 10) {
            viscoSetpoint = p.getProperty("10");
        }
         else if(inkTemp <= 15) {
            viscoSetpoint = p.getProperty("15");
        }
        else if(inkTemp <= 20) {
            viscoSetpoint = p.getProperty("20");
        }
        else if(inkTemp <= 25) {
            viscoSetpoint = p.getProperty("25");
        }
        else if(inkTemp <= 30) {
            viscoSetpoint = p.getProperty("30");
        }
        else if(inkTemp <= 35) {
            viscoSetpoint = p.getProperty("35");
        }
        else if(inkTemp <= 40) {
            viscoSetpoint = p.getProperty("40");
        }
        else if(inkTemp <= 45) {
            viscoSetpoint = p.getProperty("45");
        }
        else if(inkTemp > 45) {
            viscoSetpoint = p.getProperty("50");
        }

        return viscoSetpoint;
    }

    public static void main() throws IOException {
        ConnectArduino connectArduino = new ConnectArduino();
    }
}

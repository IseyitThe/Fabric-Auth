package me.seyit.socketauthclient;

import net.fabricmc.api.ClientModInitializer;

import java.io.*;
import java.net.*;

public class SocketAuthClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        try {
            Socket socket = new Socket("localhost", 3366);
            System.out.println("Connected server localhost:3366");

            String hwid = getHWID();
            System.out.println("Client HWID: " + hwid);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println(hwid);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);

            if ("Not authorized!".equals(serverMessage)) {
                System.out.println("HWID not authorized.");
                System.exit(-1);
            } else {
                System.out.println("HWID authorized.");
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Server connection failed");
            System.exit(-1);
        }
    }

    private static String getHWID() {
        String[] components = {
                System.getProperty("user.name"),
                System.getenv("COMPUTERNAME"),
                System.getProperty("user.home"),
                System.getenv("os"),
                System.getProperty("os.version"),
                System.getProperty("os.name"),
                System.getenv("PROCESSOR_IDENTIFIER"),
                System.getenv("PROCESSOR_LEVEL")
        };

        StringBuilder encoded = new StringBuilder();
        for (String property : components) {
            if (property != null) {
                for (int i = 0; i < property.length(); i++) {
                    int value = property.charAt(i) - ' ';
                    encoded.append(value);
                }
                encoded.append("-");
            }
        }
        if (encoded.length() > 0) {
            encoded.setLength(encoded.length() - 1);
        }

        return encoded.toString();
    }
}

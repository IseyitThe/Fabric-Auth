package me.seyit.socketauthserver;

import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.HashSet;

public class SocketAuthServer {

    public static void main(String[] args) {
        try {
            Set<String> HwidList = loadHwidListFromWebsite("https://example.com/HWID.txt");

            ServerSocket serverSocket = new ServerSocket(3366);
            System.out.println("Server started. Listening port 3366....");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());

                String clientHwid = getClientHwid(clientSocket);
                System.out.println("Connected Hwid: " + clientHwid);

                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream, true);

                if (HwidList.contains(clientHwid)) {
                    out.println("Authorized!");
                } else {
                    out.println("Not authorized!");
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> loadHwidListFromWebsite(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Set<String> hwidSet = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                hwidSet.add(line);
                System.out.println("Loaded HWIDS: " + line);
            }
        } finally {
            conn.disconnect();
        }

        return hwidSet;
    }

    private static String getClientHwid(Socket clientSocket) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        return in.readLine();
    }
}
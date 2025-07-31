package org.example.services;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Serveur démarré sur le port 8080");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connexion acceptée");

            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isBlank()) {
                System.out.println(line);
            }

            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter outputStreamWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            outputStreamWriter.write("HTTP/1.1 200 OK\r\n");
            outputStreamWriter.write("Content-Type: text/html\r\n");
            outputStreamWriter.write("\r\n");
            outputStreamWriter.write("<html><body><h1>Hello World</h1></body></html>");
            outputStreamWriter.flush();

            socket.close();
        }
    }
}

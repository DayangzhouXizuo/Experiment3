package Experiment3.CS2;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234); // 监听1234端口
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // 接受客户端连接
                System.out.println("New client connected");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received: " + inputLine);

                    // 简单的请求处理
                    if (inputLine.startsWith("ADD")) {
                        out.println("Contact added");
                    } else if (inputLine.startsWith("MODIFY")) {
                        out.println("Contact modified");
                    } else if (inputLine.startsWith("DELETE")) {
                        out.println("Contact deleted");
                    } else {
                        out.println("Unknown command");
                    }
                }
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
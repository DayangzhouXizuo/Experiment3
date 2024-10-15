package Experiment3.CS2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField inputField;
    private PrintWriter out;

    public Client() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText("Enter command (ADD, MODIFY, DELETE, EXIT)\n");
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommand();
            }
        });
        frame.getContentPane().add(inputField, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void sendCommand() {
        String command = inputField.getText();
        if (command.equalsIgnoreCase("EXIT")) {
            System.exit(0);
        } else {
            textArea.append("Client: " + command + "\n");
            try {
                out.println(command); // 发送请求到服务器
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            inputField.setText("");
            textArea.append("Enter command (ADD, MODIFY, DELETE, EXIT)\n");
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            Socket socket = new Socket("localhost", 1234); // 连接到服务器的1234端口
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            client.out = new PrintWriter(socket.getOutputStream(), true);

            // 接收服务器响应的线程
            Thread readerThread = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        client.textArea.append("Server: " + line + "\n");
                        client.textArea.append("Enter command (ADD, MODIFY, DELETE, EXIT)\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
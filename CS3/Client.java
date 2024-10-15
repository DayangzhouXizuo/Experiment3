package Experiment3.CS3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField inputField;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String serverAddress, int port) {
        initialize(serverAddress, port);
    }

    private void initialize(String serverAddress, int port) {
        try {
            frame = new JFrame("Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            textArea = new JTextArea();
            textArea.setEditable(false);
            frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

            inputField = new JTextField();
            inputField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    sendCommand();
                }
            });
            frame.getContentPane().add(inputField, BorderLayout.SOUTH);

            frame.setVisible(true);

            socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String response;
                    textArea.append("Enter command (ADD, MODIFY, DELETE, EXIT)\n");
                    while ((response = in.readLine()) != null) {
                        textArea.append("Server: " + response + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand() {
        String command = inputField.getText();
        if (command.equalsIgnoreCase("EXIT")) {
            System.exit(0);
        } else {
            out.println(command);
            inputField.setText("");
            textArea.append("Client: " + command + "\n");
        }
    }

    public static void main(String[] args) {
        new Client("localhost", 1234);
    }
}
package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            String clientMessage = "";
            while (!"Exit".equals(clientMessage)) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    for (String str = in.readLine(); str != null && !str.isEmpty(); str = in.readLine()) {
                        System.out.println(str);
                        if (str.contains("?msg")) {
                            String[] strings = str.split(" ");
                            clientMessage = strings[1].substring(strings[1].indexOf("=") + 1);
                            if ("Hello".equals(clientMessage)) {
                                out.write("Hello, dear friend.".getBytes());
                            } else {
                                out.write("What?".getBytes());
                            }
                        }
                    }
                    out.flush();
                }
            }
        }
    }
}
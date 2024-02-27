import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    static String memory = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("port=");
        int port = Integer.parseInt(scanner.nextLine());

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("server starting on ip=" + InetAddress.getLocalHost() + ", port=" + port);
            while (true) {
                Socket socket = serverSocket.accept();

                new Thread(() -> {
                    try {

                        InputStream inputStream = socket.getInputStream();
                        OutputStream outputStream = socket.getOutputStream();

                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            String message = new String(buffer, 0, bytesRead);

                            String finalmsg = UserHandler.getUser(socket.getInetAddress().toString()) + ":" + message;
                            System.out.println(finalmsg);
                            if (!message.equals(" ")) {
                                MessageHandler.saveMessage(finalmsg);
                            }

                            if (message.startsWith(" !")) {
                                Commands.execute(message, socket);
                            }
                            outputStream.write(MessageHandler.readMessages().getBytes());
                            outputStream.flush();
                        }
                    } catch (IOException e) {
                        System.out.println("Error handling client connection: " + e.getMessage());
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Error closing socket: " + e.getMessage());
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }
}
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    static String memory = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverAddress = "localhost";
        int port = 8888;
        NotificationManager.setNotification_sound("information/notification.wav");

        boolean on = true;
        boolean joined = false;
        System.out.println("type !cmds to get a list of commands");
        while (on) {
            System.out.print(" #> ");
            String menuInput = scanner.nextLine();

            if (menuInput.startsWith("!join")) {
                System.out.print("ip=");
                serverAddress = scanner.nextLine();
                System.out.print("port=");
                port = Integer.parseInt(scanner.nextLine());
                joined = true;
            } else if (menuInput.startsWith("!exit")) {
                on = false;
            } else if (menuInput.startsWith("!cmds")) {
                System.out.println("Commands: \n > !join -- prompts a ip and port to join a server. \n > !cmds -- gives a list of commands. \n > !exit -- quits the menu and closes the app.");
            } else if (menuInput.startsWith("!servers")) {
                try {
                    System.out.println(new String(Files.readAllBytes(Paths.get("information/joined_servers.txt")), StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            while (joined) {
                try (Socket socket = new Socket(serverAddress, port)) {
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();

                    System.out.print(" > ");
                    String message = " " + scanner.nextLine();


                    if (message.startsWith(" !")) {
                        Commands.execute(message, socket);
                    }

                    outputStream.write(message.getBytes());
                    outputStream.flush();

                    byte[] buffer = new byte[1024];
                    int bytesRead = inputStream.read(buffer);

                    String msg = new String(buffer, 0, bytesRead);

                    NotificationManager.notification();
                    
                    System.out.println(msg);

                    if (message.equals(" !log")) {
                        joined = false;
                        try (PrintWriter writer = new PrintWriter(new FileWriter("information/joined_servers.txt", true))) {
                            writer.println(serverAddress + ":" + port);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    System.out.println("failed to connect to: ip=" + serverAddress + ", port=" + port);
                    joined = false;

                    try (PrintWriter writer = new PrintWriter(new FileWriter("information/joined_servers.txt", true))) {
                        writer.println(serverAddress + ":" + port);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }
    }
}
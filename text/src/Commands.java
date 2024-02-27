import java.net.Socket;

public class Commands {
    public static void execute(String msg, Socket socket) {
        if (msg.startsWith(" !user")) {
            UserHandler.eraseUser(socket.getInetAddress().toString());
            UserHandler.putUser(socket.getInetAddress().toString(), msg.split(" ")[2]);
        } else if (msg.startsWith(" !cls") || msg.startsWith(" !clear") || msg.startsWith(" !clr")) {
            MessageHandler.clearMessages(UserHandler.getUser(socket.getInetAddress().toString()));
        } else if (msg.startsWith(" !write")) {
            MessageHandler.saveMessage(msg.split(" ")[2]);
        } else if (msg.startsWith(" !save") || msg.startsWith(" !s")) {
            Main.memory = msg.split(" ")[2];
        } else if (msg.startsWith(" !read") || msg.startsWith(" !r")) {
            MessageHandler.saveMessage(Main.memory);
        }
    }
}

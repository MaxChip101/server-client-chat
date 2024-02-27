import java.net.Socket;

public class Commands {

    public static void execute(String msg, Socket socket) {
        if (msg.startsWith(" !cmds") || msg.startsWith(" !help") || msg.startsWith(" !commands")) {
            System.out.println("Commands: \n > !user [arg1] -- changes the username of your ip address. \n > !cls/!clear/!clr -- clears the messages and writes who cleared the messages. \n > !write/!w [arg1] -- writes a message. \n > !cmds/!help/!commands -- gives a list of commands. \n > !save/!s [arg1] -- saves a message in a variable on the server. \n > !read/!r -- reads the message saved on the server variable. \n > !csave/!cs [arg1] -- saves a message on your local client. \n > !cread/!cr -- reads the message saved in a variable on your local client. \n > !log -- disconnects from the server you are connected to and returns you to the menu.");
        } else if (msg.startsWith(" !csave") || msg.startsWith(" !cs")) {
            Main.memory = msg.split(" ")[2];
        } else if (msg.startsWith(" !cread") || msg.startsWith(" !cr")) {
            System.out.println(Main.memory);
        }
    }

}

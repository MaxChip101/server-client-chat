public class ServerClientCommands {
    public static void parse(String message) {
        if (message.equals(";n")) {
            NotificationManager.notification();
        }
    }
}

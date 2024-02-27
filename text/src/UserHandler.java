import java.io.*;

public class UserHandler {
    static String filePath = "information/users.txt";

    public static void putUser(String ip, String username) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(ip + "," + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void eraseUser(String ip) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split(",");
                if (!splitted[0].equals(ip)) {
                    content.append(line).append(System.lineSeparator());
                }
            }


            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(content.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUser(String ip) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(ip)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown_User";
    }


}

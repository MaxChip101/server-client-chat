import java.io.*;
import java.util.Properties;

public class SettingsManager {

    static String filePath = "information/rules.set";

    public static void addRule(String rule, String value) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(rule + "=" + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void eraseRule(String rule) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split("=");
                if (!splitted[0].equals(rule)) {
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

    public static String readRuleVal(String rule) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2 && parts[0].equals(rule)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
}

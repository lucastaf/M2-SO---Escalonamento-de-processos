package GPT;

import java.io.*;
import java.util.*;

public class ProcessCreator {
    public static List<GPT.Process> parse(String filePath) throws IOException {
        List<GPT.Process> processes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\|");
            String id = parts[0].trim();
            int arrival = Integer.parseInt(parts[1].trim());
            int exec1 = Integer.parseInt(parts[2].trim());
            boolean hasIO = parts[3].trim().equalsIgnoreCase("yes");
            int waitIO = Integer.parseInt(parts[4].trim());
            int exec2 = Integer.parseInt(parts[5].trim());

            processes.add(new Process(id, arrival, exec1, hasIO, waitIO, exec2));
        }
        return processes;
    }
}

package GPT;

import java.util.*;

public class Timeline {
    private List<List<String>> coreTimelines;

    public Timeline(int coreCount) {
        coreTimelines = new ArrayList<>();
        for (int i = 0; i < coreCount; i++) {
            coreTimelines.add(new ArrayList<>());
        }
    }

    public void addTick(int coreIndex, String processId) {
        coreTimelines.get(coreIndex).add(processId);
    }

    public void print() {
        for (int i = 0; i < coreTimelines.size(); i++) {
            System.out.print("Core " + i + ": ");
            for (String tick : coreTimelines.get(i)) {
                System.out.print(tick + " ");
            }
            System.out.println();
        }
    }
}

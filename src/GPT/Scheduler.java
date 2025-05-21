package GPT;

import java.util.*;

public class Scheduler {
    private final int quantum;
    private final List<CPUCore> cores;
    private final Queue<Process> readyQueue = new LinkedList<>();
    private final List<Process> ioQueue = new ArrayList<>();
    private final List<Process> allProcesses = new ArrayList<>();
    private final Timeline timeline;

    public Scheduler(int quantum, int coreCount, List<Process> processList) {
        this.quantum = quantum;
        for (Process p : processList) allProcesses.add(p);
        cores = new ArrayList<>();
        for (int i = 0; i < coreCount; i++) cores.add(new CPUCore());
        timeline = new Timeline(coreCount);
    }

    public void runSimulation() {
        int time = 0;
        while (!allProcesses.stream().allMatch(Process::isComplete)) {
            // Admit new processes
            for (Process p : allProcesses) {
                if (p.arrivalTime == time) readyQueue.add(p);
            }

            // IO queue
            Iterator<Process> it = ioQueue.iterator();
            while (it.hasNext()) {
                Process p = it.next();
                p.ioCountdown--;
                if (p.ioCountdown <= 0) {
                    p.inIO = false;
                    readyQueue.add(p);
                    it.remove();
                }
            }

            // Assign cores
            for (int i = 0; i < cores.size(); i++) {
                CPUCore core = cores.get(i);

                if (core.isIdle()) {
                    if (!readyQueue.isEmpty()) {
                        core.currentProcess = readyQueue.poll();
                        core.currentProcess.contextSwitches++;
                        core.timeSliceRemaining = quantum;
                    }
                } else {
                    core.currentProcess.remainingTime--;
                    core.timeSliceRemaining--;
                    timeline.addTick(i, core.currentProcess.id);

                    if (core.currentProcess.remainingTime <= 0) {
                        if (core.currentProcess.hasIO && !core.currentProcess.inIO) {
                            core.currentProcess.enterIO();
                            ioQueue.add(core.currentProcess);
                        } else {
                            core.currentProcess.finished = true;
                            core.currentProcess.turnaroundTime = time + 1 - core.currentProcess.arrivalTime;
                        }
                        core.currentProcess = null;
                    } else if (core.timeSliceRemaining <= 0) {
                        readyQueue.add(core.currentProcess);
                        core.currentProcess = null;
                    }
                }

                // Idle cores
                if (core.isIdle()) {
                    timeline.addTick(i, ".");
                }
            }

            // Update wait time
            for (Process p : readyQueue) p.waitTime++;

            time++;
        }

        printResults();
    }

    private void printResults() {
        System.out.println("ID\tWait\tTurnaround\tContext Switches");
        for (Process p : allProcesses) {
            System.out.printf("%s\t%d\t%d\t\t%d%n", p.id, p.waitTime, p.turnaroundTime, p.contextSwitches);
        }
        System.out.println("\nTimeline:");
        timeline.print();
    }
}

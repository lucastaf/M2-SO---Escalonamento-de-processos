package GPT;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Process> processes = ProcessCreator.parse("GPT/processes.txt");

        //Tempo de execução máximo de um processo
        int quantum = 4;
        //Quantidade de núcleos da CPU
        int cores = 2;

        Scheduler scheduler = new Scheduler(quantum, cores, processes);
        scheduler.runSimulation();
    }
}

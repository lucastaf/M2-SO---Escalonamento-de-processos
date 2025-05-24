import lib.FutureProcess;
import lib.ProcessParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<FutureProcess> futureProcesses = ProcessParser.parseFile("src/lib/processes.txt");
        CPUCore[] cores = new CPUCore[4];
        for (int i = 0; i < cores.length; i++) {
            cores[i] = new CPUCore(i);
        }
        CPUScheduler Scheduler = new CPUScheduler(cores, 3);
        for (CPUCore Core : cores){
            Core.setRegisterEvent(Scheduler);
        }
        while (!Scheduler.finished || !futureProcesses.isEmpty()) {
            if (!futureProcesses.isEmpty()) {
                for (int index = 0; index < futureProcesses.size(); index++) {
                    FutureProcess Process = futureProcesses.get(index);
                    if (Scheduler.getCounter() >= Process.initTime) {
                        Scheduler.includeProcess(Process.process);
                        futureProcesses.remove(index);
                        index --;

                    }
                }
            }
            System.out.println("Iniciando novo ciclo: " + Scheduler.getCounter());
            Scheduler.runScheduler();
            System.out.println();
        }

        System.out.println(Scheduler.registers);
        System.out.println("Todos processos rodados");
    }

}

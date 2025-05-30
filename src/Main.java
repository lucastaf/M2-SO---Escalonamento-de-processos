import lib.FutureProcess;
import lib.ProcessParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        while (!Scheduler.IsSchedulerEnded() || !futureProcesses.isEmpty()) {
            if (!futureProcesses.isEmpty()) {
                for (int index = 0; index < futureProcesses.size(); index++) {
                    FutureProcess Process = futureProcesses.get(index);
                    if (Scheduler.getCounter() >= Process.initTime) {
                        Scheduler.includeReadyProcess(Process.process);
                        futureProcesses.remove(index);
                        index --;

                    }
                }
            }
            System.out.println("Iniciando novo ciclo: " + Scheduler.getCounter());
            Scheduler.runScheduler();
            System.out.println();
        }

        System.out.println("Todos processos rodados");
        try{

        File outPutFile = new File("output.txt");
        FileWriter writer = new FileWriter(outPutFile);
        writer.write(Scheduler.registers);
        writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

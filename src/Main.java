import lib.FutureProcess;
import lib.ProcessParser;

public class Main {
    public static void main(String[] args) {
        FutureProcess newProcess = ProcessParser.parseInstruction("Novo_ID 2 3 sim 2 3");
        System.out.println(newProcess.process.id);
        CPUCore[] cores = new CPUCore[4];
        for(int i = 0; i < cores.length; i++){
            cores[i] = new CPUCore();
        }
        CPUScheduler Scheduler = new CPUScheduler(cores);
        Scheduler.includeProcess(newProcess.process);
        while (!Scheduler.finished) {
            Scheduler.runScheduler();
        }
    }

}

import lib.FutureProcess;
import lib.ProcessParser;

public class Main {
    public static void main(String[] args) {
        FutureProcess newProcess = ProcessParser.parseInstruction("Novo_ID 2 10 sim 5 10");
        System.out.println(newProcess.process.id);
        CPUCore core = new CPUCore();
        core.setProcess(newProcess.process, 3);
        while (true) {
            core.runInstruction();
            if (newProcess.process.waitTime > 0) {
                System.out.println("Processo aguardando");
                newProcess.process.processWait();
            } else {

                if (newProcess.process.finished) {
                    System.out.println("Processo finalizado");
                    break;
                }
                if (core.isFree()) {
                    System.out.println("Realocando espaço");
                    core.setProcess(newProcess.process, 3);
                }
            }
        }
    }

}

import lib.Process;

import java.util.ArrayList;
import java.util.List;

public class CPUScheduler {
    CPUCore[] Cores;
    List<lib.Process> Processes = new ArrayList<lib.Process>();
    List<lib.Process> EndedProcesses = new ArrayList<lib.Process>();
    public boolean finished = true;
    private int schedulerTime = 0;
    private int counter;


    public int getCounter(){
        return this.counter;
    }

    public CPUScheduler(CPUCore[] Cores, int schedulerTime) {
        this.Cores = Cores;
        this.schedulerTime = schedulerTime;
    }


    void runScheduler() {
        //Checa por processos em espera
        checkProcesses();

        if(this.Processes.isEmpty()){
            this.finished = true;
            return;
        }
        for (CPUCore CPU : Cores) {
            if (CPU.isFree()) {
                for(Process process : Processes) {
                    if (!process.isRunning && process.waitTime == 0 && !process.finished) {
                        System.out.println("Realocando espaço");
                        CPU.setProcess(process, schedulerTime);
                    }
                }
            }
            CPU.runInstruction();
            //Alocação dos processos
        }
    }

    public void includeProcess(lib.Process process) {
        this.Processes.add(process);
        finished = false;
    }

    void checkProcesses() {
        for (int index = 0; index < Processes.size(); index++) {
            Process process = Processes.get(index);
            process.processWait();

            //Se o processo finalizar após a espera, desaloque da lista;
            if (process.finished) {
                EndedProcesses.add(process);
                Processes.remove(index);
                index--;
            }
        }
    }


}

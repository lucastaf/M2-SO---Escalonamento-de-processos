import lib.OnRegisterEvent;
import lib.Process;
import lib.ProcessParser;

import java.util.ArrayList;
import java.util.List;

public class CPUScheduler implements OnRegisterEvent {
    CPUCore[] Cores;
    List<lib.Process> Processes = new ArrayList<lib.Process>();
    List<lib.Process> EndedProcesses = new ArrayList<lib.Process>();
    public boolean finished = true;
    private int schedulerTime = 0;
    private int counter = 0;
    public String registers = "";


    public int getCounter() {
        return this.counter;
    }

    public CPUScheduler(CPUCore[] Cores, int schedulerTime) {
        this.Cores = Cores;
        this.schedulerTime = schedulerTime;
    }


    void runScheduler() {
        //Checa por processos em espera
        checkProcesses();

        if (this.Processes.isEmpty()) {
            this.finished = true;
            counter++;
            return;
        }
        for (CPUCore CPU : Cores) {
            if (CPU.isFree()) {
                for (Process process : Processes) {
                    //Aloca se, a cpu ta livre, o processo não ta rodando, o processo não ta esperando e o processo não ta finalizado
                    if (CPU.isFree() && !process.isRunning && process.waitTime == 0 && !process.finished) {
                        System.out.println("CPU " + CPU.id + ": Realocando espaço: " + process.id);
                        CPU.setProcess(process, schedulerTime);
                        registers += "IN " + CPU.id + " " + process.id + "\n";
                        break;
                    }
                }
            }
            CPU.runInstruction();
            //Alocação dos processos
        }
        counter++;
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

    @Override
    public void onProcessFree(int CoreId, String ProcessesID) {
        this.registers += "OUT " + CoreId + " " + ProcessesID + "\n";
    }
}

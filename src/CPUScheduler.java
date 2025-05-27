import lib.OnRegisterEvent;
import lib.Process;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CPUScheduler implements OnRegisterEvent {
    private final Queue<CPUCore> ReadyCores = new LinkedList<>();
    private final Queue<Process> ReadyProcesses = new LinkedList<>();
    private final List<Process> WaitingProcesses = new LinkedList<>();
    private final List<CPUCore> RunningCores = new LinkedList<>();
    private int quantumTime = 0;
    private int counter = 0;
    public String registers = "";


    public CPUScheduler(CPUCore[] Cores, int quantumTime) {
        this.ReadyCores.addAll(Arrays.asList(Cores));
        this.quantumTime = quantumTime;
    }

    public int getCounter() {
        return this.counter;
    }


    void runScheduler() {
        //Checa por processos em espera
        CheckWaitingProcess();
        RunAllCores();

        //Alocação dos processos
        while (!ReadyCores.isEmpty() && !ReadyProcesses.isEmpty()) {
            Process nextProcess = ReadyProcesses.poll();
            CPUCore core = ReadyCores.poll();
            System.out.println("CPU " + core.id + ": Realocando espaço: " + nextProcess.id);
            core.setProcess(nextProcess, quantumTime);
            RunningCores.add(core);
            registers += "IN " + this.counter + " " + core.id + " " + nextProcess.id + "\n";
            core.runInstruction();
        }

        counter++;
    }


    public boolean IsSchedulerEnded() {
        //Se não houver processo pronto, não houver processo esperando e não tiver nenhuma CPU ocupada, acabou;
        return this.ReadyProcesses.isEmpty() && this.WaitingProcesses.isEmpty() && this.RunningCores.isEmpty();
    }

    private void RunAllCores() {
        for (int index = 0; index < RunningCores.size(); index++) {
            CPUCore currentCore = RunningCores.get(index);
            currentCore.runInstruction();
            if (currentCore.isFree()) {
                this.ReadyCores.add(currentCore);
                this.RunningCores.remove(index);
                index--;
            }
        }
    }

    private void CheckWaitingProcess() {
        for (int index = 0; index < WaitingProcesses.size(); index++) {
            Process process = WaitingProcesses.get(index);
            process.processWait();
            if (!process.isWaiting()) {
                if (process.hasMoreInstruction()) {
                    ReadyProcesses.add(process);
                }
                WaitingProcesses.remove(index);
                index--;
            }
        }
    }

    @Override
    public void includeReadyProcess(lib.Process process) {
        this.ReadyProcesses.add(process);
    }

    @Override
    public void onProcessFree(int CoreId, Process process, String Reason) {
        if (process.hasMoreInstruction()) {
            this.WaitingProcesses.add(process);
        }
        System.out.println("CPU " + CoreId + ": " + "Liberando processo: " + process.id+ ", " + Reason);
        this.registers += Reason + " " + this.counter + " " + CoreId + " " + process.id + "\n";
    }
}

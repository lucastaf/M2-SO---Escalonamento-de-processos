import lib.OnRegisterEvent;
import lib.Process;

public class CPUCore {
    Process currentProcess = null;
    private int scheduler;
    private boolean isFree = true;
    public int id = 0;
    private OnRegisterEvent registerEvent;

    public CPUCore(int id) {
        this.id = id;
    }

    public void setRegisterEvent(OnRegisterEvent registerEvent) {
        this.registerEvent = registerEvent;
    }

    public void setProcess(Process currentProcess, int scheduler) {
        if (this.isFree) {
            this.currentProcess = currentProcess;
            this.scheduler = scheduler;
            this.isFree = false;
        }
    }

    public boolean isFree() {
        return isFree;
    }

    void runInstruction() {
        if (currentProcess != null && !this.isFree) {
            int currentInstruction = currentProcess.instructions[currentProcess.currentInstructionCounter];
            if (currentInstruction <= 0) {
                System.out.println("CPU " + this.id + ": Rodando processo: " + currentProcess.id);
                currentProcess.isRunning = true;
                currentProcess.currentInstructionCounter++;
                scheduler--;
                //Verifica se o processo encerrou
                if (currentProcess.instructions.length <= currentProcess.currentInstructionCounter) {
                    this.registerEvent.onProcessFree(this.id, currentProcess.id, "ended");
                    isFree = true;
                    currentProcess.isRunning = false;
                    currentProcess.finished = true;
                    return;
                }
                if (scheduler <= 0) {
                    isFree = true;
                    currentProcess.isRunning = false;
                    this.registerEvent.onProcessFree(this.id, currentProcess.id, "scheduler");
                }
            } else {
                currentProcess.waitTime = currentInstruction;
                this.registerEvent.onProcessFree(this.id, currentProcess.id, "IO");
                currentProcess.isRunning = false;
                isFree = true;
            }

        }
    }


}

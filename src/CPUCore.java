import lib.OnRegisterEvent;
import lib.Process;

public class CPUCore {
    Process currentProcess = null;
    private int quantum;
    public int id = 0;
    private OnRegisterEvent registerEvent;

    public CPUCore(int id) {
        this.id = id;
    }

    public boolean isFree() {
        return currentProcess == null;
    }

    public void setRegisterEvent(OnRegisterEvent registerEvent) {
        this.registerEvent = registerEvent;
    }

    public void setProcess(Process currentProcess, int quantum) {

        this.currentProcess = currentProcess;
        this.quantum = quantum;

    }

    void runInstruction() {
        if (currentProcess != null) {

            //Verifica se entrou em estado de espera
            if(currentProcess.isWaiting()){
                this.registerEvent.onProcessFree(this.id, currentProcess, "IO");
                this.currentProcess = null;
                return;
            }


            currentProcess.passToNextInstruction(this.id);
            quantum--;
            //Verifica se o processo encerrou
            if (!currentProcess.hasMoreInstruction()) {
                this.registerEvent.onProcessFree(this.id, currentProcess, "ended");
                this.currentProcess = null;
                return;
            }



            //Verifica se o tempo de quantum acabou
            if (quantum <= 0) {
                this.registerEvent.onProcessFree(this.id, currentProcess, "scheduler");
                this.currentProcess = null;
                return;
            }


        }

    }
}



import lib.Process;

public class CPUCore {
    Process currentProcess = null;
    private int scheduler;
    private boolean isFree = true;

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
                System.out.println("Rodando processo: " + currentProcess.id +".\n");
                currentProcess.isRunning = true;
                currentProcess.currentInstructionCounter++;
                scheduler--;
                if(scheduler <= 0){
                    isFree = true;
                    currentProcess.isRunning = false;
                }
                //Verifica se o processo encerrou
                if (currentProcess.instructions.length <= currentProcess.currentInstructionCounter) {
                    isFree = true;
                    currentProcess.isRunning = false;
                    currentProcess.finished = true;
                }
            } else {
                currentProcess.waitTime = currentInstruction;
                currentProcess.isRunning = false;
                isFree = true;
            }

        }
    }


}

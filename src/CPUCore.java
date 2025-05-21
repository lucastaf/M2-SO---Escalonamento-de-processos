public class CPUCore {
    Process currentProcess = null;
    private int scheduler;
    private boolean isFree = true;

    public boolean isFree() {
        return isFree;
    }

    void runInstruction(){
        if(currentProcess != null){
            int currentInstruction = currentProcess.instructions[currentProcess.currentInstructionCounter];
            if(currentInstruction <= 0){
                currentProcess.currentInstructionCounter++;
                scheduler--;
                //Verifica condição de parada
                if(scheduler == 0 || currentProcess.instructions.length >= currentProcess.currentInstructionCounter) {
                    currentProcess.finished = true;
                    isFree = true;
                }
                isFree = false;
            }else{
                currentProcess.waitTime = currentInstruction;
                isFree = true;
            }

        }
    }


}

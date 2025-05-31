package lib;

public class Process {
    public String id;
    private final int[] instructions;
    private int waitTime = 0
            ;
    private int currentInstructionCounter = 0;

    public boolean isWaiting(){
        return waitTime > 0;
    }

    public void passToNextInstruction(int processorID){
        System.out.println("CPU " + processorID + ": Rodando processo: " + this.id);
        if(currentInstructionCounter <= instructions.length - 1){
            currentInstructionCounter++;
            if(!this.hasMoreInstruction()){
                return;
            }
            if(this.getCurrentInstruction() > 0 ){
                this.waitTime = this.getCurrentInstruction();
            }
        }
    }

    public void processWait() {
        if (this.waitTime > 0) {
            System.out.println("Processo aguardando: " + this.id);
            this.waitTime--;
            if (this.waitTime <= 0) {
                this.currentInstructionCounter++;
            }
        }
    }

    public boolean hasMoreInstruction(){
        return (this.instructions.length -1 >= this.currentInstructionCounter);
    }

    private int getCurrentInstruction() {
        return instructions[currentInstructionCounter];
    }

    public Process(String id, int[] instructions) {
        this.id = id;
        this.instructions = instructions;
    }
}

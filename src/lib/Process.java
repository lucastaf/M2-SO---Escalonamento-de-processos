package lib;

public class Process {
    public String id;
    public int[] instructions;
    public int waitTime = 0;
    public int currentInstructionCounter = 0;
    public boolean finished = false;
    public boolean isRunning = false;
    public int initTime;
    public int endTime;

    public void processWait() {
        if (this.waitTime > 0) {
            System.out.println("Processo aguardando: " + this.id);
            this.waitTime--;
            if (this.waitTime <= 0) {
                this.currentInstructionCounter++;
                if (this.currentInstructionCounter >= this.instructions.length) {
                    this.finished = true;
                }
            }
        }
    }

    public int getCurrentInstruction() {
        return instructions[currentInstructionCounter];
    }

    public Process(String id, int[] instructions) {
        this.id = id;
        this.instructions = instructions;
    }
}

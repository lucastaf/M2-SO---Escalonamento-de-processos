public class Process {
    String id;
    public int[] instructions;
    public int waitTime = 0;
    public int currentInstructionCounter = 0;
    public boolean finished = false;

    public Process(String id, int[] instructions){
        this.id = id;
        this.instructions = instructions;
    }
}

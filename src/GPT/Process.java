package GPT;

public class Process {
    public String id;
    public int arrivalTime;
    public int exec1;
    public boolean hasIO;
    public int ioTime;
    public int exec2;

    public int remainingTime;
    public int waitTime = 0;
    public int turnaroundTime = 0;
    public int contextSwitches = 0;
    public boolean inIO = false;
    public int ioCountdown = 0;
    public boolean finished = false;

    public Process(String id, int arrivalTime, int exec1, boolean hasIO, int ioTime, int exec2) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.exec1 = exec1;
        this.hasIO = hasIO;
        this.ioTime = ioTime;
        this.exec2 = exec2;
        this.remainingTime = exec1;
    }

    public boolean isComplete() {
        return finished;
    }

    public void enterIO() {
        inIO = true;
        ioCountdown = ioTime;
        remainingTime = exec2;
    }
}

package GPT;

public class CPUCore {
    public Process currentProcess = null;
    public int timeSliceRemaining = 0;

    public boolean isIdle() {
        return currentProcess == null;
    }
}

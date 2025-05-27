package lib;

public interface OnRegisterEvent
{
    void onProcessFree(int CoreId, Process Process, String reason);
    void includeReadyProcess(lib.Process process);
}

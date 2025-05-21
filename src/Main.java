import lib.FutureProcess;
import lib.ProcessParser;

public class Main {
    public static void main(String[] args) {
        FutureProcess newProcess = ProcessParser.parseInstruction("Novo_ID 2 10 sim 5 10");
        System.out.println(newProcess.process.id);
    }

}

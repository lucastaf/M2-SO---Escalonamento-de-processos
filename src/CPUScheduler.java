import java.util.List;

public class CPUScheduler {
    CPUCore[] Cores;
    List<Process> Processes;


    void runScheduler(){
        //Checa por processos em espera
        checkWaitingProcesses();

        for(CPUCore CPU : Cores){
            //Alocação dos processos
        }
    }

    public void includeProcess(Process process){
        this.Processes.add(process);
    }

    void checkWaitingProcesses(){
        for (int index = 0; index < Processes.size(); index ++){
            Process process = Processes.get(index);
            //Se o processo estiver em espera, diminua o tempo de espera
            if(process.waitTime > 0){
                process.waitTime--;
                //Se o tempo de espera acabar, pule para a próxima instrução
                if(process.waitTime <= 0){
                    process.currentInstructionCounter++;
                    //Se o processo finalizar após a espera, desaloque da lista;
                    if(process.currentInstructionCounter >= process.instructions.length){
                        Processes.remove(index);
                        index --;
                    }
                }
            }
        }
    }


}

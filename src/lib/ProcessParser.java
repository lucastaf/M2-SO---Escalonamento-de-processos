package lib;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ProcessParser {

    private ProcessParser() {
    }

    public static List<FutureProcess> parseFile(String fileName) {
        try {
            File FileObj = new File(fileName);
            Scanner fileReader = new Scanner(FileObj);
            List<FutureProcess> Processes = new ArrayList<FutureProcess>();
            while (fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                if(line.startsWith("#") || line.isEmpty()){
                    continue;
                }
                Processes.add(ProcessParser.parseInstruction(line));
                System.out.println(line);
            }
            return Processes;
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler o arquivo");
            return new ArrayList<>();
        }
    }

    ;

    public static FutureProcess parseInstruction(String instruction) {
        // ID | Tempo de Chegada | Execucao1 | Bloqueio? | Espera | Execucao2
        String[] tokens = instruction.split(" ");
        String id = tokens[0];
        int initTime = Integer.parseInt(tokens[1]);
        int[] Instructions = getIntegers(tokens);

        Process newProcess = new Process(id, Instructions);
        return new FutureProcess(newProcess, initTime);


    }

    private static int[] getIntegers(String[] tokens) {
        int exec1Time = Integer.parseInt(tokens[2]);
        boolean haveWait = tokens[3].equals("sim");
        int waitTime = Integer.parseInt(tokens[4]);
        int exec2Time = Integer.parseInt(tokens[5]);

        List<Integer> Instructions = new ArrayList<Integer>();
        for (int i = 0; i < exec1Time; i++) {
            Instructions.add(0);
        }
        if (haveWait) {
            Instructions.add(waitTime);
        }
        for (int i = 0; i < exec2Time; i++) {
            Instructions.add(0);
        }
        return Instructions.stream().mapToInt(i -> i).toArray();
    }
}

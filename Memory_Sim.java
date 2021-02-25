/*
    Programmer: Hugh Masters
    Date Of Modification: 31/10/2020
    Program: Simulates execution of processes and memory use
 */

import java.io.*;
import java.util.*;

public class Memory_Sim {
    public static void main(String args[]) throws FileNotFoundException {
        final int frames = Integer.parseInt(args[0]); //Number of frames available
        final int quantum = Integer.parseInt(args[1]); //Quantum size of round-robin
        Process.setQ(quantum);
        Process[] Processes = new Process[args.length-2]; //System Processes for LRU
        Process[] clk_Proc = new Process[args.length-2]; //Process for Clock
        for (int i=0;i<args.length-2;i++){ //Create processes
            Scanner reader = new Scanner(new FileInputStream(args[i+2]));
            String line = reader.nextLine();
            if (line.equals("begin")){
                int[] arr = new int[50];
                line = reader.nextLine();
                int k=0;
                while (!line.equals("end")){
                    arr[k] = Integer.parseInt(line);
                    line = reader.nextLine();
                    k++;
                }
                Processes[i] = new Process(arr,i+1, (int) Math.floor(frames/(args.length-2)),"LRU");
                clk_Proc[i] = new Process(arr,i+1, (int) Math.floor(frames/(args.length-2)),"CLOCK");
            }
        }
        int Complete = 0;
        int time=0;
        int rr=0;

        while (Complete<args.length-2){
            int count=0;
            while(!Processes[rr].run(time) && count < args.length-2){
                rr++;
                count++;
                if (rr>=args.length-2){
                    rr=0;
                }
            }
            if (Processes[rr].isDone()) {
                Complete++;
            }
            time++;
        }

        Complete=0;
        time=0;
        rr=0;

        while (Complete<args.length-2){
            int count=0;
            while(!clk_Proc[rr].run(time) && count < args.length-2){
                rr++;
                count++;
                if (rr>=args.length-2){
                    rr=0;
                }
            }
            if (clk_Proc[rr].isDone()) {
                Complete++;
            }
            time++;
        }

        System.out.println("LRU - Fixed:");
        System.out.printf("%-6s%-16s%-20s%-12s%-20s\n", "PID","Process Name","Turnaround Time","#Faults","Fault Times");
        for (int i=1;i<args.length-1;i++){
            String TT = Processes[i-1].getTT();
            String no_f = Processes[i-1].getF();
            String Faults = Processes[i-1].getFaults();
            System.out.printf("%-6s%-16s%-20s%-12s%-20s\n", String.valueOf(i),args[i+1],TT,no_f,Faults);
        }
        System.out.println("\n------------------------------------------------------------\n");

        System.out.println("Clock - Fixed:");
        System.out.printf("%-6s%-16s%-20s%-12s%-20s\n", "PID","Process Name","Turnaround Time","#Faults","Fault Times");
        for (int i=1;i<args.length-1;i++){
            String TT = clk_Proc[i-1].getTT();
            String no_f = clk_Proc[i-1].getF();
            String Faults = clk_Proc[i-1].getFaults();
            System.out.printf("%-6s%-16s%-20s%-12s%-20s\n", String.valueOf(i),args[i+1],TT,no_f,Faults);
        }
    }
}

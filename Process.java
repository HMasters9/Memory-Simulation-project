/*
    Programmer: Hugh Masters
    Date Of Modification: 31/10/2020
    Program: Class for storing process information
 */

public class Process {
    private int[] pages = new int[51]; //Pages used by process
    private int id; //Id of process
    private Memory m; //Memory for each process
    private static int q; //Quantum for Round-Robin
    private int x=0; //index of current instruction of process
    private boolean isFinished;
    private int quantumLock=0;
    private int releaseTime=0;
    private String faults = "";
    private String TT; //Turnaround time
    private int F=0; //Number of faults

    //Constructor
    public Process(int[] arr, int k, int mem_size, String type) {
        for (int i=0;i<50;i++){
            pages[i]=arr[i];
        }
        id=k;
        if (type.equals("LRU"))
            m = new LRU_mem(mem_size);
        if (type.equals("CLOCK"))
            m=new Clock_mem(mem_size);
    }

    //Sets quantum for all processes
    public static void setQ(int quantum) {
        q=quantum;
    }

    //Method for execution of process on processor
    //Returns true if some work is done
    //Returns false if blocked
    public boolean run(int time) {
        if (isFinished)
            return false;
        if (time >=releaseTime){
            if (quantumLock<q){
                quantumLock++;
                if (m.hasPage(pages[x])){
                    x++;
                    if (pages[x]==0) {
                        isFinished = true;
                        TT = String.valueOf(time+1);
                    }
                    return true;
                }
                else{
                    F++;
                    faults = faults+", "+time;
                    quantumLock=0;
                    releaseTime=time+6;
                    m.fetch(pages[x]);
                    return false;
                }
            }
            else{
                quantumLock=0;
                return false;
            }
        }
        else
            return false;
    }

    //Return true exactly once after process completes
    private boolean returned=false;
    public boolean isDone() {
        if (isFinished&& !returned){
            returned = true;
            return true;
        }
        else return false;
    }

    //Returns turnaround time of completed process
    public String getTT() {
        return TT;
    }

    //Returns number of faults
    public String getF() {
        return String.valueOf(F);
    }

    //Returns string of all fault times
    public String getFaults() {
        return "{"+faults.substring(2)+"}";
    }
}

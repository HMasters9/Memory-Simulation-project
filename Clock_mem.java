/*
    Programmer: Hugh Masters
    Date Of Modification: 31/10/2020
    Program: Simulates clock memory
 */

public class Clock_mem extends Memory{
    private int index=0; //Index for circular memory
    private int[] bits; //Array of clock bits

    //Constructor
    public Clock_mem(int frames) {
        super(frames);
        bits = new int[frames];
    }

    //Finds desired page, increases clock bit to 1
    //Returns true if page in memory
    public boolean hasPage(int page) {
        for (int i=0;i< memory.length;i++){
            if (page == memory[i]){
                bits[i]=1;
                return true;
            }
        }
        return false;
    }

    //Finds next page from index which has clock bit = 0
    //And replaces it
    public void replace(int page){
        for (int i=0;i< memory.length+1;i++){
            if (bits[index]==0){
                bits[index]=1;
                memory[index]=page;
                index++;
                if (index> bits.length-1)
                    index=0;
                return;
            }
            else{
                bits[index]=0;
                index++;
                if (index> bits.length-1)
                    index=0;
            }
        }
    }
}

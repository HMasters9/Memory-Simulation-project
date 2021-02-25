/*
    Programmer: Hugh Masters
    Date Of Modification: 31/10/2020
    Program: Simulates memory with restricted number of frames
 */

public class Memory {
    protected int[] memory;

    //Constructor
    public Memory(int frames) {
        memory = new int[frames];
    }

    //Searches Memory for particular page
    public boolean hasPage(int page) {
        for (int i=0;i< memory.length;i++){
            if (page == memory[i]){
                access(i);
                return true;
            }
        }
        return false;
    }

    //For overwrite by child classes
    public void access(int i) {
    }

    //Moves page into main memory
    public void fetch(int page) {
        for (int i=0;i< memory.length;i++){
            if (memory[i]==page)
                return;
            if (memory[i]==0){
                memory[i]=page;
                return;
            }
        }
        replace(page);
    }

    //Implement replacement policy by child classes
    public void replace(int page) {
    }
}

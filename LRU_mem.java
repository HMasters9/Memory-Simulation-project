/*
    Programmer: Hugh Masters
    Date Of Modification: 31/10/2020
    Program: Simulates Least Recently Used memory
 */


public class LRU_mem extends Memory{

    //Constructor
    public LRU_mem(int frames) {
        super(frames);
    }

    //Accesses item at index i, and moves it to the front of the memory
    public void access(int i){
        while (i>=1){
            int a = memory[i-1];
            memory[i-1]=memory[i];
            memory[i]=a;
            i--;
        }
    }

    //Replaces the least recently used page
    public void replace(int page){
        memory[memory.length-1]=page;
    }
}

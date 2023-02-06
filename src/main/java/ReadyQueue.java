import java.util.ArrayList;

public class ReadyQueue {
    public static ArrayList<PCB> ready = new ArrayList<> (8);
    public static int i = 0;
    public static PCB getTurn() {
        for (; i <= 8; i++) {
            if(i == 8)
                i = 0;
            if (ready.get (i).ioBurst == 0)
                return ready.get (i);
        }
        return null;
    }
    public static void killProcess(PCB pcb) {
        ready.remove (pcb);
        System.out.println (pcb+ " finished");
        pcb = new PCB ();
        System.out.println ("new PCB is created \n"+pcb);
        PCB newPCB = Main.jobQueue.poll ();
        Main.jobQueue.add (pcb);
        ready.add (newPCB);
        System.out.println (pcb +" added to job queue");
        System.out.println (newPCB +" added to ready queue");
    }

    public static void quantumEnd(PCB pcb) {
        ready.remove (pcb);
        pcb.pc = 100;
        ready.add (pcb);
    }
}

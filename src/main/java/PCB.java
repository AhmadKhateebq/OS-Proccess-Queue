import lombok.Data;

@Data
public class PCB {
    private static int idCounter = 0;
    public int id, pc, cpuBurst,ioBurst, r1, r2;
    public PCB(){
        pc = 100;
        this.id = (idCounter++);
        int rand = (int)(Math.random()*100);
        this.cpuBurst =(rand < 10 ? rand+10 : rand);
        this.cpuBurst =(this.cpuBurst == 100 ? 90 : this.cpuBurst);
    }
}

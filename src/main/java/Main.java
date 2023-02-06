import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

public static Queue<PCB> IOqueue = new ArrayDeque<>();
public static Queue<PCB> jobQueue = new ArrayDeque<>();

	public static void initialize() {
		for (int i =0;i<8;i++) {
			ReadyQueue.ready.add (new PCB());
		}
		for (int i =0;i<9;i++) {
			Main.jobQueue.add(new PCB());
		}
	}
	public static void main(String[] args) {
		initialize ();
		Thread CPU = new Thread(()->{
			do {
				PCB pcb = ReadyQueue.getTurn ();
				switch (pcb.pc) {
					case 100: {
						pcb.r1 = 50;
						System.out.println (pcb + " Started");
						pcb.pc = 200;
						pcb.cpuBurst--;
						break;
					}
					case 200: {
						pcb.r2 += 5;
						pcb.pc = 300;
						pcb.cpuBurst--;
						break;
					}
					case 300: {
						pcb.pc = 400;
						pcb.r2++;
						pcb.cpuBurst--;
						break;
					}
					case 400: {
						pcb.r1 = 70;
						pcb.pc = 500;
						pcb.cpuBurst--;
						break;
					}

					case 500: {
						pcb.r2 --;
						pcb.pc = 600;
						pcb.ioBurst = (int) (Math.random () * 5 + 1);
						System.out.println (pcb +
								"added to the IO queue. I/O burst Time is: " + pcb.ioBurst);
						Main.IOqueue.add (pcb);
						break;
					}
					case 600: {
						pcb.r2 += 70;
						System.out.println (pcb + " io is served and return to execution");
						pcb.pc = 700;
						pcb.cpuBurst--;
						break;
					}
					case 700: {
						pcb.r1 += 2;
						pcb.pc = 800;
						pcb.cpuBurst--;
						break;
					}
					case 800: {
						pcb.r2 += 20;
						pcb.pc = 900;
						pcb.cpuBurst--;
						break;
					}
					case 900: {
						pcb.r1 --;
						pcb.pc = 1000;
						pcb.cpuBurst--;
						break;
					}
					case 1000: {
						pcb.r2 ++;
						pcb.cpuBurst--;
						System.out.println (pcb + " Quantum time over");
						ReadyQueue.quantumEnd (pcb);
						break;
					}
				}
				if (pcb.cpuBurst == 0) {
					ReadyQueue.killProcess (pcb);
				}
				try {
					Thread.sleep (500);
				} catch (InterruptedException e) {
					e.printStackTrace ();
				}
			} while (true);
		});

		Thread IO = new Thread(()->{
			while (true) {
				if (!Main.IOqueue.isEmpty()) {
					Main.IOqueue.peek().ioBurst--;
					if (Main.IOqueue.peek().ioBurst == 0) {
						System.out.println(Main.IOqueue.peek() + "finished io and moved back");
						Main.IOqueue.remove (Main.IOqueue.peek());
						Main.IOqueue.poll();
					}
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		CPU.start();
		IO.start();
	}

}

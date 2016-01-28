package core;

import data.global;

public class world implements Runnable {
	global worldData;
	boolean terminated;
	//in msec
	int sleepTime = 1000;
	public world(global data) {
		worldData = data;
	}
	
	
	public void run() {
		long start;
		terminated = false;
		while(!terminated) {
			start = System.nanoTime();
			
			
			
			
			try {
				Thread.sleep(sleepTime - (System.nanoTime() - start) / 1000000);
			} catch (InterruptedException e) {
				//ServerLog.write("WORLD SIMULATION: no time to sleep: wait < 0");
			} catch(IllegalArgumentException ex) {
				//ServerLog.write("gamestate THREAD: CPU too slow");	
			}
		}
		
	}
	public void halt() {
		terminated = true;
	}
	
	
}

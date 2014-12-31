package com.dhanjal.build.Process;

public class ThreadCounter {

	private volatile int counter = 0;
	
	public int getCounter() {
		return counter;
	}

	public synchronized void incrementCounter(){
		this.counter++;
	}
	
	
	public synchronized void decrementCounter(){
		this.counter--;
	}
}

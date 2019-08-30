package net.douglashiura.scenario.plugin.editor.run;

import org.eclipse.debug.core.ILaunch;

public class DefenderWithoutExecution implements Runnable {

	private long TIME_OUT = 5 * 1000;
	private ILaunch launch;
	private Processor processor;
	private Thread thread;

	public DefenderWithoutExecution(ILaunch launch, Processor processor) {
		this.launch = launch;
		this.processor = processor;
		thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.setDaemon(false);
		thread.setName("DefenderWithoutBasicUsJarInProject");
		thread.start();
	}

	public void run() {
		try {
			Thread.sleep(TIME_OUT);
			if (launch.isTerminated()) {
				processor.exitIfSockIsOpen();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void join() throws InterruptedException {
		thread.join();
	}

}

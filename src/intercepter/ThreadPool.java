package intercepter;

import java.util.LinkedList;


/**
 * Intercepter: Intercepter Thread Pool
 * 
 * @author Ghost
 */

public class ThreadPool extends ThreadGroup {

	/**
	 * Indicate the thread pool is closed
	 */

	private boolean isClosed = false;

	/**
	 * Work queue
	 */

	private LinkedList<Runnable> workQueue;

	/**
	 * Thread pool ID
	 */

	private static int threadPoolID;

	/**
	 * Thread ID
	 */

	private int threadID;

	
	
	
	/**
	 * Create a new Thread pool
	 * 
	 * @param poolSize
	 *            The size of working thread
	 */

	public ThreadPool(int poolSize) {
		super("ThreadPool-" + (threadPoolID++));
		setDaemon(true);
		workQueue = new LinkedList<Runnable>();
		for (int i = 0; i < poolSize; i++) {
			new WorkThread().start();
		}
	}

	/**
	 * Add a new Thread work
	 * 
	 * @param task
	 *            The runnable task
	 */

	public synchronized void execute(Runnable task) {

		// Check if the thread pool is closed
		if (isClosed) {
			throw new IllegalStateException();
		}
		if (task != null) {
			workQueue.add(task);
			notify();
		}
	}

	/**
	 * Get a task from queue
	 */

	protected synchronized Runnable getTask() throws InterruptedException {
		while (workQueue.size() == 0) {
			if (isClosed)
				return null;
			wait();
		}
		return workQueue.removeFirst();
	}

	/**
	 * Stores Threads
	 */
	
	private class WorkThread extends Thread {
		public WorkThread() {
			super(ThreadPool.this, "WorkThread-" + (threadID++));
		}

		public void run() {
			while (!isInterrupted()) {
				Runnable task = null;
				try {

					// Get a new task
					task = getTask();
				} catch (InterruptedException e) {

				}

				// End the thread if the next task is null
				if (task == null)
					return;

				try {
					task.run();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

}
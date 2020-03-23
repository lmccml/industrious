

package concurrent;



import concurrent.assitpackage.AbstractTerminatableThread;
import concurrent.assitpackage.TerminationToken;
import concurrent.assitpackage.WorkStealingChannel;
import concurrent.assitpackage.WorkStealingEnabledChannel;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 工作窃取算法示例。 该类使用Two-phase Termination模式（参见第5章）。
 * 
 *
 *
 */
public class WorkStealingExample {
	private final WorkStealingEnabledChannel<String> channel;
	private final TerminationToken token = new TerminationToken();

	public WorkStealingExample() {
		int nCPU = Runtime.getRuntime().availableProcessors();
		int consumerCount = nCPU / 2 + 1;

		@SuppressWarnings("unchecked")
		BlockingDeque<String>[] managedQueues 
												= new LinkedBlockingDeque[consumerCount];

		// 该通道实例对应了多个队列实例managedQueues
		channel = new WorkStealingChannel<String>(managedQueues);

		Consumer[] consumers = new Consumer[consumerCount];
		for (int i = 0; i < consumerCount; i++) {
			managedQueues[i] = new LinkedBlockingDeque<String>();
			consumers[i] = new Consumer(token, managedQueues[i]);

		}

		for (int i = 0; i < nCPU; i++) {
			new Producer().start();
		}

		for (int i = 0; i < consumerCount; i++) {

			consumers[i].start();
		}

	}

	public void doSomething() {

	}

	public static void main(String[] args) throws InterruptedException {
		WorkStealingExample wse;
		wse = new WorkStealingExample();

		wse.doSomething();
		Thread.sleep(3500);

	}

	private class Producer extends AbstractTerminatableThread {
		private int i = 0;

		@Override
		protected void doRun() throws Exception {
			channel.put(String.valueOf(i++));
			token.reservations.incrementAndGet();
		}

	}

	private class Consumer extends AbstractTerminatableThread {
		private final BlockingDeque<String> workQueue;

		public Consumer(TerminationToken token, BlockingDeque<String> workQueue) {
			super(token);
			this.workQueue = workQueue;
		}

		@Override
		protected void doRun() throws Exception {

			/*
			 * WorkStealingEnabledChannel接口的take(BlockingDequepreferedQueue)方法
			 * 实现了工作窃取算法
			 */
			String product = channel.take(workQueue);

			System.out.println("Processing product:" + product);

			// 模拟执行真正操作的时间消耗
			try {
				Thread.sleep(new Random().nextInt(50));
			} catch (InterruptedException e) {
				;
			} finally {
				token.reservations.decrementAndGet();
			}

		}

	}

}

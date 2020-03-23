

package concurrent;

import concurrent.assitpackage.Blocker;
import concurrent.assitpackage.ConditionVarBlocker;
import concurrent.assitpackage.GuardedAction;
import concurrent.assitpackage.Predicate;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

@Slf4j
public class NestedMonitorLockoutExample {

	public static void main(String[] args) {
		final Helper helper = new Helper();
		log.info("Before calling guaredMethod.");

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				String result;
				result = helper.xGuarededMethod("test");
				log.info(result);
			}

		});
		t.start();

		final Timer timer = new Timer();

		// 延迟50ms调用helper.stateChanged方法
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				helper.xStateChanged();
				timer.cancel();
			}

		}, 50, 10);

	}

	private static class Helper {
		private volatile boolean isStateOK = false;
		private final Predicate stateBeOK = new Predicate() {

			@Override
			public boolean evaluate() {
				return isStateOK;
			}

		};

		private final Blocker blocker = new ConditionVarBlocker();

		public synchronized String xGuarededMethod(final String message) {
			GuardedAction<String> ga = new GuardedAction<String>(stateBeOK) {

				@Override
				public String call() throws Exception {
					return message + "->received.";
				}

			};
			String result = null;
			try {
				result = blocker.callWithGuard(ga);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		public synchronized void xStateChanged() {
			try {
				blocker.signalAfter(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						isStateOK = true;
						log.info("state ok.");
						return Boolean.TRUE;
					}

				});
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
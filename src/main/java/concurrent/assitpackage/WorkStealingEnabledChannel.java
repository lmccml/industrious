

package concurrent.assitpackage;

import java.util.concurrent.BlockingDeque;

public interface WorkStealingEnabledChannel<P> extends Channel<P> {
	P take(BlockingDeque<P> preferredQueue) throws InterruptedException;
}

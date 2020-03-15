package book.Java系统性能优化源代码.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class AccessAtomicCount {
	AtomicInteger count = new AtomicInteger();
	public   int  add(){
		return count.incrementAndGet();
	}
	public int getTotal(){
		return count.get();
	}
}

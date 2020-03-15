package book.Java系统性能优化源代码.concurrent;

public class AccessSynchronizedCount {
	int count = 0;
	public  synchronized int add(){
		count++;
		return count;
	}
	public int getTotal(){
		return count;
	}
}

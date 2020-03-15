package book.Java系统性能优化源代码.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 演示了不加锁操作的计数{@code IDSeqHelper}会出现各种问题，
 * @author java系统优化
 */
public class IDSeqHelper {
	static  int a = 0;
  public   static int add(){
    a++;
    return a;
  }

  public static void main(String[] args){
	  /**
	   * 俩个并发线程
	   */
	  CountThread aThread =  new CountThread("a");
	  CountThread bThread =  new CountThread("b");
	  aThread.start();
	  bThread.start();

  }

  static class CountThread extends  Thread{
  	int last = 0;
  	public CountThread(String name){
  		super(name);
	}
  	public void run(){
  		while(true){
			int a = IDSeqHelper.add();
			if(a==last){
				System.out.println("error "+a);
			}
			last = a;

		}
	}
  }

}
